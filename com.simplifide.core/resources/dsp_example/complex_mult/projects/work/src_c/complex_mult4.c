#include "dsp_basic.h"

void complex_mult4_fixed(int ind,float Ar,float Ai,float Br,float Bi,float *Zr,float *Zi) { 

float X;
static float X_1 = 0.0;
float Y;
static float Y_1 = 0.0;
float Zrt;
static float Zrt_1 = 0.0;
static float Zr_1 = 0.0;
static float Zi_1 = 0.0;
float X_m0;
switch(ind) {
    case 0 : 
        X_m0 = Ar;
         break;
    case 1 : 
        X_m0 = Ai;
         break;
    case 2 : 
        X_m0 = Ar;
         break;
    case 3 : 
        X_m0 = Ai;
         break;
}
float X_m1;
switch(ind) {
    case 0 : 
        X_m1 = Br;
         break;
    case 1 : 
        X_m1 = Bi;
         break;
    case 2 : 
        X_m1 = Bi;
         break;
    case 3 : 
        X_m1 = Br;
         break;
}
float XM = X_m0 * X_m1;
float XR = trunc_fixed(XM,16,8,16,8);
X = XR;
float Y_m0;
switch(ind) {
    case 0 : 
        Y_m0 = Y_1;
         break;
    case 1 : 
        Y_m0 = 0.0;
         break;
    case 2 : 
        Y_m0 = Y_1;
         break;
    case 3 : 
        Y_m0 = 0.0;
         break;
}
float Y_m1;
switch(ind) {
    case 0 : 
        Y_m1 = X_1;
         break;
    case 1 : 
        Y_m1 = X_1;
         break;
    case 2 : 
        Y_m1 = -X_1;
         break;
    case 3 : 
        Y_m1 = X_1;
         break;
}
float YRR_i0R = trunc_fixed(select_fixed(Y_m0,8,2,9,-6) + Y_m1,16,8,16,8);
float YR = round_fixed(YRR_i0R,8,2,16,8);
float YC = clip(YR,5,14);
Y = select_fixed(YC,14,8,13,6);
float Zrt_m0;
switch(ind) {
    case 0 : 
        Zrt_m0 = Zrt_1;
         break;
    case 1 : 
        Zrt_m0 = Zrt_1;
         break;
    case 2 : 
        Zrt_m0 = Zrt_1;
         break;
    case 3 : 
        Zrt_m0 = Y_1;
         break;
}
Zrt = Zrt_m0;
float Zr_m0;
switch(ind) {
    case 0 : 
        Zr_m0 = *Zr;
         break;
    case 1 : 
        Zr_m0 = Zrt;
         break;
    case 2 : 
        Zr_m0 = Zr_1;
         break;
    case 3 : 
        Zr_m0 = Zr_1;
         break;
}
*Zr = Zr_m0;
float Zi_m0;
switch(ind) {
    case 0 : 
        Zi_m0 = Zi_1;
         break;
    case 1 : 
        Zi_m0 = Y_1;
         break;
    case 2 : 
        Zi_m0 = Zi_1;
         break;
    case 3 : 
        Zi_m0 = Zi_1;
         break;
}
*Zi = Zi_m0;
// Signal Delay
Zi_1 = *Zi;
Zr_1 = *Zr;
Zrt_1 = Zrt;
Y_1 = Y;
X_1 = X;
}

void complex_mult4_float(int ind,float Ar,float Ai,float Br,float Bi,float *Zr,float *Zi) { 

float X;
static float X_1 = 0.0;
float Y;
static float Y_1 = 0.0;
float Zrt;
static float Zrt_1 = 0.0;
static float Zr_1 = 0.0;
static float Zi_1 = 0.0;
float X_m0;
switch(ind) {
    case 0 : 
        X_m0 = Ar;
         break;
    case 1 : 
        X_m0 = Ai;
         break;
    case 2 : 
        X_m0 = Ar;
         break;
    case 3 : 
        X_m0 = Ai;
         break;
}
float X_m1;
switch(ind) {
    case 0 : 
        X_m1 = Br;
         break;
    case 1 : 
        X_m1 = Bi;
         break;
    case 2 : 
        X_m1 = Bi;
         break;
    case 3 : 
        X_m1 = Br;
         break;
}
float XM = X_m0 * X_m1;
float XR = trunc_float(XM,16,8,16,8);
X = XR;
float Y_m0;
switch(ind) {
    case 0 : 
        Y_m0 = Y_1;
         break;
    case 1 : 
        Y_m0 = 0.0;
         break;
    case 2 : 
        Y_m0 = Y_1;
         break;
    case 3 : 
        Y_m0 = 0.0;
         break;
}
float Y_m1;
switch(ind) {
    case 0 : 
        Y_m1 = X_1;
         break;
    case 1 : 
        Y_m1 = X_1;
         break;
    case 2 : 
        Y_m1 = -X_1;
         break;
    case 3 : 
        Y_m1 = X_1;
         break;
}
float YRR_i0R = trunc_float(select_float(Y_m0,8,2,9,-6) + Y_m1,16,8,16,8);
float YR = round_float(YRR_i0R,8,2,16,8);
float YC = YR;
Y = select_float(YC,14,8,13,6);
float Zrt_m0;
switch(ind) {
    case 0 : 
        Zrt_m0 = Zrt_1;
         break;
    case 1 : 
        Zrt_m0 = Zrt_1;
         break;
    case 2 : 
        Zrt_m0 = Zrt_1;
         break;
    case 3 : 
        Zrt_m0 = Y_1;
         break;
}
Zrt = Zrt_m0;
float Zr_m0;
switch(ind) {
    case 0 : 
        Zr_m0 = *Zr;
         break;
    case 1 : 
        Zr_m0 = Zrt;
         break;
    case 2 : 
        Zr_m0 = Zr_1;
         break;
    case 3 : 
        Zr_m0 = Zr_1;
         break;
}
*Zr = Zr_m0;
float Zi_m0;
switch(ind) {
    case 0 : 
        Zi_m0 = Zi_1;
         break;
    case 1 : 
        Zi_m0 = Y_1;
         break;
    case 2 : 
        Zi_m0 = Zi_1;
         break;
    case 3 : 
        Zi_m0 = Zi_1;
         break;
}
*Zi = Zi_m0;
// Signal Delay
Zi_1 = *Zi;
Zr_1 = *Zr;
Zrt_1 = Zrt;
Y_1 = Y;
X_1 = X;
}

