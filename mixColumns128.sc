#define DEBUG_MIX 1

#if DEBUG_MIX
#include <stdio.h>
#endif

#include "shared.h"

behavior mixColumns128(in unsigned char block_in[16], inout unsigned char block_out[16]) {
    
    void mixColumn(unsigned char *r) {
        unsigned char a[4];
        unsigned char b[4];
        unsigned char c;
        unsigned char h;    

        for(c = 0; c < 4; c++) {
            a[c] = r[c];
            h = r[c] & 0x80; /* hi bit */
            b[c] = r[c] << 1;
            if(h == 0x80) 
                b[c] ^= 0x1B; /* Rijndael's Galois field */
        }

        r[0] = b[0] ^ a[3] ^ a[2] ^ b[1] ^ a[1];
        r[1] = b[1] ^ a[0] ^ a[3] ^ b[2] ^ a[2];
        r[2] = b[2] ^ a[1] ^ a[0] ^ b[3] ^ a[3];
        r[3] = b[3] ^ a[2] ^ a[1] ^ b[0] ^ a[0];
    }


    void main(void) {
#if DEBUG_MIX
	    int count = 0;
#endif
      int i;
#if DEBUG_MIX
		printf("MixColumns received block %u\n", ++count);
		printf("MixColumns block data received:\n");
		for (i = 0; i < 16; i++){
			printf("%02hhx ", block_in[i]);
		}
		printf("\n");
#endif

    // copy input to output
    for (i = 0; i < 16; i++) {
      block_out[i] = block_in[i];
    }

    // mixcolumns on output block
    for (i = 0; i < 4; i++) {
        mixColumn(block_out + (i*4));
    }

#if DEBUG_MIX
		printf("MixColumns sent block %u\n", count);
		printf("MixColumns block data sent:\n");
		for (i = 0; i < 16; i++){
			printf("%02hhx ", block_out[i]);
		}
		printf("\n");
#endif
    }
};
