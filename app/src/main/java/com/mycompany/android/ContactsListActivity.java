package com.mycompany.android;

import java.util.HashMap;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mycompany.ContactsGsonRequest;
import com.mycompany.Friend;
import com.mycompany.Contacts;
import com.mycompany.GsonRequest;
import com.mycompany.android.androidproject.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
public class ContactsListActivity extends ListActivity implements ContactsAPICallback {
//public class ContactsListActivity extends Activity implements ContactsAPICallback {
	RequestQueue queue;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		/*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
				*/
		setContentView(R.layout.contacts);
		//ActionBar actionBar = this.getActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);

		//String url = "https://jbossas-fuhu.rhcloud.com/contact/jsonlist.json";
		String url = "https://jsonplaceholder.typicode.com/users";
//		ContactsAPICaller cac = new ContactsAPICaller(this, this);
//		cac.execute(url);
		queue = Volley.newRequestQueue(this);

		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
		ContactsGsonRequest<Contacts> myReq = new ContactsGsonRequest<Contacts>(
				"https://jsonplaceholder.typicode.com/users",
				Contacts.class,
				headerMap,
				createMyReqSuccessListener(),
				createMyReqErrorListener());

		queue.add(myReq);

	}

	private Response.Listener<Contacts> createMyReqSuccessListener() {
		return new Response.Listener<Contacts>() {
			@Override
			public void onResponse(Contacts response) {
				// Do whatever you want to do with response;
				// Like response.tags.getListing_count(); etc. etc.
				//cList.getContactList().add(response);
				Log.d("CONTACTSLIST", String.valueOf(response.getContactList().size()));
//				List<Friend> cList = response.getContactList();
//				ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(this,
//						android.R.layout.simple_list_item_1, cList);
//				setListAdapter(adapter);
				onRequestCompleted(response);
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

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onRequestCompleted(Contacts profile) {
		List<Friend> cList = profile.getContactList();
		ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(this,
				android.R.layout.simple_list_item_1, cList);
		setListAdapter(adapter);
		
	}
}
