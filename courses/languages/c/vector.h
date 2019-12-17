
// Supports pointers and values up to 32 bits.

typedef struct VECTOR {
    long* ptr;
    long* begin;
    long* end;
} vector;

vector* vect_new();
void vect_push(vector* v, long val); 
long vect_pop(vector* v);

long vect_get(vector* v, int index);

long* vect_begin(vector* v);
int vect_size(vector* v);
long* vect_end(vector* v);
