
SRC_PATH = ../src_c
CC = gcc
CFLAGS = -g -lm -O2 -I ${SRC_PATH} 

OBJECTS = dsp_basic.o file_util.o complex_mult.o complex_mult4.o complex_mult_main.o

dsp_basic.o : $(SRC_PATH)/dsp_basic.c
	$(CC) $(CFLAGS) -c $(SRC_PATH)/dsp_basic.c
	
file_util.o : $(SRC_PATH)/file_util.c
	$(CC) $(CFLAGS) -c $(SRC_PATH)/file_util.c

complex_mult.o : ${SRC_PATH}/complex_mult.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/complex_mult.c

complex_mult4.o : ${SRC_PATH}/complex_mult4.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/complex_mult4.c

complex_mult_main.o : ${SRC_PATH}/complex_mult_main.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/complex_mult_main.c
	
complex_mult_test : ${OBJECTS}
	$(CC) -o complex_mult_c $(OBJECTS) -lm
	
clean : 
	rm *.o complex_mult

fuse : 
	fuse work.complex_mult_test -prj complex_mult.prj -o complex_mult

sim : 
	./complex_mult -tclbatch complex_mult.tcl
	
all : complex_mult_test fuse

run : all sim
	./complex_mult_c
 