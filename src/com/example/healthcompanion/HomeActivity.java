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



public class HomeActivity extends Activity {

	Button myButton, logoutButton;
	EditText myEditText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_upload_images);
       // Intent i = new Intent (HomeActivity.this, Upload_images.class);
        //startActivity(i);
        //finish();
       
       // Intent i = new Intent (HomeActivity.this, BrowsePictureActivity.class);
       // startActivity(i);
        //finish();
       
        //setContentView(R.layout.activity_home);
        
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
        myButton = (Button) findViewById(R.id.upld_btn);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        myEditText = (EditText) findViewById(R.id.editText1);
        
        //Settign up Firebase
        Firebase.setAndroidContext(this);

        
        // Button click send data to firebase
        final Firebase healthcompFB = new Firebase("https://healthcompanion.firebaseio.com/");
        
        // Button click listener
        myButton.setOnClickListener(new OnClickListener() {
        	 
			@Override
			public void onClick(View arg0) {
				healthcompFB.child("data").push().setValue(myEditText.getText().toString());
				myEditText.setText("");
			}
 
		});
        
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
