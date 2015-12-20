#define SEPCOL " "

/*typedef struct s_Table t_Table;*/

typedef struct s_Colonne{
    char *nom;
    TypeElement type;
}Colonne;

/**
    Fonction qui permet d'ajouter une colonne, à la fin de la liste de colonnes de la table passée en paramètre.
    Si le pointeur sur la table passée en paramètre ou le pointeur sur le nom de la colonne est NULL alors cette fonction ne fait rien.
    Le type de la colonne ne peut pas être NULLTYPE ou UNKNOWN sinon ne fait rien.

    param :
        Table *table : paramètre de sortie, table contenant la liste de colonnes où l'on souhaite ajouter une nouvelle colonne.
        const char *nomCol : pointeur sur le nom de la nouvelle colonne, n'est pas modifié.
        TypeElement type : type des données de la colonne.
**/
void addCol(t_Table *table, const char *nomCol, TypeElement type);

/**
    Fonction qui permet de savoir si la liste de colonnes passée en paramètre contient la colonne, identifiée par le nom passé en paramètre.
    Si le pointeur de liste ou le pointeur sur le nom est NULL, alors cette fonction retourne 0.
    Si le type de la liste passée en paramètre n'est pas de type COLONNE alors aussi retourne 0.
    Si la colonne est présente dans la liste alors retourne 1, sinon 0.

    param :
        const List *listeCol : pointeur sur la liste où l'on souhaite rechercher, n'est pas modifié.
        char *nomCol : pointeur sur la chaine de caractères correspondant au nom de la colonne recherchée, n'est pas modifié.
**/
int containsCol(const List *listeCol, char *nomCol);

/**
    Fonction qui permet de comparer deux Datavalue de colonnes par rapport aux noms des colonnes.
    Retourne 0 si les noms sont identiques, -1 si le premier paramètre est inférieur dans l'ordre alphabétique au deuxième et 1 si l'inverse.

    param :
        const DataValue dv1 : DataValue contenant (et devant contenir) une colonne à comparée, n'est pas modifiée.
        const DataValue dv2 : DataValue contenant (et devant contenir) une colonne à comparée, n'est pas modifiée.
**/
int compareCol(const DataValue dv1, const DataValue dv2);

/**
    Fonction qui permet d'afficher une colonne à partir d'une DataValue de colonne.
    La DataValue doit contenir une colonne.

    param :
        DataValue dv : Datavalue contenant une colonne qui doit être affichée, n'est pas modifiée.
**/
void displayCol(const DataValue dv);

/**
    Fonction qui affiche chaque colonne de la liste de colonnes passée en paramètre.
    Si le pointeur de liste est NULL ou que le type de la liste passée en paramètre est différent de COLONNE alors cette fonction ne fait rien.

    param :
        const List *listeCol : pointeur sur une liste de colonnes qui doivent être affichées, n'est pas modifiée.
**/
void displayAllCols(const List *listeCol);

/**
    Fonction qui permet de supprimer une colonne d'une table ainsi que toutes les données des tuples, associées à cette colonne.
    Si la table ne contient aucun tuples alors elle supprime simplement la colonne.
    Si la colonne n'est pas dans la table, elle retourne 0.
    Si au moins la colonne a pu être supprimée, retourne 1.
    Si le pointeur sur le nom de la colonne est NULL, retourne 0 et ne fait rien.
    Si le pointeur sur la table est NULL, retourne 0 et ne fait rien.
    Si le nombre d'éléments dans la liste de colonnes de la table est égal à 0, retourne 0 et ne fait rien.

    param :
        t_Table *table : pointeur de table de type Table, étant la table contenant la colonne à supprimer.
        char *nomCol : pointeur sur la chaine de caractères contenant le nom de la colonne à supprimer.

**/
int removeCol(t_Table *table, char *nomCol);

/**
    Fonction qui permet de détruire une DataValue contenant une Colonne.

    param :
        DataValue dv : Datavalue qui doit contenir une Colonne à détruire.
**/
void destroyCol(DataValue dvCol);

/**
    Fonction qui crée une nouvelle DataValue correspondant à une nouvelle colonne initialisée et la retourne.
    Le type passée en paramètre ne peut pas être UNKNOWN ou NULLTYPE.
    Si le pointeur sur le nom de la colonne est NULL, retourne une DataValue vide.

    param :
        const char *nomCol : pointeur sur la chaine représentant le nom de la nouvelle colonne, n'est pas modifié.
        TypeElement type : type correspondant à la colonne.
**/
DataValue createCol(const char *nomCol, TypeElement type);
