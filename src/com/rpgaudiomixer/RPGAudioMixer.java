package com.rpgaudiomixer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;

import com.rpgaudiomixer.audioengine.AudioEngine;
import com.rpgaudiomixer.audioengine.AudioEngineListener;
import com.rpgaudiomixer.audioengine.JavaZoomAudioEngine;
import com.rpgaudiomixer.model.Alias;
import com.rpgaudiomixer.model.AliasCollector;
import com.rpgaudiomixer.model.Folder;
import com.rpgaudiomixer.model.IResource;
import com.rpgaudiomixer.model.Library;
import com.rpgaudiomixer.model.Palette;
import com.rpgaudiomixer.model.Playlist;
import com.rpgaudiomixer.ui.AudioExplorer;
import com.rpgaudiomixer.ui.AudioExplorerListener;
import com.rpgaudiomixer.ui.LibraryExplorer;
import com.rpgaudiomixer.ui.LibraryExplorerListener;
import com.rpgaudiomixer.ui.PaletteViewer;
import com.rpgaudiomixer.ui.PlaylistViewer;
import com.rpgaudiomixer.ui.SongPlayer;
import com.rpgaudiomixer.util.FilenameExtensionFilter;
import com.thoughtworks.xstream.XStream;

public class RPGAudioMixer extends ApplicationWindow
		implements AudioEngineListener, LibraryExplorerListener, AudioExplorerListener {

	public static void main(String[] args) {
		RPGAudioMixer rpgam = new RPGAudioMixer();
		rpgam.setBlockOnOpen(true);
		rpgam.open();
		Display.getCurrent().dispose();
		rpgam.quit();
	}

	private final static int MAX_RESOURCE_NAME_LENGTH = 32;
	
	private Action newFolderAction, deleteFolderAction, renameFolderAction;
	private Action newPlaylistAction, playPlaylistAction, deletePlaylistAction, renamePlaylistAction;
	private Action newPaletteAction, deletePaletteAction, renamePaletteAction;
	
	private Action newSongAction;
	private Action playSongAction;
	private Action deleteSongAction;
	private Action renameSongAction;
	private Action moveSongDownAction;
	private Action moveSongUpAction;

	private Action playEffectAction, newEffectAction, renameEffectAction, deleteEffectAction;
	
	private Action directoryAddPlaylistAction, directoryAddPaletteAction;
	private Action fileAddPlaylistAction, fileAddPaletteAction;
	private Action filePreviewAction;

	private Action newLibraryAction, openLibraryAction, saveLibraryAction, saveLibraryAsAction, closeLibraryAction, exitAction;
	
	private Library library;
	private Alias activeSong;
	private AudioEngine audioEngine;
	private boolean bDirtyData;
	private FileDialog saveLibraryDialog, openFileDialog, songDialog;
	private FilenameExtensionFilter audioFileFilter = new FilenameExtensionFilter(new String[] {"wav", "mp3", "ogg", "flac"});
	private Group explorerComposite, paletteComposite, playlistComposite, resourceComposite;
	private InputDialog renameDialog;
	private IResource selectedResource;
	private MenuManager directoryPopupMenuManager;
	private MenuManager effectTableContextMenu;
	private MenuManager filePopupMenuManager;
	private MenuManager folderContextMenu, playlistContextMenu, paletteContextMenu, songTableContextMenu;
	private MenuManager newMenu;
	private MessageBox confirmDialog;
	private Palette selectedPalette;
	private Playlist activePlaylist, selectedPlaylist;
	private String libraryPath;
	private String[] audioExtensions = new String[] {"*.mp3;*.wav;*.ogg;*.flac", "*.wav", "*.mp3", "*.ogg", "*.flac"};
	private String[] rpgamExtension = new String[] {"*.raml"};
	private TableViewer effectTableViewer;
	private XStream xstream;
	private Image deleteImage;
	private Image saveImage;
	private Image saveAsImage;
	private Image newLibraryImage;
	private Image newFolderImage;
	private Image newPlaylistImage;
	private Image newPaletteImage;
	private IInputValidator resourceNameInputValidator;

	private IAction showLibraryExplorerAction;
	private IAction showAudioExplorerAction;
	private IAction showPlaylistAction;
	private IAction showPaletteAction;
	private IAction showSongPlayerAction;

	private SashForm mainSash;

	private Composite middleComposite;

	private Group playerComposite;

	private LibraryExplorer libraryViewer;

	private PlaylistViewer playlistViewer;

	private PaletteViewer paletteViewer;

	private AudioExplorer audioExplorer;

	private SongPlayer songPlayer;
	
	public RPGAudioMixer() {
		super(null);

		audioEngine = new JavaZoomAudioEngine();
		audioEngine.init();
		audioEngine.addAudioEngineListener(this);
		
		//library = new Library();

		xstream = new XStream();
		xstream.alias("library", Library.class);
		xstream.alias("alias", Alias.class);
		xstream.alias("folder", Folder.class);
		xstream.alias("palette", Palette.class);
		xstream.alias("playlist", Playlist.class);

		createActions();
		createContextMenus();

		addMenuBar();
		addToolBar(SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	}
	
	private void createActions() {
		// File
		newLibraryAction = new Action("&New", IAction.AS_PUSH_BUTTON) {
			public void run() {
				newLibrary();
			}
		};
		
		openLibraryAction = new Action("&Open", IAction.AS_PUSH_BUTTON) {
			public void run() {
				openLibrary();
			}
		};
		
		saveLibraryAction = new Action("&Save", IAction.AS_PUSH_BUTTON) {
			public void run() {
				saveLibrary();
			}
		};
		
		saveLibraryAsAction = new Action("Save &As...",
				IAction.AS_PUSH_BUTTON) {

			public void run() {
				saveLibraryAs();
			}
		};
		
		closeLibraryAction = new Action("&Close", IAction.AS_PUSH_BUTTON) {
			public void run() {
				closeLibrary();
			}
		};
		
		exitAction = new Action("E&xit", IAction.AS_PUSH_BUTTON) {
			public void run() {
				quit();
			}
		};
		
		newLibraryAction.setAccelerator(SWT.CTRL + 'N');
		openLibraryAction.setAccelerator(SWT.CTRL + 'O');
		saveLibraryAction.setAccelerator(SWT.CTRL + 'S');
		
		newLibraryAction.setEnabled(true);
		openLibraryAction.setEnabled(true);
		saveLibraryAction.setEnabled(false);
		saveLibraryAsAction.setEnabled(false);
		closeLibraryAction.setEnabled(false);
		exitAction.setEnabled(true);
		
		// View Actions
		showLibraryExplorerAction = new Action("Show Library Explorer",
				IAction.AS_CHECK_BOX) {

			public void run() {
				toggleLibraryExplorer();
			}
		};
		showAudioExplorerAction = new Action("Show Audio Explorer",
				IAction.AS_CHECK_BOX) {

			public void run() {
				toggleAudioExplorer();
			}
		};
		showPlaylistAction = new Action("Show Playlist",
				IAction.AS_CHECK_BOX) {

			public void run() {
				togglePlaylist();
			}
		};
		showPaletteAction = new Action("Show Palette",
				IAction.AS_CHECK_BOX) {

			public void run() {
				togglePalette();
			}
		};
		showSongPlayerAction = new Action("Show Song Player",
				IAction.AS_CHECK_BOX) {

			public void run() {
				toggleSongPlayer();
			}
		};
		
		showLibraryExplorerAction.setAccelerator(SWT.ALT+ '1');
		showAudioExplorerAction.setAccelerator(SWT.ALT+ '2');
		showPlaylistAction.setAccelerator(SWT.ALT+ '3');
		showPaletteAction.setAccelerator(SWT.ALT+ '4');
		showSongPlayerAction.setAccelerator(SWT.ALT+ '5');

		showLibraryExplorerAction.setChecked(true);
		showAudioExplorerAction.setChecked(true);
		showPlaylistAction.setChecked(true);
		showPaletteAction.setChecked(true);
		showSongPlayerAction.setChecked(true);
		
		
		// Resources
		newFolderAction = new Action("Folder", IAction.AS_PUSH_BUTTON) {
			public void run() {
				newFolder();
			}
		};
		newPlaylistAction = new Action("Playlist", IAction.AS_PUSH_BUTTON) {
			public void run() {
				newPlaylist();
			}
		};
		newPaletteAction = new Action("Palette", IAction.AS_PUSH_BUTTON) {
			public void run() {
				newPalette();
			}
		};
		
		// Resource - Folder
		renameFolderAction = new Action("Rename", IAction.AS_PUSH_BUTTON) {
			public void run() {
				renameFolder();
			}
		};
		
		deleteFolderAction = new Action("Delete", IAction.AS_PUSH_BUTTON) {
			public void run() {
				deleteSelectedFolder();
			}
		};

		// Resource - Playlist
		playPlaylistAction = new Action("Play", IAction.AS_PUSH_BUTTON) {
			public void run() {
				activatePlaylist();
			}
		};
		renamePlaylistAction = new Action("Rename", IAction.AS_PUSH_BUTTON) {
			public void run() {
				renamePlaylist();
			}
		};
		deletePlaylistAction = new Action("Delete", IAction.AS_PUSH_BUTTON) {
			public void run() {
				deleteSelectedPlaylist();
			}
		};

		// Resource - Palette
		renamePaletteAction = new Action("Rename", IAction.AS_PUSH_BUTTON) {
			public void run() {
				renamePalette();
			}
		};
		deletePaletteAction = new Action("Delete", IAction.AS_PUSH_BUTTON) {
			public void run() {
				deleteSelectedPalette();
			}
		};

		// Song
		playSongAction = new Action("Play", IAction.AS_PUSH_BUTTON) {
			public void run() {
				playSelectedSong();
			}
		};
		newSongAction = new Action("New", IAction.AS_PUSH_BUTTON) {
			public void run() {
				newSong();
			}
		};
		renameSongAction = new Action("Rename", IAction.AS_PUSH_BUTTON) {
			public void run() {
				renameSelectedSongs();
			}
		};
		deleteSongAction = new Action("Delete", IAction.AS_PUSH_BUTTON) {
			public void run() {
				deleteSelectedSongs();
			}
		};
		moveSongUpAction = new Action("Move Up", IAction.AS_PUSH_BUTTON) {
			public void run() {
				moveSongUp();
			}
		};
		moveSongDownAction = new Action("Move Down", IAction.AS_PUSH_BUTTON) {
			public void run() {
				moveSongDown();
			}
		};

		/*
		moveSongUpAction.setAccelerator(SWT.ALT + SWT.ARROW_UP);
		moveSongDownAction.setAccelerator(SWT.ALT + SWT.ARROW_DOWN);
		*/

		// Effect
		playEffectAction = new Action("Play", IAction.AS_PUSH_BUTTON) {
			public void run() {
				playSelectedEffect();
			}
		};
		
		newEffectAction = new Action("New", IAction.AS_PUSH_BUTTON) {
			public void run() {
				newEffect();
			}
		};
		renameEffectAction = new Action("Rename", IAction.AS_PUSH_BUTTON) {
			public void run() {
				renameSelectedEffects();
			}
		};
		deleteEffectAction = new Action("Delete", IAction.AS_PUSH_BUTTON) {
			public void run() {
				deleteSelectedEffects();
			}
		};

		// Audio Explorer - Directory
		directoryAddPlaylistAction = new Action("Add Files To Playlist",
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				directoryAddPlaylist();
			}
		};
		directoryAddPaletteAction = new Action("Add Files To Palette",
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				directoryAddPalette();
			}
		};	

		// Audio Explorer - File
		fileAddPlaylistAction = new Action("Add to Playlist",
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				fileAddPlaylist();
			}
		};
		fileAddPaletteAction = new Action("Add to Palette",
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				fileAddPalette();
			}
		};
		filePreviewAction = new Action("Preview", IAction.AS_PUSH_BUTTON) {
			public void run() {
				filePreview();
			}
		};

		filePreviewAction.setEnabled(false);
		fileAddPlaylistAction.setEnabled(false);
		fileAddPaletteAction.setEnabled(false);
		
	}

	// UI Creation Methods
	
	protected void toggleSongPlayer() {
		playerComposite.setVisible(!playerComposite.getVisible());
		middleComposite.layout();	
	}

	protected void togglePalette() {
		paletteComposite.setVisible(!paletteComposite.getVisible());
		middleComposite.layout();
	}

	protected void togglePlaylist() {
		playlistComposite.setVisible(!playlistComposite.getVisible());
		middleComposite.layout();
	}

	protected void toggleAudioExplorer() {
		explorerComposite.setVisible(!explorerComposite.getVisible());
		mainSash.layout();
	}

	protected void toggleLibraryExplorer() {
		resourceComposite.setVisible(!resourceComposite.getVisible());
		mainSash.layout();
	}

	protected void stopSong() {
		audioEngine.stopSong();
	}

	protected Control createContents(Composite parent) {
		getShell().setText("RPG Audio Mixer");
		parent.setSize(800, 600);
		parent.setLocation(16, 16);

		createDialogs();

		deleteImage = new Image(getShell().getDisplay(), "icons/delete.gif");

		newLibraryImage = new Image(getShell().getDisplay(), "icons/newlibrary.gif");
		newFolderImage = new Image(getShell().getDisplay(), "icons/folder.gif");
		newPlaylistImage = new Image(getShell().getDisplay(), "icons/newplaylist.gif");
		newPaletteImage = new Image(getShell().getDisplay(), "icons/newpalette.gif");
		saveImage = new Image(getShell().getDisplay(), "icons/save.gif");
		saveAsImage = new Image(getShell().getDisplay(), "icons/saveas.gif");

		newLibraryAction.setImageDescriptor(ImageDescriptor.createFromImage(newLibraryImage));
		newFolderAction.setImageDescriptor(ImageDescriptor.createFromImage(newFolderImage));
		newPlaylistAction.setImageDescriptor(ImageDescriptor.createFromImage(newPlaylistImage));
		newPaletteAction.setImageDescriptor(ImageDescriptor.createFromImage(newPaletteImage));

		ImageDescriptor deleteImageDescriptor = ImageDescriptor.createFromImage(deleteImage);
		ImageDescriptor saveImageDescriptor = ImageDescriptor.createFromImage(saveImage);
		ImageDescriptor saveAsImageDescriptor = ImageDescriptor.createFromImage(saveAsImage);

		deleteFolderAction.setImageDescriptor(deleteImageDescriptor);
		deletePlaylistAction.setImageDescriptor(deleteImageDescriptor);
		deletePaletteAction.setImageDescriptor(deleteImageDescriptor);
		deleteSongAction.setImageDescriptor(deleteImageDescriptor);
		deleteEffectAction.setImageDescriptor(deleteImageDescriptor);
		saveLibraryAction.setImageDescriptor(saveImageDescriptor);
		saveLibraryAsAction.setImageDescriptor(saveAsImageDescriptor);
		
		mainSash = new SashForm(parent, SWT.HORIZONTAL);
		mainSash.SASH_WIDTH = 4;
		
		// Resource Viewer
		resourceComposite = new Group(mainSash, SWT.SHADOW_NONE);
		resourceComposite.setLayout(new FillLayout());
		resourceComposite.setText("Library Explorer");
		libraryViewer = new LibraryExplorer(resourceComposite, SWT.NULL);
		libraryViewer.addLibraryViewerListener(this);
		
		middleComposite = new Composite(mainSash, SWT.NULL);
		middleComposite.setLayout(new FillLayout(SWT.VERTICAL));

		// Middle Column - Playlist Viewer
		playlistComposite = new Group(middleComposite, SWT.SHADOW_NONE);
		playlistComposite.setLayout(new FillLayout());
		playlistComposite.setText("Playlist");
		playlistViewer = new PlaylistViewer (playlistComposite, SWT.NULL);
		
		playerComposite = new Group(middleComposite, SWT.SHADOW_NONE);
		playerComposite.setLayout(new FillLayout());
		playerComposite.setText("Song Player");
		songPlayer = new SongPlayer(playerComposite, SWT.NULL);
		
		// Middle Column - Palette Viewer
		paletteComposite = new Group(middleComposite, SWT.SHADOW_NONE);
		paletteComposite.setLayout(new FillLayout());
		paletteComposite.setText("Palette");
		paletteViewer = new PaletteViewer(paletteComposite, SWT.NULL);

		// Audio Explorer
		explorerComposite = new Group(mainSash, SWT.SHADOW_NONE);
		explorerComposite.setLayout(new FillLayout());
		explorerComposite.setText("Audio Explorer");
		audioExplorer = new AudioExplorer(explorerComposite, SWT.NULL);
		audioExplorer.addAudioExplorerListener(this);
		audioExplorer.setDirectoryContextMenu(directoryPopupMenuManager);
		audioExplorer.setFileContextMenu(filePopupMenuManager);
		
		mainSash.setWeights(new int[] {20, 60, 20});
		
		return mainSash;
	}

	private void createDialogs() {

		resourceNameInputValidator = new IInputValidator() {
	    	public String isValid(String newText) {
	    		if (newText.equals("")) {
	    			return ("The name cannot be blank.");
	    		}
	    		
	    		if (newText.length() > MAX_RESOURCE_NAME_LENGTH) {
	    			return ("The name cannot exceed " + MAX_RESOURCE_NAME_LENGTH + " characters.");
	    		}
	    		return null;
	    	}
		};

		saveLibraryDialog = new FileDialog(getShell(), SWT.SAVE | SWT.SINGLE);
		saveLibraryDialog.setFilterExtensions(rpgamExtension);

		openFileDialog =  new FileDialog(getShell(), SWT.SINGLE);
		openFileDialog.setFilterExtensions(rpgamExtension);

		songDialog = new FileDialog(getShell(), SWT.MULTI);
		songDialog.setFilterExtensions(audioExtensions);
		
		confirmDialog = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		confirmDialog.setText("RPG Audio Mixer");
	}

	private void createContextMenus() {
		// Library - New
		newMenu = new MenuManager("New");
		newMenu.add(newFolderAction);
		newMenu.add(newPlaylistAction);
		newMenu.add(newPaletteAction);

		// Library - Folder
		folderContextMenu = new MenuManager("Folder");
		folderContextMenu.add(newMenu);
		folderContextMenu.add(new Separator());
		folderContextMenu.add(deleteFolderAction);
		folderContextMenu.add(renameFolderAction);

		// Library - Playlist
		playlistContextMenu = new MenuManager("Playlist");
		playlistContextMenu.add(playPlaylistAction);
		playlistContextMenu.add(new Separator());
		playlistContextMenu.add(deletePlaylistAction);
		playlistContextMenu.add(renamePlaylistAction);

		// Library - Palette
		paletteContextMenu = new MenuManager("Palette");
		paletteContextMenu.add(deletePaletteAction);
		paletteContextMenu.add(renamePaletteAction);

		// Song Table
		songTableContextMenu = new MenuManager("Playlist");
		songTableContextMenu.add(playSongAction);
		songTableContextMenu.add(new Separator());
		songTableContextMenu.add(newSongAction);
		songTableContextMenu.add(new Separator());
		songTableContextMenu.add(renameSongAction);
		songTableContextMenu.add(deleteSongAction);
		songTableContextMenu.add(moveSongUpAction);
		songTableContextMenu.add(moveSongDownAction);

		// Effect Table
		effectTableContextMenu = new MenuManager("Palette");
		effectTableContextMenu.add(playEffectAction);
		effectTableContextMenu.add(new Separator());
		effectTableContextMenu.add(newEffectAction);
		effectTableContextMenu.add(new Separator());
		effectTableContextMenu.add(renameEffectAction);
		effectTableContextMenu.add(deleteEffectAction);

		// Audio Explorer - Directory
		directoryPopupMenuManager = new MenuManager();
		directoryPopupMenuManager.add(directoryAddPlaylistAction);
		directoryPopupMenuManager.add(directoryAddPaletteAction);
		
		// Audio Explorer - File
		filePopupMenuManager = new MenuManager();
		filePopupMenuManager.add(filePreviewAction);
		filePopupMenuManager.add(fileAddPlaylistAction);
		filePopupMenuManager.add(fileAddPaletteAction);
	}

	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager tbm = new ToolBarManager(style);
		tbm.add(newLibraryAction);
		tbm.add(openLibraryAction);
		tbm.add(saveLibraryAction);
		tbm.add(saveLibraryAsAction);
		tbm.add(new Separator());
		tbm.add(newFolderAction);
		tbm.add(newPlaylistAction);
		tbm.add(newPaletteAction);

		return tbm;
	}
	
	protected MenuManager createMenuManager() {
		MenuManager fileMenuManager	= new MenuManager("&File");
		fileMenuManager.add(newLibraryAction);
		fileMenuManager.add(openLibraryAction);
		fileMenuManager.add(new Separator());
		fileMenuManager.add(saveLibraryAction);
		fileMenuManager.add(saveLibraryAsAction);
		fileMenuManager.add(new Separator());
		fileMenuManager.add(closeLibraryAction);
		fileMenuManager.add(new Separator());
		fileMenuManager.add(exitAction);

		MenuManager viewMenuManager = new MenuManager("&View");
		viewMenuManager.add(showLibraryExplorerAction);
		viewMenuManager.add(showAudioExplorerAction);
		
		// TODO: Put back in when Middle Column is switched to a resizable component
		//viewMenuManager.add(showPlaylistAction);
		//viewMenuManager.add(showPaletteAction);
		//viewMenuManager.add(showSongPlayerAction);
		
		MenuManager libraryMenuManager = new MenuManager("&Library");
		//libraryMenuManager.add(newMenu);
		
		MenuManager playlistMenuManager = new MenuManager("Playlist");

		MenuManager paletteMenuManager = new MenuManager("Palette");
		
		MenuManager mainMenu = new MenuManager();
		mainMenu.add(fileMenuManager);
		mainMenu.add(libraryMenuManager);
		mainMenu.add(playlistMenuManager);
		mainMenu.add(paletteMenuManager);
		mainMenu.add(viewMenuManager);

		return mainMenu;
	}
	
	// Action Methods
	
	// Audio Explorer Actions
	
	private void directoryAddPlaylist() {
		// TODO: Change how this is implemented
		addSelectedDirectory(audioExplorer.getDirectorySelection(), effectTableViewer, selectedPlaylist);
	}

	private void directoryAddPalette() {
		addSelectedDirectory(audioExplorer.getDirectorySelection(), effectTableViewer, selectedPalette);
	}

	private void fileAddPlaylist() {
		// TODO: Change how this is implemented
		addSelectedFiles(audioExplorer.getFileSelection(), effectTableViewer, selectedPlaylist);
	}

	private void fileAddPalette() {
		addSelectedFiles(audioExplorer.getFileSelection(), effectTableViewer, selectedPalette);
	}

	private void filePreview() {
		IStructuredSelection selection = (IStructuredSelection) audioExplorer.getFileSelection();
		File selectedFile = (File) selection.getFirstElement();
		previewSong(selectedFile);
	}
	
	private void addSelectedDirectory(IStructuredSelection source, Viewer destinationViewer, AliasCollector ac) {
		//IStructuredSelection selection = (IStructuredSelection) sourceViewer.getSelection();
		File f = (File) source.getFirstElement();

		// Get all its files
		File[] paths = f.listFiles(audioFileFilter);

		// Loop through and add the to the currently selected Playlist
		int iPathCount = paths.length;
		if (iPathCount > 0) {
			String sName;
			File g;
			for (int i = 0; i < iPathCount; i++) {
				g = paths[i];
				if (g.exists()) {
					sName = g.getName();

					if (!sName.equals("")) {
						Alias a = new Alias(sName, g.getPath());
						ac.add(a);
						destinationViewer.refresh();
					}
				}
			}

			touchLibrary();
		}
	}

	private void addSelectedFiles(IStructuredSelection source, Viewer destinationViewer, AliasCollector ac) {
		//IStructuredSelection selection = (IStructuredSelection) sourceViewer.getSelection();
		Iterator i = source.iterator();
		File f;
		Alias a;
		while (i.hasNext()) {
			f = (File) i.next();
			a = new Alias(f.getName(), f.getPath());

			ac.add(a);
		}
		destinationViewer.refresh();
		touchLibrary();
	}

	// Resource Viewer Actions

	private void deleteSelectedFolder() {
		deleteSelectedResource();
	}

	private void deleteSelectedPalette() {
		deleteSelectedResource();		
	}

	private void deleteSelectedPlaylist() {
		deleteSelectedResource();
	}

	private void deleteSelectedResource() {
		confirmDialog.setMessage("Delete '" + selectedResource.getName() + "'?");
		if (selectedResource instanceof Folder) {
				int childrenCount = selectedResource.getChildren().size();
				if (childrenCount > 0) {
					confirmDialog.setMessage("Delete '" + selectedResource.getName() + "' and all items underneath it?");
				}
		}
		int iResponse = confirmDialog.open();

		switch (iResponse) {
			case SWT.OK:
				Folder f = (Folder) selectedResource.getParent();
				f.removeItem(selectedResource);
				// TODO: Replace this with a message from the model
				//resourceViewer.refresh(f);
				touchLibrary();
				
			case SWT.CANCEL:
				break;
			
		}

	}

	// TODO: These 3 look pretty similar, no?
	private void newFolder() {
		InputDialog newFolderDialog = new InputDialog(this.getShell(), "New Folder" , "Enter Folder name", "Default Folder", resourceNameInputValidator);
		if (newFolderDialog.open() == InputDialog.OK) {
			Folder f = new Folder(newFolderDialog.getValue());
			if (selectedResource != null) {
				((Folder)selectedResource).addItem(f);
			} else {
				((Folder) library.getRoot()).addItem(f);
			}
			touchLibrary();
			// TODO: Replace this
			//resourceViewer.refresh(selectedResource);
			//resourceViewer.expandToLevel(selectedResource, 1);
			libraryViewer.refresh(selectedResource);
		}
	}

	private void newPalette() {
		InputDialog newPaletteDialog = new InputDialog(this.getShell(), "New Playlist" , "Enter Palette name", "Default Palette", resourceNameInputValidator);
		if (newPaletteDialog.open() == InputDialog.OK) {
			Palette p = new Palette(newPaletteDialog.getValue());
			if (selectedResource != null) {
				((Folder)selectedResource).addItem(p);
			} else {
				((Folder) library.getRoot()).addItem(p);				
			}
			touchLibrary();
			// TODO: Replace this
			//resourceViewer.refresh(selectedResource);
			//resourceViewer.expandToLevel(selectedResource, 1);
			libraryViewer.refresh(selectedResource);
		}	
	}
	
	private void newPlaylist() {
		InputDialog newPlaylistDialog = new InputDialog(this.getShell(), "New Playlist" , "Enter Playlist name", "Default Playlist", resourceNameInputValidator);
		if (newPlaylistDialog.open() == InputDialog.OK) {
			Playlist p = new Playlist(newPlaylistDialog.getValue());
			if (selectedResource != null) {
				((Folder)selectedResource).addItem(p);
			} else {
				((Folder) library.getRoot()).addItem(p);
			}
			touchLibrary();
			// TODO: Replace this
			//resourceViewer.refresh(selectedResource);
			//resourceViewer.expandToLevel(selectedResource, 1);
			libraryViewer.refresh(selectedResource);
		}
	}

	private void renameFolder() {
		renameResource("Folder", selectedResource);
	}

	private void renamePalette() {
		renameResource("Palette", selectedResource);
	}

	private void renamePlaylist() {
		renameResource("Playlist", selectedResource);
	}
	
	private void renameSelectedResource() {
		if (selectedResource instanceof Folder) {
			renameFolder();
		} else if (selectedResource instanceof Palette) {
			renamePalette();
			
		} else if (selectedResource instanceof Playlist) {
			renamePlaylist();
		}
	}

	private void renameResource(String typeName, IResource resource) {
		// Prompt for new name
		renameDialog = new InputDialog(this.getShell(), "Rename " + typeName + ": " + resource.getName() , "Enter new name", resource.getName(), resourceNameInputValidator);

		if (renameDialog.open() == InputDialog.OK) {
			resource.setName(renameDialog.getValue());
			touchLibrary();
			// TODO: Replace this
			//resourceViewer.refresh(resource.getParent());		
		}
	}

	// Playlist and Palette Actions

	private void activateEffect(Alias a) {
		playEffect(a);
	}

	private void activateSong(Alias a) {
		playSong(a);
	}

	private void deleteSelectedSongs() {
		// TODO: Change how this is implemented
		//deleteSelectedAliases(selectedPlaylist, songTableViewer);
	}

	private void deleteSelectedEffects() {
		deleteSelectedAliases(selectedPalette, effectTableViewer);		
	}

	private void deleteSelectedAliases(AliasCollector ac, Viewer v) {
		Alias a;
		IStructuredSelection selection = (IStructuredSelection) v.getSelection();
		Iterator i = selection.iterator();
		while (i.hasNext()) {
			a = (Alias) i.next();
			ac.remove(a);
		}
		v.refresh();

		touchLibrary();
	}

	private void moveSongUp() {
		// TODO: Change how this is implemented
		/*
		Alias a;
		IStructuredSelection selection = (IStructuredSelection) songTableViewer.getSelection();
		Iterator i = selection.iterator();
		while (i.hasNext()) {
			a = (Alias) i.next();
			selectedPlaylist.moveUp(a);
		}
		songTableViewer.refresh();
		touchLibrary();
		*/
	}
	
	private void moveSongDown() {
		// TODO: Change how this is implemented
		/*
		Alias a;
		IStructuredSelection selection = (IStructuredSelection) songTableViewer.getSelection();

		// TODO: Need to reverse my list otherwise moving down becomes pointless when starting with the first item
		// This can probably be done a much better way...
		Object[] foo = selection.toArray();
		
		for (int i = foo.length - 1; i >= 0; i--) {
			a = (Alias) foo[i];
			selectedPlaylist.moveDown(a);				
		}
		
		songTableViewer.refresh();
		touchLibrary();
		*/
	}

	private void newSong() {
		// TODO: Change how this is implemented
		//addChosenFiles(selectedPlaylist, songTableViewer);	
	}

	private void newEffect() {
		addChosenFiles(selectedPalette, effectTableViewer);		
	}

	private void addChosenFiles(AliasCollector ac, Viewer v) {
		boolean bDataChanged = false;
		// Prompt user for files
		songDialog.open();

		// Get files and path
		String[] paths = songDialog.getFileNames();
		String directory = songDialog.getFilterPath();

		// Loop through files and add to Playlist
		int iPathCount = paths.length;
		if (iPathCount > 0) {
			String sName;
			File f;
			for (int i = 0; i < iPathCount; i++) {
				f = new File(directory + "/" + paths[i]);
				if (f.exists()) {
					sName = f.getName();

					if (!sName.equals("")) {
						Alias a = new Alias(sName, f.getPath());
						ac.add(a);
						bDataChanged = true;
					}
				}
			}
			
			if (bDataChanged) {
				v.refresh();
				touchLibrary();
			}
		}
	}

	private void playSelectedSong() {
		// TODO: Change how this is implemented
		//IStructuredSelection selection = (IStructuredSelection) songTableViewer.getSelection();
		//Alias a = (Alias) selection.getFirstElement();
		//playSong(a);
	}
	
	private void playSelectedEffect() {
		IStructuredSelection selection = (IStructuredSelection) effectTableViewer.getSelection();
		Alias a = (Alias) selection.getFirstElement();
		playEffect(a);
		
	}
	
	private void playEffect(Alias a) {
		if (a != null) {
			File file = new File(a.getPath());
			
			if (file.exists()) {
				boolean success = audioEngine.playEffect(file);

				if (success) {
					// Update UI?
				}
			}
			
		} else {
			// TODO: Handle non-existant file

		}
		
	}

	private void playSong(Alias a) {
		if (a != null) {
			File file = new File(a.getPath());

			if (file.exists()) {
				activeSong = a;
				activePlaylist = selectedPlaylist;
				audioEngine.playSong(file);

			} else {
				// TODO: Handle non-existant file
			}
			
			// See http://dev.eclipse.org/viewcvs/index.cgi/platform-swt-home/faq.html?rev=1.56#uithread
			getShell().getDisplay().syncExec(
				new Runnable() {
		        	public void run() {
		        		//songTableViewer.refresh();
		        	}
		        }
		    );

		}

	}

	private void renameSelectedSongs() {
		// TODO: Change how this is implemented
		//renameSelectedAliases(songTableViewer, "Song");
	}

	private void renameSelectedEffects() {
		renameSelectedAliases(effectTableViewer, "Effect");
	}

	private void renameSelectedAliases(Viewer v, String type) {
		Alias a;
		IStructuredSelection selection = (IStructuredSelection) v.getSelection();
		Iterator i = selection.iterator();
		while (i.hasNext()) {
			a = (Alias) i.next();
			renameDialog = new InputDialog(this.getShell(), "Rename " + type + ": " + a.getName() , "Enter new name", a.getName(), resourceNameInputValidator);
			if (renameDialog.open() == InputDialog.OK) {
				a.setName(renameDialog.getValue());
				touchLibrary();
				v.refresh();
			}
		}
	}

	// Other Actions
	
	private void addFiles(String[] paths, Viewer v, AliasCollector ac) {
		int numPaths = paths.length;
		File f;
		for (int i = 0; i < numPaths; i++) {
			f = new File(paths[i]);
			Alias a = new Alias(f.getName(), f.getAbsolutePath());

			ac.add(a);
		}
		v.refresh();
		touchLibrary();
	}
	
	private void nextSong() {
		Alias a = activePlaylist.getNext(activeSong);
		playSong(a);
	}

	private void activatePlaylist() {
		activePlaylist = (Playlist) selectedResource;
		playSong(activePlaylist.getFirst());
	}
	
	private void previewSong(File f) {
		if (f.exists()) {
			audioEngine.previewSong(f);
		}
	}

	private void openPlaylist(Playlist p) {
		selectedPlaylist = p;
		playlistComposite.setText("Playlist: " + p.getName());
		playlistViewer.setPlaylist(p);
		fileAddPlaylistAction.setEnabled(true);

	}

	private void openPalette(Palette p) {
		selectedPalette = p;
		paletteComposite.setText("Palette: " + p.getName());	
		paletteViewer.setPalette(p);
		fileAddPaletteAction.setEnabled(true);

	}
	
	// File Methods

	private void newLibrary() {
		boolean bContinue = confirmSaveDialog();
		
		if (bContinue) {
			audioEngine.stop();
			library = new Library();
			libraryPath = null;
			untouchLibrary();
			
			// TODO: Update a bunch of UI here - probably shared by Open Library as well
			libraryViewer.setLibrary(library);
			
			playlistViewer.setPlaylist(null);
			paletteViewer.setPalette(null);
			
			saveLibraryAction.setEnabled(true);
			closeLibraryAction.setEnabled(true);
		}
	}
	
	private void openLibrary() {
		boolean bContinue = confirmSaveDialog();
		
		if (bContinue) {
			openFileDialog.open();
			String openFilename = openFileDialog.getFileName();
			String openDirectory = openFileDialog.getFilterPath();
			
			if (!openFilename.equals("")) {
				audioEngine.stop();
				libraryPath = openDirectory + File.separator + openFilename;
				//libraryFilename = openFilename;
				loadLibraryXML(libraryPath);
				untouchLibrary();

				libraryViewer.setLibrary(library);
				
				playlistViewer.setPlaylist(null);
				paletteViewer.setPalette(null);
				
				saveLibraryAction.setEnabled(false);
				saveLibraryAsAction.setEnabled(true);
				closeLibraryAction.setEnabled(true);
				
			}
		}
	}

	private boolean saveLibrary() {
		boolean bSuccess = false;
		
		if (libraryPath != null) {
			// Already have a path, just save the Library
			bSuccess = saveLibraryXML();

		} else {
			// Otherwise, prompt for a path and filename
			bSuccess = showSaveDialog();
		}
		
		if (bSuccess) {
			untouchLibrary();
		}
		
		return bSuccess;
	}

	private void saveLibraryAs() {
		// TODO: Implement saveLibraryAs
		// Show saveDialog asking for new Library filename
	}

	private void closeLibrary() {
		boolean bContinue = confirmSaveDialog();
		
		if (bContinue) {
			audioEngine.stop();

			library = null;
			libraryPath = null;
			untouchLibrary();

			//resourceViewer.setInput(null);
			//resourceViewer.getTree().setVisible(false);
			libraryViewer.setLibrary(null);

			playlistViewer.setPlaylist(null);
			paletteViewer.setPalette(null);
			
			saveLibraryAction.setEnabled(false);
			saveLibraryAsAction.setEnabled(false);
			closeLibraryAction.setEnabled(false);
		}

	}

	private boolean confirmSaveDialog() {
		if (library != null && isLibraryDirty()) {
			confirmDialog = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
			confirmDialog.setText("Save Current Library?");
			confirmDialog.setMessage("The currently opened Library is not saved.  Do you want to save it before continuing?");
			
			int iResponse = confirmDialog.open();

			switch (iResponse) {
				case SWT.YES:
					// If they cancel out of the save, cancel out of this as well
					return saveLibrary();
					
				case SWT.NO:
					return true;
					
				case SWT.CANCEL:
					return false;
			}
		}
		
		return true;
	}
	
	private void quit() {
		audioEngine.stop();
		close();
	}

	private void loadLibraryXML(String libraryFilePath) {
		File libraryFile = new File(libraryFilePath);

		if (libraryFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(libraryFile));
				StringBuffer xml = new StringBuffer();
				String str;
				while((str = in.readLine()) != null) {
					xml.append(str);
				}
				in.close();
								
				library = (Library) xstream.fromXML(xml.toString());
								
			} catch (Exception e) {
				System.out.println(e.toString());
				
			}			
		}
		
	}
	
	private boolean saveLibraryXML() {
		String xml = xstream.toXML(library);
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(libraryPath));
			out.write(xml);
			out.close();
			untouchLibrary();
			
			return true;
			
		} catch (Exception e) {
		
			return false;
		}
	}

    private boolean showSaveDialog() {
		saveLibraryDialog.open();
		String newFilename = saveLibraryDialog.getFileName();
		String newDirectory = saveLibraryDialog.getFilterPath();
		
		if (!newFilename.equals("")) {
			libraryPath = newDirectory + File.separator + newFilename;
			System.out.println(libraryPath);	

			//libraryFilename = newFilename;
			return saveLibraryXML();
			
		} else {
			return false;
			
		}
	}

    // Library Data 

	private boolean isLibraryDirty() {
		return bDirtyData;
	}
        
	private void touchLibrary() {
		bDirtyData = true;
		saveLibraryAction.setEnabled(true);
		updateWindowTitle();
	}

	private void untouchLibrary() {
		bDirtyData = false;
		saveLibraryAction.setEnabled(false);
		updateWindowTitle();
	}
	
	// Misc Support Methods
	public void updateWindowTitle() {
		StringBuffer title = new StringBuffer("RPG Audio Mixer");

		if (library != null) {
			if (libraryPath != null) {
				title.append(": " + libraryPath);
			} else {
				title.append(": Unsaved Library");
			}
			
			if (isLibraryDirty()) {
					title.append(" [unsaved]");
			}

		}
		
		getShell().setText(title.toString());
	}

	// AudioEngineListener Implementation
	public void songFinished() {
		nextSong();		
	}

	// LibraryExplorerListener Implemenation
	public void resourceSelected(IResource resource) {
		this.selectedResource = resource;
	}

	public void playlistOpened(IResource playlist) {
		openPlaylist((Playlist) playlist);
	}

	public void paletteOpened(IResource palette) {
		openPalette((Palette) palette);		
	}
	
	// AudioExplorerListener Implementation
	public void fileDoubleClicked(File f) {
		System.out.println("Previewing " + f);
		previewSong(f);
	}

	public void moveResource(IResource selectedResource,
			IResource targetResource) {

		Folder oldParent = (Folder) selectedResource.getParent();
		Folder newParent;
		if (targetResource != null) {
			newParent = (Folder) targetResource;
		} else {
			newParent = (Folder) library.getRoot();
		}

		oldParent.removeItem(selectedResource);
		newParent.addItem(selectedResource);
		
	}

	public void directorySelectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        if (selection.size() > 0) {   	
	        //Object selected_file = selection.getFirstElement();
	        //fileViewer.setInput(selected_file);
	        
	        directoryAddPlaylistAction.setEnabled(true);
	        directoryAddPaletteAction.setEnabled(true);
	        
        } else {
        	//fileViewer.setInput(null);

	        directoryAddPlaylistAction.setEnabled(false);
	        directoryAddPaletteAction.setEnabled(false);
        	
        }
		
	}

	public void fileSelectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (selection.size() > 0) {
			// Only allow previewing of single files
			if (selection.size() == 1) {
				filePreviewAction.setEnabled(true);
			} else {
				filePreviewAction.setEnabled(false);
			}

			// TODO: Also need to check if a Playlist/Palette is open
			if (selectedPlaylist != null) {
				fileAddPlaylistAction.setEnabled(true);
			}
			
			if (selectedPalette != null) {
				fileAddPaletteAction.setEnabled(true);
			}
			
		} else {
			filePreviewAction.setEnabled(false);
			fileAddPlaylistAction.setEnabled(false);
			fileAddPaletteAction.setEnabled(false);

		}
		
	}

}
