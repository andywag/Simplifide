 

module fir (clk,reset,enable,x,z); 

output signed [7:0] z;
input  clk,reset,enable;
input  signed [7:0] x;



/* simplifide 
  
clock_head clo {
    clock               "clk"        posedge
    reset               "reset"      sync             active_low
    enable              "enable"
}

  
dsp fir {
   clock_head clo;
   
   fixed  <8,4>  iwidth;
   
   constant <iwidth> alpha = 0.500000;
   constant <iwidth> beta  = 1.5;
   
   input  <iwidth>  x[2];
   output <iwidth>  z; 

   z[n] = alpha*x[n] + beta*x[n-1] + alpha*x[n-2];
}

*/
/* AUTO GENERATED (DO NOT EDIT MANUALLY) */
// Signal Declarations
wire  signed [7:0] x;
reg signed [7:0] x_1;
reg signed [7:0] x_2;
wire  signed [7:0] z;
// z[n] = alpha*x[n] + beta*x[n-1] + alpha*x[n-2];
wire signed [15:0] zRR_i0M = $signed({{5{x[7]}},x,3'd0});
wire signed [7:0] zRR_i0R = $signed(zRR_i0M[11:4]);
wire signed [7:0] zRR_i0 = $signed(zRR_i0R[7:0]);
wire signed [15:0] zRR_i1M = $signed({{4{x_1[7]}},x_1,4'd0}) + $signed({{5{x_1[7]}},x_1,3'd0});
wire signed [7:0] zRR_i1R = $signed(zRR_i1M[11:4]);
wire signed [7:0] zRR_i1 = $signed(zRR_i1R[7:0]);
wire signed [15:0] zRR_i2M = $signed({{5{x_2[7]}},x_2,3'd0});
wire signed [7:0] zRR_i2R = $signed(zRR_i2M[11:4]);
wire signed [7:0] zRR_i2 = $signed(zRR_i2R[7:0]);
wire signed [7:0] zR = $signed(zRR_i0[7:0]) + $signed(zRR_i1[7:0]) + $signed(zRR_i2[7:0]);
assign z = $signed(zR[7:0]);
// Delay Lines
always @(posedge clk) begin
    if (!reset) begin
        x_1 <= 0;
        x_2 <= 0;
    end
    else if (enable) begin
        x_1 <= x;
        x_2 <= x_1;
    end
end
/* END AUTOGENERATION */
endmodule
