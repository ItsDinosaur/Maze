#include <stdio.h>
#include "graf.h"
#include <string.h>
#include "znajdz_rozwiazanie.h"
#include <stdlib.h>

short int* wyznaczRozmiar (FILE* plik){
    short int* rozmiar = malloc (2*sizeof(short int));
    short int a = 0;
    short int b = 0;
    char c;
    while ((c = fgetc(plik)) != EOF){
        if (c == '\n'){
            b++;
            continue;
        }
        else {
            a++;
        }
    }
    rozmiar[0] = a/b;
    rozmiar[1] = b;
    printf("Rozmiar tego labiryntu to: %d x %d\n",rozmiar[0],rozmiar[1]);
    return rozmiar;
}

char** wczytajKolejny(char** buf, short int b, FILE* plik){ //jak blad to upewnic sie zeby nie wczytywac wiecej jak 3 rzedy przed koncem pliku, aka ta funkcja nie zajmuje sie tym, najwyzej dostaniesz NULLa
    strcpy(buf[1],buf[2]);
    strcpy(buf[2],buf[3]);
    fgets(buf[3], b, plik);
    return buf;
}

char wyznaczRozgalezienia(char** buf, char row, char collumn, char strona){
    #define zgory (buf[row][collumn-1] == ' ' || buf[row][collumn-1] == 'P' || buf[row][collumn-1] == 'K')
    #define zdolu (buf[row][collumn+1] == ' ' || buf[row][collumn+1] == 'P' || buf[row][collumn+1] == 'K')
    #define zlewej (buf[row-1][collumn] == ' ' || buf[row-1][collumn] == 'P' || buf[row-1][collumn] == 'K')
    #define zprawej (buf[row+1][collumn] == ' ' || buf[row+1][collumn] == 'P' || buf[row+1][collumn] == 'K')
    char ilosc = 0;
    
    if (zprawej) ilosc++;
    if (zlewej) ilosc++;
    if (strona == 'G'){
        if (zdolu) ilosc++;
    }
    else if (strona == 'D'){
        if (zgory) ilosc++;
    }
    else{
        if (zgory) ilosc++;
        if (zdolu) ilosc++;
    }
    
    

    if (ilosc > 2){ //to jest rozgalezienie
        return ilosc;
    }
    else{ //to jest zakret
        if (strona == 'G'){
            if (zdolu && zprawej || zdolu && zlewej) return 2;
        }
        else if (strona == 'D'){
            if (zgory && zprawej || zgory && zlewej) return 2;
        }
        else{
        if (zgory && zprawej || zprawej && zdolu || zdolu && zlewej || zlewej && zgory) return 2;
        }
    }
    return 0;
}

int dobryZnak(char c){
    if (c == 'X' || c == 'P' || c == 'K' || c == ' '){
        printf("'%c' to jest dobry znak\n",c);
        return 1;
    }
    printf("'%c' to zly znak\n",c);
    return 0;
}

node_t** stworzPunkty(char** buf,short int* rozmiar, FILE* plik){
    char i = 0;
    char bladSkladni = 0;
    short int index = 0;
    short indx = 0;
    node_t* punkty[100];
    fgets(buf[0], rozmiar[1], plik);
    fgets(buf[1], rozmiar[1], plik);
    fgets(buf[2], rozmiar[1], plik);

    while (index+1 != rozmiar[0]){// dopoki nie skonczy sie plik (wczytywanie w pionie); index oraz romiar[1] to pion, i oraz rozmiar[0] to poziom
        if (index == 0){ //to jest pierwsza linijka
            for (int i = 1; i < rozmiar[0]-1; i++){ //przeczytaj całą linijkę i sprawdź, czy to nie jest przypadkiem rozgałęzienie
                if (!(dobryZnak(buf[index][i]))) bladSkladni = 1;
                if(wyznaczRozgalezienia(buf,0,i,'G') != 0){
                    punkty[indx++] = init_node(index,i);
                }
            }
        }
        else if (index == rozmiar[1] - 1){ //to jest ostatnia linijka        
            for (int i = 1; i < rozmiar[0]-1; i++){
                if (!(dobryZnak(buf[index][i]))) bladSkladni = 1;
                if(wyznaczRozgalezienia(buf,2,i,'D') != 0){
                    punkty[indx++] = init_node(index,i);
                }
            }
            break;
        }
        else{ //to nie jest pierwsza ani ostatnia linijka
            for (int i = 1; i < rozmiar[0]-1; i++){
                if (!(dobryZnak(buf[index][i]))) bladSkladni = 1;
                if(wyznaczRozgalezienia(buf,1,i,'W') != 0){
                    punkty[indx++] = init_node(index,i);
                }
            }
        }
        if (bladSkladni == 1){
            printf("W labiryncie sa niepozadane znaki!\n");
            break;
        }
        index++;
        wczytajKolejny(buf,rozmiar[0],plik);
    }
    return punkty;
}