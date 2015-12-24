#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "myString.h"
#include "global.h"
#include "liste.h"
#include "typeElement.h"
#include "donnee.h"
#include "colonne.h"
#include "tuple.h"
#include "table.h"

Valeur getValeur(TypeElement type, const char *val)
{
    Valeur res;

    if(type == STR)
    {
        res.chaine = mystrdup(val);
    }
    else if(type == INT)
    {
        res.entier = atoi(val);
    }
    else if(type == NULLTYPE)
    {
        res.chaine = mystrdup(NULLTYPETOSTR);
    }
    return res;
}

void displayDonnee(const DataValue dv, FILE *outputFile)
{
    if(dv.donnee->type == INT)
    {
        printf("%d",dv.donnee->val.entier);
        if(outputFile)
        	fprintf(outputFile,"%d",dv.donnee->val.entier);
    }
    else if(dv.donnee->type == STR)
    {
        printf("%s",dv.donnee->val.chaine);
        if(outputFile)
        	fprintf(outputFile,"%s",dv.donnee->val.chaine);
    }
    else if(dv.donnee->type == NULLTYPE)
    {
        printf("%s",dv.donnee->val.chaine);
        if(outputFile)
        	fprintf(outputFile,"%s",dv.donnee->val.chaine);
    }
}

void destroyDonnee(DataValue dv)
{
    if(dv.donnee->type == STR || dv.donnee->type == NULLTYPE)
    {
       free(dv.donnee->val.chaine);
    }
    free(dv.donnee);
}

DataValue createDonnee(const char *valFromStr, TypeElement type)
{
    DataValue res;
    Donnee *newDonnee;

    if(!valFromStr || type == UNKNOWN) return res;

    newDonnee = malloc(sizeof(Donnee));
    testerPointeur(newDonnee);
    newDonnee->type = type;
    newDonnee->val = getValeur(type,valFromStr);
    res.donnee = newDonnee;
    return res;
}
