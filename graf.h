#ifndef _NODE_HANDLER_H
#define _NODE_HANDLER_H
typedef struct n {
  short x, y;
  struct n **links;
  char count;
} node_t;

node_t *init_node(short x, short y);
void increase(node_t *node);
void link_nodes(node_t *parent, node_t *child);

#endif
