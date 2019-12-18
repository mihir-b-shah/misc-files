
// Supports pointers and values up to 32 bits.

typedef struct VECTOR {
    unsigned long* ptr;
    unsigned long* begin;
    unsigned long* end;
} vector;

vector* vect_new();
void vect_push(vector* v, unsigned long val); 
unsigned long vect_pop(vector* v);

unsigned long vect_get(vector* v, int index);

unsigned long* vect_begin(vector* v);
int vect_size(vector* v);
unsigned long* vect_end(vector* v);
