#ifndef _WCZYTAJ_LABIRYNT_H
#define _WCZYTAJ_LABIRYNT_H

#include <stdio.h>

short int* wyznaczRozmiar (FILE* plik);
char** wczytajKolejny(char** buf, short int b);
char wyznaczRozgalezienia(char** buf, char r, char i, char strona);
void stworzPunkty(char** buf,short int* rozmiar);
int dobryZnak(char c);

#endif