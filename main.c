#include "graf.h"
#include "pomoc.h"
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include "znajdz_rozwiazanie.h"
#include "rozwiazanie.h"
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

  // file handler function(s)

  // I assume they come all linked already here

  // Search the way out
  

  // PRZETESTOWANIE LINKOWANIA / DZIAŁA
  node_t *nowy = init_node(0, 0);
  for (int i = 1; i < 11; i++) {
    node_t *next = init_node(i, i);
    link_nodes(nowy, next);
    /*for(int j=0; j<nowy->count; j++){
      printf("%d -> %d, %d\n", j, nowy->links[j]->x, nowy->links[j]->y);
    }*/
  }
  node_t *last = init_node(15,10);
  node_t *mid = init_node(3,2);
  link_nodes(nowy, mid);
  link_nodes(mid, last);
  
  list_t path = malloc(sizeof *path);
  path->x = -10;
  path->y = -10;
  path->next = NULL;
  
  printf("DFS Path:\n");
  if (!dfs(nowy, last, path))
    printf("No path found from first to last node.\n");
  else
    pr_moves(path);
  return 0;
}
void pr_moves(list_t path){
    printf("Moves:\n");
    while (path != NULL) {
        printf("(%d, %d) -> ", path->x, path->y);
        path = path->next;
    }
    printf("Finish\n");
  }