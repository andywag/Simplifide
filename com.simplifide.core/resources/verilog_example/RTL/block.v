
/** Module : block
 *  Author : Andy
 *  Date   : Sun, 21 Oct 2007 15:00:25 -0700
 */

/** Test Block
 *  @port clk Main clk
 *  @port reset Main reset
 *  @port in1 Test Input
 *  @port in2 Test Input
 *  @port out1 Test Output
 *  @port out2 Test Output
 */
module block (/*AUTOARGS*/
         clk,
         in1,
         in2,
         reset,
         out1,
         out2); 

input clk, reset;
input in1, in2;

output out1, out2;

endmodule
