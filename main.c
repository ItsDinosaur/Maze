#include "graf.h"
#include <stdio.h>
#include <stdlib.h>
int main(int argc, char **argv) {
  node_t *nowy = init_node(0, 0);
  for (int i = 0; i < 10; i++) {
    increase(nowy);
    printf("|%d\n", nowy->count);
  }
  return 0;
}
