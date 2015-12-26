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

void displayTuple(const DataValue dv, FILE *outputFile)
{
    displayList(dv.tupl->listeDonnee,displayDonnee,SEPDONNEETUPLE,outputFile);
}

void displayAllTuples(const List *listeTuple, FILE *outputFile)
{
    if(!listeTuple || listeTuple->type != TUPLE) return;
    displayList(listeTuple,displayTuple,SEPTUPLES,outputFile);
}

void displayAllTuplesFromTables(t_Table **tabTables, FILE *outputFile)
{
	List *tmpListeTuples;

	if(!tabTables || !(*tabTables)) return;

	tmpListeTuples = newList(TUPLE);

	for(;*tabTables;tabTables++)
	{
		concatenateTuples(tmpListeTuples,(*tabTables)->listeTuple);
	}

	displayAllTuples(tmpListeTuples,outputFile);
	destroyList(tmpListeTuples,destroyTuple);
}

void concatenateTuples(List *listeTuplesDest, List *listeTuples2)
{
	List *res;
	ListNode *tmpNode1, *tmpNode2;
	List **tabLists;

	if(!listeTuplesDest || !listeTuples2 || listeTuplesDest->type != TUPLE || listeTuples2->type != TUPLE) return;

	if(listeTuplesDest->nbElem == 0 && listeTuples2->nbElem == 0)
		return;
	else if(listeTuplesDest->nbElem == 0)
	{
		tmpNode1 = listeTuples2->first;
		while(tmpNode1)
		{
			addAsLast(listeTuplesDest,duplicateTuple(tmpNode1->valeur));
			tmpNode1 = tmpNode1->suivant;
		}
		return;
	}
	else if(listeTuples2->nbElem == 0)
		return;

	res = newList(TUPLE);
	tabLists = (List **)malloc(sizeof(List *) * 2);

	tmpNode1 = listeTuplesDest->first;
	while(tmpNode1)
	{
		tmpNode2 = listeTuples2->first;
		while(tmpNode2)
		{
			DataValue dvTmpTuple;
			Tuple *tmpTuple;

			tabLists[0] = tmpNode1->valeur.tupl->listeDonnee;
			tabLists[1] = tmpNode2->valeur.tupl->listeDonnee;
			tmpTuple = (Tuple *)malloc(sizeof(Tuple));
			dvTmpTuple.tupl = tmpTuple;
			dvTmpTuple.tupl->listeDonnee = concatenateLists(tabLists,2,DONNEE,duplicateDonnee);
			addAsLast(res,dvTmpTuple);

			tmpNode2 = tmpNode2->suivant;
		}
		tmpNode1 = tmpNode1->suivant;
	}
	free(tabLists);

	removeAllElements(listeTuplesDest,destroyTuple);

	tmpNode1 = res->first;
	while(tmpNode1)
	{
		addAsLast(listeTuplesDest,duplicateTuple(tmpNode1->valeur));
		tmpNode1 = tmpNode1->suivant;
	}
	destroyList(res,destroyTuple);
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

DataValue duplicateTuple(DataValue dvTuple)
{
	DataValue res;
	ListNode *tmpNode;

	tmpNode = dvTuple.tupl->listeDonnee->first;
	res = createTuple();
	while(tmpNode)
	{
		DataValue tmpDonnee;
		tmpDonnee = duplicateDonnee(tmpNode->valeur);
		addAsLast(res.tupl->listeDonnee,tmpDonnee);
		tmpNode = tmpNode->suivant;
	}
	return res;
}
