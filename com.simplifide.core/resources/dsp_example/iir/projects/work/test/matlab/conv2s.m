function out1 = conv2s(in1,width,frac)
  dat = in1;
  a = find(dat >= 2^(width-1));
  dat(a) = dat(a) - 2^width;
  dat = dat/2^frac;
  out1 = dat;  
