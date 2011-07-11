/*
 * ParseDescriptor.java
 *
 * Created on October 17, 2005, 3:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.error.ErrorEnableHolder;
import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.SearchReferenceHolder;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;





/**
 *
 * @author awagner
 */
public class ParseDescriptor extends TopObjectBase{
        
    /** Reference to the suite project */
    private ReferenceItem<? extends CoreProjectSuite> suiteReference;
    /** Reference to the project which holds the file */
    private ReferenceItem<CoreProjectBasic> projectReference;
    
    //private HardwareSourceTop source;
    
    private PositionStream stream;
    
    private TopASTNode oldRoot;
    
    private TopASTNode root;
    private SuperModule module;
    
    private ArrayList folds;

    private ModuleObject regularErrors;
    private List<TopError> errorList = new ArrayList();
    
    private ReferenceLocation baseLocation;

    private boolean errored;

    private int mode; // Mode to use for parsing
    
    // These items are used only for finding reference objects which is kind of a waste but ...
    private EditorFindItem editorFindItem;
    
    private List<ReferenceUsage> findUsages;
    
    private ErrorEnableHolder errorConstants;
    private URI uri;
    
    private ModuleObjectWithList completionList;
    
    private List<DefineObject> defines = new ArrayList<DefineObject>();
    
    /** Creates a new instance of ParseDescriptor 
     * @param source 
     * @param name 
     */

    public ParseDescriptor(String name,URI uri) {
    	super(name);
    	this.uri = uri;
    	init();
    }
    
    public void clearDefines() {
    	this.defines.clear();
    }
    
    private void init()
    {
        this.module = new SuperModule(this.getname());
        this.setRegularErrors(new ModuleObject("Errors"));
        
    }
     
    public void deleteObject()
    {
        this.module.deleteObject();
        this.suiteReference = null;
        this.projectReference = null;
        this.stream = null;
        this.root = null;
        this.module = null;
        this.folds = null;
        //this.regularErrors = null;
        //super.deleteObject();
    }
    
    public TopASTNode getUsableRoot() {
    	if (this.errored) return oldRoot;
    	else return this.root;
    }
    
    public void storeLastModule() {
    	if (this.errored == false) {
    		ModuleObjectBaseItem item = new ModuleObjectBaseItem("");
    		
    		List<ReferenceItem> alist = this.module.findPrefixItemList(item, 
    				ReferenceUtilities.REF_MODULEOBJECT);
    		
    		this.completionList = new ModuleObjectWithList();
    		// Add the Entity and the Module to the list
    		for (ReferenceItem ref : alist) {
    			if (!(ref.getObject() instanceof ClassModule)) {
    			List<ReferenceItem> blist = ref.findPrefixItemList(item, ReferenceUtilities.REF_MODULEOBJECT);
    			for (ReferenceItem bref : blist) {
    				this.completionList.addReferenceItem(bref);
    			}
    			ModuleObject obj = ref.getObject(); 
    			if (obj instanceof SearchReferenceHolder) {
    				SearchReferenceHolder holder = (SearchReferenceHolder) obj;
    				if (holder.getContext() != null) {
    					blist = holder.getContext().findPrefixItemList(item, ReferenceUtilities.REF_MODULEOBJECT);
        				for (ReferenceItem bref : blist) {
            				this.completionList.addReferenceItem(bref);
            			}
    				}
    			}
    			}
    		}  		
    		// Add the context items to the list

    	}
    	
    }
    
    private void createDummyBlocks() {}

  
 
    protected ParseContext createParseContext(int mode) {
    	
    	ParseContext context = new ParseContext(this,mode);
    	return context;
    }
    protected ParseContextUsages createParseContextUsages(int mode) {
    	return new ParseContextUsages(this,mode);
    }
    
    
    public ParseContext createContext()
    {
        this.createDummyBlocks();
        ParseContext cont;
        
        if (this.getMode() == BuildInterface.BUILD_FIND_USAGES)
        {
            cont = this.createParseContextUsages(this.getMode());//new ParseContextUsages(this.getMode());
            ParseContextUsages usageContext = (ParseContextUsages) cont;
            usageContext.setEditorFindItem(getEditorFindItem());            
        }
        else
        {
            cont = this.createParseContext(this.getMode()); //new ParseContext(this.getMode());
        }
        
        if (this.getBaseLocation() != null)
        {
            ReferenceLocation loc = new ReferenceLocation(this.uri,
            		-1,
            		0,
            		0);
            cont.setBaseLocation(loc);
        }
        cont.setErrorEnableHolder(this.errorConstants);
        
        if (this.getSuiteReference() != null)
        {
            cont.getRefHandler().setGlobalReference(this.getSuiteReference());
        }
              
        cont.getRefHandler().setActiveReference(this.getSuiteReference());
        cont.getRefHandler().setProjectReference(this.getProjectReference());
       
        
        cont.getRefHandler().setSuperModuleReference(this.getModule().createReferenceItem());
        
            
        // Something is really really broken in the ordering
        this.getErrorList().clear();
        //if (this.getRegularErrors() != null)
        //    this.getRegularErrors().clearList();
        //cont.setActiveError(this.getRegularErrors());
        
        return cont;
    }
    
    
    
    
   
   /** Method which clears out the unused values when the build is completed */
    
    public void clean()
    {
        this.stream = null; // Should be enough for garbage collection
        if (folds != null) this.folds.clear();
        this.root = null;
        if (this.module != null) this.module.cleanReferences();
        this.stream = null;
        this.errorList.clear();
        //if (this.regularErrors != null) this.regularErrors.clearList();        
    }
    
    /** Attach the suite and project to this descriptor 
     * @param suiteReference 
     * @param projectReference 
     * @param loc 
     */
    public void attachProject(ReferenceItem<? extends CoreProjectSuite> suiteReference,ReferenceItem<CoreProjectBasic> projectReference, ReferenceLocation loc) {
        
        this.suiteReference = suiteReference;
       
        this.projectReference = projectReference;
        this.module.setProjectReference(projectReference);
       // this.suiteFinder = project.getSuite();
        this.baseLocation = loc;
    }
    
     
   
    
     
    /**
     * 
     * @return 
     */
    public PositionStream getStream() {
        return stream;
    }

    /**
     * 
     * @param stream 
     */
    public void setStream(PositionStream stream) {
        this.stream = stream;
    }

    /**
     * 
     * @return 
     */
    public TopASTNode getRoot() {
        return root;
    }

    /**
     * 
     * @param root 
     */
    public void setRoot(TopASTNode root) {
        this.root = root;
    }

    /**
     * 
     * @return 
     */
    public boolean isErrored() {
        return errored;
    }

    /**
     * 
     * @param errored 
     */
    public void setErrored(boolean errored) {
        this.errored = errored;
    }

    /**
     * 
     * @return 
     */
    public SuperModule getModule() {
        return module;
    }

    /**
     * 
     * @param module 
     */
    public void setModule(SuperModule module) {
        this.module = module;
    }

    /**
     * 
     * @return 
     */
    public ArrayList getFolds() {
        return folds;
    }

    /**
     * 
     * @param folds 
     */
    public void setFolds(ArrayList folds) {
        this.folds = folds;
    }

   

    

   

    /**
     * 
     * @return 
     */
    public ModuleObject getRegularErrors() {
        return regularErrors;
    }

    /**
     * 
     * @param regularErrors 
     */
    public void setRegularErrors(ModuleObject regularErrors) {
        this.regularErrors = regularErrors;
    }

    /**
     * 
     * @return 
     */
    public int getMode() {
        return mode;
    }

    /**
     * 
     * @param mode 
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    

    

    /**
     * 
     * @return 
     */
    public ReferenceLocation getBaseLocation() {
        return baseLocation;
    }

    /**
     * 
     * @param baseLocation 
     */
    public void setBaseLocation(ReferenceLocation baseLocation) {
        this.baseLocation = baseLocation;
    }

    /**
     * 
     * @return 
     */
    public ReferenceItem<CoreProjectBasic> getProjectReference() {
        return projectReference;
    }

    /**
     * 
     * @param projectReference 
     */
    public void setProjectReference(ReferenceItem<CoreProjectBasic> projectReference) {
        this.projectReference = projectReference;
    }

    /**
     * 
     * @return 
     */
    public ReferenceItem<? extends CoreProjectSuite> getSuiteReference() {
        return suiteReference;
    }

    /**
     * 
     * @param suiteReference 
     */
    public void setSuiteReference(ReferenceItem<? extends CoreProjectSuite> suiteReference) {
        this.suiteReference = suiteReference;
    }

	public void setErrorList(List<TopError> errorList) {
		this.errorList = errorList;
	}

	public List<TopError> getErrorList() {
		return errorList;
	}

	public void setOldRoot(TopASTNode oldRoot) {
		this.oldRoot = oldRoot;
	}

	public TopASTNode getOldRoot() {
		return oldRoot;
	}

	


	public void setFindUsages(List<ReferenceUsage> findUsages) {
		this.findUsages = findUsages;
	}

	public List<ReferenceUsage> getFindUsages() {
		return findUsages;
	}

	public void setErrorConstants(ErrorEnableHolder errorConstants) {
		this.errorConstants = errorConstants;
	}

	public ErrorEnableHolder getErrorConstants() {
		return errorConstants;
	}

	public void setEditorFindItem(EditorFindItem editorFindItem) {
		this.editorFindItem = editorFindItem;
	}

	public EditorFindItem getEditorFindItem() {
		return editorFindItem;
	}



	public void setCompletionList(ModuleObjectWithList completionList) {
		this.completionList = completionList;
	}

	public ModuleObjectWithList getCompletionList() {
		return completionList;
	}

	public void setDefines(List<DefineObject> defines) {
		this.defines = defines;
	}

	public List<DefineObject> getDefines() {
		return defines;
	}

   

    
   
    
}
