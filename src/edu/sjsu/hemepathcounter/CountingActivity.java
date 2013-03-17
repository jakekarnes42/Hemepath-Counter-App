package edu.sjsu.hemepathcounter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import edu.sjsu.hemepathcounter.adapter.CountingAdapter;
import edu.sjsu.hemepathcounter.model.CountingData;

public class CountingActivity extends Activity {

	private CountingData mData;
	private CountingAdapter mAdapter;
	private ArrayList<Integer> mSequence;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counting);
		
		//Loading data from database depends New Counting or Favorites
		mData = new CountingData();
		mAdapter = new CountingAdapter(this, mData);
		mSequence = new ArrayList<Integer>();
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(mAdapter);
	    
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            mData.cells.get(position).number++;
	            mData.total++;
	            mSequence.add(position);
	            mAdapter.notifyDataSetChanged();     
	            //play sound ....
	            
	        }
	    });	    
	        
	    //Undo button
	    Button btnUndo = (Button) findViewById(R.id.counting_activity_undo);
	    btnUndo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (mSequence.size() > 0) {
					int position = mSequence.get(mSequence.size() - 1);
					mSequence.remove(mSequence.size() - 1);
					mData.cells.get(position).number--;
					mData.total--;
		            mAdapter.notifyDataSetChanged();
				}
			}
		});
	    
	    //Save button
	    Button btnSave = (Button) findViewById(R.id.counting_activity_save);
	    btnSave.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {			
				//Save mData to file. 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counting, menu);
		return true;
	}

}
