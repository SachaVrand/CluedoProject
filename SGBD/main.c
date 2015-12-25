#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include "myString.h"
#include "global.h"
#include "liste.h"
#include "typeElement.h"
#include "donnee.h"
#include "tuple.h"
#include "colonne.h"
#include "table.h"
#define MAXCARACSAISIE 512
#define MOTCLELSTCOLS "COLS"
#define MOTCLEAJOUT "ADD"
#define MOTCLEAFF "DISP"
#define MOTCLESUPPR "DEL"
#define MOTCLEEXIT "QUIT"
#define MOTCLETABLE "TABLE"
#define MOTCLETUPLE "TUPLE"
#define MOTCLECOL "COL"
#define MOTCLELSTTABLES "TABLES"
#define MOTCLELSTTUPLES "TUPLES"
#define ERRUK "UNKNOWN_KEYWORD"
#define ERRTMW "TOO_MUCH_WORDS"
#define ERRNEW "NOT_ENOUGH_WORDS"
#define ERRUT "UNKNOWN_TABLE"
#define ERRUC "UNKOWN_COL"
#define ERRUTYPE "UNKNOWN_TYPE"
#define ERRET "EXISTING_TABLE"
#define ERREC "EXISTING_COL"
#define ERRCC "CANNOT_CAST"
#define ERRTMP "TOO_MUCH_PARAMS"
#define ERRNEP "NOT_ENOUGH_PARAMS"
#define ERRNCIT "NO_COL_IN_TABLE"
#define ERRNAVN "NOT_A_VALID_NAME"
#define ERRUOPT "UNKNOWN_OPTION"
#define ERRINPUT "ERR_INPUT_FILE"
#define ERROUTPUT "ERR_OUTPUT_FILE"
#define OPTINPUT "-i"
#define OPTOUTPUT "-o"


static char *str_strip (const char *string);
static char **str_split(const char *string, char delimiter, int *nbMots);
static void viderBuffer();
static int lireLigneES(char *chaineDest, int n);
static int isAValidName(const char *nom);
static int doCommand(char *commande, List *listeTables, int displayed, FILE *outputFile);
static void interpretationClavier(List *listeTables, FILE *outputFile);
static void interpretationFichier(List *listeTables, FILE *fichier);
static void myPrintString(FILE *outputFile, int nbArg, ...);

int main(int argc, char **argv)
{
    List *listeTables;
    FILE *outputFile = NULL;
    int alreadyOne = 0;

    listeTables = newList(TABLE);

    for(argv++;*argv;argv++)
    {
    	if(strcmp(*argv,OPTINPUT) == 0)
    	{
    		argv++;
    		if(!(*argv))
    		{
    			printf("%s\n",ERRINPUT);
    			break;
    		}
    		else if(strcmp(*argv,OPTINPUT) == 0 || strcmp(*argv, OPTOUTPUT) == 0)
    		{
    			argv--;
    			printf("%s\n",ERRINPUT);
    			continue;
    		}
    		else
    		{
    			FILE *flot;
    			if((flot = fopen(*argv,"r")) == NULL)
    			{
    				printf("%s\n",ERRINPUT);
    			}
    			else
    			{
    				interpretationFichier(listeTables,flot);
    				fclose(flot);
    			}
    		}
    	}
    	else if(strcmp(*argv, OPTOUTPUT) == 0)
    	{
    		argv++;
			if(!(*argv))
			{
				printf("%s\n",ERROUTPUT);
				break;
			}
			else if(strcmp(*argv,OPTINPUT) == 0 || strcmp(*argv, OPTOUTPUT) == 0)
			{
				argv--;
				printf("%s\n",ERROUTPUT);
				continue;
			}
			else
			{
				if(alreadyOne)
				{
					printf("%s\n",ERROUTPUT);
				}
				/* test si le fichier existe */
				else if((outputFile = fopen(*argv,"r")) != NULL)
				{
					fclose(outputFile);
					outputFile = NULL;
					printf("%s\n",ERROUTPUT);
				}
				else if((outputFile = fopen(*argv,"w+")) == NULL)
				{
					printf("%s\n",ERROUTPUT);
				}
				else if(outputFile)
				{
					alreadyOne = 1;
				}
			}
    	}
    	else
    	{
    		printf("ERR : %s %s\n",ERRUOPT, *argv);
    	}
    }

    interpretationClavier(listeTables,outputFile);

    destroyList(listeTables,destroyTable);
    if(outputFile)
    {
    	fclose(outputFile);
    }
    return 0;
}

/**
M�thode qui decoupe une chaine de caracteres en sous chaines en fonction du delimiteur pass�e en param�tre. Ne d�coupe pas les chaines entre "".
Retourne un tableau contenant les sous chaines. Retourne NULL si string=NULL ou nbMots=NULL.
Param :
    const char *string : chaine de caract�res � d�couper.
    char delimiter : delimiteur pour d�couper les sous chaines.
    int *nbMots : pointeur d'un entier qui est mis � z�ro au d�but de la fonction et qui est mis au nombre de sous chaines contenu dans le tableau retourn�.

ex :
    delimiteur = ' ', *string = add table table1 -> res[0]=add, res[1]=table, res[2]=table1, *nbMots=3
    delimiteur = ' ', *string = add table "table  1 " -> res[0]=add, res[1]=table, res[2]="table  1 ", *nbMots=3
    delimiteur = ' ', *string = (vide) -> res[0]->'\0', *nbMots=1
    delimiteur = ' ', *string = ' ' -> res[0]->'\0', res[1]='\0', *nbMots=2
**/
static char **str_split(const char *string, char delimiter, int *nbMots)
{
    char **tab = malloc(sizeof(char*));
    int indiceDebMot = 0;
    int i = 0;
    int chaine = 0;
    *nbMots = 0;

    testerPointeur(tab);
    if(string == NULL || nbMots == NULL)
        return NULL;

    do
    {
        if(string[i] == '"')
        {
            if(chaine)
                chaine = 0;
            else
                chaine = 1;
        }
        else if((string[i] == delimiter && !chaine )|| string[i] == '\0')
        {
            char **tmp;
            char *mot = malloc(sizeof(char) * (i - indiceDebMot + 1));
            testerPointeur(mot);
            strncpy(mot,string + indiceDebMot,i-indiceDebMot);
            (*nbMots)++;
            mot[i-indiceDebMot] = '\0';
            tmp = realloc(tab,sizeof(char*)*(*nbMots));
            testerPointeur(tmp);
            tab = tmp;
            tab[*nbMots - 1] = mot;
            indiceDebMot = i + 1;
        }
    }while(string[i++]);

    return tab;
}

/**
Fonction qui retourne une nouvelle chaine de caract�res correspondant � la chaine pass�e en param�tre sans les espace inutiles,
c'est � dire avec aucun espace au d�but et � la fin de la chaine, et un seul espace entre chaque mots.
N'enl�ve pas les espaces inutiles pour les caract�res entre "".
Retourne NULL si string=NULL.
Param :
    const char *string : chaine dont vous souhaitez enlever les espaces inutiles.

ex :
    *string = '  add table    table1  ' -> *res = 'add table table1'
    *string = '  add table  "  table1  "' -> *res = 'add table "  table1  "'
**/
static char *str_strip (const char *string)
{
   char *res = NULL;

   if (string != NULL)
   {
        int i, j;
        int espace = 0;
        int chaine = 0;
        res = malloc(sizeof(char) * (strlen(string) + 1));

        testerPointeur(res);

        for (i = 0, j = 0; string[i]; i++)
        {
            if (string[i] == ' ' && !chaine)
            {
               if (!espace)
               {
                  res[j] = string[i];
                  espace = 1;
                  j++;
               }
            }
            else if(string[i] == '"')
            {
                if(chaine)
                {
                    chaine = 0;
                }
                else
                {
                    chaine = 1;
                }
                res[j] = string[i];
                j++;
                espace = 0;
            }
            else
            {
               res[j] = string[i];
               espace = 0;
               j++;
            }
         }
         if(j != 0 && res[j-1] == ' ')
            res[j-1] = '\0';
         else
            res[j] = '\0';
         if(*res == ' ')
         {
             char *tmp = malloc(sizeof(char) * strlen(res));
             tmp = strcpy(tmp,res + 1);
             free(res);
             res = tmp;
         }
   }
   return res;
}

/**
Fonction qui vide le buffer.
**/
static void viderBuffer()
{
    int c = 0;
    while (c != '\n' && c != EOF)
    {
        c = getchar();
    }
}

/**
M�thode qui r�cup�re la saisie d'une ligne sur l'entr�e standard.
Retourne 0 si la saisie s'est pass�e correctement, 1 si le nombre de caract�res saisies d�pasent la limite n
et 2 si il y a eu une erreur lors de la lecture de la ligne.

Param :
    *chaineDest : chaine o� sera mis ce qu'a saisie l'utlisateur.
    n : taille maximale de la chaineDest.
**/
static int lireLigneES(char *chaineDest, int n)
{
    char *positionEntree = NULL;

    if (fgets(chaineDest, n, stdin) != NULL)
    {
        positionEntree = strchr(chaineDest, '\n');
        if (positionEntree != NULL)
        {
            *positionEntree = '\0';
            return 0;
        }
        else
        {
            viderBuffer();
            return 1;
        }
    }
    else
    {
        viderBuffer();
        return 2;
    }
}

/**
    Fonction qui v�rifie si une chaine est un nom valide pour une table ou une colonne.
    On ne peut faire usage que des 26 lettres de l'alphabet, des dix chiffres et du caract�re underscore pour un nom valide.

    param :
        const char *nom : chaine que l'on souhaite v�rifi�e, n'est pas modifi�e.
**/
static int isAValidName(const char *nom)
{
    int i,size = strlen(nom);
    for(i = 0 ; i < size ; i++)
    {
        if( !((nom[i] >= '0' && nom[i] <='9') || (nom[i] >='A' && nom[i] <= 'Z') || (nom[i] >='a' && nom[i] <= 'z') || nom[i] == '_') )
            return 0;
    }
    return 1;
}

static int doCommand(char *commande, List *listeTables, int displayed, FILE *outputFile)
{
	char *commandeSansEspacesInutiles = NULL;
	char **tableauDeMots = NULL;
	int nbMots,i,res = 0;

	if(!commande || !listeTables || listeTables->type != TABLE) return 0;

	/*Decouper commande*/
	commandeSansEspacesInutiles = str_strip(commande);
	tableauDeMots = str_split(commandeSansEspacesInutiles,' ',&nbMots);

	/*Tester commande*/
	/*ADD*/
	if(mystrcasecmp(MOTCLEAJOUT,tableauDeMots[0]) == 0)
	{
		if(nbMots <= 1)
		{
			/*NOT ENOUGH WORDS*/
			if(displayed)
			{
				myPrintString(outputFile,2,"ERR : ",ERRNEW);
			}
		}
		else
		{
			/*TABLE*/
			if(mystrcasecmp(MOTCLETABLE,tableauDeMots[1]) == 0)
			{
				if(nbMots < 3)
				{
					/*NOT ENOUGH WORDS*/
					if(displayed)
						myPrintString(outputFile,2,"ERR : ",ERRNEW);
				}
				else if(nbMots > 3)
				{
					/*TOO MUCH WORDS*/
					if(displayed)
						myPrintString(outputFile,2,"ERR : ",ERRTMW);
				}
				else
				{
					if(!isAValidName(tableauDeMots[2]))
					{
						/*NOT A VALID NAME*/
						if(displayed)
							myPrintString(outputFile,4,"ERR : ",ERRNAVN," ",tableauDeMots[2]);
					}
					else if(containsTable(listeTables,tableauDeMots[2]))
					{
						/*EXISTING TABLE*/
						if(displayed)
							myPrintString(outputFile,4,"ERR : ",ERRET," ",tableauDeMots[2]);
					}
					else
					{
						addTable(listeTables,tableauDeMots[2]);
						if(displayed)
							myPrintString(outputFile,1,"OK");
					}
				}
			}
			/*COL*/
			else if(mystrcasecmp(MOTCLECOL,tableauDeMots[1]) == 0)
			{
				if(nbMots < 5)
				{
					/*NOT ENOUGH WORDS*/
					if(displayed)
						myPrintString(outputFile,2,"ERR : ",ERRNEW);
				}
				else if(nbMots > 5)
				{
					/*TOO MUCH WORDS*/
					if(displayed)
						myPrintString(outputFile,2,"ERR : ",ERRTMW);
				}
				else
				{
					if(containsTable(listeTables,tableauDeMots[2]))
					{
						Table *tmpTable = getTable(listeTables,tableauDeMots[2]);
						if(!isAValidName(tableauDeMots[3]))
						{
							/*NOT A VALID NAME*/
							if(displayed)
								myPrintString(outputFile,4,"ERR : ",ERRNAVN," ",tableauDeMots[3]);
						}
						else if(!containsCol(tmpTable->listeColonne,tableauDeMots[3]))
						{
							TypeElement tmpType = getTypeElementFromStr(tableauDeMots[4]);
							if(tmpType != UNKNOWN && tmpType != NULLTYPE)
							{
								addCol(tmpTable,tableauDeMots[3],tmpType);
								if(displayed)
									myPrintString(outputFile,1,"OK");
							}
							else
							{
								/*UNKNOWN TYPE*/
								if(displayed)
									myPrintString(outputFile,4,"ERR : ",ERRUTYPE," ",tableauDeMots[4]);
							}
						}
						else
						{
							/*EXISTING COL*/
							if(displayed)
								myPrintString(outputFile,4,"ERR : ",ERREC," ",tableauDeMots[3]);
						}
					}
					else
					{
						/*UNKNOWN_TABLE*/
						if(displayed)
							myPrintString(outputFile,4,"ERR : ",ERRUT," ",tableauDeMots[2]);
					}
				}
			}
			/*TUPLE*/
			else if(mystrcasecmp(MOTCLETUPLE,tableauDeMots[1]) == 0)
			{
				if(nbMots < 3)
				{
					/*NOT ENOUGH WORDS*/
					if(displayed)
						myPrintString(outputFile,2,"ERR : ",ERRNEW);
				}
				else
				{
					if(containsTable(listeTables,tableauDeMots[2]))
					{
						Table *tmp = getTable(listeTables,tableauDeMots[2]);
						if(tmp->listeColonne->nbElem == 0)
						{
							/*NO COL IN TABLE*/
							if(displayed)
								myPrintString(outputFile,4,"ERR : ",ERRNCIT," ",tableauDeMots[2]);
						}
						else if(((nbMots)-3) < (tmp->listeColonne->nbElem))
						{
							/*NOT ENOUGH PARAMS*/
							if(displayed)
								myPrintString(outputFile,2,"ERR : ",ERRNEP);
						}
						else if(((nbMots)-3) > (tmp->listeColonne->nbElem))
						{
							/*TOO MUCH PARAMS*/
							if(displayed)
								myPrintString(outputFile,2,"ERR : ",ERRTMP);
						}
						else
						{
							/*tester le type de chaque donn�es par rapport � sa colonne*/
							TypeElement tmpType = UNKNOWN;
							char *tmpErrDonnee = conversionsPossibles(tmp->listeColonne,tableauDeMots + 3, &tmpType);
							if(tmpErrDonnee != NULL)
							{
								/*CANNOT CAST*/
								if(displayed)
									myPrintString(outputFile,6,"ERR : ",ERRCC," ",tmpErrDonnee," to ",getTypeElementToStr(tmpType));
							}
							else
							{
								if(tmpType == UNKNOWN)
								{
									if(displayed)
										printf("Ce n'est pas cens� arriv�!\n");
								}
								else
								{
									/*ajouter tuple*/
									addTuple(tmp->listeTuple,tableauDeMots + 3,tmp->listeColonne);
									if(displayed)
										myPrintString(outputFile,1,"OK");
								}
							}
						}
					}
					else
					{
						/*UNKNOWN TABLE*/
						if(displayed)
							myPrintString(outputFile,4,"ERR : ",ERRUT," ",tableauDeMots[2]);
					}
				}
			}
			else
			{
				/*UNKNOWN_KEYWORD*/
				if(displayed)
					myPrintString(outputFile,4,"ERR : ",ERRUK," ",tableauDeMots[1]);
			}
		}
	}
	/*DISP*/
	else if(mystrcasecmp(MOTCLEAFF,tableauDeMots[0]) == 0 && displayed)
	{
		if(nbMots <= 1)
		{
			/*NOT ENOUGH WORDS*/
			myPrintString(outputFile,2,"ERR : ",ERRNEW);
		}
		/*TABLES*/
		else if(mystrcasecmp(MOTCLELSTTABLES,tableauDeMots[1]) == 0)
		{
			if(nbMots > 2)
			{
				/*TOO MUCH WORDS*/
				myPrintString(outputFile,2,"ERR : ",ERRTMW);
			}
			else
			{
				/*afficher tables*/
				displayAllTables(listeTables,outputFile);
			}
		}
		/*COLS*/
		else if(mystrcasecmp(MOTCLELSTCOLS,tableauDeMots[1]) == 0)
		{
			if(nbMots <= 2)
			{
				/*NOT ENOUGH WORDS*/
				myPrintString(outputFile,2,"ERR : ",ERRNEW);
			}
			else if(nbMots > 3)
			{
				/*TOO MUCH WORDS*/
				myPrintString(outputFile,2,"ERR : ",ERRTMW);
			}
			else
			{
				if(containsTable(listeTables,tableauDeMots[2]))
				{
					displayAllCols(getTable(listeTables,tableauDeMots[2])->listeColonne, outputFile);
				}
				else
				{
					/*UNKNOWN TABLE*/
					myPrintString(outputFile,4,"ERR : ",ERRUT," ",tableauDeMots[2]);
				}
			}
		}
		/*TUPLES*/
		else if(mystrcasecmp(MOTCLELSTTUPLES,tableauDeMots[1]) == 0)
		{
			if(nbMots <= 2)
			{
				/*NOT ENOUGH WORDS*/
				myPrintString(outputFile,2,"ERR : ",ERRNEW);
			}
			else if(nbMots > 3)
			{
				/*TOO MUCH WORDS*/
				myPrintString(outputFile,2,"ERR : ",ERRTMW);
			}
			else
			{
				if(containsTable(listeTables,tableauDeMots[2]))
				{
					Table *tmp = getTable(listeTables,tableauDeMots[2]);
					displayAllTuples(tmp->listeTuple, outputFile);
				}
				else
				{
					myPrintString(outputFile,4,"ERR : ",ERRUT," ",tableauDeMots[2]);
				}
			}
		}
		else
		{
			/*UNKNOWN_KEYWORD*/
			myPrintString(outputFile,4,"ERR : ",ERRUK," ",tableauDeMots[1]);
		}
	}
	/*DEL*/
	else if(mystrcasecmp(MOTCLESUPPR,tableauDeMots[0]) == 0)
	{
		if(nbMots <= 1)
		{
			/*NOT ENOUGH WORDS*/
			if(displayed)
				myPrintString(outputFile,2,"ERR : ",ERRNEW);
		}
		/*TABLE*/
		else if(mystrcasecmp(MOTCLETABLE,tableauDeMots[1]) == 0)
		{
			if(nbMots < 3)
			{
				/*NOT ENOUGH WORDS*/
				if(displayed)
					myPrintString(outputFile,2,"ERR : ",ERRNEW);
			}
			else if(nbMots > 3)
			{
				/*TOO MUCH WORDS*/
				if(displayed)
					myPrintString(outputFile,2,"ERR : ",ERRTMW);
			}
			else
			{
				if(containsTable(listeTables,tableauDeMots[2]))
				{
					/*del table*/
					removeTable(listeTables,tableauDeMots[2]);
					if(displayed)
						myPrintString(outputFile,1,"OK");
				}
				else
				{
					/*UNKNOWN TABLE*/
					if(displayed)
						myPrintString(outputFile,4,"ERR : ",ERRUT," ",tableauDeMots[2]);
				}
			}
		}
		/*COL*/
		else if(mystrcasecmp(MOTCLECOL,tableauDeMots[1]) == 0)
		{
			if(nbMots < 4)
			{
				/*NOT ENOUGH WORDS*/
				if(displayed)
					myPrintString(outputFile,2,"ERR : ",ERRNEW);
			}
			else if(nbMots > 4)
			{
				/*TOO MUCH WORDS*/
				if(displayed)
					myPrintString(outputFile,2,"ERR : ",ERRTMW);
			}
			else
			{
				if(containsTable(listeTables,tableauDeMots[2]))
				{
					Table *tmp = getTable(listeTables,tableauDeMots[2]);
					if(containsCol(tmp->listeColonne,tableauDeMots[3]))
					{
						removeCol(tmp,tableauDeMots[3]);
						if(displayed)
							myPrintString(outputFile,1,"OK");
					}
					else
					{
						/*UNKNOWN COL*/
						if(displayed)
							myPrintString(outputFile,4,"ERR : ",ERRUC," ",tableauDeMots[3]);
					}
				}
				else
				{
					/*UNKNOWN TABLE*/
					if(displayed)
						myPrintString(outputFile,4,"ERR : ",ERRUT," ",tableauDeMots[2]);
				}
			}
		}
		else
		{
			/*UNKNOWN_KEYWORD*/
			if(displayed)
				myPrintString(outputFile,4,"ERR : ",ERRUK," ",tableauDeMots[1]);
		}
	}
	else if(mystrcasecmp(tableauDeMots[0],MOTCLEEXIT) == 0)
	{
		if(nbMots > 1)
		{
			/*TOO MUCH WORDS*/
			if(displayed)
				myPrintString(outputFile,2,"ERR : ",ERRTMW);
		}
		else
		{
			if(displayed)
				myPrintString(outputFile,1,"OK");
			res = 1;
		}
	}
	else
	{
		/*UNKNOWN KEYWORD */
		if(displayed)
			myPrintString(outputFile,4,"ERR : ",ERRUK," ",tableauDeMots[0]);
	}

	/*Liberer la mémoire allouer*/
	if(commandeSansEspacesInutiles != NULL)
	{
		free(commandeSansEspacesInutiles);
	}
	if(tableauDeMots != NULL)
	{
		for(i=0;i<nbMots;i++)free(tableauDeMots[i]);
		free(tableauDeMots);
	}
	return res;
}

static void interpretationClavier(List *listeTables, FILE *outputFile)
{
	char *commande;
	int k,erreurSaisie;

	if(!listeTables || listeTables->type != TABLE) return;

    do
	{
		printf("=> ");
		if(outputFile)
			fprintf(outputFile,"=> ");
		commande = malloc(sizeof(char) * MAXCARACSAISIE);
		testerPointeur(commande);
		k = lireLigneES(commande,MAXCARACSAISIE);
		if(outputFile)
			fprintf(outputFile,"%s\n",commande);
		erreurSaisie = 0;
		if(k == 2)
		{
			erreurSaisie = 1;
			printf("Erreur lors de la saisie.\n");
			if(outputFile)
				fprintf(outputFile,"Erreur lors de la saisie.\n");
			free(commande);
			continue;
		}
		else if(k == 1)
		{
			erreurSaisie = 1;
			printf("Erreur : la saisie est limitee a %d caracteres.\n",MAXCARACSAISIE);
			if(outputFile)
				fprintf(outputFile,"Erreur : la saisie est limitee a %d caracteres.\n");
			free(commande);
			continue;
		}

		k = doCommand(commande,listeTables,1,outputFile);
		free(commande);

	}while(erreurSaisie || !k);
}

static void interpretationFichier(List *listeTables, FILE *fichier)
{
	char *commande,*positionEntree;
	int k = 0;

	if(!listeTables || listeTables->type != TABLE || !fichier) return;

	commande = (char *)malloc(sizeof(char) * MAXCARACSAISIE);
	testerPointeur(commande);

	while(!k && fgets(commande, MAXCARACSAISIE, fichier))
	{
		positionEntree = strchr(commande, '\n');
		if (positionEntree != NULL)
		{
			*positionEntree = '\0';
		}

		k = doCommand(commande,listeTables,0, NULL);

		free(commande);
		commande = (char *)malloc(sizeof(char) * MAXCARACSAISIE);
		testerPointeur(commande);
	}
	free(commande);
}

static void myPrintString(FILE *outputFile, int nbArg, ...)
{
	int i;
	va_list arg;
	char *tmp;

	if(nbArg < 1) return;

	va_start(arg,nbArg);
	for(i = 0; i < nbArg; i++)
	{
		tmp = va_arg(arg,char *);
		printf("%s", tmp);
		if(outputFile)
			fprintf(outputFile,"%s",tmp);
	}
	va_end(arg);

	printf("\n");
	if(outputFile)
		fprintf(outputFile,"\n");
}
