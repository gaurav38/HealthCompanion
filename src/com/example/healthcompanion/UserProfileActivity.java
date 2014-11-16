package com.example.healthcompanion;


import com.firebase.client.Firebase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserProfileActivity extends Activity {
	
	RadioGroup medicalConditionGroup;
	RadioButton medicalCondition;
	Button doneButton;
	String uid,userCondition;
	Firebase healthcompFB;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		Firebase.setAndroidContext(this);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	    final Editor editor = pref.edit();
		
		//Log.d("UID",uid);
		
		userCondition = pref.getString("userCondition", null);
		
		if(userCondition != null){
			if(userCondition.equals("Heart Problem")){
				medicalCondition = (RadioButton) findViewById(R.id.radio0);
				medicalCondition.setChecked(true);
			}
			if(userCondition.equals("Diabetes")){
				medicalCondition = (RadioButton) findViewById(R.id.radio1);
				medicalCondition.setChecked(true);
			}
			if(userCondition.equals("Obesity")){
				medicalCondition = (RadioButton) findViewById(R.id.radio2);
				medicalCondition.setChecked(true);
			}
			if(userCondition.equals("Others")){
				medicalCondition = (RadioButton) findViewById(R.id.RadioButton01);
				medicalCondition.setChecked(true);
			}
		}
		//Setting up UI elements
		medicalConditionGroup = (RadioGroup) findViewById(R.id.radioGroup2);
		doneButton = (Button) findViewById(R.id.button1);
		
		healthcompFB = new Firebase("https://healthcompanion.firebaseio.com");
		
		doneButton.setOnClickListener(new OnClickListener() {
	        
       	 
			@Override
			public void onClick(View arg0) {
				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			    final Editor editor = pref.edit();
				
				uid = pref.getString("uid", null);
				int selectedRadio = medicalConditionGroup.getCheckedRadioButtonId();
				medicalCondition = (RadioButton) findViewById(selectedRadio);
				userCondition = medicalCondition.getText().toString();
				editor.putString("userCondition", userCondition);
				editor.commit();
				Log.d("UID",uid + userCondition + "  "+healthcompFB.toString());
				healthcompFB.child("users").child(uid).child("medicalcondition").setValue(userCondition);
				
				Toast.makeText(getApplicationContext(), "Your profile has been updated!", Toast.LENGTH_LONG).show();
				
				Intent goHome = new Intent(UserProfileActivity.this, HomeActivity.class);
				startActivity(goHome);
				finish();
				
			}
 
		});
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
