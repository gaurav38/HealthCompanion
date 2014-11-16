package com.example.healthcompanion;

//import com.firebase.client.utilities.Base64.InputStream;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Upload_images extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_images);
		
		//Button btn_select = (Button) findViewById(R.id.select_btn);
		Button btn_upload = (Button) findViewById(R.id.upld_btn);
		
		Button btn_choose_photo = (Button) findViewById(R.id.select_btn); // Replace with id of your button.
		btn_choose_photo.setOnClickListener(choosePic);
		
		
		
		
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload_images, menu);
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    super.onActivityResult(requestCode, resultCode, data); 

	    switch(requestCode) { 
	    case 1234:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();


	            Bitmap bm = BitmapFactory.decodeFile(filePath);
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
	            byte[] b = baos.toByteArray();
	            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
	            System.out.println(encodedImage);
	        }
	     
	    	
	    	
	    }

	}

	public OnClickListener choosePic = new OnClickListener(){
		public void onClick(View v) {
	       // Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
	    	//String nakul = null;
	    	//Log.i(nakul, "working before intent");
	        //System.out.println("HOLA");
	    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
	                  "content://media/internal/images/media"));
	    	final int ACTIVITY_SELECT_IMAGE = 1234;
	    	
			//Log.i(nakul, "working on click");
	        startActivityForResult(intent, ACTIVITY_SELECT_IMAGE); 
	    }
	};


}
