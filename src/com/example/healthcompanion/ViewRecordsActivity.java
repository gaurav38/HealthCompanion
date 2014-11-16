package com.example.healthcompanion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

public class ViewRecordsActivity extends Activity {
	
	Button submitButton;
	Firebase healthcompFB;
	DatePicker datePicker;
	String uid;
	String queryDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_records);
		
		//Settign up Firebase
        Firebase.setAndroidContext(this);
        
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	    final Editor editor = pref.edit();
		uid = pref.getString("uid", null);
		
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		submitButton = (Button) findViewById(R.id.button1);
	    
	    submitButton.setOnClickListener(new OnClickListener() {
	        
       	 
			@Override
			public void onClick(View arg0) {
				int day = datePicker.getDayOfMonth();
			    int month = datePicker.getMonth();
			    int year =  datePicker.getYear();

			    Calendar calendar = Calendar.getInstance();
			    calendar.set(year, month, day);
			    Date date = calendar.getTime();
			    
			    queryDate = new SimpleDateFormat("dd-MMM-yy").format(date);
			    Toast.makeText(getApplicationContext(), queryDate, Toast.LENGTH_LONG).show();
			    healthcompFB = new Firebase("https://healthcompanion.firebaseio.com/users/"+uid+"/"+queryDate);
			    
			    healthcompFB.addValueEventListener(new ValueEventListener() {
			        @Override
			        public void onDataChange(DataSnapshot snapshot) {
			            //System.out.println(snapshot.getValue());
			        	
			        	if(snapshot.getValue() == null) {
			        		Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
			        	}
			        	else {
			        		Intent recordPage = new Intent(getApplicationContext(), View_Single_Record.class);
			        		recordPage.putExtra("queryDate",queryDate);
			        		startActivity(recordPage);
			        		finish();
			        	}
			        }
			        @Override
			        public void onCancelled(FirebaseError firebaseError) {
			        	Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
			        }
			    });
			}
 
		});
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_records, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
