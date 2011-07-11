
/** @module : dsp_test_test
 *  @author : Andy
 *  @date   : Oct 29, 2010 
 *
 *
 */


module iir_test (); 

reg clk,reset,enable;
reg  [7:0]   x;
reg  [7:0]   y;
wire  [7:0]   z; 
integer count;

reg [7:0] inputMem [0:9999];
integer outptr;

reg [7:0] a0,a1;
reg [7:0] b0,b1,b2;

reg resetd;

initial begin
	count  = 0;
    clk    = 0;
    reset  = 0;
    enable = 1;
    b0     = 8;//-8;
    b1     = 16;//16;
    b2     = 8;//-8;
    a0     = -8;
    a1     = -8;
    //$readmemh("/home/andy/workspaces/dspc_workspace/dsp_test/data/datain.txt",inputMem);
    //outptr   = $fopen("/home/andy/workspaces/dspc_workspace/dsp_test/data/dataout.txt");
    $readmemh("./data/iir_in.txt",inputMem);
    outptr   = $fopen("./data/iir_out.txt");
end

always #10 clk = ~clk;
always @(posedge clk) count <= count + 1;

always @(posedge clk) begin
	resetd <= reset;
    if (count == 10) reset <= 1;
end
   
    
always @(posedge clk or negedge reset) begin
    if (!reset) begin
        x <= 0;
    end
    else if (count > 9999) begin
        $finish;
        x <= 0;
    end
    else if (count > 10) begin
        x <= inputMem[count-11];
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
        $fdisplay(outptr,"%d %d",x,z);
    end
end


iir  iir     
		(.z                       (z),
		.clk                     (clk),
		.reset                   (reset),
		.enable                  (enable),
		.x                       (x),
		//.y                       (y),
		.a0                      (a0),
		.a1                      (a1),
		.b0                      (b0),
		.b1                      (b1),
		.b2                      (b2));





endmodule
