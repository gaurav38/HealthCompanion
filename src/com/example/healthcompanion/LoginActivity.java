package com.example.healthcompanion;


import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.ResultHandler;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText emailText, passwordText;
	Button submit, forgotPassword, signUp;
	Firebase healthcompFB;
	String email,password, uid, medicalCondition;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		Editor editor = pref.edit();
		
		//Setting up UI elements
		emailText = (EditText) findViewById(R.id.emailText);
		passwordText = (EditText) findViewById(R.id.passwordText);
		submit = (Button) findViewById(R.id.submitButton);
		forgotPassword = (Button) findViewById(R.id.forgotButton);
		signUp = (Button) findViewById(R.id.signUpButton);
		// Setting up FireBase
		Firebase.setAndroidContext(this);
		
		// Initializing FireBase
        healthcompFB = new Firebase("https://healthcompanion.firebaseio.com");
		
        // Sign Up Listener
        signUp.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {
        		Intent registration = new Intent(LoginActivity.this, RegistrationActivity.class);
        		startActivity(registration);
        		finish();
        	}
        });
		// Button click listener
        submit.setOnClickListener(new OnClickListener() {
        
        	 
			@Override
			public void onClick(View arg0) {
				email = emailText.getText().toString();
				password = passwordText.getText().toString();
				//Toast.makeText(getApplicationContext(), email+" "+password, Toast.LENGTH_LONG);
				new LoginUser().execute();
			}
 
		});
        
        forgotPassword.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				email = emailText.getText().toString();
				new ForgotPassword().execute();
				
			}
        	
        });
	}
	
	
	private class LoginUser extends AsyncTask<Void,Void,String>
	{

		@Override
		protected String doInBackground(Void... params) {
			
			healthcompFB.authWithPassword(email, password,
			    new Firebase.AuthResultHandler() {

			    @Override
			    public void onAuthenticated(AuthData authData) {
			    	Log.d("UserAutoLogin","User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
			    	
			    	// Setting user preferences to logged in
			    	// Preference manager set user log in
			    	SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
			    	final Editor editor = pref.edit();
			    	editor.putString("username", email);
			    	editor.commit();
			    	
			    	 //Setting up uid in shared preferences
	    	        editor.putString("uid", authData.getUid());
	    	        editor.commit();
	    	        uid = authData.getUid();
	    	        
	    	        //Retrieve user profile
	    	        Firebase userProfileRef = new Firebase("https://healthcompanion.firebaseio.com/users/"+uid+"/medicalcondition");
	    	        userProfileRef.addValueEventListener(new ValueEventListener() {
	    	            @Override
	    	            public void onDataChange(DataSnapshot snapshot) {
	    	                System.out.println(snapshot.getValue());
	    	                medicalCondition = snapshot.getValue().toString();
	    	                editor.putString("medicalcondition", medicalCondition);
	    	                editor.commit();
	    	                
	    	            }
	    	            @Override
	    	            public void onCancelled(FirebaseError firebaseError) {
	    	                System.out.println("The read failed: " + firebaseError.getMessage());
	    	            }
	    	        });
			    	//Navigating to home activity
			    	Intent goHome = new Intent(LoginActivity.this, HomeActivity.class);
			    	startActivity(goHome);
			    	finish();
			    }

			    @Override
			    public void onAuthenticationError(FirebaseError error) {
			    	Log.d("AutoLoginError",error.getDetails());
			    	Toast.makeText(getApplicationContext(), "Incorrect email/password. Try Again!", Toast.LENGTH_LONG).show();
			    }
			});
			return null;
		}
		
	}
	
	private class ForgotPassword extends AsyncTask<Void,Void,String>
	{

		@Override
		protected String doInBackground(Void... params) {
			healthcompFB.resetPassword(email, new ResultHandler(){

				@Override
				public void onError(FirebaseError arg0) {
					
					
				}

				@Override
				public void onSuccess() {
					Toast.makeText(getApplicationContext(), "An email has been sent to your account!", Toast.LENGTH_LONG).show();
					
				}});
			return null;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
