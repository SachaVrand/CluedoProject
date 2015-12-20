#include <stdlib.h>
#include "myString.h"
#include <string.h>

char *mystrdup(const char *s)
{
    char *res = malloc(sizeof(char *) * strlen(s) + 1);
    res = strcpy(res,s);
    return res;
}

int mystrcasecmp(const char *s1, const char *s2)
{
    int i;
    for(i=0; s1[i] && s2[i] ; i++)
    {
        if(MIN(s1[i]) < MIN(s2[i]))
            return -1;
        else if (MIN(s1[i]) > MIN(s2[i]))
            return 1;
    }
    if(s1[i] == s2[i])
        return 0;
    else
        if(s1[i] < s2[i])
            return -1;
        else
            return 1;
}
