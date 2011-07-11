/*
 * dsp_basic.c
 *
 *  Created on: Oct 30, 2010
 *      Author: Andy
 */

#include <math.h>

float overflow(float in, float max) {
	float value = in;
	if (value >= max) value = value - 2*max;   // Overflow
	if (value < -max)  value = value + 2*max;   // Overflow
	return value;
}

/** Overflows based on the maximum width */
float overflow_pow(float in, float index) {
	float max = pow(2.0,index);
	float uvalue = in + max;
	float dval = floor(uvalue/2.0/max);
	float tvalue = uvalue - 2.0*dval*max;
	float rvalue = tvalue - max;
	return rvalue;

}




float round_float(float in, int outwidth, int outfrac, int intwidth, int intfrac) {
	return in;
}

float round_fixed(float in, int outwidth, int outfrac, int intwidth, int intfrac) {
	//float tmax = pow(2.0,outwidth-outfrac-1);
	float use = overflow_pow(in, intwidth-intfrac);

	if (outfrac >= intfrac) return use;
	float rterm = pow(2.0,-outfrac-1);
	//float max   = pow(2.0, outwidth-outfrac-1);
	float res  = use + rterm;
	float tot  = overflow_pow(res,intwidth-intfrac);
	return tot;
}

/** Models a hardware clip to the maximum value associated with the
 *  input width.
 */
float clip(float in,int width,int tot) {
	float max = pow(2.0,width);
	float del = pow(2.0,width - tot+1);
	float out = in;
	if (in >= max) out = max - del;
	if (in < -max) out = -max + del;
	return out;
}


/** Models simple bit selection operation in rtl */
float select_fixed_scale(float in, int width, int frac, int top,  int shift, int scale) {
	float usc = pow(2.0,-scale);
	float ovalue = usc*in;
	if (shift > 0) { // Truncates the data if it is shifted away
		float scale = pow(2.0,shift-frac);       // Scale to Shift to integer
		float value = in/scale;              // Integer Value
		ovalue = scale*floor(value);       // Scale back to correct position
	}

	float uvalue = overflow_pow(ovalue,top-frac);       // Wrap the input value if necessary
	return uvalue;
}

float select_float_scale(float in, int frac, int top, int shift, int scale) {
	float usc = pow(2.0,-scale);
	return usc*in;
}

/** Models simple bit selection operation in rtl */
float select_fixed(float in, int width, int frac, int top,  int shift) {
	//float del   = pow(2.0,width);
	//float max   = pow(2.0,width - 1);        // Maximum Value
	float ovalue = in;
	if (shift > 0) { // Truncates the data if it is shifted away
		float scale = pow(2.0,shift-frac);       // Scale to Shift to integer
		float value = in/scale;              // Integer Value
		ovalue = scale*floor(value);       // Scale back to correct position
	}

	float uvalue = overflow_pow(ovalue,top-frac);       // Wrap the input value if necessary
	return uvalue;
}

float select_float(float in, int frac, int top, int shift) {
	return in;
}

float trunc_float(float in, int outwidth, int outfrac, int intwidth, int intfrac) {
	return select_float(in,outfrac,outwidth-1,0);
	//return in;
}

float trunc_fixed(float in, int outwidth, int outfrac, int intwidth, int intfrac) {
	return select_fixed(in,outwidth,outfrac,outwidth-1,0);
	//return in;
}


