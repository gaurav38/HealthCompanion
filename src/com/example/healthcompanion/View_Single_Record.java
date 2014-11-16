package com.example.healthcompanion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class View_Single_Record extends Activity {
	
	String uid;
	Firebase healthcompFB;
	TextView textview;
	Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_single_record);
		
		//Settign up Firebase
        Firebase.setAndroidContext(this);
        
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	    final Editor editor = pref.edit();
		uid = pref.getString("uid", null);
		textview = (TextView) findViewById(R.id.textView1);
		backButton = (Button) findViewById(R.id.button1);
		
	    Intent intent = new Intent();
	    String queryDate = intent.getStringExtra("queryDate");
	    Toast.makeText(getApplicationContext(), queryDate, Toast.LENGTH_LONG).show();
	    healthcompFB = new Firebase("https://healthcompanion.firebaseio.com/users/"+uid+"/"+queryDate);
	    
	    healthcompFB.addValueEventListener(new ValueEventListener() {
	        @Override
	        public void onDataChange(DataSnapshot snapshot) {
	            //System.out.println(snapshot.getValue());
	        	
	        	Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
	        	
	        	StringBuilder outString = new StringBuilder();
	        	Set<String> keyList = new HashSet<String>();
	        	keyList = map.keySet();
	        	for(String key : keyList) {
	        		outString.append(key + ": " + map.get(key) + "\n");
	        	}
	        	textview.setText(outString);
	        	//textview.setText(snapshot.getValue().toString());
	        }
	        @Override
	        public void onCancelled(FirebaseError firebaseError) {
	        	Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
	        }
	    });
	    
	    backButton.setOnClickListener(new OnClickListener() {
	        
	       	 
			@Override
			public void onClick(View arg0) {
				Intent backPage = new Intent(getApplicationContext(), ViewRecordsActivity.class);
				startActivity(backPage);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view__single__record, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
