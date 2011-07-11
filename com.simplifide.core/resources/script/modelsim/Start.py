
from Simplifide.Interface2.Startup import Main

import Simplifide.Interface2.Tools.ModelSim as ModelSim

import ModelSimMenu as ModelSimMenu
import XilinxMenu as XilinxMenu

class Base(Main):
    def __init__(self):
        Main.__init__(self)
        self.addSaveActionProvider(ModelSim.Provider())

        
        