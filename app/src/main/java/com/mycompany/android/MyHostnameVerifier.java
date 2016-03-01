package com.mycompany.android;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import android.util.Log;

class MyHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		
		Log.d("SecureAPICaller", "verifying hostname arg0;"+arg0);
		Log.d("SecureAPICaller", "verifying hostname arg1;"+arg1.getPeerHost());
		if (arg0.equals("connectme.no-ip.org")) return true;
		if (arg0.equals("wojia.dyndns.org")) return true;
		if (arg0.equals("jbossas-fuhu.rhcloud.com")) return true;
		return false;
	}
	
}