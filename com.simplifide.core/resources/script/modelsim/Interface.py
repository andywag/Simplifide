
import Simplifide.General.Eclipse.InputOutput as InputOutput
import Simplifide.Interface.Tools.ModelSimNew as ModelSimNew
import Simplifide.General.Eclipse.ToolContext as ToolContext
import Simplifide.General.Eclipse.ProjectInterface as ProjectInterface

def clean() :
    sim = ModelSimNew.ModelSuite(ProjectInterface.getActiveSuite())
    sim.clean()
    
def build() :
    sim = ModelSimNew.ModelSuite(ProjectInterface.getActiveSuite())
    sim.all()
	
def onSave() :
    sim = ModelSimNew.ModelSuite(ProjectInterface.getActiveSuite())
    sim.all()