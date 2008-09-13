/**
 * 
 */
package com.rpgaudiomixer.resources;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author delegreg
 * This class is designed to serve localized strings to the requesting App 
 */
public class LocalisedRessourcesManager {
	private static LocalisedRessourcesManager instance = new LocalisedRessourcesManager();
	//TODO : fnd out how to influence the ressourcebundles via locales
	private String currentLocale = Locale.getDefault().toString(); 

	//TODO : sort both files to separate strings from messages
	private static final String STRINGS_BUNDLE_NAME = "com.rpgaudiomixer.ressources.strings"; //$NON-NLS-1$

	private static final String MESSAGES_BUNDLE_NAME = "com.rpgaudiomixer.ressources.messages"; //$NON-NLS-1$

	private static final ResourceBundle STRINGS_RESOURCE_BUNDLE = ResourceBundle.getBundle(STRINGS_BUNDLE_NAME);

	private static final ResourceBundle MESSAGES_RESOURCE_BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME);
	
	public static enum RessourceKeys {
		AliasTypeEffect,		
		ApplicationTitle,
		AudioExplorerCaption,
		DefaultFolderName,
		DefaultPaletteName,
		DefaultPlaylistName,
		DialogTitle,
		LibraryExplorerCaption,
		MenuFile,
		MenuFileClose,
		MenuFileExit,
		MenuFileNew,
		MenuFileOpen,
		MenuFileSave,
		MenuFileSaveAs,
		MenuLibrary,
		MenuPalette,
		MenuPlaylist,
		MenuView,
		MenuViewExplorer,
		MenuViewLibrary,
		MenuViewPalette,
		MenuViewPlayer,
		MenuViewPlaylist,
		NewFolder,
		NewPalette,
		NewPlaylist,
		PaletteCaption,
		PlayerCaption,
		PlaylistCaption,
		PopupFileAddToPalette,
		PopupFileAddToPlaylist,
		PopupFilePreview,
		PopupFolderAddToPalette,
		PopupFolderAddToPlaylist,
		PopupLibraryFolder,
		PopupLibraryFolderDelete,
		PopupLibraryFolderRename,
		PopupLibraryNew,
		PopupLibraryNewFolder,
		PopupLibraryNewPalette,
		PopupLibraryNewPlaylist,
		PopupLibraryPalette,
		PopupLibraryPaletteDelete,
		PopupLibraryPaletteRename,
		PopupLibraryPlaylist,
		PopupLibraryPlaylistDelete,
		PopupLibraryPlaylistPlay,
		PopupLibraryPlaylistRename,
		PopupPalette,
		PopupPaletteDelete,
		PopupPaletteNew,
		PopupPalettePlay,
		PopupPaletteRename,
		PopupPlaylist,
		PopupPlaylistDelete,
		PopupPlaylistMoveDown,
		PopupPlaylistMoveUp,
		PopupPlaylistNew,
		PopupPlaylistPlay,
		PopupPlaylistRename,
		RessourceTypeFolder,
		RessourceTypePalette,
		RessourceTypePlaylist,
		UnsavedStatus,
		UnsavedTitle,
		WindowTitle
	}
	
	public static enum MessageKeys {	
		ConfirmDelete,
		ConfirmDeleteRecursive,
		ConfirmRenameAlias,
		ConfirmRenameRessource,
		EnterFolderName,
		EnterNewName,
		EnterPaletteName,
		EnterPlaylistName,
		ErrorMaxLength,
		ErrorNotBlank,
		Previewing,
		SaveCurrentLibrary,
		SaveCurrentLibraryFull,
		SelectedPalette,
		SelectedPlaylist
	}

	
	/**
	 * 
	 */
	private LocalisedRessourcesManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the currentLocale
	 */
	public String getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * @param currentLocale the currentLocale to set
	 */
	public void setCurrentLocale(String currentLocale) {
		this.currentLocale = currentLocale;
	}

	/**
	 * @return the instance
	 */
	public static LocalisedRessourcesManager getInstance() {
		return instance;
	}


	/**
	 * @return the localised ressource string associated with the key
	 */
	public static String getString(RessourceKeys key) {
		try {
			return STRINGS_RESOURCE_BUNDLE.getString(key.toString());
		} catch (MissingResourceException e) {
			return '!' + key.toString() + '!';
		}
	}

	/**
	 * @return the localised message string associated with the key without parameters
	 */
	public String getMessage(MessageKeys key) {
		try {

			return MESSAGES_RESOURCE_BUNDLE.getString(key.toString());
		} catch (MissingResourceException e) {
			return '!' + key.toString() + '!';
		}
	}

	/**
	 * @return the localised message string associated with the key without parameters
	 */
	public String getMessage(MessageKeys key,String parameter) {
		try {

			return MESSAGES_RESOURCE_BUNDLE.getString(key.toString()).replaceAll("%1", parameter);
		} catch (MissingResourceException e) {
			return '!' + key.toString() + '!';
		}
	}	
	
	/**
	 * @return the localised message string associated with the key without parameters
	 */
	public String getMessage(MessageKeys key,String firstParameter,String secondParameter) {
		try {

			StringBuilder message=new StringBuilder();
			message.append(MESSAGES_RESOURCE_BUNDLE.getString(key.toString()));
			int pos=message.indexOf("%1");
			message.replace(pos, pos+2, firstParameter);
			pos=message.indexOf("%2");
			message.replace(pos, pos+2, secondParameter);
			return message.toString();
		} catch (MissingResourceException e) {
			return '!' + key.toString() + '!';
		}
	}	
	
	/**
	 * @return the localised message string associated with the key, parameters included
	 */
	public String getMessage(MessageKeys key,String[] parameters) {
		try {
			StringBuilder message=new StringBuilder();
			message.append(MESSAGES_RESOURCE_BUNDLE.getString(key.toString()));
			for (int i = 1; i <= parameters.length; i++) {
				String paramName="%"+i;
				int pos=message.indexOf(paramName);
				message.replace(pos, pos+paramName.length(), parameters[i-1]);
			}
			return message.toString();
		} catch (MissingResourceException e) {
			return '!' + key.toString() + '!' + parameters.toString();
		}
	}
	
}
