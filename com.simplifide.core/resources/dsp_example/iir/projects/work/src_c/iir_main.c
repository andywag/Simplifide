/*
 * dsp_main.c
 *
 *  Created on: Oct 30, 2010
 *      Author: Andy
 */

#include <stdio.h>
#include <stdlib.h>
#include "file_util.h"
#include "iir.h"

int main() {

	int length = 256*256;

	float* in1 = loadFile("./data/iir_float.txt",length);

	int i;

	FILE* fout = fopen("./data/iir_outc.txt","w");
	for (i=0;i<length;i++) {
		float input = in1[i];
		float* fixed = (float*) malloc(sizeof(float));
		float* flo   = (float*) malloc(sizeof(float));

		iir_fixed(input, -.5, -.5 , .5 , 1.0 , 0.5 ,fixed);
		iir_float(input, -.5, -.5 , .5 , 1.0 , 0.5, flo);
		fprintf(fout,"%f %f %f\n",in1[i],*fixed,*flo);
	}
	fclose(fout);
	printf("finished");
	return 0;

}
