/**
 * 
 */
package com.rpgaudiomixer.ui;

import java.util.EventListener;

import com.rpgaudiomixer.model.IResource;

/**
 * @author delegreg
 *
 */
public interface PlaylistViewerListener extends EventListener {
	public void dropFiles(String[] paths,
			IResource targetResource);

}
