#include <stdio.h>
#include <stdlib.h>
#include "global.h"
#include "liste.h"
#include "typeElement.h"
#include "donnee.h"
#include "colonne.h"
#include "tuple.h"
#include "table.h"

List *newList(DataType dt)
{
	List *l = malloc(sizeof(List));

	l->type = dt;
	l->nbElem = 0;
	l->first = NULL;
	l->last = NULL;
	return l;
}

void destroyList(List *l,void(*removeFunc)(DataValue dv))
{
	ListNode *suiv,*des;

	if(!l)return;

	des = l->first;
	while(des != NULL)
	{
		suiv = des->suivant;
		if(removeFunc != NULL)
            (*removeFunc)(des->valeur);
		free(des);
		des = suiv;
	}
	free(l);
}

void removeAllElements(List *l,void(*removeFunc)(DataValue dv))
{
	ListNode *suiv,*des;

	if(!l)return;

	des = l->first;
	while(des != NULL)
	{
		suiv = des->suivant;
		if(removeFunc != NULL)
            (*removeFunc)(des->valeur);
		free(des);
		des = suiv;
	}
	l->nbElem = 0;
	l->last = NULL;
	l->first = NULL;
}

void addAsLast(List *l, const DataValue dv)
{
	ListNode *newNode;

	if(!l)return;

	newNode = malloc(sizeof(ListNode));
	testerPointeur(newNode);
	newNode->valeur = dv;
	newNode->suivant = NULL;
	if(l->first == NULL)
	{
		l->first=newNode;
	}
	else
	{
		l->last->suivant = newNode;
	}
	l->last = newNode;
	(l->nbElem)++;
}

void displayList(const List *l, void(*DisplayFunc)(DataValue,FILE *), const char *sep, FILE *outputFile)
{
    ListNode *courant;

	if(!l || l->first == NULL) return;

	courant = l->first;
	while(courant != NULL)
	{
		(*DisplayFunc)(courant->valeur,outputFile);
		printf("%s",sep);
		courant = courant->suivant;
	}
}

int isInList(const List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv)
{
	int res;
	ListNode *courant;

	if(!l || !cmpFunc) return 0;

	courant = l->first;
	while(courant != NULL)
	{
		res = (*cmpFunc)(courant->valeur,dv);
		if(res == 0) return 1;
		courant = courant->suivant;
	}
	return 0;
}

ListNode *removeElement(List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv)
{
    ListNode *precedent,*courant;

    if(!l || !cmpFunc)return NULL;

    precedent = NULL;
    courant = l->first;
    while(courant != NULL)
    {
        if((*cmpFunc)(courant->valeur,dv) == 0)
        {
            /*premier elem*/
            if(precedent == NULL)
            {
                l->first = courant->suivant;
                if(!courant->suivant)
                {
                    l->last = NULL;
                }
            }
            else
            {
                precedent->suivant = courant->suivant;
                if(!courant->suivant)
                {
                    l->last = precedent;
                }
            }
            (l->nbElem)--;
            return courant;
        }
        precedent = courant;
        courant = courant->suivant;
    }
    return NULL;
}

List *satisfyingSubList(List *l, SatisfyingFunc sf)
{
    ListNode *courant;
    List *subList;

    if(!l)return NULL;

	subList = newList(l->type);
	courant = l->first;
	while(courant != NULL)
	{
		if(sf(courant->valeur))
		{
			addAsLast(subList,courant->valeur);
		}
		courant = courant->suivant;
	}
	return subList;
}

ListNode *getElement(const List *l, int(*cmpFunc)(DataValue,DataValue),const DataValue dv)
{
    ListNode *courant;

    if(!l || !cmpFunc)return NULL;

    courant = l->first;
    while(courant != NULL)
    {
        if((*cmpFunc)(courant->valeur,dv) == 0)
            return courant;
        courant=courant->suivant;
    }
    return NULL;
}

ListNode *removeElementByIndex(List *l, int ind)
{
    int i = 0;
    ListNode *courant,*prec = NULL;

    if(!l || ind < 0 || ind >= l->nbElem) return NULL;

    courant = l->first;
    while(courant != NULL)
    {
        if(i == ind)
        {
            if(prec == NULL)
            {
                l->first = courant->suivant;
            }
            else
            {
                prec->suivant = courant->suivant;
            }
            (l->nbElem)--;
            return courant;
        }
        prec = courant;
        courant = courant->suivant;
        i++;
    }
    return NULL;
}

int getIndex(const List *l, DataValue dv, int(*cmpFunc)(DataValue,DataValue))
{
    ListNode *courant;
    int i = 0;

    if(!l || !cmpFunc) return -1;

    courant = l->first;
    while(courant != NULL)
    {
        if((*cmpFunc)(courant->valeur,dv) == 0)
        {
            return i;
        }
        courant = courant->suivant;
        i++;
    }
    return -1;
}
