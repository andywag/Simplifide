/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.license;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;
import de.schlichtherle.util.ObfuscatedString;

public class ClientLicenseParam implements LicenseParam{

	private static long[] PR2 =  new long[] {0xB5841242A26289D5L, 0xFB51FE94B05433A3L, 0x6DDBA7BC07826902L};
	private static long[] PU  =  new long[] {0x12C94CFB890DCA14L, 0x97EF698AEF75E3L, 0x7E3DD97BDC58D872L};
	private static long[] AL = new long[] {0xAB2E4AC289AA5590L, 0x387E02725076FF78L};
	

	private static ClientLicenseParam instance;
	
	public ClientLicenseParam() {}
	
	
	public static ClientLicenseParam getInstance() {
		if (instance == null) {
			instance = new ClientLicenseParam();
		}
		return instance;
	}
	
	public CipherParam getCipherParam() {
		return new HardwareCipherParam();
	}

	public KeyStoreParam getKeyStoreParam() {
		return new HardwareKeyStoreParam();
	}

	public Preferences getPreferences() {
		return Preferences.userNodeForPackage(ClientLicenseParam.class);
	}

	public String getSubject() {
		return "";
	}
	
	public static class HardwareCipherParam implements CipherParam{

		public HardwareCipherParam() {}
		public String getKeyPwd() {
			return new ObfuscatedString(PR2).toString();
			
		}

	}
	
	public static class HardwareKeyStoreParam implements KeyStoreParam{

		public HardwareKeyStoreParam() {
			
		}
		
		public String getAlias() {
			return new ObfuscatedString(AL).toString();
		}

		public String getKeyPwd() {
			
			return null;
		}

		public String getStorePwd() {
			return new ObfuscatedString(PU).toString();
		}

		public InputStream getStream() throws IOException {
			final String resourceName = "public.store";
			final InputStream in = getClass().getResourceAsStream(resourceName);
			if (in == null)
				throw new FileNotFoundException(resourceName);
			return in;
		}

	}


}
