package com.example.healthcompanion;


import com.firebase.client.Firebase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
		uid = pref.getString("uid", null);
		
		//Setting up UI elements
		medicalConditionGroup = (RadioGroup) findViewById(R.id.radioGroup2);
		doneButton = (Button) findViewById(R.id.button1);
		
		healthcompFB = new Firebase("https://healthcompanion.firebaseio.com");
		
		doneButton.setOnClickListener(new OnClickListener() {
	        
       	 
			@Override
			public void onClick(View arg0) {
				
				int selectedRadio = medicalConditionGroup.getCheckedRadioButtonId();
				medicalCondition = (RadioButton) findViewById(selectedRadio);
				userCondition = medicalCondition.getText().toString();
				editor.putString("userCondition", userCondition);
				editor.commit();
				
				healthcompFB.child("users").child(uid).child("medical condition").setValue(userCondition);
				
				Toast.makeText(getApplicationContext(), "Your profile has been updated!", Toast.LENGTH_LONG).show();
				
				
				
			}
 
		});
		
	}

}
