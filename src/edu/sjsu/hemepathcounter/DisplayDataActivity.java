package edu.sjsu.hemepathcounter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import edu.sjsu.hemepathcounter.model.*;

public class DisplayDataActivity extends Activity {

	private Counter mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_data);
		
		// Loading data from CountingActivity or DataActivity
		mData = getIntent().getParcelableExtra("counter");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_data, menu);
		return true;
	}

}