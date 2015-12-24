#define SEPTABLES "\n"

typedef struct s_Table{
    char *nom;
    List *listeColonne;
    List *listeTuple;
}Table;

/**
    Fonction qui permet d'ajouter une table � la fin de la liste de tables pass�e en param�tre.
    Si la liste de tables pass�e en param�tre ou le pointeur sur le nom de la table est NULL alors cette fonction ne fait rien.
    Si le type de la liste pass�e en parametre n'est pas de type TABLE, alors elle ne fait rien non plus.

    param :
        List *listeTables : param�tre de sortie, liste de tables o� l'on souhaite ajouter une nouvelle table.
        const char *nomTable : pointeur sur le nom de la nouvelle table, n'est pas modifi�.
**/
void addTable(List *listeTables, const char *nomTable);

/**
    Fonction qui permet de savoir si la liste de tables pass�e en param�tre contient la table de nom du pointeur de caract�res pass�e en param�tre.
    Si le pointeur de liste ou le pointeur sur le nom est NULL, alors cette fonction retourne 0.
    Si le type de la liste pass�e en param�tre n'est pas de type TABLE alors aussi retourne 0.
    Si la table est pr�sente dans la liste alors retourne 1, sinon 0.

    param :
        const List *listeTables : pointeur sur la liste o� l'on souhaite rechercher, n'est pas modifi�.
        char *nomTable : pointeur sur la chaine de caract�res correspondant au nom de la table recherch�e, n'est pas modifi�.
**/
int containsTable(const List *listeTables, char *nomTable);

/**
    Fonction qui permet de comparer deux Datavalue de tables par rapport aux noms des tables.
    Retourne 0 si les noms sont identiques, -1 si le premier param�tre est inf�rieur dans l'ordre alphab�tique au deuxi�me et 1 si l'inverse.

    param :
        const DataValue dv1 : DataValue contenant (et devant contenir) une table � compar�e, n'est pas modifi�e.
        const DataValue dv2 : DataValue contenant (et devant contenir) une table � compar�e, n'est pas modifi�e.
**/
int compareTable(const DataValue dv1, const DataValue dv2);

/**
    Fonction qui permet d'afficher une table � partir d'une DataValue de table.
    La DataValue doit contenir une table.

    param :
        DataValue dv : Datavalue contenant une table qui doit �tre affich�e, n'est pas modifi�e.
**/
void displayTable(const DataValue dv,FILE *outputFile);

/**
    Fonction qui affiche chaque tables de la liste de tables pass�e en param�tre.
    Si le pointeur de liste est NULL ou que le type de la liste pass�e en param�tre est diff�rent de TABLE alors cette fonction ne fait rien.

    param :
        const List *listeTables : pointeur sur une liste de tables devant �tre affich�es, n'est pas modifi�e.
**/
void displayAllTables(const List *listeTables,FILE *outputFile);

/**
    Fonction qui permet de retirer une table de la liste de tables pass�e en param�tre � l'aide du nom pass�e en param�tre, et retourne 1 si l'op�ration s'est d�roul�e correctement, 0 sinon.
    Si le pointeur sur la liste ou le pointeur sur la chaine de cract�re contenant le nom de la table � supprimer est NULL. Alors retourne 0.
    Si la liste de table pass�e en param�tre n'est pas de type TABLE retourne 0.
    Si la table a �t� trouv�e, elle l'a d�truit et retourne 1.
    Si la table n'a pas �t� trouv�e, ne fait rien et retourne 0.

    param :
        List *listeTable : param�tre de sortie, pointeur sur la liste de table o� l'on souhaite supprimer la table.
        char *nomTable : pointeur sur la chaine de caract�re contenant le nom de la table recherch�e, n'est pas modifi�e.
**/
int removeTable(List *listeTable, char *nomTable);

/**
    Fonction qui permet de r�cup�rer une table en fonction du nom pass�e en param�tre dans la liste de tables pass�e en param�tre.
    Retourne NULL si le pointeur de liste ou le pointeur sur le nom est NULL.
    Si le type de la liste n'est pas de type TABLE, retourne NULL.
    Si aucune table correspondant au nom pass�e en parm�tre n'est trouv�e alors retourne NULL.
    Si au moins une table correspondante est trouv�e alors retourne la premi�re dans la liste.

    param :
        const List *listeTable : pointeur sur la liste de tables dans laquelle chercher, n'est pas modifi�.
        char *nomTable : pointeur sur la chaine de caract�re contenant le nom de la table recherch�e, n'est pas modifi�.
**/
Table *getTable(const List *listeTable, char *nomTable);

/**
    Fonction qui permet de d�truire une DataValue contenant une Table.

    param :
        DataValue dv : Datavalue qui doit contenir une Table � d�truire.
**/
void destroyTable(DataValue dv);

/**
    Fonction qui cr�e une nouvelle DataValue correspondant � une nouvelle table initialis�e et la retourne.
    Si le pointeur sur le nom de la table est NULL retourne une DataValue vide.

    param :
        const char *nomTable : pointeur sur la chaine repr�sentant le nom de la nouvelle table, n'est pas modifi�.
**/
DataValue createTable(const char *nomTable);
