function udat = fixed2Twos(data,width,frac)
	udat = data*2^frac;
	a = find(udat < 0);
	udat(a) = udat(a) + 2^width;