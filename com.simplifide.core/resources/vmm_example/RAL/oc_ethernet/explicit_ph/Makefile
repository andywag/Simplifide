## 
## -------------------------------------------------------------
##    Copyright 2004-2008 Synopsys, Inc.
##    All Rights Reserved Worldwide
## 
##    Licensed under the Apache License, Version 2.0 (the
##    "License"); you may not use this file except in
##    compliance with the License.  You may obtain a copy of
##    the License at
## 
##        http://www.apache.org/licenses/LICENSE-2.0
## 
##    Unless required by applicable law or agreed to in
##    writing, software distributed under the License is
##    distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
##    CONDITIONS OF ANY KIND, either express or implied.  See
##    the License for the specific language governing
##    permissions and limitations under the License.
## -------------------------------------------------------------
## 

VMM	= +incdir+$(VMM_HOME)/sv
#VMM	= -ntb_opts rvm

TESTDIR	= $(VMM_HOME)/sv/RAL/tests

WB	= $(VMM_HOME)/sv/examples/std_lib/wishbone
MII	= $(VMM_HOME)/sv/examples/std_lib/ethernet
TB	= tb_top.sv $(VMM) +incdir+$(WB)+$(MII)

DUTDIR	= $(VMM_HOME)/shared/examples/oc_ethernet
DUT	= -F $(DUTDIR)/rtl/rtl_file_list.lst +incdir+$(DUTDIR)/rtl

OPTS	=

VCS	= vcs -R -sverilog -ntb_opts dtm +verilog1995ext+.v -extinclude \
	      +warn=noBCNACMBP $(OPTS)


all: tests 

tests: hw_reset \
	bit_bash \
	reg_access \
	mem_walk

%: ral_oc_ethernet.sv $(TESTDIR)/%.sv
	$(VCS) timescale.v $(TESTDIR)/$*.sv $(TB) $(DUT) \
               +define+OC_ETHERNET_TOP_PATH=tb_top.dut \
               +define+VMM_RAL_TEST_PRE_INCLUDE=ral_env.svh \
               +define+VMM_RAL_TEST_POST_INCLUDE=ral_pgm.svh \
               +define+SINGLE_RAM_VARIABLE

report:
	urg -dir simv.vdb
	urg -show text -dir simv.vdb

ral_oc_ethernet.sv: $(DUTDIR)/oc_ethernet.ralf
	$(VMM_HOME)/shared/bin/ralgen -b -l sv -t oc_ethernet $(DUTDIR)/oc_ethernet.ralf

clean:
	rm -rf *~ *.key *.vro *.xml *.log csrc simv* *.h *.db *.html \
	       result.* urgReport ral_oc*.sv
