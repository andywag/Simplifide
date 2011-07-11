len = 10000;

float_in = createRand(len,8,4);
data_in  = fixed2Twos(float_in,8,4);

fin = fopen('../data/fir_in.txt','w');
fprintf(fin,"%x\r\n",data_in)
fclose(fin);

fin = fopen('../data/fir_float.txt','w');
fprintf(fin,"%f\r\n",float_in)
fclose(fin);
