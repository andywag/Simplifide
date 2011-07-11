
/** Module : example
 *  Author : Andy
 *  Date   : Sun, 21 Oct 2007 14:47:24 -0700
 */


// {/*AUTOARGS*/} creates the port list of the module from the input/output declaration */
module example (/*AUTOARGS*/
         USBWireVMI,
         USBWireVPI,
         address,
         chipselect,
         clk,
         read,
         reset,
         usbClk,
         write,
         writedata,
         USBFullSpeed,
         USBWireDataInTick,
         USBWireDataOutTick,
         USBWireOutEn_n,
         USBWireVMO,
         USBWireVPO); 


input clk;
input reset;
input [7:0] address; 
input [7:0] writedata; 

input write; 
input read;

input chipselect;

input usbClk;
input USBWireVPI /* synthesis useioff=1 */;
input USBWireVMI /* synthesis useioff=1 */;

output USBWireVPO /* synthesis useioff=1 */;
output USBWireVMO /* synthesis useioff=1 */;
output USBWireDataOutTick  /* synthesis useioff=1 */;
output USBWireDataInTick /* synthesis useioff=1 */;
output USBWireOutEn_n /* synthesis useioff=1 */;
output USBFullSpeed /* synthesis useioff=1 */;

wire uin, in2;
wire uout;

//// {/*AUTOWIRE*/} automatically create the connections for this block which are not included 
//// in I/O ports of this module and when the local port name is the same as the instance port name

/*AUTOWIRE*//* AUTO GENERATED (DO NOT EDIT MANUALLY) */
// Outputs of iblock
wire  out2;

// Outputs of iusbHostSlaveAvalonWrap
wire  irq;
wire [7:0] readdata;
wire  waitrequest;

/* END AUTOGENERATION */



// {/*AUTOINST*/} automatically creates an instance when the input port name is the same as the output
// port name

usbHostSlaveAvalonWrap iusbHostSlaveAvalonWrap  (/*AUTOINST*/
         .USBWireVMI(USBWireVMI),
         .USBWireVPI(USBWireVPI),
         .address(address),
         .chipselect(chipselect),
         .clk(clk),
         .read(read),
         .reset(reset),
         .usbClk(usbClk),
         .write(write),
         .writedata(writedata),
         .USBFullSpeed(USBFullSpeed),
         .USBWireDataInTick(USBWireDataInTick),
         .USBWireDataOutTick(USBWireDataOutTick),
         .USBWireOutEn_n(USBWireOutEn_n),
         .USBWireVMO(USBWireVMO),
         .USBWireVPO(USBWireVPO),
         .irq(irq),
         .readdata(readdata),
         .waitrequest(waitrequest));

/* python
import Template.Element.Instance as Instance
inst = Instance.Top('block','iblock',{'in1' : 'uin', 'out1' : 'uout'})
writer.writeVerilog(inst)
*/
/* AUTO GENERATED (DO NOT EDIT MANUALLY) */

block iblock (
         .clk(clk),
         .in1(uin),
         .in2(in2),
         .reset(reset),
         .out1(uout),
         .out2(out2));

/* END AUTOGENERATION */


endmodule
