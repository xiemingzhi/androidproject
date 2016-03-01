package com.mycompany.android;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
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

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import android.content.Context;
import android.util.Log;

import com.mycompany.android.androidproject.MainActivity;

public class SecureSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

	@Override
	public HttpURLConnection openConnection(URL url, Proxy proxy) {
		HttpsURLConnection urlConnection = null;
		URL urlURL = null;
		try {
			SSLContext sslcontext = MainActivity.getSSLContext();
			// Tell the URLConnection to use a SocketFactory from our SSLContext
			urlURL = url;
			urlConnection = (HttpsURLConnection) urlURL.openConnection();
			urlConnection.setSSLSocketFactory(sslcontext.getSocketFactory());
			MyHostnameVerifier v = new MyHostnameVerifier(); 
			urlConnection.setHostnameVerifier(v);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "fnfe exception;" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("SecureAPICaller", "io exception;" + e.getMessage());
		} finally {
		}
		return urlConnection;
	}
}
