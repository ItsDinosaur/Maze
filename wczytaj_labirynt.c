#include <stdio.h>
#include "graf.h"
#include <string.h>

short int* wyznaczRozmiar (FILE* plik){
    plik = fopen("plik","r");
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

char wyznaczRozgalezienia(char** buf, char r, char i){
    #define zgory (buf[r][i-1] == ' ' || buf[r][i-1] == 'P' || buf[r][i-1] == 'K')
    #define zdolu (buf[r][i+1] == ' ' || buf[r][i+1] == 'P' || buf[r][i+1] == 'K')
    #define zlewej (buf[r-1][i] == ' ' || buf[r-1][i] == 'P' || buf[r-1][i] == 'K')
    #define zprawej (buf[r+1][i] == ' ' || buf[r+1][i] == 'P' || buf[r+1][i] == 'K')
    char ilosc = 0;
    if (zgory) ilosc++;
    if (zdolu) ilosc++;
    if (zlewej) ilosc++;
    if (zprawej) ilosc++;

    if (ilosc > 2){ //to jest rozgalezienie
        return ilosc;
    }
    else{
        if (zgory && zprawej || zprawej && zdolu || zdolu && zlewej || zlewej && zgory) return 2; //to jest zakret
    }
    return 0; //to nie jest ani rozgalezienie ani zakret

    //całe te ify nie maja sensu, wystarczy return ilosc i jak nie ma rozgalezien to i tak bedzie 0 jak ma 2 to ma 2 czyli zakret, jak
    // wiecej to wyswietli sie wiecej...
}

void stworzPunkty(char** buf,short int* rozmiar, FILE* plik){
    char i = 0;
    short int index = 0;
    fopen(plik,"r");
    fgets(buf[0], rozmiar[1], plik);
    fgets(buf[1], rozmiar[1], plik);
    fgets(buf[2], rozmiar[1], plik);

    while (index+1 != rozmiar[0]){// dopoki nie skonczy sie plik (wczytywanie w pionie)
        if (index == 0){ //to jest pierwsza linijka (obecnie mazanie po pamieci, bo porownuje sie z niewczytanym miejscem; nie ma nic nad pierwszym rzedem)
            for (int i = 0; i < rozmiar[1]; i++){ //przeczytaj całą linijkę i sprawdź, czy to nie jest przypadkiem rozgałęzienie
                if(wyznaczRozgalezienia(buf,0,i) != 0){
                    init_node(index,i);
                }
            }
        }
        else if (index == rozmiar[1] - 1){ //to jest ostatnia linijka (obecnie mazanie po pamieci, bo porownuje sie z niewczytanym miejscem; nie ma nic pod ostatnim rzedem)           
            for (int i = 0; i < rozmiar[1]; i++){
                if(wyznaczRozgalezienia(buf,2,i) != 0){
                    init_node(index,i);
                }
            }
            break;
        }
        else{ //to nie jest pierwsza ani ostatnia linijka
            for (int i = 0; i < rozmiar[1]; i++){
                if(wyznaczRozgalezienia(buf,1,i) != 0){
                    init_node(index,i);
                }
            }
        }
        index++;
    }
}