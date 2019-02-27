// Send a Struct Containing an Array of Structs to C
#ifndef _EXAMPLE_15_
#define _EXAMPLE_15_
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Example15StructA
{
    int val;
} Example15StructA;

typedef struct Example15StructB
{
    int numAs;
    Example15StructA *as;
} Example15StructB;

// Send a Struct Containing an Array of Structs to C
void example15_send(const Example15StructB *pVal);

// Receive a Struct Containing an Array of Structs from C
Example15StructB example15_get();

// cleanup
void example15_cleanup(Example15StructB sVal);
}
#endif /* _EXAMPLE_15_ */
