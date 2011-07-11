/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.license;

import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;

public class ClientKeyStoreParam implements KeyStoreParam{

	public String getAlias() {
		return "publiccert";
	}

	public String getKeyPwd() {
		return null;
	}

	public String getStorePwd() {
		return "pSR521qzX";
	}

	public InputStream getStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public static class ClientCipherParam implements CipherParam {

		public String getKeyPwd() {
			return "pSR521qzX";
		}
	}
	
	public static class ClientLicenseParam implements LicenseParam {

		public CipherParam getCipherParam() {
			return new ClientCipherParam();
		}

		public KeyStoreParam getKeyStoreParam() {
			return new ClientKeyStoreParam();
		}

		public Preferences getPreferences() {
			return Preferences.userNodeForPackage(LicenseMain.class);
		}

		public String getSubject() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
