package com.example.healthcompanion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.Firebase;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class Form_data_general extends Activity {
	
	EditText tempText, weightText,bpLowText,bpHighText, heartRateText, respRateText;
	Float temp, weight, bpLow, bpHigh, heartRate, respRate;
	Button submitButton;
	Firebase healthcompFB;
	String uid,currentDate;
	ScrollView sv;
	RelativeLayout rl;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_data_general);
		Firebase.setAndroidContext(this);
		
		//Retrieving user's UID
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		final Editor editor = pref.edit();
		uid = pref.getString("uid", null);
				
		//sv = (ScrollView) findViewById(R.id.scrollView1);
		//rl = (RelativeLayout) sv.findViewById(R.id.RelativeLayout1);
		
		tempText = (EditText) findViewById(R.id.editText2);
		weightText = (EditText) findViewById(R.id.editText1);
		bpLowText = (EditText) findViewById(R.id.editText4);
		bpHighText = (EditText) findViewById(R.id.editText3);
		heartRateText = (EditText) findViewById(R.id.editText5);
		respRateText = (EditText) findViewById(R.id.editText6);
		submitButton = (Button) findViewById(R.id.button1);
		
		healthcompFB = new Firebase("https://healthcompanion.firebaseio.com");
		
		// Set current date
	  	  Calendar currDate = Calendar.getInstance();
	  	  currDate.set(Calendar.HOUR_OF_DAY, 0);
	  	  currDate.set(Calendar.MINUTE, 0);
	  	  currDate.set(Calendar.SECOND, 0);
	  	  currDate.set(Calendar.MILLISECOND, 0);
	  	  Date date = currDate.getTime();
	  	  currentDate = new SimpleDateFormat("dd-MMM-yy").format(date);
	  	  
	  	 submitButton.setOnClickListener(new OnClickListener() {
	 		public void onClick(View arg0) {
	 			Map<String, Float> map = new HashMap<String, Float>();
	 			if(!tempText.getText().toString().matches(""))
	 				map.put("temp", Float.parseFloat(tempText.getText().toString()));
	 			if(!bpLowText.getText().toString().matches(""))
	 				map.put("bpLow", Float.parseFloat(bpLowText.getText().toString()));
	 			if(!bpHighText.getText().toString().matches(""))
	 				map.put("bpHigh", Float.parseFloat(bpHighText.getText().toString()));
	 			if(!heartRateText.getText().toString().matches(""))
	 				map.put("heartrate", Float.parseFloat(heartRateText.getText().toString()));
	 			if(!weightText.getText().toString().matches(""))
	 				map.put("weight", Float.parseFloat(weightText.getText().toString()));
	 			if(!respRateText.getText().toString().matches(""))
	 				map.put("respirationrate", Float.parseFloat(respRateText.getText().toString()));
	 			healthcompFB.child("users").child(uid).child(currentDate).setValue(map);
	 			
	 			//Toast
	 			Toast.makeText(getApplicationContext(), "Observation Saved!", Toast.LENGTH_SHORT).show();
	 			tempText.setText("");
	 			bpLowText.setText("");
	 			bpHighText.setText("");
	 			heartRateText.setText("");
	 			weightText.setText("");
	 			respRateText.setText("");
	 			
	 			//Intent
	 			Intent goHome = new Intent(Form_data_general.this, HomeActivity.class);
	 			startActivity(goHome);
	 			finish();
	 		}
	 	});
	 	   
		
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_data_general, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		final Editor editor = pref.edit();
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent goToProfile = new Intent(getApplicationContext(),UserProfileActivity.class);
        	startActivity(goToProfile);
        	finish();
        }
        if (id == R.id.Home) {
        	Intent goHome = new Intent(getApplicationContext(),HomeActivity.class);
        	startActivity(goHome);
        	finish();
        }
        if(id == R.id.Logout){
        	Intent logout = new Intent(getApplicationContext(),LoginActivity.class);
        	
        	editor.clear();
        	editor.commit();
        	startActivity(logout);
        	finish();
        }
		return super.onOptionsItemSelected(item);
	}
}
