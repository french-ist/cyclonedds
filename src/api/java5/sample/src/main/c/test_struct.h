typedef struct _Point
{
  int x, y;
} Point;
typedef Point Point_by_value;

// Pointer-to-Structure Arguments
Point *oper_with_pointer_to_struct_args(Point *pt, int dx, int dy);

// Structure by Value Arguments/Return
Point_by_value oper_with_by_value_args(Point_by_value pt, int dx, int dy);

// Array-of-Structure Arguments
void get_points(struct _Point out[], int len);
void set_points(struct _Point out[], struct _Point in[], int len);

typedef struct _Items
{
  char code[10];
  char description[30];
  int stock;
} Items;

void get_items(struct _Items items[], int len);

// Returning an Array of struct
struct Point *return_points(int *pcount);
void free_points(Point *points);

// Nested Structure Definitions
// Nested structures are treated as consecutive memory
// (as opposed to pointers to structures)
typedef struct _Line
{
  Point start;
  Point end;
} Line;
// Pointer-to-Structure Arguments
Line *oper_with_nested_structs(Line *l, int dx, int dy);

// If you need a pointer to a structure within your structure, 
// you can use the Structure.ByReference tagging interface to 
//indicate the field should be treated as a pointer instead of 
// inlining the full structure.
// Original C code
typedef struct _Line2 {
  Point* start;
  Point* end;
} Line2;
// Pointer-to-Structure Arguments
void oper_with_nested_structs2(Line2 *l, int dx, int dy);