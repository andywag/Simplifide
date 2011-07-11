% Load the data from the rtl simulations
load "../data/mult_out.txt"
load "../data/mult_out_c.txt"

% Input Width
width = 8;
input_frac = 4;
output_frac = 2;

% Convert the rtl data to integer data to floating point
in1  = conv2s(mult_out(:,1),width,input_frac)  + j*conv2s(mult_out(:,2),width,input_frac);
in2  = conv2s(mult_out(:,3),width,input_frac)  + j*conv2s(mult_out(:,4),width,input_frac);
out  = conv2s(mult_out(:,5),width,output_frac) + j*conv2s(mult_out(:,6),width,output_frac);
out4  = conv2s(mult_out(:,7),width,output_frac) + j*conv2s(mult_out(:,8),width,output_frac);

% Convert the c data to integer floating point
cout  = mult_out_c(:,5) + j*mult_out_c(:,6);
cout4 = mult_out_c(:,7) + j*mult_out_c(:,8);

% Floating Point Complex Multiplication
rout = in1.*in2;

% Plot the error associated with the complex multiplication
figure(1)
sout  = out(7:end);
mout  = rout(4:end-3);
sout4 = out4(9:end);
 
subplot(211) 
plot(real(sout-mout))
subplot(212)
plot(imag(sout-mout))

% Compare the C and Rtl Outputs
lcout  = cout(4:end);
lcout4 = cout4(6:end);
err  = sout - lcout(1:length(sout));
err4 = sout4 - lcout4(1:length(sout4));
figure(2)
subplot(211)
plot(abs(err))
subplot(212)
plot(abs(err))
