package com.example.healthcompanion;

import com.firebase.client.Firebase;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        //Settign up Firebase
        Firebase.setAndroidContext(this);
        Button btn_general= (Button)findViewById(R.id.button1);
        Button btn_heart= (Button)findViewById(R.id.button2);
        Button btn_diabetes= (Button)findViewById(R.id.button3);
        
        
        btn_general.setOnClickListener(new OnClickListener(){
        	public void onClick (View v){
        	setContentView(R.layout.activity_form_data_general);	
        }});
        btn_heart.setOnClickListener(new OnClickListener(){
        	public void onClick (View v){
        	setContentView(R.layout.activity_form_data_heart);	
        }});
        btn_diabetes.setOnClickListener(new OnClickListener(){
        	public void onClick (View v){
        	setContentView(R.layout.activity_form_data);	
        }});
        
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
