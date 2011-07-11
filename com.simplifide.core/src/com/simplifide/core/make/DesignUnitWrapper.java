package com.simplifide.core.make;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.module.PackageModuleBody;

public abstract class DesignUnitWrapper {

	abstract public String getName();
	abstract public String getSuffix();
	abstract public String getUnitName();
	
	public static DesignUnitWrapper createWrapper(ModuleObjectNew object) {
		if (object instanceof PackageModule) return new Package((PackageModule)object);
		else if (object instanceof PackageModuleBody) return new PackageBody((PackageModuleBody)object);
		else if (object instanceof Entity) return new EntityWrap((Entity)object);
		else if (object instanceof HardwareModule) return new Architecture((HardwareModule)object);
		return null;
	}
	
	public static class Package extends DesignUnitWrapper{
		
		public String getName() {return module.getname();}
		public String getSuffix() {return "_primary.dat";}
		public String getUnitName() {return module.getname();}
		
		private PackageModule module;
		public Package(PackageModule mod) {
			this.module = module;
		}
	}
	
	public static class PackageBody extends DesignUnitWrapper{
		
		public String getName() {return module.getname();}
		public String getSuffix() {return "_primary.dat";}
		public String getUnitName() {return module.getname() + "-body";}
		
		private PackageModuleBody module;
		public PackageBody(PackageModuleBody body) {
			this.module = body;
		}
	}
	
	public static class EntityWrap extends DesignUnitWrapper{
		public String getName() {return module.getname();}
		public String getSuffix() {return "_primary.dat";}
		public String getUnitName() {return module.getname();}
		
		private Entity module;
		public EntityWrap(Entity entity) {
			this.module = entity;
		}
	}
	
	public static class Architecture extends DesignUnitWrapper{
		
		public String getName() {return module.getname();}
		public String getSuffix() {return module.getArchName();}
		public String getUnitName() {return module.getname() + "-" + module.getArchName();}
		
		private HardwareModule module;
		public Architecture(HardwareModule hmod) {
			this.module = hmod;
		}
	}
	
}
