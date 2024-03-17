#include "graf.h"
#include "pomoc.h"
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
int main(int argc, char **argv) {
  int opt;
  FILE *in;
  char *fileInputName, fileOutputName;
  char trybSzukania;
  char isGood=0;
  
  while((opt = getopt(argc, argv, "ho:f:s:")) != -1){
    switch (opt)
    {
      case 'h':
          wyswietl_pomoc();
          return 0;
        break;
      case 'f':
        fileInputName = optarg;
        isGood=1;
        break;
      case 'o':
        fileOutputName = optarg;
        break;
      case 's':
        trybSzukania = atoi(optarg);
        if(trybSzukania != 1 && trybSzukania != 2){
          fprintf(stderr, "Flaga -s przyjmuje wartosci z zakresu {1, 2}\n");
          wyswietl_pomoc();
          return 1;
        }
        break;
      default:
        break;
    }
  }
  if (argc == 1 || isGood != 1) {
    fprintf(stderr, "Brak flagi obowiązkowej -f\n");
    wyswietl_pomoc();
    return 1;
  }
  /* PRZETESTOWANIE LINKOWANIA / DZIAŁA
  node_t *nowy = init_node(0, 0);
  for (int i = 0; i < 10; i++) {
    node_t *next = init_node(i, i);
    link_nodes(nowy, next);
    for(int j=0; j<nowy->count; j++){
      printf("%d -> %d, %d\n", j, nowy->links[j]->x, nowy->links[j]->y);
    }
  }
  */


  return 0;
}
