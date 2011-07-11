#include "dsp_basic.h"

void fir_fixed(float x,float *z) { 

static float x_1 = 0.0;
static float x_2 = 0.0;
// z[n] = alpha*x[n] + beta*x[n-1] + alpha*x[n-2];
float zRR_i0M = select_fixed_scale(x,8,4,11,-4,1);
float zRR_i0R = trunc_fixed(select_fixed(zRR_i0M,16,8,11,4),8,4,8,4);
float zRR_i0 = zRR_i0R;
float zRR_i1M = select_fixed_scale(x_1,8,4,11,-4,0) + select_fixed_scale(x_1,8,4,11,-4,1);
float zRR_i1R = trunc_fixed(select_fixed(zRR_i1M,16,8,11,4),8,4,8,4);
float zRR_i1 = zRR_i1R;
float zRR_i2M = select_fixed_scale(x_2,8,4,11,-4,1);
float zRR_i2R = trunc_fixed(select_fixed(zRR_i2M,16,8,11,4),8,4,8,4);
float zRR_i2 = zRR_i2R;
float zR = trunc_fixed(zRR_i0 + zRR_i1 + zRR_i2,8,4,8,4);
*z = zR;
// Signal Delay
x_2 = x_1;
x_1 = x;
}

void fir_float(float x,float *z) { 

static float x_1 = 0.0;
static float x_2 = 0.0;
// z[n] = alpha*x[n] + beta*x[n-1] + alpha*x[n-2];
float zRR_i0M = select_float_scale(x,8,4,11,-4,1);
float zRR_i0R = trunc_float(select_float(zRR_i0M,16,8,11,4),8,4,8,4);
float zRR_i0 = zRR_i0R;
float zRR_i1M = select_float_scale(x_1,8,4,11,-4,0) + select_float_scale(x_1,8,4,11,-4,1);
float zRR_i1R = trunc_float(select_float(zRR_i1M,16,8,11,4),8,4,8,4);
float zRR_i1 = zRR_i1R;
float zRR_i2M = select_float_scale(x_2,8,4,11,-4,1);
float zRR_i2R = trunc_float(select_float(zRR_i2M,16,8,11,4),8,4,8,4);
float zRR_i2 = zRR_i2R;
float zR = trunc_float(zRR_i0 + zRR_i1 + zRR_i2,8,4,8,4);
*z = zR;
// Signal Delay
x_2 = x_1;
x_1 = x;
}

