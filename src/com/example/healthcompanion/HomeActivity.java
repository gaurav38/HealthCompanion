package com.example.healthcompanion;

import com.firebase.client.Firebase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;



public class HomeActivity extends Activity {

	Button logoutButton;
	ImageButton recordObs, viewRecords;
	String userCondition;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        // Check if user is logged in
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final Editor editor = pref.edit();
        String loggedinEmail = pref.getString("username", null);
        if(loggedinEmail == null){
        	Intent goToLogin = new Intent(HomeActivity.this, LoginActivity.class);
        	startActivity(goToLogin);
        	finish();
        }
        
        userCondition = pref.getString("userCondition", "Others");
        
        //Setting up the environment

        
        viewRecords = (ImageButton) findViewById(R.id.imageButton2);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        recordObs = (ImageButton) findViewById(R.id.imageButton1);
      
        
        //Settign up Firebase
        Firebase.setAndroidContext(this);

        
        // Button click send data to firebase
        final Firebase healthcompFB = new Firebase("https://healthcompanion.firebaseio.com/");
        
        
        
        
        // Button click listener
        logoutButton.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View view) {
				editor.clear();
				editor.commit();
				Intent goToLogin = new Intent(HomeActivity.this, LoginActivity.class);
				startActivity(goToLogin);

			}
 
		});
        
     // Button click listener
        viewRecords.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View view) {
				Intent goToViewRecords = new Intent(HomeActivity.this, ViewRecordsActivity.class);
				startActivity(goToViewRecords);
				finish();
				
			}
 
		});
        
     // Button click listener
        recordObs.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View view) {
				Log.d("UserCondition",userCondition); 
				if(userCondition.equals("Heart Problem")){
				Intent recordDataHeart = new Intent(HomeActivity.this, Form_data_heart.class);
				startActivity(recordDataHeart);
				}
				else if(userCondition.equals("Diabetes")){
					Intent recordDataDiabetes = new Intent(HomeActivity.this, Form_data_diabetes.class);
					startActivity(recordDataDiabetes);
				}
				else if(userCondition.equals("Obesity")){
					Intent recordDataObesity = new Intent(HomeActivity.this, Form_data_obesity.class);
					startActivity(recordDataObesity);
				}
				else
				{
					Intent recordDataOthers = new Intent(HomeActivity.this, Form_data_general.class);
					startActivity(recordDataOthers);
				}
				
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
