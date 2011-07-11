
/** @module : dsp_test_test
 *  @author : Andy
 *  @date   : Oct 29, 2010 
 *
 *
 */


module complex_mult_test (); 

reg clk,reset,enable;
reg  [1:0] index;
reg  [7:0]   x;
reg  [7:0]   y;
wire  [7:0]   z; 
integer count;

reg [7:0] inputXr [0:9999], inputXi [0:9999], inputYr [0:9999], inputYi [0:9999];
integer outptr;

reg [7:0]  Xr, Xi, Yr, Yi;
wire [7:0] Zr2, Zi2, Zr4, Zi4;

reg resetd;

initial begin
	count  = 0;
    clk    = 0;
    reset  = 0;
    enable = 1;
    index  = 0;
   
    $readmemh("data/xr_in.txt",inputXr);
    $readmemh("data/xi_in.txt",inputXi);
    $readmemh("data/yr_in.txt",inputYr);
    $readmemh("data/yi_in.txt",inputYi);
    
    outptr   = $fopen("data/mult_out.txt");
end

always #10 clk = ~clk;
always @(posedge clk)
	if (index==2) count <= count + 1;

always @(posedge clk) begin
	resetd <= reset;
    if (count == 10) reset <= 1;
    index <= index + 1;
end
   
   
always @(posedge clk or negedge reset) begin
    if (!reset) begin
        Xr <= 0;
        Xi <= 0;
        Yr <= 0;
        Yi <= 0;
    end
    else if (count > 9999) begin
        $finish;
        Xr <= 0;
        Xi <= 0;
        Yr <= 0;
        Yi <= 0;
    end
    else if (count > 10) begin
        Xr <= inputXr[count-11];
        Xi <= inputXi[count-11];
        Yr <= inputYr[count-11];
        Yi <= inputYi[count-11];
    end
end
 

/*
always @(posedge clk or negedge reset) begin
    if (!reset) begin
        x <= 127;
        y <= -128;
       
    end
    else begin
        x <= x + 1;
        if (x == 127) y <= y + 1;
        if (y == 127 && x ==127) $finish;
        
    end
end
*/

always @(posedge clk or negedge reset) begin
    if (resetd) begin
        $fdisplay(outptr,"%d %d %d %d %d %d %d %d",Xr, Xi, Yr, Yi, Zr2, Zi2, Zr4, Zi4);
    end
end




complex_mult  icomplex_mult     
		(.clk                     (clk),
		.reset                   (reset),
		.enable                  (enable),
		.ind                     (index[0]),
		.Ar                      (Xr),
		.Ai                      (Xi),
		.Br                      (Yr),
		.Bi                      (Yi),
		.Zr                      (Zr2),
		.Zi                      (Zi2));

complex_mult4  icomplex_mult4     
		(.clk                     (clk),
		.reset                   (reset),
		.enable                  (enable),
		.ind                     (index),
		.Ar                      (Xr),
		.Ai                      (Xi),
		.Br                      (Yr),
		.Bi                      (Yi),
		.Zr                      (Zr4),
		.Zi                      (Zi4));





endmodule
