#define SEPTUPLES "\n"
#define SEPDONNEETUPLE " "

typedef struct s_Tuple{
    List *listeDonnee;
}Tuple;

/**
    Fonction qui permet d'ajouter un tuple à la liste de tuples passée en paramètre.
    Chaque élément du tableau doit être transformable dans le type de sa colonne associée.
    La nombre d'élément dans le tableau doit être égal au nombre de colonnes dans la liste. Sinon on parcourt le tableau par rapport au nombre de colonnes.
    Si le tableau possède moins d'éléments que le nombre de colonnes, alors il essayera d'accèder à des zones mémoires interdites.
    Si la liste de colonnes ou la liste de tuples est NULL, ne fait rien.
    Si la listeTuple n'est pas de type TUPLE ou la listeColonne n'est pas de type COLONNE, ne fait rien.
    Si le pointeur sur le tableau de données est NULL ou que l'une des données du tableau est NULL, ne fait rien.

    param :
        List *listeTuple : pointeur sur la liste de tuples où l'on souhaite ajouter le nouveau tuples.
        char **tableauDonnees : tableau de chaine de caractères représentant les données du nouveau tuple, n'est pas modifiée.
        const List *listeColonne : pointeur sur la liste des colonnes associées au nouveau tuple, n'est pas modifiée.
**/
void addTuple(List *listeTuple,char **tableauDonnees,const List *listeColonne);

/**
    Fonction qui permet de détruire une DataValue contenant un Tuple.

    param :
        DataValue dv : Datavalue qui doit contenir un Tuple à détruire.
**/
void destroyTuple(DataValue dv);

/**
    Fonction qui affiche chaque tuple de la liste de tuples passée en paramètre.
    Si le pointeur de liste est NULL ou que le type de la liste passée en paramètre est différent de TUPLE alors cette fonction ne fait rien.

    param :
        const List *listeTuple : pointeur sur une liste de tuples devant être affichées, n'est pas modifiée.
**/
void displayTuple(const DataValue dv);

/**
    Fonction qui affiche chaque tuple de la liste de tuples passée en paramètre.
    Si le pointeur de liste est NULL ou que le type de la liste passée en paramètre est différent de TUPLE alors cette fonction ne fait rien.

    param :
        const List *listeTuple : pointeur sur une liste de tuples qui doivent être affichés, n'est pas modifiée.
**/
void displayAllTuples(const List *listeTuple);

/**
    Fonction qui permet d'enlever une donnée dans chaque tuples en fonction d'un indice après la suppression d'une colonne.
    Si le pointeur sur la liste de tuple est NULL ou la liste n'est pas de type TUPLE, cette fonction ne fait rien.

    param :
        int ind : indice, dans la liste, auquel doit etre enlever la donnée dans chaque tuple.
        List *listeTuples : pointeur sur la liste de tuples auquel on souhaite enlever une donnée dans chaque tuple.
**/
void removeDataInTuple(int ind, List *listeTuples);

/**
    Fonction qui crée une nouvelle DataValue correspondant à un nouveau tuple initialisé et la retourne.
**/
DataValue createTuple();
