package com.rpgaudiomixer.ui.transfers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.rpgaudiomixer.model.Palette;

public class PaletteTransfer extends ByteArrayTransfer {
	private static final String PALETTE_TRANSFER_NAME = "PALETTE";
	private static final int PALETTE_TRANSFER_ID = registerType(PALETTE_TRANSFER_NAME);
	private static final PaletteTransfer instance = new PaletteTransfer();
	
	public static PaletteTransfer getInstance() {
		return instance;
	}
	
	@Override
	protected int[] getTypeIds() {
		return new int[] { PALETTE_TRANSFER_ID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { PALETTE_TRANSFER_NAME };
	}

	protected void javaToNative(Object object, TransferData transferData) {
		if (object == null || !(object instanceof Palette)) {
			return;
		}
		
		Palette palette = (Palette) object;
		if (isSupportedType(transferData)) {
			try {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream);
				out.writeUTF(palette.getName());
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
			
			Palette palette = new Palette();
			
			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(raw);
				DataInputStream in = new DataInputStream(stream);
				palette.setName(in.readUTF());
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return palette;
		} else {
			return null;
		}
	}

}
