#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "myString.h"
#include "liste.h"
#include "typeElement.h"
#include "colonne.h"


TypeElement getTypeElementFromStr(const char *type)
{
    if(!type) return UNKNOWN;
    if(!mystrcasecmp(type,TYPESTRTOSTR))
    {
        return STR;
    }
    else if(!mystrcasecmp(type,TYPEINTTOSTR))
    {
        return INT;
    }
    else if(!mystrcasecmp(type,NULLTYPETOSTR))
    {
        return NULLTYPE;
    }
    return UNKNOWN;
}

char *getTypeElementToStr(TypeElement type)
{
    if(type == INT)
        return TYPEINTTOSTR;
    else if(type == STR)
        return TYPESTRTOSTR;
    else if(type == NULLTYPE)
        return NULLTYPETOSTR;
    else
        return NULL;
}

int isCastableToTypeElement(const char *donnee, TypeElement type)
{
    if(!donnee) return 0;
    if(mystrcasecmp(donnee,NULLTYPETOSTR) == 0)
    {
        return 1;
    }
    else if(type == INT)
    {
        if(strcmp(donnee,"0") ==  0)
        {
            return 1;
        }
        else if(atoi(donnee) != 0)
        {
            return 1;
        }
        else
            return 0;
    }
    else if(type == STR)
    {
        if(*donnee == '"' && donnee[strlen(donnee) - 1] == '"')
        {
            return 1;
        }
        else
            return 0;
    }
    return 0;
}

char *conversionsPossibles(const List *listeColonne, char **tableauDeDonnees,TypeElement *type)
{
    int i = 0;
    ListNode *courant;

    if(!listeColonne || listeColonne->type != COLONNE || !type || listeColonne->nbElem <= 0 || !tableauDeDonnees)
    {
        *type = UNKNOWN;
        return NULL;
    }

    courant = listeColonne->first;
    while(courant != NULL)
    {
        if(!(isCastableToTypeElement(tableauDeDonnees[i++],courant->valeur.colonne->type)))
        {
            if(!tableauDeDonnees[i-1])
            {
                *type = UNKNOWN;
            }
            else
            {
                *type = courant->valeur.colonne->type;
            }
            return tableauDeDonnees[i-1];
        }
        courant = courant->suivant;
    }
    *type = INT;
    return NULL;
}
