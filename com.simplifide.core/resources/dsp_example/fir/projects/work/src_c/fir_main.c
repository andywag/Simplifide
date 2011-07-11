/*
 * dsp_main.c
 *
 *  Created on: Oct 30, 2010
 *      Author: Andy
 */

#include <stdio.h>
#include <stdlib.h>
#include "file_util.h"
#include "fir.h"

int main() {

	int length = 256*256; //10000;

	float* in1 = loadFile("./data/fir_float.txt",length);
	//float* in1 = (float*) malloc(length*sizeof(float));
	float* in2 = (float*) malloc(length*sizeof(float));

	int i;

	int j;
	/*int index = 0;
	for (i = 0;i<256;i++) {
		for (j=0;j<256;j++) {
			in1[index] = j/16.0-8.0;
			in2[index] = i/16.0-8.0;
			index++;
		}
	}*/

	FILE* fout = fopen("./data/fir_outc.txt","w");
	for (i=0;i<length;i++) {
		float input = in1[i];
		float* fixed = (float*) malloc(sizeof(float));
		float* flo   = (float*) malloc(sizeof(float));

		fir_fixed(input ,fixed);
		fir_float(input, flo);
		fprintf(fout,"%f %f %f %f\n",in1[i],*fixed,*flo);
	}
	fclose(fout);
	printf("finished");
	return 0;

}
