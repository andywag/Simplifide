//
// -------------------------------------------------------------
//    Copyright 2004-2008 Synopsys, Inc.
//    All Rights Reserved Worldwide
//
//    Licensed under the Apache License, Version 2.0 (the
//    "License"); you may not use this file except in
//    compliance with the License.  You may obtain a copy of
//    the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in
//    writing, software distributed under the License is
//    distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
//    CONDITIONS OF ANY KIND, either express or implied.  See
//    the License for the specific language governing
//    permissions and limitations under the License.
// -------------------------------------------------------------
//



class alu_mon2cov_connector extends alu_mon_callbacks;
   alu_cov cov;

   function new(alu_cov cov);
     this.cov = cov;
   endfunction

   virtual task post_mon(alu_mon   mon,
                         alu_data tr,
                         ref bit          drop);
      cov.tr = tr; 
      -> cov.cov_event;
   endtask

endclass
