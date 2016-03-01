package com.mycompany.android.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mycompany.android.ContactsListActivity;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity {
    private static SSLContext sslcontext;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        setSSLContext();
    }

    public void addListenerOnButton() {
        final Context context = this;
        button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ContactsListActivity.class);
                startActivity(intent);
            }
        });
    }

    public static SSLContext getSSLContext() {
        return MainActivity.sslcontext;
    }

    private void setSSLContext() {
        InputStream caInput = null;
        InputStream in = null;

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream fIn = this.getApplication().getResources().getAssets()
                    .open("load-der.crt", this.getApplication().MODE_WORLD_READABLE);
            caInput = new BufferedInputStream(fIn);
            Certificate ca;

            ca = cf.generateCertificate(caInput);
            Log.d("SecureAPICaller",
                    "ca=" + ((X509Certificate) ca).getSubjectDN());

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            Log.d("SecureAPICaller", "keystore create success");

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            Log.d("SecureAPICaller", "trustmanager create success");

            // Create an SSLContext that uses our TrustManager
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, tmf.getTrustManagers(), null);



        } catch (CertificateException ce) {
            ce.printStackTrace();
            Log.e("SecureAPICaller",
                    "certificate exception;" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("SecureAPICaller", "fnfe exception;" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SecureAPICaller", "io exception;" + e.getMessage());
        } catch (KeyStoreException e) {
            e.printStackTrace();
            Log.e("SecureAPICaller", "keystore exception;" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("SecureAPICaller",
                    "no such algorithm exception;" + e.getMessage());
        } catch (KeyManagementException e) {
            e.printStackTrace();
            Log.e("SecureAPICaller",
                    "keymanagement exception;" + e.getMessage());
        } finally {
            try {
                caInput.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
