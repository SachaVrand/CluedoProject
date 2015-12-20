typedef union{
    char *chaine;
    int entier;
} Valeur;

typedef struct s_Donnee{
    TypeElement type;
    Valeur val;
}Donnee;

/**
    Fonction qui permet de récupérer une Valeur en fonction du type et de la chaine de caractères passés en paramètres.
    Si le type est STR, retourne une Valeur avec un nouveau pointeur sur la chaine passée en paramètre.
    Si le type est INT, retourne une Valeur avec comme entier la conversion de la chaine de caractères passée en paramètre.
    Si le type NULLTYPE, retourne une Valeur avec comme chaine un nouveau pointeur sur la chaine de caractere TYPENULLTOSTR.
    Si le type est UNKNOWN retourne une Valeur vide.
    La chaine de caractère passée en paramètre doit être correcte et convertible.
    Dans le cas où les paramètres ne serait pas correcte :
        Si le type est INT et que la chaine n'est pas convertible, retourne une valeur avec comme entier 0.
        Si le type est STR et que la chaine n'est pas correcte (n'est pas entourée de ""), retourne une valeur, avec comme chaine la copie de la chaine passée en paramètre.

    param :
        TypeElement type : type de la chaine de caractère passée en paramètre.
        const char *val : représentation de la Valeur sous forme de chaine de caractères.
**/
Valeur getValeur(TypeElement type, const char *val);

/**
    Fonction qui permet d'afficher une donnee à partir d'une DataValue de Donnee.
    La DataValue doit contenir une Donnee.

    param :
        DataValue dv : Datavalue contenant une donnee qui doit être affichée, n'est pas modifiée.
**/
void displayDonnee(const DataValue dv);

/**
    Fonction qui permet de détruire une DataValue contenant une Donnee.

    param :
        DataValue dv : Datavalue qui doit contenir une Donnee à détruire.
**/
void destroyDonnee(DataValue dv);

/**
    Fonction qui crée une nouvelle DataValue correspondant à une nouvelle Donnée initialisée et la retourne.
    Le type passée en paramètre ne peut pas être UNKNOWN.
    Si le pointeur sur le nom de la donnee est NULL, retourne une DataValue vide.

    param :
        const char *valFromStr : pointeur sur la chaine représentant le nom de la nouvelle donnée, n'est pas modifié.
        TypeElement type : type correspondant à la colonne.
**/
DataValue createDonnee(const char *valFromStr, TypeElement type);
