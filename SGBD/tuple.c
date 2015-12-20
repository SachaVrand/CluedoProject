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

void addTuple(List *listeTuple,char **tableauDonnees,const List *listeColonne)
{
    int i;
    ListNode *courant;
    DataValue dvTuple;

    if(!listeTuple || listeTuple->type != TUPLE || !tableauDonnees || !listeColonne || listeColonne->type != COLONNE) return;
    for(i=0;i<listeColonne->nbElem;i++)if(!(tableauDonnees[i]))return;

    i=0;
    courant = listeColonne->first;
    dvTuple = createTuple();
    while(courant != NULL)
    {
        DataValue dvDonnee;

        if(!mystrcasecmp(NULLTYPETOSTR,tableauDonnees[i]))
        {
            dvDonnee = createDonnee(NULLTYPETOSTR,NULLTYPE);
        }
        else
        {
            dvDonnee = createDonnee(tableauDonnees[i++],courant->valeur.colonne->type);
        }
        addAsLast(dvTuple.tupl->listeDonnee,dvDonnee);
        courant = courant->suivant;
    }
    addAsLast(listeTuple,dvTuple);
}

void destroyTuple(DataValue dv)
{
    destroyList(dv.tupl->listeDonnee,destroyDonnee);
    free(dv.tupl);
}

void displayTuple(const DataValue dv)
{
    displayList(dv.tupl->listeDonnee,displayDonnee,SEPDONNEETUPLE);
}

void displayAllTuples(const List *listeTuple)
{
    if(!listeTuple || listeTuple->type != TUPLE) return;
    displayList(listeTuple,displayTuple,SEPTUPLES);
}

void removeDataInTuple(int ind, List *listeTuples)
{
    ListNode *tuple;
    ListNode *tmp;

    if(!listeTuples || listeTuples->type != TUPLE) return;

    tuple = listeTuples->first;
    while(tuple != NULL)
    {
        tmp = removeElementByIndex(tuple->valeur.tupl->listeDonnee,ind);
        destroyDonnee(tmp->valeur);
        free(tmp);
        tuple = tuple->suivant;
    }
}

DataValue createTuple()
{
    DataValue res;
    List *newListDonnee;
    Tuple *newTuple;

    newListDonnee = newList(DONNEE);
    newTuple = malloc(sizeof(Tuple));
    testerPointeur(newTuple);
    newTuple->listeDonnee = newListDonnee;
    res.tupl = newTuple;
    return res;
}
