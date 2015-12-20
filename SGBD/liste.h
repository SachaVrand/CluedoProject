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
    Fonction qui crée une nouvelle liste de type DataType dt passé en paramètre et retourne un pointeur sur la nouvelle liste.

    param :
        Datatype dt : type des données devant être contenu dans la liste.
**/
List *newList(DataType dt);

/**
    Fonction qui détruit la liste ainsi que tous ses éléments à l'aide la fonction de destruction de cette élément passée en paramètre. Si le pointeur sur la liste passée en paramètre est NULL, la fonction ne fait rien.

    param :
        List *l : paramètre de sortie, pointeur sur la liste que l'on souhaite détruire.
        void(*removeFunc)(DataValue dv) : pointeur sur la fonction permettant de detruire l'element de la liste. Peut être NULL si l'element n'a pas besoin d'être désalloué.
**/
void destroyList(List *l,void(*removeFunc)(DataValue dv));

/**
    Fonction qui permet d'ajouter un élément (DataValue passée en paramètre) à la fin de la liste. Si le pointeur sur la liste passée en paramètre est NULL, alors cette fonction ne fait rien.

    param :
        List *l : paramètre de sortie, pointeur sur la liste où l'on souhaite ajouter l'élément.
        DataValue dv : élément a ajouter à la fin de la liste.
**/
void addAsLast(List *l,const DataValue dv);

/**
    Fonction qui permet de recupérer l'élément correspondant à la DataValue passée en paramètre. Si le pointeur sur la liste ou le pointeur de fonction est NULL, alors retourne NULL.
    Si aucun élément correspondant à la Datavalue n'est trouvée ou que la liste est vide, alors retourne aussi NULL.
    Si plusieurs éléments correspondent à l'élément recherché dans la liste à l'aide de la fonction de comparaison, alors le première élément correspondant dans la liste est retournée.

    param :
        List *l : pointeur sur la liste où l'on souhaite récupérer l'élément.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur la fonction permettant de comparer les éléments de la liste à la DataValue passée en paramètre.
        DataValue dv : DataValue qui sera comparé pour récupérer l'élément dans liste.
**/
ListNode* getElement(const List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv);

/**
    Fonction qui permet de retirer un élément de la liste et de le retourner. Si le pointeur sur la liste ou le pointeur sur la fonction de comparaison est NULL retourne NULL.
    Si auncun élément correspondant à la DataValue n'est trouvée ou que la liste est vide, alors retourne aussi NULL.
    Si plusieurs éléments correspondent à l'élément recherché dans la liste à l'aide de la fonction de comparaison, alors le première élément correspondant dans la liste est enlevé puis retournée.

    param :
        List *l : paramètre de sortie, pointeur sur la liste où l'on souhaite retirer l'élément.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur la fonction permettant de comparer les éléments de la liste à la DataValue passée en paramètre.
        DataValue dv : DataValue qui sera comparé pour retirer l'élément dans liste.
**/
ListNode* removeElement(List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv);

/**
    Fonction qui permet d'afficher tous les éléments de la liste. Si le pointeur sur la liste passé en paramètre est NULL alors n'affiche rien, de même si la liste est vide.
    Affiche chaque élément de la liste, à l'aide du pointeur de fonction passée en paramètre, suivi du séparateur passée en paramètre.

    param :
        List *l : pointeur sur la liste où l'on souhaite afficher chaque élément.
        void(*DisplayFunc)(DataValue) : pointeur sur la fonciton permettant d'afficher un élément de la liste.
        char *sep : séparateur affiché après chaque affichage d'un élément de la liste.
**/
void displayList(const List *l, void(*DisplayFunc)(DataValue), const char *sep);

/**
    Fonction qui permet de savoir un élément est dans la liste. Si le pointeur sur la liste ou le pointeur sur la fonction est NULL alors retourne 0.
    Si la liste est vide retourne 0. Si aucun élément correspondant n'est trouvé alors retourne 0.
    Si au moins un élément correspondant à la DataValue passée en paramètre, comparé à l'aide de la fonction passée en paramètre, est trouvée, alors retourne 1.

    param :
        List *l : pointeur sur la liste où l'on souhaite chercher l'élément.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur la fonction permettant de comparer la DataValue passée en paramètre.
        DataValue dv : élément servant de comparaison pour retrouvé l'élément dans la liste.
**/
int isInList(const List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv);

/**
    Fonction qui permet de retirer un élément de la liste passée en paramètre en fonction de l'indice passée en paramètre.
    Retourne l'élément retiré si il a été trouvé.
    Si le pointeur sur la liste est NULL, retourne NULL.
    Si la liste est vide, retourne NULL.
    Si l'indice pasée en paramètre est inférieur à 0 ou supérieur ou égal au nombre d'élément dans la liste, retourne NULL.
    ind = 0 : retire le première élément de la liste.

    param :
        List *l : pointeur sur la liste dans laquelle on souhaite retirer l'élément à l'indice demandé.
        int ind : indice auquel retirer l'élément. Doit être entre 0 et nb éléments-1.

**/
ListNode *removeElementByIndex(List *l, int ind);

/**
    Fonction qui permet de récupérer l'index d'un élément de la liste.
    Si le pointeur de la liste ou le pointeur de la fonction de comparaison est NULL, alors retourne -1.
    Si l'élément n'est pas dans la liste, alors retourne -1.

    param :
        const List *l : pointeur sur la liste dans laquelle on souhaite savoir l'indice de l'élément.
        DataValue dv : élément de la liste dont on recherche l'indice.
        int(*cmpFunc)(DataValue,DataValue) : pointeur sur une fonction permettant de comparer les éléments de la liste.
**/
int getIndex(const List *l, DataValue dv, int(*cmpFunc)(DataValue,DataValue));

/**
    Fonction qui retire tous les éléments, en les détruisant, de la liste passée en paramètre.
    Si le pointeur sur la liste est NULL, ne fait rien.
    Si le pointeur sur la fonction est NULL, ne détruit pas chaque élément mais les retire.

    param :
        List *l : paramètre de sortie, pointeur sur la liste où l'on souhaite retirer et détruire chaque élément.
        void(*removeFunc)(DataValue dv) : pointeur sur la fonction permettant de détruire un élément de la liste.
**/
void removeAllElements(List *l,void(*removeFunc)(DataValue dv));

/** Ne pas utiliser pour le moment **/
List *satisfyingSubList(List *l, SatisfyingFunc sf);
