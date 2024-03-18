#ifndef _ROZWIAZANIE_H
#define _ROZWIAZANIE_H

typedef struct r{
    struct r * next;
    short x,y;
} *list_t;

list_t append(list_t l, short x, short y);

#endif