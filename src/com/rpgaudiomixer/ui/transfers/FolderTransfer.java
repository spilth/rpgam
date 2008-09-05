package com.rpgaudiomixer.ui.transfers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.rpgaudiomixer.model.Folder;

public class FolderTransfer extends ByteArrayTransfer {
	private static final String FOLDER_TRANSFER_NAME = "FOLDER";
	private static final int FOLDER_TRANSFER_ID = registerType(FOLDER_TRANSFER_NAME);
	private static final FolderTransfer instance = new FolderTransfer();
	
	public static FolderTransfer getInstance() {
		return instance;
	}
	
	@Override
	protected int[] getTypeIds() {
		return new int[] { FOLDER_TRANSFER_ID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { FOLDER_TRANSFER_NAME };
	}

	protected void javaToNative(Object object, TransferData transferData) {
		if (object == null || !(object instanceof Folder)) {
			return;
		}
		
		Folder folder = (Folder) object;
		if (isSupportedType(transferData)) {
			try {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream);
				out.writeUTF(folder.getName());
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
			
			Folder folder = new Folder();
			
			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(raw);
				DataInputStream in = new DataInputStream(stream);
				folder.setName(in.readUTF());
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return folder;
		} else {
			return null;
		}
	}

}
