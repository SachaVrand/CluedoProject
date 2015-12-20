#define TYPESTRTOSTR "STR"
#define TYPEINTTOSTR "INT"
#define NULLTYPETOSTR "NULL"
typedef enum {INT,STR,NULLTYPE,UNKNOWN} TypeElement;

/**
    Fonction qui retourne le TypeElement correspondant à la chaine de caractères passée en paramètre.
    Si aucun type ne correspond à la chaine de caractères ou que le pointeur sur la chaine est NULL, alors retourne UNKNOWN.

    param :
        const char *type : pointeur sur la chaine de caractère correspondant au TypeElement recherché, n'est pas modifié.

**/
TypeElement getTypeElementFromStr(const char *type);

/**
    Fonction qui retourne un nouveau pointeur sur une chaine de caractères correspondant au TypeElement passé en paramètre.
    Si le TypeElement est UNKNOWN ou NULLTYPE, retourne NULL.

    param :
            TypeElement type : type dont on souhaite avoir la représentation sous forme de chaine de caractères.
**/
char *getTypeElementToStr(TypeElement type);

/**
    Fonction qui permet de savoir si une chaine de caractères est transformable dans le type passée en paramètre.
    Retourne 1 si c'est possible, sinon 0.
    Retourne 0 si le pointeur sur la chaine passée en paramètre est NULL ou si le type passée en paramètre est UNKNOWN.

    param :
        const char *donnee : pointeur sur la chaine de caractères dont ou souhaite savoir si elle est transformable.
        TypeElement type : type dans lequel on souhaite transformer la chaine de caractères.
**/
int isCastableToTypeElement(const char *donnee, TypeElement type);

/**
    Fonction qui permet savoir si toutes les donnees, dans le tableau de chaine de carctères passé en paramètre, sont transformable dans le type des colonnes de la liste passée en paramètre.
    On vérifie la première donnée avec le type de la première colonne et ainsi de suite. Le parcours se fait sur le nombre de colonnes de la liste. Le nombre d'éléments dans la liste et le tableau doit égal, sinon on parcours jusqu'au nombre de colonnes.
    Attention le nombre de données dans le tableau ne doit surtout pas être inférieur au nombre de colonnes, car la fonction essayera d'accéder dans ce cas à des zones de mémoire en dehors du tableau et cela pourra engendrer des résultats innattendus.
    Retourne NULL et un type différent de UNKNOWN si toutes les conversions sont possibles.
    Retourne le premiere pointeur sur chaine dont la conversion n'est pas possible et modifie le type passée en paramètre au type de la colonne associée a la chaine qui ne peut pas être convertie.
    Retourne NULL et modifie le type à UNKNOWN si le pointeur sur la liste est NULL, ou si le type de la liste est différent de colonne, ou si le pointeur sur le type est NULL, ou si la liste est vide,
    ou si le pointeur de tableau est NULL ou si l'un des élément du tableau était NULL.

    param :
        const List *listeColonne : pointeur sur la liste de colonnes dont on souhaite savoir si la conversion des données est possible, n'est pas modifié.
        const char **tableauDeDonnees : tableau de chaine de caractères représentant les données que l'on souhaite comparées, n'est pas modifié.
        TypeElement *type : paramètre de sortie, pointeur sur un type qui sera modifié pour donner le type que l'on ne peut pas convertir si c'est le cas.
**/
char *conversionsPossibles(const List *listeColonne,char **tableauDeDonnees,TypeElement *type);









