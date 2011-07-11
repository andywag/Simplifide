SRC_PATH = ../src_c
CC = gcc
CFLAGS = -g -lm -O2 -I ${SRC_PATH} 

OBJECTS = dsp_basic.o file_util.o iir.o iir_main.o

dsp_basic.o : $(SRC_PATH)/dsp_basic.c
	$(CC) $(CFLAGS) -c $(SRC_PATH)/dsp_basic.c
	
file_util.o : $(SRC_PATH)/file_util.c
	$(CC) $(CFLAGS) -c $(SRC_PATH)/file_util.c

iir.o : ${SRC_PATH}/iir.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/iir.c

iir_main.o : ${SRC_PATH}/iir_main.c
	$(CC) $(CFLAGS) -c ${SRC_PATH}/iir_main.c
	
iir_c : ${OBJECTS}
	$(CC) -o iir_c $(OBJECTS) -lm
	
clean : 
	rm *.o iir_c iir

fuse : 
	fuse work.iir_test -prj iir.prj -o iir

sim: 
	./iir -tclbatch iir.tcl
	
all : iir_c fuse

run : all sim
	./iir_c
 