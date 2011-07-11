#include "dsp_basic.h"

void iir_fixed(float x,float a0,float a1,float b0,float b1,float b2,float *z) { 

float y;
static float y_1 = 0.0;
static float y_2 = 0.0;
static float y_3 = 0.0;
// y[n]  =  RC<16,8>(x[n] + RC<16,8>(a0*y[n-1]) + RC<16,8>(a1*y[n-2]));
float yRR_i0RR_i1RR_i0M = a0 * y_1;
float yRR_i0RR_i1RR_i0R = trunc_fixed(yRR_i0RR_i1RR_i0M,16,8,16,8);
float yRR_i0RR_i1R = round_fixed(yRR_i0RR_i1RR_i0R,16,8,16,8);
float yRR_i0RR_i1 = yRR_i0RR_i1R;
float yRR_i0RR_i2RR_i0M = a1 * y_2;
float yRR_i0RR_i2RR_i0R = trunc_fixed(yRR_i0RR_i2RR_i0M,16,8,16,8);
float yRR_i0RR_i2R = round_fixed(yRR_i0RR_i2RR_i0R,16,8,16,8);
float yRR_i0RR_i2 = yRR_i0RR_i2R;
float yRR_i0R = trunc_fixed(select_fixed(x,8,4,11,-4) + yRR_i0RR_i1 + yRR_i0RR_i2,16,8,16,8);
float yR = round_fixed(yRR_i0R,8,4,16,8);
float yC = clip(yR,3,12);
y = select_fixed(yC,12,8,11,4);
// z[n]  =  RC<16,8>(RC<16,8>(b0*y[n-1]) + RC<16,8>(b1*y[n-2]) + RC<16,8>(b2*y[n-3]));
float zRR_i0RR_i0RR_i0M = b0 * y_1;
float zRR_i0RR_i0RR_i0R = trunc_fixed(zRR_i0RR_i0RR_i0M,16,8,16,8);
float zRR_i0RR_i0R = round_fixed(zRR_i0RR_i0RR_i0R,16,8,16,8);
float zRR_i0RR_i0 = zRR_i0RR_i0R;
float zRR_i0RR_i1RR_i0M = b1 * y_2;
float zRR_i0RR_i1RR_i0R = trunc_fixed(zRR_i0RR_i1RR_i0M,16,8,16,8);
float zRR_i0RR_i1R = round_fixed(zRR_i0RR_i1RR_i0R,16,8,16,8);
float zRR_i0RR_i1 = zRR_i0RR_i1R;
float zRR_i0RR_i2RR_i0M = b2 * y_3;
float zRR_i0RR_i2RR_i0R = trunc_fixed(zRR_i0RR_i2RR_i0M,16,8,16,8);
float zRR_i0RR_i2R = round_fixed(zRR_i0RR_i2RR_i0R,16,8,16,8);
float zRR_i0RR_i2 = zRR_i0RR_i2R;
float zRR_i0R = trunc_fixed(zRR_i0RR_i0 + zRR_i0RR_i1 + zRR_i0RR_i2,16,8,16,8);
float zR = round_fixed(zRR_i0R,8,4,16,8);
float zC = clip(zR,3,12);
*z = select_fixed(zC,12,8,11,4);
// Signal Delay
y_3 = y_2;
y_2 = y_1;
y_1 = y;
}

void iir_float(float x,float a0,float a1,float b0,float b1,float b2,float *z) { 

float y;
static float y_1 = 0.0;
static float y_2 = 0.0;
static float y_3 = 0.0;
// y[n]  =  RC<16,8>(x[n] + RC<16,8>(a0*y[n-1]) + RC<16,8>(a1*y[n-2]));
float yRR_i0RR_i1RR_i0M = a0 * y_1;
float yRR_i0RR_i1RR_i0R = trunc_float(yRR_i0RR_i1RR_i0M,16,8,16,8);
float yRR_i0RR_i1R = round_float(yRR_i0RR_i1RR_i0R,16,8,16,8);
float yRR_i0RR_i1 = yRR_i0RR_i1R;
float yRR_i0RR_i2RR_i0M = a1 * y_2;
float yRR_i0RR_i2RR_i0R = trunc_float(yRR_i0RR_i2RR_i0M,16,8,16,8);
float yRR_i0RR_i2R = round_float(yRR_i0RR_i2RR_i0R,16,8,16,8);
float yRR_i0RR_i2 = yRR_i0RR_i2R;
float yRR_i0R = trunc_float(select_float(x,8,4,11,-4) + yRR_i0RR_i1 + yRR_i0RR_i2,16,8,16,8);
float yR = round_float(yRR_i0R,8,4,16,8);
float yC = yR;
y = select_float(yC,12,8,11,4);
// z[n]  =  RC<16,8>(RC<16,8>(b0*y[n-1]) + RC<16,8>(b1*y[n-2]) + RC<16,8>(b2*y[n-3]));
float zRR_i0RR_i0RR_i0M = b0 * y_1;
float zRR_i0RR_i0RR_i0R = trunc_float(zRR_i0RR_i0RR_i0M,16,8,16,8);
float zRR_i0RR_i0R = round_float(zRR_i0RR_i0RR_i0R,16,8,16,8);
float zRR_i0RR_i0 = zRR_i0RR_i0R;
float zRR_i0RR_i1RR_i0M = b1 * y_2;
float zRR_i0RR_i1RR_i0R = trunc_float(zRR_i0RR_i1RR_i0M,16,8,16,8);
float zRR_i0RR_i1R = round_float(zRR_i0RR_i1RR_i0R,16,8,16,8);
float zRR_i0RR_i1 = zRR_i0RR_i1R;
float zRR_i0RR_i2RR_i0M = b2 * y_3;
float zRR_i0RR_i2RR_i0R = trunc_float(zRR_i0RR_i2RR_i0M,16,8,16,8);
float zRR_i0RR_i2R = round_float(zRR_i0RR_i2RR_i0R,16,8,16,8);
float zRR_i0RR_i2 = zRR_i0RR_i2R;
float zRR_i0R = trunc_float(zRR_i0RR_i0 + zRR_i0RR_i1 + zRR_i0RR_i2,16,8,16,8);
float zR = round_float(zRR_i0R,8,4,16,8);
float zC = zR;
*z = select_float(zC,12,8,11,4);
// Signal Delay
y_3 = y_2;
y_2 = y_1;
y_1 = y;
}

