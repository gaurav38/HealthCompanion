package com.example.healthcompanion;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.firebase.client.Firebase;

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
import android.widget.RadioButton;
import android.widget.Toast;

public class Form_data_diabetes extends Activity {

	String uid;
	Button submitButton;
	float weight, bpLow, bpHigh, sugar;
	EditText weightText, bpLowText, bpHighText, sugarText;
	Firebase healthcompFB;
	String currentDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_data_diabetes);
		
		Firebase.setAndroidContext(this);
		healthcompFB = new Firebase("https://healthcompanion.firebaseio.com");
		
		//Retrieving user's UID
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
    	final Editor editor = pref.edit();
    	uid = pref.getString("uid", null);
    	weightText = (EditText) findViewById(R.id.editText2);
    	bpLowText = (EditText) findViewById(R.id.editText4);
    	bpHighText = (EditText) findViewById(R.id.editText3);
    	sugarText = (EditText) findViewById(R.id.editText5);
    	submitButton = (Button) findViewById(R.id.button1);
    	
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
    			if(weightText.getText().toString() != null)
					map.put("weight", Float.parseFloat(weightText.getText().toString()));
    			if(bpLowText.getText().toString() != null)
    				map.put("bpLow", Float.parseFloat(bpLowText.getText().toString()));
    			if(bpHighText.getText().toString() != null)
    				map.put("bpHigh", Float.parseFloat(bpHighText.getText().toString()));
    			if(sugarText.getText().toString() != null)
    				map.put("sugar", Float.parseFloat(sugarText.getText().toString()));
    			healthcompFB.child("users").child(uid).child(currentDate).setValue(map);
    			
    			//Toast
    			Toast.makeText(getApplicationContext(), "Observation Saved!", Toast.LENGTH_SHORT).show();
    			weightText.setText("");
    			bpLowText.setText("");
    			bpHighText.setText("");
    			sugarText.setText("");
    			
    			//Intent
    			Intent goHome = new Intent(Form_data_diabetes.this, HomeActivity.class);
    			startActivity(goHome);
    			finish();
			}
    	});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form_data, menu);
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
