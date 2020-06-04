package com.mycompany.android.androidproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mycompany.Contacts;
import com.mycompany.Friend;
import com.mycompany.FriendList;
import com.mycompany.GsonRequest;
import com.mycompany.android.ContactsListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static SSLContext sslcontext;
    Button button;
    Button buttonRequest;
    TextView textView;
    RequestQueue queue;
    Contacts cList = new Contacts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        //setSSLContext();
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        buttonRequest = (Button) findViewById(R.id.buttonRequest);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void makeRequest(View view) {
        Log.d(TAG, "inside makerequest");
        //JsonArrayRequest request = new JsonArrayRequest("http://192.168.111.1:8080/userman/services/api/users?_type=json", new Response.Listener<JSONArray>() {
        /*JsonArrayRequest request = new JsonArrayRequest("https://jsonplaceholder.typicode.com/users", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                StringBuilder sb = new StringBuilder();
                try {
                    //read your json here
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            sb.append(jsonObject.getString("username")+";");
                        }
                    }
                } catch (JSONException jsone) {
                    jsone.printStackTrace();
                } catch (Exception e) {
                    // log and show error message with error code;
                    e.printStackTrace();
                }
                textView.setText(sb.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // log and show error message with error code;
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String encodedCredentials = Base64.encodeToString("admin:password".getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + encodedCredentials);
                return headers;
            }
        };
        queue.add(request);*/
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        GsonRequest<FriendList> myReq = new GsonRequest<FriendList>(
                "https://jsonplaceholder.typicode.com/users",
                FriendList.class,
                headerMap,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        queue.add(myReq);
    }

    private Response.Listener<FriendList> createMyReqSuccessListener() {
        return new Response.Listener<FriendList>() {
            @Override
            public void onResponse(FriendList response) {
                // Do whatever you want to do with response;
                // Like response.tags.getListing_count(); etc. etc.
                Log.d(TAG, String.valueOf(response.size()));
                textView.setText(String.valueOf(response.size()));
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                Log.e("ERRORLISTENER", error.getMessage());
            }
        };
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
