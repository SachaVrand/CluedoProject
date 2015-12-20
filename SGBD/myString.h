#define MIN(n) ((((n) >= 'a') && ((n) <= 'z')) ? ((n)-'a'+'A') : (n))

/**
    Fonction qui permet de copier une chaine de caract�re, retourne un pointeur sur la copie de la chaine de caract�re.

    param :
        const char *s : chaine � copier, n'est pas modifi�e.
**/
char *mystrdup(const char *s);

/**
    Fonction qui permet de comparer deux chaines de caract�res en ignorant la casse.
    Retourne 0 si elle sont �gales, -1 si la premi�re est inf�rieur � la seconde dans l'odre alphab�tique, sinon 1.

    param :
        const char *s1 : chaine de caract�res � comparer avec l'autre, n'est pas modifi�.
        const char *s2 : chaine de caract�res � comparer avec l'autre, n'est pas modifi�.
**/
int mystrcasecmp(const char *s1, const char *s2);
