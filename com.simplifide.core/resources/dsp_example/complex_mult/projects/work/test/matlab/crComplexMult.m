len = 10000;

xr_in = createRand(len,8,4);
xi_in = createRand(len,8,4);
yr_in = createRand(len,8,4);
yi_in = createRand(len,8,4);

data_in  = fixed2Twos(xr_in,8,4);
fin = fopen('../data/xr_in.txt','w');
fprintf(fin,"%x\r\n",data_in)
fclose(fin);

data_in  = fixed2Twos(xi_in,8,4);
fin = fopen('../data/xi_in.txt','w');
fprintf(fin,"%x\r\n",data_in)
fclose(fin);

data_in  = fixed2Twos(yr_in,8,4);
fin = fopen('../data/yr_in.txt','w');
fprintf(fin,"%x\r\n",data_in)
fclose(fin);

data_in  = fixed2Twos(yi_in,8,4);
fin = fopen('../data/yi_in.txt','w');
fprintf(fin,"%x\r\n",data_in)
fclose(fin);



fin = fopen('../data/xr_float.txt','w');
fprintf(fin,"%f\r\n",xr_in)
fclose(fin);

fin = fopen('../data/xi_float.txt','w');
fprintf(fin,"%f\r\n",xi_in)
fclose(fin);

fin = fopen('../data/yr_float.txt','w');
fprintf(fin,"%f\r\n",yr_in)
fclose(fin);

fin = fopen('../data/yi_float.txt','w');
fprintf(fin,"%f\r\n",yi_in)
fclose(fin);
