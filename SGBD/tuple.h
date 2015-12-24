#define SEPTUPLES "\n"
#define SEPDONNEETUPLE " "

typedef struct s_Tuple{
    List *listeDonnee;
}Tuple;

/**
    Fonction qui permet d'ajouter un tuple � la liste de tuples pass�e en param�tre.
    Chaque �l�ment du tableau doit �tre transformable dans le type de sa colonne associ�e.
    La nombre d'�l�ment dans le tableau doit �tre �gal au nombre de colonnes dans la liste. Sinon on parcourt le tableau par rapport au nombre de colonnes.
    Si le tableau poss�de moins d'�l�ments que le nombre de colonnes, alors il essayera d'acc�der � des zones m�moires interdites.
    Si la liste de colonnes ou la liste de tuples est NULL, ne fait rien.
    Si la listeTuple n'est pas de type TUPLE ou la listeColonne n'est pas de type COLONNE, ne fait rien.
    Si le pointeur sur le tableau de donn�es est NULL ou que l'une des donn�es du tableau est NULL, ne fait rien.

    param :
        List *listeTuple : pointeur sur la liste de tuples o� l'on souhaite ajouter le nouveau tuples.
        char **tableauDonnees : tableau de chaine de caract�res repr�sentant les donn�es du nouveau tuple, n'est pas modifi�e.
        const List *listeColonne : pointeur sur la liste des colonnes associ�es au nouveau tuple, n'est pas modifi�e.
**/
void addTuple(List *listeTuple,char **tableauDonnees,const List *listeColonne);

/**
    Fonction qui permet de d�truire une DataValue contenant un Tuple.

    param :
        DataValue dv : Datavalue qui doit contenir un Tuple � d�truire.
**/
void destroyTuple(DataValue dv);

/**
    Fonction qui affiche chaque tuple de la liste de tuples pass�e en param�tre.
    Si le pointeur de liste est NULL ou que le type de la liste pass�e en param�tre est diff�rent de TUPLE alors cette fonction ne fait rien.

    param :
        const List *listeTuple : pointeur sur une liste de tuples devant �tre affich�es, n'est pas modifi�e.
**/
void displayTuple(const DataValue dv, FILE *outputFile);

/**
    Fonction qui affiche chaque tuple de la liste de tuples pass�e en param�tre.
    Si le pointeur de liste est NULL ou que le type de la liste pass�e en param�tre est diff�rent de TUPLE alors cette fonction ne fait rien.

    param :
        const List *listeTuple : pointeur sur une liste de tuples qui doivent �tre affich�s, n'est pas modifi�e.
**/
void displayAllTuples(const List *listeTuple, FILE *outputFile);

/**
    Fonction qui permet d'enlever une donn�e dans chaque tuples en fonction d'un indice apr�s la suppression d'une colonne.
    Si le pointeur sur la liste de tuple est NULL ou la liste n'est pas de type TUPLE, cette fonction ne fait rien.

    param :
        int ind : indice, dans la liste, auquel doit etre enlever la donn�e dans chaque tuple.
        List *listeTuples : pointeur sur la liste de tuples auquel on souhaite enlever une donn�e dans chaque tuple.
**/
void removeDataInTuple(int ind, List *listeTuples);

/**
    Fonction qui cr�e une nouvelle DataValue correspondant � un nouveau tuple initialis� et la retourne.
**/
DataValue createTuple();
