// $Id: classA.svh,v 1.9 2009/05/01 14:34:38 redelman Exp $
//----------------------------------------------------------------------
//   Copyright 2007-2009 Mentor Graphics Corporation
//   Copyright 2007-2009 Cadence Design Systems, Inc.
//   All Rights Reserved Worldwide
//
//   Licensed under the Apache License, Version 2.0 (the
//   "License"); you may not use this file except in
//   compliance with the License.  You may obtain a copy of
//   the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in
//   writing, software distributed under the License is
//   distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
//   CONDITIONS OF ANY KIND, either express or implied.  See
//   the License for the specific language governing
//   permissions and limitations under the License.
//----------------------------------------------------------------------
`ifndef CLASSA_SVH
`define CLASSA_SVH

`include "classC.svh"

class A extends uvm_component;
  bit debug = 0;
  C u1;
  C u2;

  function new(string name, uvm_component parent);
    super.new(name, parent);
  endfunction
  function void build();
    super.build();

    void'(get_config_int("debug", debug));
    set_config_int("*", "v", 0);

    $display("%s: In Build: debug = %0d", get_full_name(), debug);

    u1 = new("u1", this); u1.build();
    u2 = new("u2", this); u2.build();
  endfunction

  function string get_type_name();
    return "A";
  endfunction
  function void do_print(uvm_printer printer);
    printer.print_field("debug", debug, 1);
  endfunction
  task run;
    begin end
  endtask
endclass

`endif
