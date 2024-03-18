#include <stdio.h>
#include "graf.h"
#include <string.h>

short int* wyznaczRozmiar (FILE* plik){
    plik = fopen(plik,"r");
    if (plik == NULL){
        printf("Blad wczytywania pliku %s\n",plik);
    }
    short int a = 0;
    short int b = 0;
    char c;
    while (c = fgetc(plik) != EOF){
        if (c == '\n'){
            b++;
        }
        else {
            a++;
        }
    }
    short int* rozmiar[2] = {a,b};
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
    return 0; //to nie jest ani rozgalezienie ani zakret
    
    //całe te ify nie maja sensu, wystarczy return ilosc i jak nie ma rozgalezien to i tak bedzie 0 jak ma 2 to ma 2 czyli zakret, jak
    // wiecej to wyswietli sie wiecej...

    //ten dlugi if jest zeby rozroznic miedzy prosta droga a zakretem
}

int dobryZnak(char c){
    if (c == 'X' || c == 'P' || c == 'K' || c == ' '){
        return 1;
    }
    return 0;
}

void stworzPunkty(char** buf,short int* rozmiar, FILE* plik){
    char i = 0;
    char bladSkladni = 0;
    short int index = 0;
    fopen(plik,"r");
    fgets(buf[0], rozmiar[1], plik);
    fgets(buf[1], rozmiar[1], plik);
    fgets(buf[2], rozmiar[1], plik);

    while (index+1 != rozmiar[0]){// dopoki nie skonczy sie plik (wczytywanie w pionie); index oraz romiar[1] to pion, i oraz rozmiar[0] to poziom
        if (index == 0){ //to jest pierwsza linijka
            for (int i = 1; i < rozmiar[0]-1; i++){ //przeczytaj całą linijkę i sprawdź, czy to nie jest przypadkiem rozgałęzienie
                if (!(dobryZnak(buf[index][i]))) bladSkladni = 1;
                if(wyznaczRozgalezienia(buf,0,i,'G') != 0){
                    init_node(index,i);
                }
            }
        }
        else if (index == rozmiar[1] - 1){ //to jest ostatnia linijka        
            for (int i = 1; i < rozmiar[0]-1; i++){
                if (!(dobryZnak(buf[index][i]))) bladSkladni = 1;
                if(wyznaczRozgalezienia(buf,2,i,'D') != 0){
                    init_node(index,i);
                }
            }
            break;
        }
        else{ //to nie jest pierwsza ani ostatnia linijka
            for (int i = 1; i < rozmiar[0]-1; i++){
                if (!(dobryZnak(buf[index][i]))) bladSkladni = 1;
                if(wyznaczRozgalezienia(buf,1,i,'W') != 0){
                    init_node(index,i);
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
}