#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "myString.h"
#include "global.h"
#include "liste.h"
#include "typeElement.h"
#include "donnee.h"
#include "tuple.h"
#include "colonne.h"
#include "table.h"


void addCol(Table *table, const char *nomCol, TypeElement type, char *defaultValue, TypeElement defaultValueType)
{
    DataValue dvNewCol;

    if(!table || !nomCol || type == UNKNOWN || type == NULLTYPE || defaultValueType == UNKNOWN)return;

    if(!defaultValue)
    	dvNewCol = createCol(nomCol,type);
    else
    	dvNewCol = createCol2(nomCol,type,defaultValue,defaultValueType);

    addAsLast(table->listeColonne,dvNewCol);
    /* si la table contenait d�j� des tuples, on ajoute la donn�e par defaut � chaque tuple */
    if(table->listeTuple->nbElem != 0)
    {
        ListNode *courant = table->listeTuple->first;

        while(courant != NULL)
        {
            DataValue dvDonnee;
            if(!defaultValue || !mystrcasecmp(NULLTYPETOSTR,defaultValue))
            	dvDonnee = createDonnee(NULLTYPETOSTR,NULLTYPE);
            else
            	dvDonnee = createDonnee(defaultValue,defaultValueType);
            addAsLast(courant->valeur.tupl->listeDonnee,dvDonnee);
            courant = courant->suivant;
        }
    }
}

int containsCol(const List *listeCol, char *nomCol)
{
    int res;
    DataValue dvTmpCol;

    if(!listeCol || listeCol->type != COLONNE || !nomCol) return 0;

    dvTmpCol = createCol(nomCol,INT);
    res = isInList(listeCol,compareCol,dvTmpCol);
    destroyCol(dvTmpCol);
    return res;
}

int compareCol(const DataValue dv1,const DataValue dv2)
{
    return mystrcasecmp(dv1.colonne->nom,dv2.colonne->nom);
}

void displayCol(const DataValue dv, FILE *outputFile)
{
    printf("%s(%s)",dv.colonne->nom,getTypeElementToStr(dv.colonne->type));
    if(outputFile)
    	fprintf(outputFile,"%s(%s)",dv.colonne->nom,getTypeElementToStr(dv.colonne->type));
}

void displayColsFromTable(const DataValue dv,FILE *outputFile)
{
	char *prefixe;
	prefixe = (char *)malloc((sizeof(char) * strlen(dv.table->nom)) + (strlen(SEPTABLECOL) * sizeof(char)) + 1);
	prefixe = strcpy(prefixe,dv.table->nom);
	prefixe = strcat(prefixe,SEPTABLECOL);
	displayList2(dv.table->listeColonne,displayCol,SEPCOL,prefixe,outputFile);
	free(prefixe);
}

void displayAllColsFromTables(const List *listeTables, FILE *outputFile)
{
    if(!listeTables || listeTables->type != TABLE) return;

    displayList(listeTables,displayColsFromTable,"",outputFile);
    if(listeTables->nbElem > 0)
    {
        printf("\n");
        if(outputFile)
        	fprintf(outputFile,"\n");
    }
}

int removeCol(Table *table, char *nomCol)
{
    ListNode *tmpLN;
    DataValue dvTmpCol;
    int ind;

    if(!table || table->listeColonne->nbElem <= 0 || !nomCol) return 0;

    dvTmpCol = createCol(nomCol,INT);
    ind = getIndex(table->listeColonne,dvTmpCol,compareCol);
    tmpLN = removeElement(table->listeColonne,compareCol,dvTmpCol);
    destroyCol(dvTmpCol);
    if(tmpLN != NULL)
    {
        destroyCol(tmpLN->valeur);
        free(tmpLN);
        removeDataInTuple(ind,table->listeTuple);
        if(table->listeColonne->nbElem == 0)
        {
            removeAllElements(table->listeTuple,destroyTuple);
        }
        return 1;
    }
    return 0;
}

void destroyCol(DataValue dvCol)
{
    free(dvCol.colonne->nom);
    if(dvCol.colonne->defaultValueType == NULLTYPE || dvCol.colonne->defaultValueType == STR)
    	free(dvCol.colonne->defaultValue.chaine);
    free(dvCol.colonne);
}

DataValue createCol(const char *nomCol, TypeElement type)
{
    DataValue res;
    Colonne *newCol;

    if(type == UNKNOWN || type == NULLTYPE || !nomCol) return res;

    newCol = malloc(sizeof(Colonne));
    testerPointeur(newCol);
    newCol->nom = mystrdup(nomCol);
    newCol->type = type;
    newCol->defaultValue = getValeur(NULLTYPE,"");
    newCol->defaultValueType = NULLTYPE;
    res.colonne = newCol;
    return res;
}

DataValue createCol2(const char *nomCol, TypeElement type, char *defaultValue, TypeElement defaultType)
{
    DataValue res;
    Colonne *newCol;

    if(type == UNKNOWN || type == NULLTYPE || !nomCol) return res;

    newCol = malloc(sizeof(Colonne));
    testerPointeur(newCol);
    newCol->nom = mystrdup(nomCol);
    newCol->type = type;
    newCol->defaultValue = getValeur(defaultType,defaultValue);
    newCol->defaultValueType = defaultType;
    res.colonne = newCol;
    return res;
}
