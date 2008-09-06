package com.rpgaudiomixer.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.rpgaudiomixer.model.Alias;
import com.rpgaudiomixer.model.Playlist;

public class PlaylistViewer extends Composite {

	private Table songTable;
	private TableViewer songTableViewer;
	private MenuManager songTableContextMenu;
	private Transfer[] fileFormat = new Transfer[] {FileTransfer.getInstance()};

	public PlaylistViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		createSongTableViewer(this);

	}

	private void createSongTableViewer(Composite parent) {
		songTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		TableColumn tcNumber = new TableColumn(songTable, SWT.LEFT);
		tcNumber.setText("#");
		tcNumber.setWidth(32);
		
		TableColumn tcName = new TableColumn(songTable, SWT.LEFT);
		tcName.setText("Name");
		tcName.setWidth(192);
		
		TableColumn tcPath = new TableColumn(songTable, SWT.LEFT);
		tcPath.setText("Path");
		tcPath.setWidth(192);
		
		songTableViewer = new TableViewer(songTable);
		songTableViewer.getTable().setLinesVisible(true);
		songTableViewer.getTable().setHeaderVisible(true);
		songTableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		// TODO: Need to create the ContextMenu before calling this
		//songTableViewer.getTable().setMenu(songTableContextMenu.createContextMenu(songTableViewer.getTable()));

		songTableViewer.setContentProvider(new IStructuredContentProvider() {
			public void dispose() { }

			public Object[] getElements(Object inputElement) {
				return ((Playlist) inputElement).getSongs().toArray();
			}
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }
		});
		
		songTableViewer.setLabelProvider(new SongLabelProvider());

		songTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				Alias a = (Alias) selection.getFirstElement();
				// TODO: Change how this is implemented
				//activateSong(a);
			}
		});

		songTableViewer.addDropSupport(DND.DROP_COPY, fileFormat, new DropTargetListener() {
			public void dragEnter(DropTargetEvent dte) {
				dte.detail = DND.DROP_COPY;
				
				//dte.currentDataType = dte.dataTypes[0];
			}
			
			public void dragLeave(DropTargetEvent dte) { }

			public void dragOperationChanged(DropTargetEvent dte) { }
			public void dragOver(DropTargetEvent dte) { }
			public void drop(DropTargetEvent dte) {
				try {
					if (FileTransfer.getInstance().isSupportedType(dte.currentDataType)) {
						String[] paths = (String[]) dte.data;						
						// TODO: Change how this is implemented
						//addFiles(paths, songTableViewer, selectedPlaylist);
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			public void dropAccept(DropTargetEvent dte) { }
		});

		songTable.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.keyCode == SWT.F2) {
					// TODO: Change how this is implemented
					//renameSelectedSongs();
				}

				if (e.keyCode == SWT.DEL) {
					// TODO: Change how this is implemented
					//deleteSelectedSongs();
				}

			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		songTable.setVisible(false);

	}

	public void setPlaylist(Playlist playlist) {
		songTableViewer.setInput(playlist);
		if (playlist == null) {
			songTable.setVisible(false);
		} else {
			songTable.setVisible(true);
		}
	}
	
	// TODO: This class needs to be able to ask the main app controller questions
	// about selected and active playlists and songs
	private final class SongLabelProvider implements ITableLabelProvider, IColorProvider {
		public String getColumnText(Object object, int columnIndex) {
			Alias a = (Alias) object;

			switch (columnIndex) {
				case 0:
					// TODO: Change how this is implemented
					//return Integer.toString(selectedPlaylist.getSongs().indexOf(a) + 1);
					return "?";
				case 1:
					return a.getName();
					
				case 2:
					return a.getPath();
						
				default:
					return a.getName();
			}
			
		}
		
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public Color getForeground(Object element) {
			Alias c = (Alias) element;
			
			// TODO: Change how this is implemented
			//if (c == activeSong) {
			//	return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);				
			//}
			return null;
		}

		public Color getBackground(Object element) {
			Alias c = (Alias) element;
			// TODO: Change how this is implemented
			//if (c == activeSong) {
			//	return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);				
			//}
			return null;
		}

		public Image getColumnImage(Object arg0, int arg1) {
			return null;
		}

		public void addListener(ILabelProviderListener listener) { }
		public void removeListener(ILabelProviderListener listener) { }
		public void dispose() { }

	}

}

