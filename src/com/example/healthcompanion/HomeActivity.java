package com.example.healthcompanion;

import com.firebase.client.Firebase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;



public class HomeActivity extends Activity {

	Button logoutButton;
	ImageButton recordObs;
	
	
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
        
        //Setting up the environment
        
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
				finish();
				
			}
 
		});
        
     // Button click listener
        recordObs.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View view) {
				Intent recordDataDiabetes = new Intent(HomeActivity.this, Form_data_diabetes.class);
				startActivity(recordDataDiabetes);
				
				
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
