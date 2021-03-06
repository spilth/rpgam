package com.rpgaudiomixer.ui;

import java.io.File;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.rpgaudiomixer.util.VisibleDirectoryFilter;
import com.rpgaudiomixer.util.FilenameExtensionFilter;

public class AudioExplorer extends Composite {

	private SashForm explorerSash;
	private TreeViewer directoryViewer;
	protected TableViewer fileViewer;
	private Transfer[] fileFormat = new Transfer[] {FileTransfer.getInstance()};
	private FilenameExtensionFilter audioFileFilter = new FilenameExtensionFilter(new String[] {"wav", "mp3", "ogg", "flac"});
	protected EventListenerList listenerList = new EventListenerList();
	
	public AudioExplorer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		createFileBrowser(this);
	}

	public void setDirectoryContextMenu(MenuManager directoryPopupMenuManager) {
		directoryViewer.getTree().setMenu(directoryPopupMenuManager.createContextMenu(directoryViewer.getTree()));
	}

	public void setFileContextMenu(MenuManager filePopupMenuManager) {
		fileViewer.getTable().setMenu(filePopupMenuManager.createContextMenu(fileViewer.getTable()));
	}
	
	private void createFileBrowser(Composite parent) {
		explorerSash = new SashForm(parent, SWT.VERTICAL);
		explorerSash.setLayout(new FillLayout());
		// Drive List
		ListViewer driveViewer = new ListViewer(explorerSash);
		
		// Drive data comes from file system roots and never changes
		driveViewer.setContentProvider(new IStructuredContentProvider() {
			public void dispose() { }
			public Object[] getElements(Object arg0) {
				return File.listRoots();
			}
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) { }
		});
		
		driveViewer.setLabelProvider(new LabelProvider() {
	    	public String getText(Object element) {
	    		return ((File) element).getPath();
	    	}
	    });

		// Drive Viewer informs directory viewer of changes
		driveViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
		        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		        if (selection.size() > 0) {   	
			        Object selectedFile = selection.getFirstElement();
			        directoryViewer.setInput(selectedFile);

		        } else {
		        	directoryViewer.setInput(null);
		        	
		        }
	    	}
	    });

		driveViewer.setInput(File.listRoots());
		
		// Directory Tree
		directoryViewer = new TreeViewer(explorerSash);
		
		// Directory viewer gets its info from currently selected drives
		directoryViewer.setContentProvider(new ITreeContentProvider() {
			public void dispose() { }

			public Object[] getChildren(Object element) {
				Object[] children = ((File) element).listFiles(new VisibleDirectoryFilter());
				return children == null ? new Object[0] : children;
			}

			public Object[] getElements(Object element) {
				return getChildren(element);
			}

			/**
			 * @author delegreg
			 * true parameter is elementOrTreePath, meaning it is called 
			 * either with a domain object or a "path" for the object
			 * thus requiring the gimmick below
			 * 
			 */
			public Object getParent(Object elementOrTreePath) {
				
				return ((File) elementOrTreePath).getParentFile();
				/*
				if (elementOrTreePath.getClass()==File.class){
					return ((File) elementOrTreePath).getParentFile();
				}
				else {
					return (new File(((String)elementOrTreePath) )).getParentFile();
				}*/
			}
			
			public boolean hasChildren(Object element) {
				return getChildren(element).length > 0;
			}
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }
		});
		
	    directoryViewer.setLabelProvider(new LabelProvider() {
	    	public String getText(Object element) {
	    		return ((File) element).getName();
	    	}
	    });

		// Directory Viewer informs file viewer of changes
		directoryViewer.addSelectionChangedListener(new ISelectionChangedListener() {
	    	public void selectionChanged(SelectionChangedEvent event) {
		        IStructuredSelection selection = (IStructuredSelection) event.getSelection();

		        if (selection.size() > 0) {   	
			        Object selected_file = selection.getFirstElement();
			        fileViewer.setInput(selected_file);
			        
			        //directoryCreatePlaylistAction.setEnabled(true);
			        //directoryCreatePaletteAction.setEnabled(true);
			        
		        } else {
		        	fileViewer.setInput(null);

			        //directoryCreatePlaylistAction.setEnabled(false);
			        //directoryCreatePlaylistAction.setEnabled(false);
		        	
		        }
		        
				// FIX: Repetitive code
				Object[] listeners = listenerList.getListenerList();
				for (int i = listeners.length - 2; i >= 0; i -= 2) {
				     if (listeners[i] == AudioExplorerListener.class) {
				         // Lazily create the event:
			    		((AudioExplorerListener) listeners[i + 1]).directorySelectionChanged(event);
				     }
				 }
		        
	    	}
	    });

		directoryViewer.addDragSupport(DND.DROP_COPY, fileFormat, new DragSourceListener() {
			public void dragFinished(DragSourceEvent dse) { }
			public void dragStart(DragSourceEvent dse) { }		

			public void dragSetData(DragSourceEvent dse) {
				if (FileTransfer.getInstance().isSupportedType(dse.dataType)) {
					IStructuredSelection selection = (IStructuredSelection) directoryViewer.getSelection();
					File selectedDirectory = (File) selection.getFirstElement();
					File[] paths = selectedDirectory.listFiles(audioFileFilter);

					String[] files = new String[paths.length];
					for (int i = 0; i < paths.length; i++) {
						files[i] = paths[i].getAbsolutePath();
					}
					
					dse.data = files;
					dse.doit = true;

				} else {
					dse.doit = false;

				}	
			}
		});

	    directoryViewer.setInput(File.listRoots()[0]);

	    // File List
		fileViewer = new TableViewer(explorerSash, SWT.BORDER | SWT.MULTI);

		// File Viewer gets content from currently selected directory
		fileViewer.setContentProvider(new IStructuredContentProvider() {
			public void dispose() { }

			public Object[] getElements(Object element) {
				Object[] children = null;
				children = ((File) element).listFiles(audioFileFilter);		
				return children == null ? new Object[0] : children;
			}
			public void inputChanged(Viewer viewer, Object oldObject, Object newObject) { }

		});
		
		fileViewer.setLabelProvider(new ITableLabelProvider() {
			  public void addListener(ILabelProviderListener ilabelproviderlistener) { }

			  public void dispose() { }

			  public Image getColumnImage(Object arg0, int arg1) {
			    return null;
			  }

			  public String getColumnText(Object obj, int i) {
			    return ((File) obj).getName();
			  }

			  public boolean isLabelProperty(Object obj, String s) {
			  	return false;
			  }
			  
			  public void removeListener(ILabelProviderListener ilabelproviderlistener) { }
		});

		// TODO: review this code
		fileViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {

				// FIX: Repetitive code
				Object[] listeners = listenerList.getListenerList();
				for (int i = listeners.length - 2; i >= 0; i -= 2) {
				     if (listeners[i] == AudioExplorerListener.class) {
				         // Lazily create the event:
			    		((AudioExplorerListener) listeners[i + 1]).fileSelectionChanged(event);
				     }
				 }
			
			}
		});
		 

		{   //block is used to shorten variables TTL
	    	//step 1 get the users home in a String
	    	String homeFolderPath = System.getProperty("user.home");
	    	ArrayList<File> homeFolderPathElements = new ArrayList<File>();

	    	//split it via the file system
	    	File theFolder = new File(homeFolderPath);
	    	
	    	//Fill the ArrayList
	    	homeFolderPathElements.add(theFolder);
	    	while (theFolder.getParentFile() != null){
	    		theFolder = theFolder.getParentFile();
	    		homeFolderPathElements.add(theFolder);
	    	}
	    	//Now we have an ArrayList with all imbricated folders
	    	//leading to user home path
	    	//we need only reverse-traverse the List to expand each item
	    	//and select the last
	    	Object[] elements=homeFolderPathElements.toArray();
	    	for (int i = elements.length-1; i >=0 ; i--) {
	    		directoryViewer.setExpandedState((File)elements[i],true);
	    		if (i==0){
	    			directoryViewer.setSelection(new StructuredSelection((File)elements[i]));
	    		}
			}
	    }
		
		// File Viewer informs application of double-clicked files
		fileViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				File selectedFile = (File) selection.getFirstElement();

				// FIX: Repetitive code
				Object[] listeners = listenerList.getListenerList();
				for (int i = listeners.length - 2; i >= 0; i -= 2) {
				     if (listeners[i] == AudioExplorerListener.class) {
				         // Lazily create the event:
			    		((AudioExplorerListener) listeners[i + 1]).fileDoubleClicked(selectedFile);
							 
				     }
				 }
			}
		});

		// File Viewer drag and drop support
		fileViewer.addDragSupport(DND.DROP_COPY, fileFormat, new DragSourceListener() {
			public void dragFinished(DragSourceEvent dse) { }
			public void dragStart(DragSourceEvent dse) { }		

			public void dragSetData(DragSourceEvent dse) {
				if (FileTransfer.getInstance().isSupportedType(dse.dataType)) {
					IStructuredSelection selection = (IStructuredSelection) fileViewer.getSelection();
					Object[] items = selection.toArray();
					String[] paths = new String[items.length];
					for (int i = 0; i < items.length; i++) {
						paths[i] = ((File) items[i]).getAbsolutePath();
					}
					dse.data = paths;
					dse.doit = true;

				} else {
					dse.doit = false;

				}	
			}
		});
		
		
	    //define relative heights
	    explorerSash.setWeights(new int[] {10, 40, 50});
	}

	public final void addAudioExplorerListener(
			final AudioExplorerListener audioExplorerListener) {
		listenerList.add(AudioExplorerListener.class, audioExplorerListener);
	}

	public final void removeAudioExplorerListener(
			final AudioExplorerListener audioExplorerListener) {
		listenerList.remove(AudioExplorerListener.class, audioExplorerListener);
	}

	public IStructuredSelection getDirectorySelection() { 
		return (IStructuredSelection) directoryViewer.getSelection();
	}		

	public IStructuredSelection getFileSelection() { 
		return (IStructuredSelection) fileViewer.getSelection();
	}		
	
	
}
