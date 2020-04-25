
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
	
}

template<uint16_t N>
static inline char BitField<N>::popcnt(unsigned long long word) const {
	#if __has_builtin(__builtin_popcountll)
		return __builtin_popcountll(word);
	#else
		unsigned long long copy = word;
		copy = (copy & 0x5555555555555555LL) + ((copy >> 1) & 0x5555555555555555LL);
		copy = (copy & 0x3333333333333333LL) + ((copy >> 2) & 0x3333333333333333LL);
		copy = (copy & 0x0F0F0F0F0F0F0F0FLL) + ((copy >> 4) & 0x0F0F0F0F0F0F0F0FLL);
		copy = (copy & 0x00FF00FF00FF00FFLL) + ((copy >> 8) & 0x00FF00FF00FF00FFLL);
		copy = (copy & 0x0000FFFF0000FFFFLL) + ((copy >> 16) & 0x0000FFFF0000FFFFLL);
		copy = (copy & 0x00000000FFFFFFFFLL) + ((copy >> 32) & 0x00000000FFFFFFFF);
		return copy;
	#endif
}

int main() {
	BitField<64> bits;
	bits.set(1);
	bits.set(2);
	printf("%d", bits.check(2));
}
