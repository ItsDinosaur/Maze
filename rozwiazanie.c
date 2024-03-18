#include <stdio.h>
#include <stdlib.h>
#include "rozwiazanie.h"

void append(list_t l, short x, short y) {
	if( l == NULL ) {
		list_t nowy = malloc( sizeof *nowy );
		nowy->x = x;
        nowy->y = y;
		nowy->next = l;
	} else {
		list_t it = l;
		while( it->next != NULL ) {
			it = it->next;
		}
		it->next = malloc( sizeof * it->next );
		it->next->x = x;
        it->next->y = y;
		it->next->next = NULL;
	}
}