#ifndef POMOC_H
#define POMOC_H

#include <stdio.h>

void wyswietl_pomoc() {
    printf("Użycie: ./maze -f [nazwa_pliku] [opcje]\n");
    printf("Flagi:\n");
    printf("    -h         Pokazuje pomoc do programu\n");
    printf("    -o [nazwa_pliku]   Wynik zostanie zapisany do podanego pliku. Brak tego argumentu oznacza wypisanie rozwiązania na standardowe wyjście.\n");
    printf("    -f [nazwa_pliku]   Określenie pliku wejściowego o podanej nazwie. W przypadku braku tego argumentu zostanie wyświetlona informacja o braku podania danych wejściowych oraz pomoc.\n");
    printf("    -s [liczba {1,2}]   Określenie trybu wyszukiwania ścieżki w labiryncie, gdzie:\n");
    printf("                  1 - opcja domyślna, czyli pierwsza znaleziona ścieżka\n");
    printf("                  2 - opcja pozwalająca znaleźć najkrótszą ścieżkę\n");
}

#endif