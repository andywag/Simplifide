/*
 * dsp_basic.h
 *
 *  Created on: Oct 30, 2010
 *      Author: Andy
 */

#ifndef DSP_BASIC_H_
#define DSP_BASIC_H_

float select_fixed(float in, int width, int frac, int top, int shift);
float select_float(float in, int width, int frac, int top,  int shift);
float select_fixed_scale(float in, int width, int frac, int top, int shift, int scale);
float select_float_scale(float in, int width, int frac, int top,  int shift, int scale);
float trunc_fixed(float in, int outwidth, int outfrac, int intwidth, int intfrac);
float trunc_float(float in, int outwidth, int outfrac, int intwidth, int intfrac);
float round_fixed(float in, int outwidth, int outfrac, int intwidth, int intfrac);
float round_float(float in, int outwidth, int outfrac, int intwidth, int intfrac);
float clip(float in,int width,int tot);
#endif /* DSP_BASIC_H_ */
