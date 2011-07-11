 

module complex_mult4 (clk,reset,enable,ind,Ar,Ai,Br,Bi,Zr,Zi); 


input  clk,reset,enable;
input  [1:0] ind;
input  signed [7:0] Ar,Ai,Br,Bi;

output signed [7:0] Zr,Zi;



/* simplifide 

clock_head alpha {
	clock "clk" posedge
	reset "reset" async active_low
	index "ind"   2
}

dsp complex_mult4 {
   clock_head alpha;
   
   fixed  <8,4>  iwidth;
   fixed  <16,8> intwidth;
  
   input  <c,2,0> ind;
   input  <iwidth>  Ar, Ai, Br, Bi; 
   signal <intwidth>  X[1];
   
   signal <8,2> Y[1];
   signal <8,2> Zrt [1];
   output <8,2> Zr[1] ,Zi [1];
   // Multiply the input signals sharing the multiplier
   X[4k]    = Ar[4k]   * Br[4k];
   X[4k+1]  = Ai[4k+1] * Bi[4k+1];
   X[4k+2]  = Ar[4k+2] * Bi[4k+2];
   X[4k+3]  = Ai[4k+3] * Br[4k+3];
   // Use an accumulator for the addition of the terms
   Y[4k]    = RC<16,8>(Y[4k-1]  +    X[4k-1]);
   Y[4k+1]  = RC<16,8>(0.0      +    X[4k]);
   Y[4k+2]  = RC<16,8>(Y[4k+1]  +   -X[4k+1]);
   Y[4k+3]  = RC<16,8>(0.0      +    X[4k+2]);
   // Store the Real Output
   Zrt[2k]    = Zrt[2k-1];
   Zrt[2k+1]  = Zrt[2k]; 
   Zrt[2k+2]  = Zrt[2k+1];
   Zrt[2k+3]  = Y[2k+2];
   // Latch the Real Output
   Zr[2k]    = Zr[2k];
   Zr[2k+1]  = Zrt[2k+1]; 
   Zr[2k+2]  = Zr[2k+1];
   Zr[2k+3]  = Zr[2k+2];
   // Latch the imaginary output
   Zi[2k]    = Zi[2k-1];
   Zi[2k+1]  = Y[2k]; 
   Zi[2k+2]  = Zi[2k+1];
   Zi[2k+3]  = Zi[2k+2];
   
   
}

*/
/* AUTO GENERATED (DO NOT EDIT MANUALLY) */
// Signal Declarations
wire  [1:0] ind;
wire  signed [7:0] Ar;
wire  signed [7:0] Ai;
wire  signed [7:0] Br;
wire  signed [7:0] Bi;
wire  signed [15:0] X;
reg signed [15:0] X_1;
wire  signed [7:0] Y;
reg signed [7:0] Y_1;
wire  signed [7:0] Zrt;
reg signed [7:0] Zrt_1;
wire  signed [7:0] Zr;
reg signed [7:0] Zr_1;
wire  signed [7:0] Zi;
reg signed [7:0] Zi_1;
reg  signed [7:0] X_m0;
always @* begin
    case(ind)
        2'd0 : X_m0 <= $signed(Ar[7:0]);
        2'd1 : X_m0 <= $signed(Ai[7:0]);
        2'd2 : X_m0 <= $signed(Ar[7:0]);
        2'd3 : X_m0 <= $signed(Ai[7:0]);
    endcase
end
reg  signed [7:0] X_m1;
always @* begin
    case(ind)
        2'd0 : X_m1 <= $signed(Br[7:0]);
        2'd1 : X_m1 <= $signed(Bi[7:0]);
        2'd2 : X_m1 <= $signed(Bi[7:0]);
        2'd3 : X_m1 <= $signed(Br[7:0]);
    endcase
end
wire signed [15:0] XM = X_m0 * X_m1;
wire signed [15:0] XR = $signed(XM[15:0]);
assign X = $signed(XR[15:0]);
reg  signed [7:0] Y_m0;
always @* begin
    case(ind)
        2'd0 : Y_m0 <= $signed(Y_1[7:0]);
        2'd1 : Y_m0 <= 8'sd0;
        2'd2 : Y_m0 <= $signed(Y_1[7:0]);
        2'd3 : Y_m0 <= 8'sd0;
    endcase
end
reg  signed [15:0] Y_m1;
always @* begin
    case(ind)
        2'd0 : Y_m1 <= $signed(X_1[15:0]);
        2'd1 : Y_m1 <= $signed(X_1[15:0]);
        2'd2 : Y_m1 <= -$signed(X_1[15:0]);
        2'd3 : Y_m1 <= $signed(X_1[15:0]);
    endcase
end
wire signed [15:0] YRR_i0R = $signed({{2{Y_m0[7]}},Y_m0,6'd0}) + $signed(Y_m1[15:0]);
wire signed [15:0] YR = $signed(YRR_i0R[15:0]) + 16'sd32;
wire signed [13:0] YC = (YR[15] && ~YR[14:13]) ? -14'd8191 : (~YR[15] && YR[14:13]) ? 14'd8191 : YR;
assign Y = $signed(YC[13:6]);
reg  signed [7:0] Zrt_m0;
always @* begin
    case(ind)
        2'd0 : Zrt_m0 <= $signed(Zrt_1[7:0]);
        2'd1 : Zrt_m0 <= $signed(Zrt_1[7:0]);
        2'd2 : Zrt_m0 <= $signed(Zrt_1[7:0]);
        2'd3 : Zrt_m0 <= $signed(Y_1[7:0]);
    endcase
end
assign Zrt = $signed(Zrt_m0[7:0]);
reg  signed [7:0] Zr_m0;
always @* begin
    case(ind)
        2'd0 : Zr_m0 <= $signed(Zr[7:0]);
        2'd1 : Zr_m0 <= $signed(Zrt[7:0]);
        2'd2 : Zr_m0 <= $signed(Zr_1[7:0]);
        2'd3 : Zr_m0 <= $signed(Zr_1[7:0]);
    endcase
end
assign Zr = $signed(Zr_m0[7:0]);
reg  signed [7:0] Zi_m0;
always @* begin
    case(ind)
        2'd0 : Zi_m0 <= $signed(Zi_1[7:0]);
        2'd1 : Zi_m0 <= $signed(Y_1[7:0]);
        2'd2 : Zi_m0 <= $signed(Zi_1[7:0]);
        2'd3 : Zi_m0 <= $signed(Zi_1[7:0]);
    endcase
end
assign Zi = $signed(Zi_m0[7:0]);
// Delay Lines
always @(posedge clk or negedge reset) begin
    if (!reset) begin
        X_1 <= 0;
        Y_1 <= 0;
        Zrt_1 <= 0;
        Zr_1 <= 0;
        Zi_1 <= 0;
    end
    else begin
        X_1 <= X;
        Y_1 <= Y;
        Zrt_1 <= Zrt;
        Zr_1 <= Zr;
        Zi_1 <= Zi;
    end
end
/* END AUTOGENERATION */
endmodule
