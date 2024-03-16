#include "graf.h"
#include "pomoc.h"
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
int main(int argc, char **argv) {
  int opt;
  node_t *nowy = init_node(0, 0);
  for (int i = 0; i < 10; i++) {
    increase(nowy);
    printf("|%d\n", nowy->count);
  }

  while((opt = getopt(argc, argv, ":ho:f:s:")) != -1){
    switch (opt)
    {
      case 'h':
          wyswietl_pomoc();
          return 0;
        break;
      case '?':
        printf("Nieznana opcja: %c. Zajrzyj do help -h\n", optopt);
        break;
      case ':':
        printf("Brak wartosci do opcji %c\n", optopt);
        break;
      default:
        fprintf(stderr, "Brak flagi obowiązkowej -f\n");
    }
  }

  return 0;
}
