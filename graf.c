#include "graf.h"
#include <stdio.h>
#include <stdlib.h>

node_t *init_node(short x, short y) {
  node_t *n = malloc(sizeof(node_t *));
  n->count = 0;
  n->x = x;
  n->y = y;
  return n;
}

void increase(node_t *node) {
  if (node->count == 0) {
    node->links = malloc(sizeof(node_t *));
  } else {
    node->links = realloc(node->links, sizeof(node_t *) * (node->count + 1));
  }
  node->count++;
}

void link_nodes(node_t *parent, node_t *child){
  increase(parent);
  parent->links[parent->count- 1 ] = child;
}
