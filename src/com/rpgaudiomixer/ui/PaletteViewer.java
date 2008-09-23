package com.rpgaudiomixer.ui;

import javax.swing.event.EventListenerList;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.rpgaudiomixer.model.Alias;
import com.rpgaudiomixer.model.IResource;
import com.rpgaudiomixer.model.Palette;

public class PaletteViewer extends Composite {

	private Table effectTable;
	private TableViewer effectTableViewer;
	private Transfer[] fileFormat = new Transfer[] {FileTransfer.getInstance()};
	protected EventListenerList listenerList = new EventListenerList();
	
	public final void addPaletteViewerListener(
			final PaletteViewerListener paletteViewerListener) {
		listenerList.add(PaletteViewerListener.class, paletteViewerListener);
	}

	public final void removePaletteViewerListener(
			final PaletteViewerListener paletteViewerListener) {
		listenerList.remove(PaletteViewerListener.class, paletteViewerListener);
	}

	public PaletteViewer(Composite parent, int style) {
		super(parent, style);

		setLayout(new FillLayout());

		createEffectTableViewer(this);
	
	}

	private void createEffectTableViewer(Composite parent) {
		effectTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		TableColumn tcEffectName = new TableColumn(effectTable, SWT.LEFT);
		tcEffectName.setText("Name");
		
		TableColumn tcEffectPath = new TableColumn(effectTable, SWT.LEFT);
		tcEffectPath.setText("Path");
		
		tcEffectName.setWidth(192);
		tcEffectPath.setWidth(256);
		
		effectTableViewer = new TableViewer(effectTable);
		effectTableViewer.getTable().setLinesVisible(true);
		effectTableViewer.getTable().setHeaderVisible(true);
		effectTableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		effectTableViewer.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object a, Object b) {
				return ((Alias)a).getName().compareTo(((Alias)b).getName());
			}
		});

		effectTableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((Palette) inputElement).getEffects().toArray();
			}
			public void dispose() { }
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }
		});

		effectTableViewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				Alias c = (Alias) element;
				if (columnIndex == 0) {
					String sName = c.getName();	
					return sName;

				} else if (columnIndex == 1) {
					return c.getPath();
					
				} else { 
					return c.getPath();

				}
			}

			public void addListener(ILabelProviderListener listener) { }
			public void dispose() { }
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			public void removeListener(ILabelProviderListener listener) { }
		});

		// Effect Table Context Popup Menu
		// TODO: Change the way this is implemented
		//effectTableViewer.getTable().setMenu(effectTableContextMenu.createContextMenu(effectTableViewer.getTable()));
		
		effectTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() == 0) {
					//renameEffectAction.setEnabled(false);
					//deleteEffectAction.setEnabled(false);

				} else {
					//renameEffectAction.setEnabled(true);
					//deleteEffectAction.setEnabled(true);

				}

			}
		});

		effectTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				Alias a = (Alias) selection.getFirstElement();
				// TODO: Change the way this is implemented
				//activateEffect(a);				
			}
		});

		effectTableViewer.addDropSupport(DND.DROP_COPY, fileFormat, new DropTargetListener() {
			public void dragEnter(DropTargetEvent dte) {
				dte.detail = DND.DROP_COPY;
				
			}
			
			public void drop(DropTargetEvent dte) {				
				try {
					if (FileTransfer.getInstance().isSupportedType(dte.currentDataType)) {
						String[] paths = (String[]) dte.data;		
						// TODO: Change the way this is implemented
						Object[] listeners = listenerList.getListenerList();
						for (int i = listeners.length - 2; i >= 0; i -= 2) {
						     if (listeners[i] == PaletteViewerListener.class) {
						         // Lazily create the event:
						         ((PaletteViewerListener) listeners[i + 1]).dropFiles(paths, (IResource) effectTableViewer.getInput());
						     }
						}
						refresh();
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}

			public void dragLeave(DropTargetEvent dte) { }
			public void dragOperationChanged(DropTargetEvent dte) { }
			public void dragOver(DropTargetEvent dte) { }
			public void dropAccept(DropTargetEvent dte) { }
		});
		
		effectTable.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.keyCode == SWT.F2) {
					// TODO: Change the way this is implemented
					//renameSelectedEffects();
				}
				
				if (e.keyCode == SWT.DEL) {
					// TODO: Change the way this is implemented
					//deleteSelectedEffects();
				}

			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		effectTable.setVisible(false);
	}
	
	public void setPalette(Palette palette) {
		effectTableViewer.setInput(palette);
		if (palette == null) {
			effectTable.setVisible(false);
		} else {
			effectTable.setVisible(true);
		}
	}

	public void refresh(){
		effectTableViewer.refresh();
	}
}
