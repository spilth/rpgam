package com.rpgaudiomixer.ui;

import java.util.List;

import javax.swing.event.EventListenerList;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.rpgaudiomixer.model.AliasCollector;
import com.rpgaudiomixer.model.Folder;
import com.rpgaudiomixer.model.IResource;
import com.rpgaudiomixer.model.Library;
import com.rpgaudiomixer.model.Palette;
import com.rpgaudiomixer.model.Playlist;
import com.rpgaudiomixer.ui.transfers.FolderTransfer;
import com.rpgaudiomixer.ui.transfers.PaletteTransfer;
import com.rpgaudiomixer.ui.transfers.PlaylistTransfer;

public class LibraryExplorer extends Composite {
	private TreeViewer treeViewer;
	private Image folderImage;
	private Image playlistImage;
	private Image paletteImage;
	private IResource selectedResource;
	private Transfer[] libraryFormat = new Transfer[] {
		FolderTransfer.getInstance(),
		PlaylistTransfer.getInstance(),
		PaletteTransfer.getInstance(),
		FileTransfer.getInstance()
	};
	protected EventListenerList listenerList = new EventListenerList();
	
	public final void addLibraryViewerListener(
			final LibraryExplorerListener libraryViewerListener) {
		listenerList.add(LibraryExplorerListener.class, libraryViewerListener);
	}

	public final void removeLibraryViewerListener(
			final LibraryExplorerListener libraryViewerListener) {
		listenerList.remove(LibraryExplorerListener.class, libraryViewerListener);
	}
	
	public LibraryExplorer(Composite parent, int style) {
		super(parent, style);

		treeViewer = new TreeViewer(this, SWT.SINGLE);

		setLayout(new FillLayout());
		
		folderImage = new Image(getShell().getDisplay(), "icons/folder.gif");
		playlistImage = new Image(getShell().getDisplay(), "icons/playlist.gif");
		paletteImage = new Image(getShell().getDisplay(), "icons/palette.gif");
	
		createTreeViewer();

	}

	private void createTreeViewer() {
		treeViewer.setContentProvider(new ITreeContentProvider() {
			public void dispose() { }

			public Object[] getChildren(Object o) {
				return ((IResource)o).getChildren().toArray();
			}

			public Object[] getElements(Object o) {
				return ((IResource)o).getChildren().toArray();
			}

			public Object getParent(Object o) {
				return ((IResource)o).getParent();
			}

			public boolean hasChildren(Object o) {
				IResource resource = (IResource) o;
				List<IResource> items = resource.getChildren();
				if (items == null || items.size() == 0) return false;

				return true;
			}

			public void inputChanged(Viewer arg0, Object arg1, Object arg2) { }

		});
		
		treeViewer.setLabelProvider(new LabelProvider() {
			public Image getImage(Object o) {
				if (o.getClass() == Folder.class) return folderImage;
				if (o.getClass() == Playlist.class) return playlistImage;
				if (o.getClass() == Palette.class) return paletteImage;
				return folderImage;
			}

			public String getText(Object o) {
				return ((IResource)o).getName();
			}
		});
		
		treeViewer.setSorter(new ViewerSorter() {
			public int category(Object o) {
				IResource resource = (IResource) o;
				if (resource.getClass() == Folder.class) return 0;
				return 1;
			}
		});
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {				
				if(event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					if (selection == null) {
						selectedResource = null;
					} else {
						selectedResource = (IResource) selection.getFirstElement();
					}
				
			        // TODO: Tell any listeners about the selection change
					Object[] listeners = listenerList.getListenerList();
					for (int i = listeners.length - 2; i >= 0; i -= 2) {
					     if (listeners[i] == LibraryExplorerListener.class) {
					         // Lazily create the event:
					         ((LibraryExplorerListener) listeners[i + 1]).resourceSelected(selectedResource);
					     }
					}

					if (selectedResource != null) {
						if (selectedResource.getClass() == Folder.class) {
							// TODO: Who should handle this activity?
							//tv.getTree().setMenu(folderContextMenu.createContextMenu(getShell()));
						}
					
					   	if (selectedResource.getClass() == Playlist.class) {
					   		// TODO: Who should handle this activity?
					   		//tv.getTree().setMenu(playlistContextMenu.createContextMenu(getShell()));
					   	}
					
					   	if (selectedResource.getClass() == Palette.class) {
					   		// TODO: Who should handle this activity?
					   		//tv.getTree().setMenu(paletteContextMenu.createContextMenu(getShell()));
					   	}
					}
				}
			}
		});
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				IResource resource = (IResource) selection.getFirstElement();
				
				if (treeViewer.isExpandable(resource)) {
					if (treeViewer.getExpandedState(resource) == true) {
						// TODO: What is the second argument here?
						treeViewer.collapseToLevel(resource, 1);
						
					} else {
						// TODO: What is the second argument here?
						treeViewer.expandToLevel(resource, 1);
					}	

				} else {

					// FIX: Repetitive code
					Object[] listeners = listenerList.getListenerList();
					for (int i = listeners.length - 2; i >= 0; i -= 2) {
					     if (listeners[i] == LibraryExplorerListener.class) {
					         // Lazily create the event:
							if (resource instanceof Playlist) {
								((LibraryExplorerListener) listeners[i + 1]).playlistOpened(selectedResource);
							} else if (resource instanceof Palette) {
						         ((LibraryExplorerListener) listeners[i + 1]).paletteOpened(selectedResource);								
							}
					     }
					 }

				}
			}
	
		});
		
		final DragSource dragSource = new DragSource(treeViewer.getTree(), DND.DROP_COPY | DND.DROP_MOVE);
		dragSource.setTransfer(libraryFormat);
		dragSource.addDragListener(new DragSourceAdapter() {
			public void dragFinished(DragSourceEvent event) { }

			public void dragStart(DragSourceEvent event) {				
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				IResource resource = (IResource) selection.getFirstElement();

				if (resource instanceof Folder) {
					dragSource.setTransfer(new Transfer[] {FolderTransfer.getInstance()});
				} else if (resource instanceof Playlist) {
					dragSource.setTransfer(new Transfer[] {PlaylistTransfer.getInstance()});					
				} else if (resource instanceof Palette) {
					dragSource.setTransfer(new Transfer[] {PaletteTransfer.getInstance()});
				}
			}

			public void dragSetData(DragSourceEvent event) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				IResource resource = (IResource) selection.getFirstElement();

				if (
						FolderTransfer.getInstance().isSupportedType(event.dataType) ||
						PlaylistTransfer.getInstance().isSupportedType(event.dataType) ||
						PaletteTransfer.getInstance().isSupportedType(event.dataType)
					) {

					if (resource instanceof Folder) {
						Folder folder = (Folder) resource;
						event.data = folder;
						event.doit = true;

					} else if (resource instanceof Playlist) {
						Playlist playlist = (Playlist) resource;
						event.data = playlist;
						event.doit = true;
						
					} else if (resource instanceof Palette) {
						Palette palette = (Palette) resource;
						event.data = palette;
						event.doit = true;
						
					} else {
						event.doit = false;

					}

				} else {
					event.doit = false;

				}

			}			

		}
		);

		final DropTarget dropTarget = new DropTarget(treeViewer.getTree(), DND.DROP_COPY | DND.DROP_MOVE);
		dropTarget.setTransfer(libraryFormat);
		dropTarget.addDropListener(new DropTargetAdapter() {
			public void dragEnter(DropTargetEvent event) {
				if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
					// Files are copied into Playlists and Palettes
					event.detail = DND.DROP_COPY;					
				} else {
					// Folders, Playlists and Palettes get moved
					event.detail = DND.DROP_MOVE;
				}
			}

			public void drop(DropTargetEvent event) {
				Tree tree = treeViewer.getTree();	
				TreeItem targetItem = tree.getItem(tree.toControl(new Point(event.x, event.y)));
				IResource resource = (IResource) selectedResource;
				IResource targetResource = null;
				
				if (targetItem != null) {
					targetResource = (IResource) targetItem.getData();
				}
				
					try {
						if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
							String[] paths = (String[]) event.data;
							AliasCollector ac = (AliasCollector) targetResource;

							if (targetResource instanceof Playlist) {
								// TODO: What should handle this activity?
								//addFiles(paths, songTableViewer, ac);

							} else if (targetResource instanceof Palette) {
								// TODO: What should handle this activity?
								//addFiles(paths, effectTableViewer, ac);

							}

						} else if (
								FolderTransfer.getInstance().isSupportedType(event.currentDataType) ||
								PlaylistTransfer.getInstance().isSupportedType(event.currentDataType) ||
								PaletteTransfer.getInstance().isSupportedType(event.currentDataType)
						) {
							// TODO: This looks like app/model specific behavior and shouldn't be here
							
							Object[] listeners = listenerList.getListenerList();
							for (int i = listeners.length - 2; i >= 0; i -= 2) {
							     if (listeners[i] == LibraryExplorerListener.class) {
							         // Lazily create the event:
							         ((LibraryExplorerListener) listeners[i + 1]).moveResource(selectedResource, targetResource);
							     }
							}
							
							treeViewer.refresh();
							
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				
			}

			public void dropAccept(DropTargetEvent event) { }
			public void dragLeave(DropTargetEvent event) { }
			public void dragOperationChanged(DropTargetEvent event) { }
			public void dragOver(DropTargetEvent event) { }
		
		});
		
		treeViewer.getTree().addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.keyCode == SWT.F2) {
					// TODO: Who should handle this activity?
					//renameSelectedResource();
				}
				
				if (e.keyCode == SWT.DEL) {
					// TODO: Who should handle this activity?
					//deleteSelectedResource();
				}

			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		treeViewer.getTree().setVisible(false);
	}
	
	public void setLibrary(Library library) {
		treeViewer.setInput(library.getRoot());
		treeViewer.getTree().setVisible(true);
	}

	public void refresh(IResource resource) {
		treeViewer.refresh(resource);
	}
}
