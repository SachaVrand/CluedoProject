typedef union{
    char *chaine;
    int entier;
} Valeur;

typedef struct s_Donnee{
    TypeElement type;
    Valeur val;
}Donnee;

/**
    Fonction qui permet de r�cup�rer une Valeur en fonction du type et de la chaine de caract�res pass�s en param�tres.
    Si le type est STR, retourne une Valeur avec un nouveau pointeur sur la chaine pass�e en param�tre.
    Si le type est INT, retourne une Valeur avec comme entier la conversion de la chaine de caract�res pass�e en param�tre.
    Si le type NULLTYPE, retourne une Valeur avec comme chaine un nouveau pointeur sur la chaine de caractere TYPENULLTOSTR.
    Si le type est UNKNOWN retourne une Valeur vide.
    La chaine de caract�re pass�e en param�tre doit �tre correcte et convertible.
    Dans le cas o� les param�tres ne serait pas correcte :
        Si le type est INT et que la chaine n'est pas convertible, retourne une valeur avec comme entier 0.
        Si le type est STR et que la chaine n'est pas correcte (n'est pas entour�e de ""), retourne une valeur, avec comme chaine la copie de la chaine pass�e en param�tre.

    param :
        TypeElement type : type de la chaine de caract�re pass�e en param�tre.
        const char *val : repr�sentation de la Valeur sous forme de chaine de caract�res.
**/
Valeur getValeur(TypeElement type, const char *val);

/**
    Fonction qui permet d'afficher une donnee � partir d'une DataValue de Donnee.
    La DataValue doit contenir une Donnee.

    param :
        DataValue dv : Datavalue contenant une donnee qui doit �tre affich�e, n'est pas modifi�e.
**/
void displayDonnee(const DataValue dv, FILE *outputFile);

/**
    Fonction qui permet de d�truire une DataValue contenant une Donnee.

    param :
        DataValue dv : Datavalue qui doit contenir une Donnee � d�truire.
**/
void destroyDonnee(DataValue dv);

/**
    Fonction qui cr�e une nouvelle DataValue correspondant � une nouvelle Donn�e initialis�e et la retourne.
    Le type pass�e en param�tre ne peut pas �tre UNKNOWN.
    Si le pointeur sur le nom de la donnee est NULL, retourne une DataValue vide.

    param :
        const char *valFromStr : pointeur sur la chaine repr�sentant le nom de la nouvelle donn�e, n'est pas modifi�.
        TypeElement type : type correspondant � la colonne.
**/
DataValue createDonnee(const char *valFromStr, TypeElement type);

DataValue duplicateDonnee(DataValue dvDonnee);
