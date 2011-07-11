'''
Created on May 4, 2010

@author: andy
'''
from Simplifide.Interface2.Startup import Main
from Simplifide.Interface2.Startup import MenuProvider

import Simplifide.Interface2.Tools.ModelSimMenu as ModelSimMenu
import Simplifide.Interface2.Tools.IsimMenu as IsimMenu
import Simplifide.Interface2.Tools.Isim as Isim
import Simplifide.Interface2.Tools.ModelSim as ModelSim

class Base(Main):
    def __init__(self):
        Main.__init__(self)
        self.setMenuProvider(Menu())
        self.addSaveActionProvider(Isim.SingleFileProvider())
        
class Menu(MenuProvider):
    def __init__(self):
        MenuProvider.__init__(self)
        self.addSuiteMenu(ModelSimMenu.SuiteMenu())
        self.addSuiteMenu(IsimMenu.SuiteMenu())
        
        self.addProjectMenu(ModelSimMenu.ProjectMenu())
        self.addProjectMenu(IsimMenu.ProjectMenu())
        