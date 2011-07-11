 

module complex_mult (clk,reset,enable,ind,Ar,Ai,Br,Bi,Zr,Zi); 


input  clk,reset,enable;
input  ind;
input  signed [7:0] Ar,Ai,Br,Bi;

output signed [7:0] Zr,Zi;

 
/** alphasde adf;lajdsf    
*/  
  
/* simplifide             
  
clock_head alpha {   
	clock "clk" posedge
	reset "reset" async active_low
	index "ind"   1
}

dsp complex_mult2 {
   clock_head alpha;
   
  
   input  <c,1,0> ind;
   input  <8,4>    Ar,Ai,Br,Bi; 
   signal <16,8>   Xr[1],Xi[1];
   signal <8,2>   Yr[1],Yi[1];
   
   output <8,2> Zr[1],Zi[1];
   
   // Real Multiplier
   Xr[2k]    = Ar[2k]   * Br[2k];
   Xr[2k+1]  = Ai[2k+1] * Bi[2k+1];
   // Real Addition Segment
   Yr[2k]    = RC<16,8>(Yr[2k-1]   +   -Xr[2k-1]);
   Yr[2k+1]  = RC<16,8>(0.0        +    Xr[2k]);
   // Latch the Real Output 
   Zr[2k]    = Zr[2k-1];
   Zr[2k+1]  = Yr[2k];
   // Imaginary Multiplier
   Xi[2k]    = Ar[2k]   * Bi[2k];
   Xi[2k+1]  = Ai[2k+1] * Br[2k+1];
   // Imaginary Addition Segment
   Yi[2k]    = RC<16,8>(Yi[2k-1] + Xi[2k-1]);
   Yi[2k+1]  = RC<16,8>(0.0      + Xi[2k]);
   // Latch the Output
   Zi[2k]    = Zi[2k-1];
   Zi[2k+1]  = Yi[2k];
   
}

*/
/* AUTO GENERATED (DO NOT EDIT MANUALLY) */
// Signal Declarations
wire  [0:0] ind;
wire  signed [7:0] Ar;
wire  signed [7:0] Ai;
wire  signed [7:0] Br;
wire  signed [7:0] Bi;
wire  signed [15:0] Xr;
reg signed [15:0] Xr_1;
wire  signed [15:0] Xi;
reg signed [15:0] Xi_1;
wire  signed [7:0] Yr;
reg signed [7:0] Yr_1;
wire  signed [7:0] Yi;
reg signed [7:0] Yi_1;
wire  signed [7:0] Zr;
reg signed [7:0] Zr_1;
wire  signed [7:0] Zi;
reg signed [7:0] Zi_1;
reg  signed [7:0] Xr_m0;
always @* begin
    case(ind)
        1'd0 : Xr_m0 <= $signed(Ar[7:0]);
        1'd1 : Xr_m0 <= $signed(Ai[7:0]);
    endcase
end
reg  signed [7:0] Xr_m1;
always @* begin
    case(ind)
        1'd0 : Xr_m1 <= $signed(Br[7:0]);
        1'd1 : Xr_m1 <= $signed(Bi[7:0]);
    endcase
end
wire signed [15:0] XrM = Xr_m0 * Xr_m1;
wire signed [15:0] XrR = $signed(XrM[15:0]);
assign Xr = $signed(XrR[15:0]);
reg  signed [7:0] Yr_m0;
always @* begin
    case(ind)
        1'd0 : Yr_m0 <= $signed(Yr_1[7:0]);
        1'd1 : Yr_m0 <= 8'sd0;
    endcase
end
reg  signed [15:0] Yr_m1;
always @* begin
    case(ind)
        1'd0 : Yr_m1 <= -$signed(Xr_1[15:0]);
        1'd1 : Yr_m1 <= $signed(Xr_1[15:0]);
    endcase
end
wire signed [15:0] YrRR_i0R = $signed({{2{Yr_m0[7]}},Yr_m0,6'd0}) + $signed(Yr_m1[15:0]);
wire signed [15:0] YrR = $signed(YrRR_i0R[15:0]) + 16'sd32;
wire signed [13:0] YrC = (YrR[15] && ~YrR[14:13]) ? -14'd8191 : (~YrR[15] && YrR[14:13]) ? 14'd8191 : YrR;
assign Yr = $signed(YrC[13:6]);
reg  signed [7:0] Zr_m0;
always @* begin
    case(ind)
        1'd0 : Zr_m0 <= $signed(Zr_1[7:0]);
        1'd1 : Zr_m0 <= $signed(Yr_1[7:0]);
    endcase
end
assign Zr = $signed(Zr_m0[7:0]);
reg  signed [7:0] Xi_m0;
always @* begin
    case(ind)
        1'd0 : Xi_m0 <= $signed(Ar[7:0]);
        1'd1 : Xi_m0 <= $signed(Ai[7:0]);
    endcase
end
reg  signed [7:0] Xi_m1;
always @* begin
    case(ind)
        1'd0 : Xi_m1 <= $signed(Bi[7:0]);
        1'd1 : Xi_m1 <= $signed(Br[7:0]);
    endcase
end
wire signed [15:0] XiM = Xi_m0 * Xi_m1;
wire signed [15:0] XiR = $signed(XiM[15:0]);
assign Xi = $signed(XiR[15:0]);
reg  signed [7:0] Yi_m0;
always @* begin
    case(ind)
        1'd0 : Yi_m0 <= $signed(Yi_1[7:0]);
        1'd1 : Yi_m0 <= 8'sd0;
    endcase
end
reg  signed [15:0] Yi_m1;
always @* begin
    case(ind)
        1'd0 : Yi_m1 <= $signed(Xi_1[15:0]);
        1'd1 : Yi_m1 <= $signed(Xi_1[15:0]);
    endcase
end
wire signed [15:0] YiRR_i0R = $signed({{2{Yi_m0[7]}},Yi_m0,6'd0}) + $signed(Yi_m1[15:0]);
wire signed [15:0] YiR = $signed(YiRR_i0R[15:0]) + 16'sd32;
wire signed [13:0] YiC = (YiR[15] && ~YiR[14:13]) ? -14'd8191 : (~YiR[15] && YiR[14:13]) ? 14'd8191 : YiR;
assign Yi = $signed(YiC[13:6]);
reg  signed [7:0] Zi_m0;
always @* begin
    case(ind)
        1'd0 : Zi_m0 <= $signed(Zi_1[7:0]);
        1'd1 : Zi_m0 <= $signed(Yi_1[7:0]);
    endcase
end
assign Zi = $signed(Zi_m0[7:0]);
// Delay Lines
always @(posedge clk or negedge reset) begin
    if (!reset) begin
        Xr_1 <= 0;
        Xi_1 <= 0;
        Yr_1 <= 0;
        Yi_1 <= 0;
        Zr_1 <= 0;
        Zi_1 <= 0;
    end
    else begin
        Xr_1 <= Xr;
        Xi_1 <= Xi;
        Yr_1 <= Yr;
        Yi_1 <= Yi;
        Zr_1 <= Zr;
        Zi_1 <= Zi;
    end
end
/* END AUTOGENERATION */
endmodule
