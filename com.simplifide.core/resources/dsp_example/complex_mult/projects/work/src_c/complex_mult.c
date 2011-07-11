#include "dsp_basic.h"

void complex_mult2_fixed(int ind,float Ar,float Ai,float Br,float Bi,float *Zr,float *Zi) { 

float Xr;
static float Xr_1 = 0.0;
float Xi;
static float Xi_1 = 0.0;
float Yr;
static float Yr_1 = 0.0;
float Yi;
static float Yi_1 = 0.0;
static float Zr_1 = 0.0;
static float Zi_1 = 0.0;
float Xr_m0;
switch(ind) {
    case 0 : 
        Xr_m0 = Ar;
         break;
    case 1 : 
        Xr_m0 = Ai;
         break;
}
float Xr_m1;
switch(ind) {
    case 0 : 
        Xr_m1 = Br;
         break;
    case 1 : 
        Xr_m1 = Bi;
         break;
}
float XrM = Xr_m0 * Xr_m1;
float XrR = trunc_fixed(XrM,16,8,16,8);
Xr = XrR;
float Yr_m0;
switch(ind) {
    case 0 : 
        Yr_m0 = Yr_1;
         break;
    case 1 : 
        Yr_m0 = 0.0;
         break;
}
float Yr_m1;
switch(ind) {
    case 0 : 
        Yr_m1 = -Xr_1;
         break;
    case 1 : 
        Yr_m1 = Xr_1;
         break;
}
float YrRR_i0R = trunc_fixed(select_fixed(Yr_m0,8,2,9,-6) + Yr_m1,16,8,16,8);
float YrR = round_fixed(YrRR_i0R,8,2,16,8);
float YrC = clip(YrR,5,14);
Yr = select_fixed(YrC,14,8,13,6);
float Zr_m0;
switch(ind) {
    case 0 : 
        Zr_m0 = Zr_1;
         break;
    case 1 : 
        Zr_m0 = Yr_1;
         break;
}
*Zr = Zr_m0;
float Xi_m0;
switch(ind) {
    case 0 : 
        Xi_m0 = Ar;
         break;
    case 1 : 
        Xi_m0 = Ai;
         break;
}
float Xi_m1;
switch(ind) {
    case 0 : 
        Xi_m1 = Bi;
         break;
    case 1 : 
        Xi_m1 = Br;
         break;
}
float XiM = Xi_m0 * Xi_m1;
float XiR = trunc_fixed(XiM,16,8,16,8);
Xi = XiR;
float Yi_m0;
switch(ind) {
    case 0 : 
        Yi_m0 = Yi_1;
         break;
    case 1 : 
        Yi_m0 = 0.0;
         break;
}
float Yi_m1;
switch(ind) {
    case 0 : 
        Yi_m1 = Xi_1;
         break;
    case 1 : 
        Yi_m1 = Xi_1;
         break;
}
float YiRR_i0R = trunc_fixed(select_fixed(Yi_m0,8,2,9,-6) + Yi_m1,16,8,16,8);
float YiR = round_fixed(YiRR_i0R,8,2,16,8);
float YiC = clip(YiR,5,14);
Yi = select_fixed(YiC,14,8,13,6);
float Zi_m0;
switch(ind) {
    case 0 : 
        Zi_m0 = Zi_1;
         break;
    case 1 : 
        Zi_m0 = Yi_1;
         break;
}
*Zi = Zi_m0;
// Signal Delay
Zi_1 = *Zi;
Zr_1 = *Zr;
Yi_1 = Yi;
Yr_1 = Yr;
Xi_1 = Xi;
Xr_1 = Xr;
}

void complex_mult2_float(int ind,float Ar,float Ai,float Br,float Bi,float *Zr,float *Zi) { 

float Xr;
static float Xr_1 = 0.0;
float Xi;
static float Xi_1 = 0.0;
float Yr;
static float Yr_1 = 0.0;
float Yi;
static float Yi_1 = 0.0;
static float Zr_1 = 0.0;
static float Zi_1 = 0.0;
float Xr_m0;
switch(ind) {
    case 0 : 
        Xr_m0 = Ar;
         break;
    case 1 : 
        Xr_m0 = Ai;
         break;
}
float Xr_m1;
switch(ind) {
    case 0 : 
        Xr_m1 = Br;
         break;
    case 1 : 
        Xr_m1 = Bi;
         break;
}
float XrM = Xr_m0 * Xr_m1;
float XrR = trunc_float(XrM,16,8,16,8);
Xr = XrR;
float Yr_m0;
switch(ind) {
    case 0 : 
        Yr_m0 = Yr_1;
         break;
    case 1 : 
        Yr_m0 = 0.0;
         break;
}
float Yr_m1;
switch(ind) {
    case 0 : 
        Yr_m1 = -Xr_1;
         break;
    case 1 : 
        Yr_m1 = Xr_1;
         break;
}
float YrRR_i0R = trunc_float(select_float(Yr_m0,8,2,9,-6) + Yr_m1,16,8,16,8);
float YrR = round_float(YrRR_i0R,8,2,16,8);
float YrC = YrR;
Yr = select_float(YrC,14,8,13,6);
float Zr_m0;
switch(ind) {
    case 0 : 
        Zr_m0 = Zr_1;
         break;
    case 1 : 
        Zr_m0 = Yr_1;
         break;
}
*Zr = Zr_m0;
float Xi_m0;
switch(ind) {
    case 0 : 
        Xi_m0 = Ar;
         break;
    case 1 : 
        Xi_m0 = Ai;
         break;
}
float Xi_m1;
switch(ind) {
    case 0 : 
        Xi_m1 = Bi;
         break;
    case 1 : 
        Xi_m1 = Br;
         break;
}
float XiM = Xi_m0 * Xi_m1;
float XiR = trunc_float(XiM,16,8,16,8);
Xi = XiR;
float Yi_m0;
switch(ind) {
    case 0 : 
        Yi_m0 = Yi_1;
         break;
    case 1 : 
        Yi_m0 = 0.0;
         break;
}
float Yi_m1;
switch(ind) {
    case 0 : 
        Yi_m1 = Xi_1;
         break;
    case 1 : 
        Yi_m1 = Xi_1;
         break;
}
float YiRR_i0R = trunc_float(select_float(Yi_m0,8,2,9,-6) + Yi_m1,16,8,16,8);
float YiR = round_float(YiRR_i0R,8,2,16,8);
float YiC = YiR;
Yi = select_float(YiC,14,8,13,6);
float Zi_m0;
switch(ind) {
    case 0 : 
        Zi_m0 = Zi_1;
         break;
    case 1 : 
        Zi_m0 = Yi_1;
         break;
}
*Zi = Zi_m0;
// Signal Delay
Zi_1 = *Zi;
Zr_1 = *Zr;
Yi_1 = Yi;
Yr_1 = Yr;
Xi_1 = Xi;
Xr_1 = Xr;
}

