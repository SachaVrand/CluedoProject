#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "myString.h"
#include "global.h"
#include "liste.h"
#include "typeElement.h"
#include "donnee.h"
#include "tuple.h"
#include "colonne.h"
#include "table.h"


void addCol(Table *table, const char *nomCol, TypeElement type)
{
    DataValue dvNewCol;

    if(!table || !nomCol || type == UNKNOWN || type == NULLTYPE)return;

    dvNewCol = createCol(nomCol,type);
    addAsLast(table->listeColonne,dvNewCol);
    /* si la table contenait d�j� des tuples, on ajoute la donn�e NULL � chaque tuple */
    if(table->listeTuple->nbElem != 0)
    {
        ListNode *courant = table->listeTuple->first;

        while(courant != NULL)
        {
            DataValue dvDonnee;
            dvDonnee = createDonnee(NULLTYPETOSTR,NULLTYPE);
            addAsLast(courant->valeur.tupl->listeDonnee,dvDonnee);
            courant = courant->suivant;
        }
    }
}

int containsCol(const List *listeCol, char *nomCol)
{
    int res;
    DataValue dvTmpCol;

    if(!listeCol || listeCol->type != COLONNE || !nomCol) return 0;

    dvTmpCol = createCol(nomCol,INT);
    res = isInList(listeCol,compareCol,dvTmpCol);
    destroyCol(dvTmpCol);
    return res;
}

int compareCol(const DataValue dv1,const DataValue dv2)
{
    return mystrcasecmp(dv1.colonne->nom,dv2.colonne->nom);
}

void displayCol(const DataValue dv, FILE *outputFile)
{
    printf("%s(%s)",dv.colonne->nom,getTypeElementToStr(dv.colonne->type));
    if(outputFile)
    	fprintf(outputFile,"%s(%s)",dv.colonne->nom,getTypeElementToStr(dv.colonne->type));
}

void displayAllCols(const List *listeCol, FILE *outputFile)
{
    if(!listeCol || listeCol->type != COLONNE) return;

    displayList(listeCol,displayCol,SEPCOL, outputFile);
    if(listeCol->nbElem > 0)
    {
        printf("\n");
        if(outputFile)
        	fprintf(outputFile,"\n");
    }
}

int removeCol(Table *table, char *nomCol)
{
    ListNode *tmpLN;
    DataValue dvTmpCol;
    int ind;

    if(!table || table->listeColonne->nbElem <= 0 || !nomCol) return 0;

    dvTmpCol = createCol(nomCol,INT);
    ind = getIndex(table->listeColonne,dvTmpCol,compareCol);
    tmpLN = removeElement(table->listeColonne,compareCol,dvTmpCol);
    destroyCol(dvTmpCol);
    if(tmpLN != NULL)
    {
        destroyCol(tmpLN->valeur);
        free(tmpLN);
        removeDataInTuple(ind,table->listeTuple);
        if(table->listeColonne->nbElem == 0)
        {
            removeAllElements(table->listeTuple,destroyTuple);
        }
        return 1;
    }
    return 0;
}

void destroyCol(DataValue dvCol)
{
    free(dvCol.colonne->nom);
    free(dvCol.colonne);
}

DataValue createCol(const char *nomCol, TypeElement type)
{
    DataValue res;
    Colonne *newCol;

    if(type == UNKNOWN || type == NULLTYPE || !nomCol) return res;

    newCol = malloc(sizeof(Colonne));
    testerPointeur(newCol);
    newCol->nom = mystrdup(nomCol);
    newCol->type = type;
    res.colonne = newCol;
    return res;
}
