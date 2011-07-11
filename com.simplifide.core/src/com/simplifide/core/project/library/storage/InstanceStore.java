/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library.storage;

import java.io.Serializable;
import java.net.URI;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.library.har.EclipseHarProject;
import com.simplifide.core.source.design.DesignFile;

public abstract class InstanceStore implements Serializable{

	
	private static final long serialVersionUID = 7441233543488577766L;

	private String name;
	
	public InstanceStore(InstanceModuleTop imodtop) {
		this.setName(imodtop.getname());
	}
	public abstract ReferenceItem loadStore(EclipseBaseProject proj);
	public abstract void convertLocations(UniqueList<DesignFile> sourceList);
	public abstract void convertLocationsToHar(EclipseHarProject har, URI baseLocation);
	
	
	public static InstanceStore newInstanceStore(ReferenceItem imod) {
		if (imod == null || imod.getObject() == null) return null;
		
		if (imod.getType() == ReferenceUtilities.REF_INSTANCE_CLASS) {
			return new InstanceStore.ClassModule((ClassInstanceModule)imod.getObject());
		}
		else if (imod.getType() == ReferenceUtilities.REF_INSTANCE_MODULE) {
			return new InstanceStore.Module((InstanceModule)imod.getObject());
		}
		else if (imod.getType() == ReferenceUtilities.REF_INSTANCE_PACKAGE) {
			return new InstanceStore.Package((InstancePackage)imod.getObject());
		}
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static class ClassModule extends Module {
		
		public ClassModule(InstanceModule module) {
			super(module);
		}
		
		protected InstanceModule createInstanceModule(EclipseBaseProject proj) {
			return new ClassInstanceModule(this.getName(),proj.createReferenceItem());
		}
	}
	
	public static class Module extends InstanceStore implements Serializable {
		
		
		private static final long serialVersionUID = 5426139999149781807L;
		private InstanceObject entity;
		private InstanceObject arch;
		private InstanceObject comp;
		
		public Module(InstanceModule module) {
			super(module);
			this.setEntity(new InstanceObject(module.getEntityReference()));
			this.setComp(new InstanceObject(module.getComponentReference()));
			this.setArch(new InstanceObject(module.getArchitectureReference()));
		}
		
		protected InstanceModule createInstanceModule(EclipseBaseProject proj) {
			return new InstanceModule(this.getName(),proj.createReferenceItem());
		}
		
		public ReferenceItem loadStore(EclipseBaseProject proj) {
			InstanceModule inst = this.createInstanceModule(proj);
			if (entity != null) inst.setEntityReference(entity.loadStore());
			if (arch != null) inst.setArchitectureReference(arch.loadStore());
			if (comp != null) inst.setComponentReference(comp.loadStore());
			return inst.createReferenceItem();
		}

		public void convertLocations(UniqueList<DesignFile> sourceList) {
			if (this.entity != null) this.getEntity().convertLocation(sourceList);
			if (this.arch != null) this.getArch().convertLocation(sourceList);
			if (this.comp != null) this.getComp().convertLocation(sourceList);
		}
		
		
		public void convertLocationsToHar(EclipseHarProject har, URI baseLocation) {
			if (this.entity != null) this.entity.convertLocationToHar(har,baseLocation);
			if (this.arch != null) this.arch.convertLocationToHar(har,baseLocation);
			if (this.comp != null) this.comp.convertLocationToHar(har,baseLocation);

		}
		
		public void setEntity(InstanceObject entity) {
			this.entity = entity;
		}

		public InstanceObject getEntity() {
			return entity;
		}

		public void setArch(InstanceObject arch) {
			this.arch = arch;
		}

		public InstanceObject getArch() {
			return arch;
		}

		public void setComp(InstanceObject comp) {
			this.comp = comp;
		}

		public InstanceObject getComp() {
			return comp;
		}

		
		

		
	}
	
	public static class Package extends InstanceStore implements Serializable {
		
		private static final long serialVersionUID = 7363366425261598218L;
		private InstanceObject dec;
		private InstanceObject body;
		
		
		public Package(InstancePackage module) {
			super(module);
			this.setDec(new InstanceObject(module.getPackageModuleReference()));
			this.setBody(new InstanceObject(module.getPackageBodyReference()));
			
		}

		public ReferenceItem loadStore(EclipseBaseProject proj) {
			InstancePackage inst = new InstancePackage(this.getName(),proj.createReferenceItem());
			if (dec != null) inst.setPackageModuleReference(dec.loadStore());
			if (body != null) inst.setPackageBodyReference(body.loadStore());
			return inst.createReferenceItem();
		}
		
		public void convertLocations(UniqueList<DesignFile> sourceList) {
			if (this.dec != null) this.dec.convertLocation(sourceList);
			if (this.body != null) this.body.convertLocation(sourceList);
		}
		
		public void convertLocationsToHar(EclipseHarProject har, URI baseLocation) {
			if (this.dec != null) this.dec.convertLocationToHar(har,baseLocation);
			if (this.body != null) this.body.convertLocationToHar(har,baseLocation);
		}

		public void setDec(InstanceObject dec) {
			this.dec = dec;
		}


		public InstanceObject getDec() {
			return dec;
		}


		public void setBody(InstanceObject body) {
			this.body = body;
		}


		public InstanceObject getBody() {
			return body;
		}
	}
	
	
	
}
