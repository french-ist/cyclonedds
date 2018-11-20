#include "test_struct.h"
#include <stdlib.h>
#include <stdio.h>

// Pointer-to-Structure Arguments
Point *oper_with_pointer_to_struct_args(Point *pt, int dx, int dy)
{
    pt->x += dx;
    pt->y += dy;
    printf("[C] Pointer-to-Structure Arguments : x = %d - [C] y = %d\n", pt->x, pt->y);
    return pt;
}

// Structure by Value Arguments/Return
Point oper_with_by_value_args(Point pt, int dx, int dy)
{
    pt.x += dx;
    pt.y += dy;
    printf("[C] Structure by Value Arguments/Return : x = %d - [C] y = %d\n", pt.x, pt.y);
    return pt;
}

// Array-of-Structure Arguments 
void get_points(struct _Point out[], int len)
{
    for (int i = 0; i < len; i++)
    {
        out[i].x = 10 + i;
        out[i].y = 10 + i;
    }
}

void set_points(struct _Point out[], struct _Point in[], int len)
{
    printf("[C] Array-of-Structure Arguments (set from java): len = %d\n", len);
    for (int i = 0; i < len; i++)
    {
        out[i] = in[i];
        printf("[C] Array-of-Structure Arguments (set from java): x = %d - [C] y = %d\n", out[i].x, out[i].y);
    }
}

// Returning an Array of struct
struct Point *return_points(int *pcount)
{
    struct Point *res = malloc(sizeof(Point) * (*pcount));
    for (int i = 0; i < *pcount; i++)
    {
        ((Point *)res)[i].x = i + 1000;
        ((Point *)res)[i].y = i + 1000;
        printf("[C] Returning an Array of struct: x = %d - [C] y = %d\n", ((Point *)res)[i].x, ((Point *)res)[i].y);
    }
    return res;
}

void free_points(struct Point *points)
{
    printf("[C] freeing the returned memory\n");
    free(points);
}

// Nested Structure Definitions
Line *oper_with_nested_structs(Line *l, int dx, int dy)
{
    printf("[C] oper_with_nested_structs\n");
    l->start.x += dx;
    l->start.y += dy;
    l->end.x += dx;
    l->end.y += dy;
    return l;
}

// Nested Structure Definitions : pointer to a structure within your structure,
void oper_with_nested_structs2(Line2 *l, int dx, int dy)
{
    printf("[C] oper_with_nested_structs2\n");
    printf("[C] in    (%d, %d)    \t (%d, %d)\n", l->start->x, l->start->y, l->end->x, l->end->y);
    l->start->x += dx;
    l->start->y += dy;
    l->end->x += dx;
    l->end->y += dy;
    printf("[C] out   (%d, %d) \t (%d, %d)\n", l->start->x, l->start->y, l->end->x, l->end->y);
}
