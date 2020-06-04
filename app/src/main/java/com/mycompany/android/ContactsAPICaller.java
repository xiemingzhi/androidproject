package com.mycompany.android;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mycompany.Contacts;
import com.mycompany.Friend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ContactsAPICaller extends AsyncTask<String, Void, Contacts> {
	
	private static final String HTTPS = "https";
    private static final String HTTPS_HOST = "localhost";
    private final RestTemplate restTemplate;
    
    private ContactsAPICallback mCallback;

    public ContactsAPICaller(ContactsAPICallback callback, Context context) {
        mCallback = callback;
    	restTemplate = new MyRestTemplate(context);

		// Add the Jackson message converter
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public Contacts getFriendsFromUrl(String url) {
    	Log.d("APICaller", "getfriendsfromurl url;"+url);

		// Make the HTTP GET request, marshaling the response from JSON to an array of Events
    	Contacts cList = null;
		try {
			cList = restTemplate.getForObject(url, Contacts.class);
		} catch (HttpClientErrorException hce) {
			Log.d("HTTP", "HttpClientErrorException occurred");
		}
		return cList;
    }
    
    @Override
    protected Contacts doInBackground(String... params) {
        String url = params[0];            
        return getFriendsFromUrl(url);
    }

    @Override
    protected void onPostExecute(Contacts result) {
    	for (Friend umi : result.getContactList()) {
			//Log.d("APICaller", "onpostexecute;" + umi.getFirstName());
		}
        mCallback.onRequestCompleted(result); 
    }
}