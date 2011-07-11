function y0 = createRand(len,width,frac) 
	 
y0 = round(2^(width)*rand(1,len) - 2^(width-1));
a = find(y0 == 2^(width-1));
y0(a) = y0(a) - 1;
y0 = y0/2^frac;
	


	

