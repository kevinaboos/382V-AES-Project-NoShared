#define DEBUG_TEST 0

#include <stdio.h>

import "stimulus";
import "design";

behavior AES (){

  unsigned char input_block[16];
  //unsigned char IV_block[16];
  unsigned char input_key[16];
  unsigned char output_block[16];
 
  unsigned char mode;
  unsigned short iter = 0;


	//stimulus instance
	stimulus stim_inst(iter, mode, input_block, input_key, output_block);
	
	Design design_inst(mode, input_block, input_key, output_block);

  void printOutput() {
    int i;
    for (i=0; i < 16; i++) {
      printf("%02hhX", output_block[i]);
    }
  }

	void main (void) {
    //printOutput();
		fsm{
			//stimulus
			stim_inst: {goto design_inst;}
			// runs both AES128Enc and AES128Dec in parallel
			design_inst: {if (iter != 2001) goto stim_inst;
                    break;}
      }
	}

};


behavior Main (){
    AES aes;

    int main(void) {
        aes.main();
        return 0;
    }


};
