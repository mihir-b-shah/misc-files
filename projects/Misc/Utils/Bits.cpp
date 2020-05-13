
#include <cstdint>
#include <cstring>
#include <cstdio>

// stack allocated.
template<uint16_t N>
class BitField {
	private:
		static const char LL_SIZE = 6;
		static const char MASK = 0x3f;
		static const uint16_t SET_SIZE = (N >> LL_SIZE) + ((N & MASK) != 0);
		uint64_t bits[SET_SIZE];
		static inline char popcnt(unsigned long long) const; 
    public:
        BitField();
        bool check(uint16_t bit) const;
        void set(uint16_t bit);
        uint16_t popCount() const;
};

template<uint16_t N>
BitField<N>::BitField(){
	memset(bits, 0LL, LL_SIZE);
}

/* bit ranges on [0, size), does not perform bounds checking */
template<uint16_t N>
bool BitField<N>::check(uint16_t bit) const {
	return (bits[bit >> LL_SIZE] & (1LL << (bit & MASK))) != 0;
}

template<uint16_t N>
void BitField<N>::set(uint16_t bit) {
    bits[bit >> LL_SIZE] |= 1LL << (bit & MASK);
}

template<uint16_t N>
uint16_t BitField<N>::popCount() const {
	uint16_t accm = 0;
	for(int i = 0; i<SET_SIZE; ++i) {
		accm += popcnt(bits[i]);
	}
	return accm;
}

static inline char popcnt(unsigned long long word) const {
	#if __has_builtin(__builtin_popcountll)
		return __builtin_popcountll(word);
	#else
		 unsigned long long y = word >>> 32;
         word -= ((word >>> 1) & 0x55555555);
         word = (word & 0x33333333) + ((word >>> 2) & 0x33333333);
         y -= ((y >>> 1) & 0x55555555);
         y = (y & 0x33333333) + ((y >>> 2) & 0x33333333);
         word += y;
         word = (word & 0x0F0F0F0F) + ((x >>> 4) & 0x0F0F0F0F);
         word += word >>> 8;
         word += word >>> 16;
         return word & 0x0000007F;
	#endif
}

int main() {
	BitField<64> bits;
	bits.set(1);
	bits.set(2);
	printf("%d", bits.popcnt()
}
