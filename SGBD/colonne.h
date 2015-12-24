#define SEPCOL " "

/*typedef struct s_Table t_Table;*/

typedef struct s_Colonne{
    char *nom;
    TypeElement type;
}Colonne;

/**
    Fonction qui permet d'ajouter une colonne, � la fin de la liste de colonnes de la table pass�e en param�tre.
    Si le pointeur sur la table pass�e en param�tre ou le pointeur sur le nom de la colonne est NULL alors cette fonction ne fait rien.
    Le type de la colonne ne peut pas �tre NULLTYPE ou UNKNOWN sinon ne fait rien.

    param :
        Table *table : param�tre de sortie, table contenant la liste de colonnes o� l'on souhaite ajouter une nouvelle colonne.
        const char *nomCol : pointeur sur le nom de la nouvelle colonne, n'est pas modifi�.
        TypeElement type : type des donn�es de la colonne.
**/
void addCol(t_Table *table, const char *nomCol, TypeElement type);

/**
    Fonction qui permet de savoir si la liste de colonnes pass�e en param�tre contient la colonne, identifi�e par le nom pass� en param�tre.
    Si le pointeur de liste ou le pointeur sur le nom est NULL, alors cette fonction retourne 0.
    Si le type de la liste pass�e en param�tre n'est pas de type COLONNE alors aussi retourne 0.
    Si la colonne est pr�sente dans la liste alors retourne 1, sinon 0.

    param :
        const List *listeCol : pointeur sur la liste o� l'on souhaite rechercher, n'est pas modifi�.
        char *nomCol : pointeur sur la chaine de caract�res correspondant au nom de la colonne recherch�e, n'est pas modifi�.
**/
int containsCol(const List *listeCol, char *nomCol);

/**
    Fonction qui permet de comparer deux Datavalue de colonnes par rapport aux noms des colonnes.
    Retourne 0 si les noms sont identiques, -1 si le premier param�tre est inf�rieur dans l'ordre alphab�tique au deuxi�me et 1 si l'inverse.

    param :
        const DataValue dv1 : DataValue contenant (et devant contenir) une colonne � compar�e, n'est pas modifi�e.
        const DataValue dv2 : DataValue contenant (et devant contenir) une colonne � compar�e, n'est pas modifi�e.
**/
int compareCol(const DataValue dv1, const DataValue dv2);

/**
    Fonction qui permet d'afficher une colonne � partir d'une DataValue de colonne.
    La DataValue doit contenir une colonne.

    param :
        DataValue dv : Datavalue contenant une colonne qui doit �tre affich�e, n'est pas modifi�e.
**/
void displayCol(const DataValue dv,FILE *outputFile);

/**
    Fonction qui affiche chaque colonne de la liste de colonnes pass�e en param�tre.
    Si le pointeur de liste est NULL ou que le type de la liste pass�e en param�tre est diff�rent de COLONNE alors cette fonction ne fait rien.

    param :
        const List *listeCol : pointeur sur une liste de colonnes qui doivent �tre affich�es, n'est pas modifi�e.
**/
void displayAllCols(const List *listeCol,FILE *outputFile);

/**
    Fonction qui permet de supprimer une colonne d'une table ainsi que toutes les donn�es des tuples, associ�es � cette colonne.
    Si la table ne contient aucun tuples alors elle supprime simplement la colonne.
    Si la colonne n'est pas dans la table, elle retourne 0.
    Si au moins la colonne a pu �tre supprim�e, retourne 1.
    Si le pointeur sur le nom de la colonne est NULL, retourne 0 et ne fait rien.
    Si le pointeur sur la table est NULL, retourne 0 et ne fait rien.
    Si le nombre d'�l�ments dans la liste de colonnes de la table est �gal � 0, retourne 0 et ne fait rien.

    param :
        t_Table *table : pointeur de table de type Table, �tant la table contenant la colonne � supprimer.
        char *nomCol : pointeur sur la chaine de caract�res contenant le nom de la colonne � supprimer.

**/
int removeCol(t_Table *table, char *nomCol);

/**
    Fonction qui permet de d�truire une DataValue contenant une Colonne.

    param :
        DataValue dv : Datavalue qui doit contenir une Colonne � d�truire.
**/
void destroyCol(DataValue dvCol);

/**
    Fonction qui cr�e une nouvelle DataValue correspondant � une nouvelle colonne initialis�e et la retourne.
    Le type pass�e en param�tre ne peut pas �tre UNKNOWN ou NULLTYPE.
    Si le pointeur sur le nom de la colonne est NULL, retourne une DataValue vide.

    param :
        const char *nomCol : pointeur sur la chaine repr�sentant le nom de la nouvelle colonne, n'est pas modifi�.
        TypeElement type : type correspondant � la colonne.
**/
DataValue createCol(const char *nomCol, TypeElement type);
