
import External.holder as holder
import Tool.ModelSim as ModelSim

reload(ModelSim)


def clean() :
	holder.util.printOutput('clean')
	holder.error.clearErrorList()
	sim = ModelSim.Simulator()
	sim.clean()
	holder.error.writeErrors()


def build() :
	holder.util.printOutput('build')
	holder.error.clearErrorList()
	sim = ModelSim.Simulator()
	sim.build()
	holder.error.writeErrors()
	