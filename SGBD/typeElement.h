#define TYPESTRTOSTR "STR"
#define TYPEINTTOSTR "INT"
#define NULLTYPETOSTR "NULL"
typedef enum {INT,STR,NULLTYPE,UNKNOWN} TypeElement;

/**
    Fonction qui retourne le TypeElement correspondant � la chaine de caract�res pass�e en param�tre.
    Si aucun type ne correspond � la chaine de caract�res ou que le pointeur sur la chaine est NULL, alors retourne UNKNOWN.

    param :
        const char *type : pointeur sur la chaine de caract�re correspondant au TypeElement recherch�, n'est pas modifi�.

**/
TypeElement getTypeElementFromStr(const char *type);

/**
    Fonction qui retourne un nouveau pointeur sur une chaine de caract�res correspondant au TypeElement pass� en param�tre.
    Si le TypeElement est UNKNOWN ou NULLTYPE, retourne NULL.

    param :
            TypeElement type : type dont on souhaite avoir la repr�sentation sous forme de chaine de caract�res.
**/
char *getTypeElementToStr(TypeElement type);

/**
    Fonction qui permet de savoir si une chaine de caract�res est transformable dans le type pass�e en param�tre.
    Retourne 1 si c'est possible, sinon 0.
    Retourne 0 si le pointeur sur la chaine pass�e en param�tre est NULL ou si le type pass�e en param�tre est UNKNOWN.

    param :
        const char *donnee : pointeur sur la chaine de caract�res dont ou souhaite savoir si elle est transformable.
        TypeElement type : type dans lequel on souhaite transformer la chaine de caract�res.
**/
int isCastableToTypeElement(const char *donnee, TypeElement type);

/**
    Fonction qui permet savoir si toutes les donnees, dans le tableau de chaine de carct�res pass� en param�tre, sont transformable dans le type des colonnes de la liste pass�e en param�tre.
    On v�rifie la premi�re donn�e avec le type de la premi�re colonne et ainsi de suite. Le parcours se fait sur le nombre de colonnes de la liste. Le nombre d'�l�ments dans la liste et le tableau doit �gal, sinon on parcours jusqu'au nombre de colonnes.
    Attention le nombre de donn�es dans le tableau ne doit surtout pas �tre inf�rieur au nombre de colonnes, car la fonction essayera d'acc�der dans ce cas � des zones de m�moire en dehors du tableau et cela pourra engendrer des r�sultats innattendus.
    Retourne NULL et un type diff�rent de UNKNOWN si toutes les conversions sont possibles.
    Retourne le premiere pointeur sur chaine dont la conversion n'est pas possible et modifie le type pass�e en param�tre au type de la colonne associ�e a la chaine qui ne peut pas �tre convertie.
    Retourne NULL et modifie le type � UNKNOWN si le pointeur sur la liste est NULL, ou si le type de la liste est diff�rent de colonne, ou si le pointeur sur le type est NULL, ou si la liste est vide,
    ou si le pointeur de tableau est NULL ou si l'un des �l�ment du tableau �tait NULL.

    param :
        const List *listeColonne : pointeur sur la liste de colonnes dont on souhaite savoir si la conversion des donn�es est possible, n'est pas modifi�.
        const char **tableauDeDonnees : tableau de chaine de caract�res repr�sentant les donn�es que l'on souhaite compar�es, n'est pas modifi�.
        TypeElement *type : param�tre de sortie, pointeur sur un type qui sera modifi� pour donner le type que l'on ne peut pas convertir si c'est le cas.
**/
char *conversionsPossibles(const List *listeColonne,char **tableauDeDonnees,TypeElement *type);

TypeElement getTypeElementOf(const char *type);







