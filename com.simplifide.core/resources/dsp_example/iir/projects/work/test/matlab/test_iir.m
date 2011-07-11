load "../data/iir_out.txt"
load "../data/iir_outc.txt"

in  = conv2s(iir_out(:,1),8,4);
out = conv2s(iir_out(:,2),8,4);

cin   = iir_outc(:,1);
cfix  = iir_outc(:,2);
cfl   = iir_outc(:,3);

error_out   = out(2:end) - cfix(2:length(in));
figure(1)
subplot(211)
plot(out);
subplot(212)
plot(error_out);
