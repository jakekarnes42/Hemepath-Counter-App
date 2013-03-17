package edu.sjsu.hemepathcounter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * This is the application's default landing page or main menu. The user will
 * begin here and can use the buttons to navigate to different activities. Other
 * activities will also route the user to this screen. From this Activity, the
 * user can view favorite button presets, launch a Counting Activity, Data
 * Activity, or Settings Activity.
 * 
 * @author Jake Karnes
 * 
 */
public class MainActivity extends Activity implements View.OnClickListener {

	private ListView favoritesListView;
	private ArrayAdapter<String> adapter;
	private Button counterButton, newButton, preferenceButton, dataButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		adapter.add("Favorite 1");
		adapter.add("Favorite 2");
		adapter.add("Favorite 3");
		adapter.add("Favorite 4");
		favoritesListView.setAdapter(adapter);
	}

	private void initialize() {
		favoritesListView = (ListView) findViewById(R.id.favorites_list);
		// initialize our button on the main activity
		counterButton = (Button) findViewById(R.id.counters_button);
		newButton = (Button) findViewById(R.id.new_button);
		preferenceButton = (Button) findViewById(R.id.preferences_button);
		dataButton = (Button) findViewById(R.id.data_button);
		// setting up click action listeners for each buttons
		counterButton.setOnClickListener(this);
		newButton.setOnClickListener(this);
		preferenceButton.setOnClickListener(this);
		dataButton.setOnClickListener(this);
		
		//Set default values if not set already.
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	}

	/**
	 * Create a menu to be displayed when user hits Menu key on device
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}

	/** Menu Item Click Listener */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_options_about:
			DialogPrompt.showAppAboutDialog(this);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onClick(View v) { // handler for click listener
		Intent intent;
		switch (v.getId()) {
		case R.id.counters_button:
			//Calling CountingActivity for testing - Minh
			intent = new Intent(MainActivity.this, CountingActivity.class);
			startActivity(intent);
			
			/*
			intent = new Intent(MainActivity.this, CountersActivity.class);
			startActivity(intent);
			*/
			break;
		case R.id.new_button:
			intent = new Intent(MainActivity.this, NewCounterActivity.class);
			startActivity(intent);
			break;
		case R.id.preferences_button:
			intent = new Intent(MainActivity.this, PreferencesActivity.class);
			startActivity(intent);
			break;
		case R.id.data_button:
			break;
		}
	}

}
