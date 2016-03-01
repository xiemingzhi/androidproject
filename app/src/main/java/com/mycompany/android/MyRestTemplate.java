package com.mycompany.android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;

public class MyRestTemplate extends RestTemplate {
    public MyRestTemplate(Context context) {
        if (getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            Log.d("HTTP", "HttpUrlConnection is used");
            setRequestFactory(new SecureSimpleClientHttpRequestFactory());
            ((SimpleClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(10 * 1000);
            ((SimpleClientHttpRequestFactory) getRequestFactory()).setReadTimeout(10 * 1000);
            ((SimpleClientHttpRequestFactory) getRequestFactory()).setReadTimeout(10 * 1000);
        } else if (getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            Log.d("HTTP", "HttpClient is used");
            ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setReadTimeout(10 * 1000);
            ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(10 * 1000);
            ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(10 * 1000);
        }
    }
    
    private static void readCertificateSetFactory(Context context) {
    	InputStream caInput = null;
		InputStream in = null;
		
		URL urlURL = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			InputStream fIn = context.getResources().getAssets().open("load-der.crt", context.MODE_WORLD_READABLE);
			caInput = new BufferedInputStream(fIn);
			Certificate ca;

			ca = cf.generateCertificate(caInput);
			Log.d("SecureAPICaller", "ca=" + ((X509Certificate) ca).getSubjectDN());

			// Create a KeyStore containing our trusted CAs
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			Log.d("SecureAPICaller", "keystore create success");
			
			// Create a TrustManager that trusts the CAs in our KeyStore
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);
			Log.d("SecureAPICaller", "trustmanager create success");
			
			// Create an SSLContext that uses our TrustManager
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, tmf.getTrustManagers(), null);

			// Tell the URLConnection to use a SocketFactory from our SSLContext
			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

		} catch (CertificateException ce) {
			ce.printStackTrace();
			Log.e("SecureAPICaller", "certificate exception;"+ce.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "fnfe exception;"+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "io exception;"+e.getMessage());
		} catch (KeyStoreException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "keystore exception;"+e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "no such algorithm exception;"+e.getMessage());
		} catch (KeyManagementException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "keymanagement exception;"+e.getMessage());
		} finally {
		    try {
				caInput.close();
				in.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
