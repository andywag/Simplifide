/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.license;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;
import de.schlichtherle.util.ObfuscatedString;

public class ClientLicenseParam implements LicenseParam{

        public static long[] SUBJECT = new long[] {0x968F8F25293F39CBL, 0x14D64663D9E725FL, 0xA7C7678299514C3BL, 0x234C1D3EFB994CDBL};
        public static long[] PASS = new long[] {0x7DF9673E4A1C1737L, 0x82A5F56ED9D8D5EL, 0x2ED9FAF4DFAB1295L};
        public static long[] PASS2 = new long[] {0x3AFC57C8E0118E62L, 0xD96E5C07FFA4B2BEL, 0x3A104BA3EEB8792L};
        public static long[] ALIAS = new long[] {0x3AA871A0E8D7A18CL, 0xC512D85401653EDDL, 0xC5F6FB09D6758B7FL};
        
	public static ClientLicenseParam instance;
	private ClientLicenseParam() {}
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
		//return null;
	}

	public Preferences getPreferences() {
		return Preferences.userNodeForPackage(LicenseMain.class);
	}

	public String getSubject() {
               // System.out.println("Subject---" + new ObfuscatedString(SUBJECT).toString());
		return new ObfuscatedString(SUBJECT).toString();
	}
	
	public static class HardwareCipherParam implements CipherParam{

		public HardwareCipherParam() {}
		public String getKeyPwd() {
                     return new ObfuscatedString(PASS).toString();
		}

	}
	
	public class HardwareKeyStoreParam implements KeyStoreParam{

		public HardwareKeyStoreParam() {}
		
		public String getAlias() {
                //    System.out.println("Alias---" + new ObfuscatedString(ALIAS).toString());
			return new ObfuscatedString(ALIAS).toString();
		}

		public String getKeyPwd() {
			return null;
		}

		public String getStorePwd() {
                    return new ObfuscatedString(PASS2).toString();
		}

		public InputStream getStream() throws IOException {
			final String resourceName = "bundle.store";
			final InputStream in = getClass().getResourceAsStream(resourceName);
			if (in == null)
				throw new FileNotFoundException(resourceName);
			return in;
		}

	}


}
