typedef enum{TABLE,COLONNE,TUPLE,DONNEE} DataType;

typedef struct s_Table t_Table;
typedef struct s_Colonne t_Colonne;
typedef struct s_Tuple t_Tuple;
typedef struct s_Donnee t_Donnee;

typedef union{
	t_Table *table;
	t_Colonne *colonne;
	t_Tuple *tupl;
	t_Donnee *donnee;
}DataValue;

typedef struct listNode{
	DataValue valeur;
	struct listNode *suivant;
}ListNode;

typedef struct{
	DataType type;
	int nbElem;
	ListNode *first;
	ListNode *last;
}List;

typedef int(*SatisfyingFunc)(DataValue);

/**
    Fonction qui cr�e une nouvelle liste de type DataType dt pass� en param�tre et retourne un pointeur sur la nouvelle liste.

    param :
        Datatype dt : type des donn�es devant �tre contenu dans la liste.
**/
List *newList(DataType dt);

/**
    Fonction qui d�truit la liste ainsi que tous ses �l�ments � l'aide la fonction de destruction de cette �l�ment pass�e en param�tre. Si le pointeur sur la liste pass�e en param�tre est NULL, la fonction ne fait rien.

    param :
        List *l : param�tre de sortie, pointeur sur la liste que l'on souhaite d�truire.
        void(*removeFunc)(DataValue dv) : pointeur sur la fonction permettant de detruire l'element de la liste. Peut �tre NULL si l'element n'a pas besoin d'�tre d�sallou�.
**/
void destroyList(List *l,void(*removeFunc)(DataValue dv));

/**
    Fonction qui permet d'ajouter un �l�ment (DataValue pass�e en param�tre) � la fin de la liste. Si le pointeur sur la liste pass�e en param�tre est NULL, alors cette fonction ne fait rien.

    param :
        List *l : param�tre de sortie, pointeur sur la liste o� l'on souhaite ajouter l'�l�ment.
        DataValue dv : �l�ment a ajouter � la fin de la liste.
**/
void addAsLast(List *l,const DataValue dv);

/**
    Fonction qui permet de recup�rer l'�l�ment correspondant � la DataValue pass�e en param�tre. Si le pointeur sur la liste ou le pointeur de fonction est NULL, alors retourne NULL.
    Si aucun �l�ment correspondant � la Datavalue n'est trouv�e ou que la liste est vide, alors retourne aussi NULL.
    Si plusieurs �l�ments correspondent � l'�l�ment recherch� dans la liste � l'aide de la fonction de comparaison, alors le premi�re �l�ment correspondant dans la liste est retourn�e.

    param :
        List *l : pointeur sur la liste o� l'on souhaite r�cup�rer l'�l�ment.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur la fonction permettant de comparer les �l�ments de la liste � la DataValue pass�e en param�tre.
        DataValue dv : DataValue qui sera compar� pour r�cup�rer l'�l�ment dans liste.
**/
ListNode* getElement(const List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv);

/**
    Fonction qui permet de retirer un �l�ment de la liste et de le retourner. Si le pointeur sur la liste ou le pointeur sur la fonction de comparaison est NULL retourne NULL.
    Si auncun �l�ment correspondant � la DataValue n'est trouv�e ou que la liste est vide, alors retourne aussi NULL.
    Si plusieurs �l�ments correspondent � l'�l�ment recherch� dans la liste � l'aide de la fonction de comparaison, alors le premi�re �l�ment correspondant dans la liste est enlev� puis retourn�e.

    param :
        List *l : param�tre de sortie, pointeur sur la liste o� l'on souhaite retirer l'�l�ment.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur la fonction permettant de comparer les �l�ments de la liste � la DataValue pass�e en param�tre.
        DataValue dv : DataValue qui sera compar� pour retirer l'�l�ment dans liste.
**/
ListNode* removeElement(List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv);

/**
    Fonction qui permet d'afficher tous les �l�ments de la liste. Si le pointeur sur la liste pass� en param�tre est NULL alors n'affiche rien, de m�me si la liste est vide.
    Affiche chaque �l�ment de la liste, � l'aide du pointeur de fonction pass�e en param�tre, suivi du s�parateur pass�e en param�tre.

    param :
        List *l : pointeur sur la liste o� l'on souhaite afficher chaque �l�ment.
        void(*DisplayFunc)(DataValue) : pointeur sur la fonciton permettant d'afficher un �l�ment de la liste.
        char *sep : s�parateur affich� apr�s chaque affichage d'un �l�ment de la liste.
**/
void displayList(const List *l, void(*DisplayFunc)(DataValue,FILE *), const char *sep,FILE *outputFile);

void displayList2(const List *l, void(*DisplayFunc)(DataValue,FILE *), const char *sep, const char *prefixe, FILE *outputFile);

/**
    Fonction qui permet de savoir un �l�ment est dans la liste. Si le pointeur sur la liste ou le pointeur sur la fonction est NULL alors retourne 0.
    Si la liste est vide retourne 0. Si aucun �l�ment correspondant n'est trouv� alors retourne 0.
    Si au moins un �l�ment correspondant � la DataValue pass�e en param�tre, compar� � l'aide de la fonction pass�e en param�tre, est trouv�e, alors retourne 1.

    param :
        List *l : pointeur sur la liste o� l'on souhaite chercher l'�l�ment.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur la fonction permettant de comparer la DataValue pass�e en param�tre.
        DataValue dv : �l�ment servant de comparaison pour retrouv� l'�l�ment dans la liste.
**/
int isInList(const List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv);

/**
    Fonction qui permet de retirer un �l�ment de la liste pass�e en param�tre en fonction de l'indice pass�e en param�tre.
    Retourne l'�l�ment retir� si il a �t� trouv�.
    Si le pointeur sur la liste est NULL, retourne NULL.
    Si la liste est vide, retourne NULL.
    Si l'indice pas�e en param�tre est inf�rieur � 0 ou sup�rieur ou �gal au nombre d'�l�ment dans la liste, retourne NULL.
    ind = 0 : retire le premi�re �l�ment de la liste.

    param :
        List *l : pointeur sur la liste dans laquelle on souhaite retirer l'�l�ment � l'indice demand�.
        int ind : indice auquel retirer l'�l�ment. Doit �tre entre 0 et nb �l�ments-1.

**/
ListNode *removeElementByIndex(List *l, int ind);

/**
    Fonction qui permet de r�cup�rer l'index d'un �l�ment de la liste.
    Si le pointeur de la liste ou le pointeur de la fonction de comparaison est NULL, alors retourne -1.
    Si l'�l�ment n'est pas dans la liste, alors retourne -1.

    param :
        const List *l : pointeur sur la liste dans laquelle on souhaite savoir l'indice de l'�l�ment.
        DataValue dv : �l�ment de la liste dont on recherche l'indice.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur une fonction permettant de comparer les �l�ments de la liste.
**/
int getIndex(const List *l, DataValue dv, int(*cmpFunc)(DataValue,DataValue));

/**
    Fonction qui retire tous les �l�ments, en les d�truisant, de la liste pass�e en param�tre.
    Si le pointeur sur la liste est NULL, ne fait rien.
    Si le pointeur sur la fonction est NULL, ne d�truit pas chaque �l�ment mais les retire.

    param :
        List *l : param�tre de sortie, pointeur sur la liste o� l'on souhaite retirer et d�truire chaque �l�ment.
        void(*removeFunc)(DataValue dv) : pointeur sur la fonction permettant de d�truire un �l�ment de la liste.
**/
void removeAllElements(List *l,void(*removeFunc)(DataValue dv));

/** Ne pas utiliser pour le moment **/
List *satisfyingSubList(List *l, SatisfyingFunc sf);

List *concatenateLists(List **tabLists, int nbLists, DataType typeForNewList, DataValue(*dupFunc)(DataValue));
