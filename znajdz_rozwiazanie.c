#include <stdio.h>
#include <stdbool.h>
#include "graf.h"
#include "rozwiazanie.h"

bool dfs(node_t *current, node_t *target, list_t *path) {
    
    if (current == target) {
        *path = append(*path, current->x, current->y);
        return true;
    }

    for (int i = 0; i < current->count; i++) {
        *path = append(*path, current->x, current->y);
        if (dfs(current->links[i], target, path)) {
            return true;
        }
        
    }
    if (*path != NULL) {
        *path = (*path)->next;
    }
    return false;
}