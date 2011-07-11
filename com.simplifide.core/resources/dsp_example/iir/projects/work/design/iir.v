 

module iir (clk,reset,enable,x,z,a0,a1,b0,b1,b2); 

output signed [7:0] z;
input  clk,reset,enable;
input  signed [7:0] x;

input signed [7:0] a0,a1;
input signed [7:0] b0,b1,b2;


/* simplifide 

clock_head clo {
    clock               "clk"        posedge
    reset               "reset"      sync             active_low
    enable              "enable"
}

dsp iir {

   clock_head clo;   
   fixed  <8,4>  iwidth;
   fixed  <16,8> intwidth;
   
   input  <iwidth>  x;
   input  <iwidth>  a0,a1; 
   input  <iwidth>  b0,b1,b2; 
   
   output <iwidth>  z; 
 
   signal <iwidth>  y[3];
   
   y[n]  =  RC<16,8>(x[n] + RC<16,8>(a0*y[n-1]) + RC<16,8>(a1*y[n-2]));
   z[n]  =  RC<16,8>(RC<16,8>(b0*y[n-1]) + RC<16,8>(b1*y[n-2]) + RC<16,8>(b2*y[n-3]));
}

*/
/* AUTO GENERATED (DO NOT EDIT MANUALLY) */
// Signal Declarations
wire  signed [7:0] x;
wire  signed [7:0] a0;
wire  signed [7:0] a1;
wire  signed [7:0] b0;
wire  signed [7:0] b1;
wire  signed [7:0] b2;
wire  signed [7:0] z;
wire  signed [7:0] y;
reg signed [7:0] y_1;
reg signed [7:0] y_2;
reg signed [7:0] y_3;
// y[n]  =  RC<16,8>(x[n] + RC<16,8>(a0*y[n-1]) + RC<16,8>(a1*y[n-2]));
wire signed [15:0] yRR_i0RR_i1RR_i0M = a0 * y_1;
wire signed [15:0] yRR_i0RR_i1RR_i0R = $signed(yRR_i0RR_i1RR_i0M[15:0]);
wire signed [15:0] yRR_i0RR_i1R = $signed(yRR_i0RR_i1RR_i0R[15:0]) + 16'sd0;
wire signed [15:0] yRR_i0RR_i1 = $signed(yRR_i0RR_i1R[15:0]);
wire signed [15:0] yRR_i0RR_i2RR_i0M = a1 * y_2;
wire signed [15:0] yRR_i0RR_i2RR_i0R = $signed(yRR_i0RR_i2RR_i0M[15:0]);
wire signed [15:0] yRR_i0RR_i2R = $signed(yRR_i0RR_i2RR_i0R[15:0]) + 16'sd0;
wire signed [15:0] yRR_i0RR_i2 = $signed(yRR_i0RR_i2R[15:0]);
wire signed [15:0] yRR_i0R = $signed({{4{x[7]}},x,4'd0}) + $signed(yRR_i0RR_i1[15:0]) + $signed(yRR_i0RR_i2[15:0]);
wire signed [15:0] yR = $signed(yRR_i0R[15:0]) + 16'sd8;
wire signed [11:0] yC = (yR[15] && ~yR[14:11]) ? -12'd2047 : (~yR[15] && yR[14:11]) ? 12'd2047 : yR;
assign y = $signed(yC[11:4]);
// z[n]  =  RC<16,8>(RC<16,8>(b0*y[n-1]) + RC<16,8>(b1*y[n-2]) + RC<16,8>(b2*y[n-3]));
wire signed [15:0] zRR_i0RR_i0RR_i0M = b0 * y_1;
wire signed [15:0] zRR_i0RR_i0RR_i0R = $signed(zRR_i0RR_i0RR_i0M[15:0]);
wire signed [15:0] zRR_i0RR_i0R = $signed(zRR_i0RR_i0RR_i0R[15:0]) + 16'sd0;
wire signed [15:0] zRR_i0RR_i0 = $signed(zRR_i0RR_i0R[15:0]);
wire signed [15:0] zRR_i0RR_i1RR_i0M = b1 * y_2;
wire signed [15:0] zRR_i0RR_i1RR_i0R = $signed(zRR_i0RR_i1RR_i0M[15:0]);
wire signed [15:0] zRR_i0RR_i1R = $signed(zRR_i0RR_i1RR_i0R[15:0]) + 16'sd0;
wire signed [15:0] zRR_i0RR_i1 = $signed(zRR_i0RR_i1R[15:0]);
wire signed [15:0] zRR_i0RR_i2RR_i0M = b2 * y_3;
wire signed [15:0] zRR_i0RR_i2RR_i0R = $signed(zRR_i0RR_i2RR_i0M[15:0]);
wire signed [15:0] zRR_i0RR_i2R = $signed(zRR_i0RR_i2RR_i0R[15:0]) + 16'sd0;
wire signed [15:0] zRR_i0RR_i2 = $signed(zRR_i0RR_i2R[15:0]);
wire signed [15:0] zRR_i0R = $signed(zRR_i0RR_i0[15:0]) + $signed(zRR_i0RR_i1[15:0]) + $signed(zRR_i0RR_i2[15:0]);
wire signed [15:0] zR = $signed(zRR_i0R[15:0]) + 16'sd8;
wire signed [11:0] zC = (zR[15] && ~zR[14:11]) ? -12'd2047 : (~zR[15] && zR[14:11]) ? 12'd2047 : zR;
assign z = $signed(zC[11:4]);
// Delay Lines
always @(posedge clk) begin
    if (!reset) begin
        y_1 <= 0;
        y_2 <= 0;
        y_3 <= 0;
    end
    else if (enable) begin
        y_1 <= y;
        y_2 <= y_1;
        y_3 <= y_2;
    end
end
/* END AUTOGENERATION */
endmodule
