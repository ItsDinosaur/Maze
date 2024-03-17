#include <stdio.h>
#include <stdbool.h>
#include "graf.h"

bool dfs(node_t *current, node_t *target) {
    if (current == target) {
        printf("Node (%d, %d) found!\n", current->x, current->y);
        return true;
    }

    for (int i = 0; i < current->count; i++) {
        if (dfs(current->links[i], target)) {
            printf("Node (%d, %d) found!\n", current->x, current->y);
            return true;
        }
    }

    return false;
}