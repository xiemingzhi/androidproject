package com.mycompany.android;

import java.util.List;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mycompany.Friend;
import com.mycompany.Contacts;
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

		String url = "https://jbossas-fuhu.rhcloud.com/contact/jsonlist.json";
		ContactsAPICaller cac = new ContactsAPICaller(this, this);
		cac.execute(url);
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
