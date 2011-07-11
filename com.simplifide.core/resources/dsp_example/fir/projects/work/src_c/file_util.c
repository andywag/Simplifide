/*
 * file_util.c
 *
 *  Created on: Oct 30, 2010
 *      Author: Andy
 */

#include <stdio.h>
#include <stdlib.h>

float* loadFile(char* name, int length) {
	int i;
	float* out = (float*) malloc(length*sizeof(float));
	FILE* fop = fopen(name,"r");
	float* temp = (float*) malloc(length*sizeof(int));
	for (i =0;i<length;i++) {
		fscanf(fop,"%f\n",temp);
		out[i] = *temp;
	}
	fclose(fop);
	return out;
}
