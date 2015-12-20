#define SEPTABLES "\n"

typedef struct s_Table{
    char *nom;
    List *listeColonne;
    List *listeTuple;
}Table;

/**
    Fonction qui permet d'ajouter une table à la fin de la liste de tables passée en paramètre.
    Si la liste de tables passée en paramètre ou le pointeur sur le nom de la table est NULL alors cette fonction ne fait rien.
    Si le type de la liste passée en parametre n'est pas de type TABLE, alors elle ne fait rien non plus.

    param :
        List *listeTables : paramètre de sortie, liste de tables où l'on souhaite ajouter une nouvelle table.
        const char *nomTable : pointeur sur le nom de la nouvelle table, n'est pas modifié.
**/
void addTable(List *listeTables, const char *nomTable);

/**
    Fonction qui permet de savoir si la liste de tables passée en paramètre contient la table de nom du pointeur de caractères passée en paramètre.
    Si le pointeur de liste ou le pointeur sur le nom est NULL, alors cette fonction retourne 0.
    Si le type de la liste passée en paramètre n'est pas de type TABLE alors aussi retourne 0.
    Si la table est présente dans la liste alors retourne 1, sinon 0.

    param :
        const List *listeTables : pointeur sur la liste où l'on souhaite rechercher, n'est pas modifié.
        char *nomTable : pointeur sur la chaine de caractères correspondant au nom de la table recherchée, n'est pas modifié.
**/
int containsTable(const List *listeTables, char *nomTable);

/**
    Fonction qui permet de comparer deux Datavalue de tables par rapport aux noms des tables.
    Retourne 0 si les noms sont identiques, -1 si le premier paramètre est inférieur dans l'ordre alphabétique au deuxième et 1 si l'inverse.

    param :
        const DataValue dv1 : DataValue contenant (et devant contenir) une table à comparée, n'est pas modifiée.
        const DataValue dv2 : DataValue contenant (et devant contenir) une table à comparée, n'est pas modifiée.
**/
int compareTable(const DataValue dv1, const DataValue dv2);

/**
    Fonction qui permet d'afficher une table à partir d'une DataValue de table.
    La DataValue doit contenir une table.

    param :
        DataValue dv : Datavalue contenant une table qui doit être affichée, n'est pas modifiée.
**/
void displayTable(const DataValue dv);

/**
    Fonction qui affiche chaque tables de la liste de tables passée en paramètre.
    Si le pointeur de liste est NULL ou que le type de la liste passée en paramètre est différent de TABLE alors cette fonction ne fait rien.

    param :
        const List *listeTables : pointeur sur une liste de tables devant être affichées, n'est pas modifiée.
**/
void displayAllTables(const List *listeTables);

/**
    Fonction qui permet de retirer une table de la liste de tables passée en paramètre à l'aide du nom passée en paramètre, et retourne 1 si l'opération s'est déroulée correctement, 0 sinon.
    Si le pointeur sur la liste ou le pointeur sur la chaine de cractère contenant le nom de la table à supprimer est NULL. Alors retourne 0.
    Si la liste de table passée en paramètre n'est pas de type TABLE retourne 0.
    Si la table a été trouvée, elle l'a détruit et retourne 1.
    Si la table n'a pas été trouvée, ne fait rien et retourne 0.

    param :
        List *listeTable : paramètre de sortie, pointeur sur la liste de table où l'on souhaite supprimer la table.
        char *nomTable : pointeur sur la chaine de caractère contenant le nom de la table recherchée, n'est pas modifiée.
**/
int removeTable(List *listeTable, char *nomTable);

/**
    Fonction qui permet de récupérer une table en fonction du nom passée en paramètre dans la liste de tables passée en paramètre.
    Retourne NULL si le pointeur de liste ou le pointeur sur le nom est NULL.
    Si le type de la liste n'est pas de type TABLE, retourne NULL.
    Si aucune table correspondant au nom passée en parmètre n'est trouvée alors retourne NULL.
    Si au moins une table correspondante est trouvée alors retourne la première dans la liste.

    param :
        const List *listeTable : pointeur sur la liste de tables dans laquelle chercher, n'est pas modifié.
        char *nomTable : pointeur sur la chaine de caractère contenant le nom de la table recherchée, n'est pas modifié.
**/
Table *getTable(const List *listeTable, char *nomTable);

/**
    Fonction qui permet de détruire une DataValue contenant une Table.

    param :
        DataValue dv : Datavalue qui doit contenir une Table à détruire.
**/
void destroyTable(DataValue dv);

/**
    Fonction qui crée une nouvelle DataValue correspondant à une nouvelle table initialisée et la retourne.
    Si le pointeur sur le nom de la table est NULL retourne une DataValue vide.

    param :
        const char *nomTable : pointeur sur la chaine représentant le nom de la nouvelle table, n'est pas modifié.
**/
DataValue createTable(const char *nomTable);
