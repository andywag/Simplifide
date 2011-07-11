/*
 * CoreProjectSuite.java
 *
 * Created on May 12, 2006, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.api.file.FileHolder;
import com.simplifide.base.basic.api.python.PythonInterface;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.hierarchy.ClassList;
import com.simplifide.base.core.hierarchy.ConnectionWrapper;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.hierarchy.HierarchyList;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.define.DefineHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;






/**
 *
 * @author awagner
 *
 *  This block
 *
 */
public class CoreProjectSuite<T extends CoreProjectBasic> extends CoreProjectTop<T>{
        
    
    /** Reference to the root set of base hierarchical nodes */
    private ReferenceItem<HierarchyList> hierListReference;
    /** Reference to a set of attached libraries which will act like projects */
    private ReferenceItem<LibraryHolder> libraryReference;
    /** Reference to the test project */
    private ReferenceItem<CoreProjectBasic> testReference;
    /** Reference to the class list for sv */
    private ReferenceItem<ClassList> classListReference;
    

    private ConnectionWrapper connections;
    private DefineHolder defineHolder; 
    private SuiteProperties suiteProps;
    
    
    public CoreProjectSuite() {}
    public CoreProjectSuite(String name) {super(name); init();}
    public CoreProjectSuite(FileHolder dir) {
        super(dir);
        init();
    }
    
   
    
    public void publicInit() {
    	this.init();
    }
    
    private void init() {
        HierarchyList hierList = new HierarchyList("RootNodes");
        this.setHierListReference(hierList.createReferenceItem());
        this.setClassListReference(new ClassList().createReferenceItem());
        this.setLibraryReference(new LibraryHolder().createReferenceItem());
        this.suiteProps = new SuiteProperties();
        this.defineHolder = new DefineHolder();
       
        PythonInterface.getInstance().setSuiteProject(this);
    }
    
    public void deleteObject() {
        
    	this.setDefineHolder(null);
    	if (this.libraryReference != null)
    		this.libraryReference.deleteObject();
    	this.classListReference = null;
        this.hierListReference = null;
        this.libraryReference = null;
        this.suiteProps = null;
        super.deleteObject();
    }
    
  
    public ReferenceLocation getFileLocation(String filename) {
    	return null;
    }
  
    
    /** Searches through the libraries to see if there are any instances there
     *  This seems to be a one off search from the suite to search what is 
     *  underneath the projects
     *  
     */
    public ReferenceItem findLibraryReference(ModuleObjectFindItem item, int type) {
   	 	if (this.getLibraryReference() == null) return null;
    	LibraryHolder holder = this.getLibraryReference().getObject();
   	 	if (holder == null) return null;
   	 	return holder.findLibarayReference(item, type);
    
    }

    /** Searches through the projects to see if there are any instances there
     *  This seems to be a one off search from the suite to search what is 
     *  underneath the projects
     *  
     */
    public ReferenceItem findProjectReference(ModuleObjectIndexTop item) {
    	for (ReferenceItem ritem : this.getGenericSelfList()) {
    		ReferenceItem sitem = ritem.findBaseReference(item);
    		if (sitem != null) return sitem;
    	}
    	return null;
    }
    
    public ReferenceItem findProjectOrLibaryReference(ModuleObjectFindItem item, int type) {
    	ReferenceItem ref = findLibraryReference(item, type);
    	if (ref == null) {
    		ReferenceItem ref1 = new ReferenceItem(item.getname(),ReferenceUtilitiesInterface.REF_MODULEOBJECT);
    		ModuleObjectIndexTop top = new ModuleObjectIndexTop(ref1,item);
    		ref = findProjectReference(top);
    	}
    	if (ref != null && ref.getObject() != null && ref.getObject() instanceof InstanceModule) {
    		InstanceModule imod = (InstanceModule) ref.getObject();
    		ref = imod.getEntityHolder().getReference();
    	}
   
    	return ref;
    }
    
    @Override
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        ReferenceItem uitem = super.findBaseReference(item);
        if (uitem == null && this.libraryReference != null) {
            uitem = this.libraryReference.findBaseReference(item);
        }
        return uitem;
    }

    @Override
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item,
                                                            int type) {
        List nlist = super.findPrefixItemList(item, type);
        if (this.getLibraryReference() != null) {
            nlist.addAll(this.getLibraryReference().findPrefixItemList(item, type));
        }
        return nlist;
    }

   
    /**  Used the Python API instead */ 
    public String getMainModuleName() {
    	NoSortList list = this.getHierListReference().getObject();
    	if (list.getnumber() == 0) return "none";
    	return list.getObject(0).getname();
    }
    
   
    
    /** Moves through the projects to create the chip hierarchy of each project
     */
    private void createProjectHierarchy(ReferenceItem<? extends CoreProjectBasic> basic) {
    	List<? extends ReferenceItem> nlist = basic.findPrefixItemList("",ReferenceUtilities.REF_INSTANCE_MODULE);
        for (ReferenceItem<InstanceModule> item : nlist) {
            ReferenceItem<ConnectorModule> connect2Ref = item.getObject().getConnectReference();
            ReferenceItem<ModuleObjectWithList> parent = connect2Ref.getObject().getParentRef();
            if (parent.getObject().getnumber() == 0) {
                this.getHierListReference().addReferenceItem(connect2Ref);
            }
            else if (parent.getObject().getnumber() == 1) {
            	if (parent.getObject().getObject(0).getname().equalsIgnoreCase(item.getname())) {
            		this.getHierListReference().addReferenceItem(connect2Ref);
            	}
            }
        }
    }
    
    /* Kludge */
    protected CoreProjectBasic getMainProjectForHierarchy() {
    	return null;
    }
    
    /** Creates the chip hierarchy 
     */
    protected void createHierarchy() {
    	CoreProjectBasic proj = this.getMainProjectForHierarchy();
    	
        this.getHierListReference().getObject().clearList();
        
        if (proj != null) {
        	this.createProjectHierarchy(proj.createReferenceItem());
        }
        else {
        	 for (ReferenceItem<? extends CoreProjectBasic> basic : this.getGenericSelfList()) {
             	this.createProjectHierarchy(basic);
             }
             if (this.getTestReference() != null) {
             	this.createProjectHierarchy(this.getTestReference());
             }
        }
       
        this.getHierListReference().getObject().createTree();
        this.createClassHierarchy();
    }
       
    
    protected void createClassHierarchy() {
    	List<ReferenceItem<ClassInstanceModule>> classes =  getClasses();
    	this.getClassListReference().getObject().updateClassList(classes);
    }
    
    public List<ReferenceItem<ClassInstanceModule>> getClasses() {
    	List<ReferenceItem<ClassInstanceModule>> classes = new ArrayList<ReferenceItem<ClassInstanceModule>>();
    	for (ReferenceItem<? extends CoreProjectBasic> basic : this.getGenericSelfList()) {
        	classes.addAll(basic.getObject().getClasses());
        }
    	for (ReferenceItem<? extends CoreProjectBasic> basic : this.getLibraryReference().getGenericSelfList()) {
        	classes.addAll(basic.getObject().getClasses());
        }
    	return classes;
    }
    
    public ReferenceItem<HierarchyList> getHierListReference() {
        return hierListReference;
    }
    
    public void setHierListReference(ReferenceItem<HierarchyList> hierListReference) {
        this.hierListReference = hierListReference;
    }
    
     public ReferenceItem<LibraryHolder> getLibraryReference() {
        return libraryReference;
    }

    public void setLibraryReference(ReferenceItem<LibraryHolder> libraryReference) {
        this.libraryReference = libraryReference;
    }
	public void setDefineHolder(DefineHolder defineHolder) {
		this.defineHolder = defineHolder;
	}
	public DefineHolder getDefineHolder() {
		return defineHolder;
	}
	public void setConnections(ConnectionWrapper connections) {
		this.connections = connections;
	}
	public ConnectionWrapper getConnections() {
		return connections;
	}
	public void setTestReference(ReferenceItem<CoreProjectBasic> testReference) {
		this.testReference = testReference;
	}
	public ReferenceItem<CoreProjectBasic> getTestReference() {
		return testReference;
	}
	public void setSuiteProps(SuiteProperties suiteProps) {
		this.suiteProps = suiteProps;
	}
	public SuiteProperties getSuiteProps() {
		return suiteProps;
	}
	public void setClassListReference(ReferenceItem<ClassList> classListReference) {
		this.classListReference = classListReference;
	}
	public ReferenceItem<ClassList> getClassListReference() {
		return classListReference;
	}
    
    
    
    
    
    
}
