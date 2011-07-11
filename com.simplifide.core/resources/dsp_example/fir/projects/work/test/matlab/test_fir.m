load "../data/fir_out.txt"
load "../data/fir_outc.txt"

in  = conv2s(fir_out(:,1),8,4);
out = conv2s(fir_out(:,2),8,4);

cin   = fir_outc(:,1);
cfix  = fir_outc(:,2);
cfl   = fir_outc(:,3);

error_out   = out(2:end) - cfix(2:length(in));
figure(1)
subplot(211)
plot(out);
subplot(212)
plot(error_out);
