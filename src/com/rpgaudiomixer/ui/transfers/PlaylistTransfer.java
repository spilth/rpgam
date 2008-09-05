package com.rpgaudiomixer.ui.transfers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.rpgaudiomixer.model.Playlist;

public class PlaylistTransfer extends ByteArrayTransfer {
	private static final String PLAYLIST_TRANSFER_NAME = "PLAYLIST";
	private static final int PLAYLIST_TRANSFER_ID = registerType(PLAYLIST_TRANSFER_NAME);
	private static final PlaylistTransfer instance = new PlaylistTransfer();
	
	public static PlaylistTransfer getInstance() {
		return instance;
	}
	
	@Override
	protected int[] getTypeIds() {
		return new int[] { PLAYLIST_TRANSFER_ID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { PLAYLIST_TRANSFER_NAME };
	}

	protected void javaToNative(Object object, TransferData transferData) {
		if (object == null || !(object instanceof Playlist)) {
			return;
		}
		
		Playlist playlist = (Playlist) object;
		if (isSupportedType(transferData)) {
			try {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream);
				out.writeUTF(playlist.getName());
				out.close();
				
				super.javaToNative(stream.toByteArray(), transferData);

			} catch (IOException e) {
				e.printStackTrace();
	
			}
			
		}
		
	}

	protected Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] raw = (byte[]) super.nativeToJava(transferData);
			if (raw == null) {
				return null;
			}
			
			Playlist playlist = new Playlist();
			
			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(raw);
				DataInputStream in = new DataInputStream(stream);
				playlist.setName(in.readUTF());
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return playlist;
		} else {
			return null;
		}
	}

}
