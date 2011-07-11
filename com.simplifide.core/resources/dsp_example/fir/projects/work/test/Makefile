SRC_PATH = ../src_c
CC = gcc
CFLAGS = -g -lm -O2 -I ${SRC_PATH} 

OBJECTS = dsp_basic.o file_util.o fir.o fir_main.o

dsp_basic.o : $(SRC_PATH)/dsp_basic.c
	$(CC) $(CFLAGS) -c $(SRC_PATH)/dsp_basic.c
	
file_util.o : $(SRC_PATH)/file_util.c
	$(CC) $(CFLAGS) -c $(SRC_PATH)/file_util.c

fir.o : ${SRC_PATH}/fir.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/fir.c

fir_main.o : ${SRC_PATH}/fir_main.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/fir_main.c
	
fir_c : ${OBJECTS}
	$(CC) -o fir_c $(OBJECTS) -lm
	
clean : 
	rm *.o fir_c fir

fuse : 
	fuse work.fir_test -prj fir.prj -o fir

sim: 
	./fir -tclbatch fir.tcl
	
all : fir_c fuse

run : all sim
	./fir_c
 