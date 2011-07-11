/*
 * dsp_main.c
 *
 *  Created on: Oct 30, 2010
 *      Author: Andy
 */

#include <stdio.h>
#include <stdlib.h>
#include "file_util.h"
#include "complex_mult.h"
#include "complex_mult4.h"

int main() {

	int length = 20000;



	float* xr = loadFile("./data/xr_float.txt",length);
	float* xi = loadFile("./data/xi_float.txt",length);
	float* yr = loadFile("./data/yr_float.txt",length);
	float* yi = loadFile("./data/yi_float.txt",length);

	int i;
	int j;

	float* zr_fix    = (float*) malloc(sizeof(float));
	float* zi_fix    = (float*) malloc(sizeof(float));
	float* zr_fix4    = (float*) malloc(sizeof(float));
	float* zi_fix4    = (float*) malloc(sizeof(float));
	float* zr_float  = (float*) malloc(sizeof(float));
	float* zi_float  = (float*) malloc(sizeof(float));
	float* zr_float4    = (float*) malloc(sizeof(float));
	float* zi_float4    = (float*) malloc(sizeof(float));

	float uxr, uxi, uyr, uyi;
	FILE* fout = fopen("data/mult_out_c.txt","w");
	for (i=0;i<length;i++) {


		for (j=0;j<4;j++) {
			uxr = xr[i]; uxi = xi[i]; uyr = yr[i]; uyi = yi[i];
			complex_mult2_fixed(j % 2,uxr,uxi,uyr,uyi,zr_fix,zi_fix);
			complex_mult2_float(j % 2,uxr,uxi,uyr,uyi,zr_float,zi_float);
			complex_mult4_fixed(j,uxr,uxi,uyr,uyi,zr_fix4,zi_fix4);
			complex_mult4_float(j,uxr,uxi,uyr,uyi,zr_float4,zi_float4);
			fprintf(fout,"%f %f %f %f %f %f %f %f\n",xr[i],xi[i],yr[i],yi[i],
					*zr_fix,*zi_fix,*zr_fix4,*zi_fix4);
		}

	}
	fclose(fout);
	printf("Finished");
	return 0;

}
