#define MIN(n) ((((n) >= 'a') && ((n) <= 'z')) ? ((n)-'a'+'A') : (n))

/**
    Fonction qui permet de copier une chaine de caractère, retourne un pointeur sur la copie de la chaine de caractère.

    param :
        const char *s : chaine à copier, n'est pas modifiée.
**/
char *mystrdup(const char *s);

/**
    Fonction qui permet de comparer deux chaines de caractères en ignorant la casse.
    Retourne 0 si elle sont égales, -1 si la première est inférieur à la seconde dans l'odre alphabétique, sinon 1.

    param :
        const char *s1 : chaine de caractères à comparer avec l'autre, n'est pas modifié.
        const char *s2 : chaine de caractères à comparer avec l'autre, n'est pas modifié.
**/
int mystrcasecmp(const char *s1, const char *s2);
