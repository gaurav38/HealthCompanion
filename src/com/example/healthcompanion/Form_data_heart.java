package com.example.healthcompanion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.firebase.client.Firebase;

import android.app.ActionBar.LayoutParams;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class Form_data_heart extends Activity {
	
	String uid;
	Float bodyTemp, bpLow, bpHigh, heartRate, cholestrol, respRate;
	EditText bodyTempText, bpLowText, bpHighText, heartRateText, cholestrolText, respRateText;
	Button submitButton;
	Firebase healthcompFB;
	RelativeLayout rl;
	String currentDate;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_data_heart);
		
		Firebase.setAndroidContext(this);
		
		//Retrieving user's UID
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		final Editor editor = pref.edit();
		uid = pref.getString("uid", null);
		
	    //ScrollView sv = new ScrollView(this);
	   //sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	   //rl = (RelativeLayout) sv.findViewById(R.id.relativeLayout1);
	   
	   //Setting UI elements
	   bodyTempText = (EditText)findViewById(R.id.editText3);
	   bpHighText = (EditText)findViewById(R.id.editText1);
	   bpLowText = (EditText)findViewById(R.id.editText2);
	   heartRateText = (EditText)findViewById(R.id.editText4);
	  cholestrolText = (EditText)findViewById(R.id.editText5);
	  respRateText = (EditText)findViewById(R.id.editText6);
	  submitButton = (Button)findViewById(R.id.button1);
	  
	  // Initializing the firebase
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
			if(!bodyTempText.getText().toString().matches(""))
				map.put("temp", Float.parseFloat(bodyTempText.getText().toString()));
			if(!bpLowText.getText().toString().matches(""))
				map.put("bpLow", Float.parseFloat(bpLowText.getText().toString()));
			if(!bpHighText.getText().toString().matches(""))
				map.put("bpHigh", Float.parseFloat(bpHighText.getText().toString()));
			if(!heartRateText.getText().toString().matches(""))
				map.put("heartrate", Float.parseFloat(heartRateText.getText().toString()));
			if(!cholestrolText.getText().toString().matches(""))
				map.put("cholestrol", Float.parseFloat(cholestrolText.getText().toString()));
			if(!respRateText.getText().toString().matches(""))
				map.put("respirationrate", Float.parseFloat(respRateText.getText().toString()));
			healthcompFB.child("users").child(uid).child(currentDate).setValue(map);
			
			//Toast
			Toast.makeText(getApplicationContext(), "Observation Saved!", Toast.LENGTH_SHORT).show();
			bodyTempText.setText("");
			bpLowText.setText("");
			bpHighText.setText("");
			heartRateText.setText("");
			cholestrolText.setText("");
			respRateText.setText("");
			
			//Intent
			Intent goHome = new Intent(Form_data_heart.this, HomeActivity.class);
			startActivity(goHome);
			finish();
		}
	});
	   

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_data_heart, menu);
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
