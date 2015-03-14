package com.example.helloworld;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.util.*;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import java.io.*;
//import android.media.*;

public class SecondActivity extends ActionBarActivity {
	ImageView showImg;
	final int PIC_CROP=2;
	Button button;
	public Uri picUri;
	public static File imgFile;
	
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		String myRef = this.getIntent().getStringExtra("name");
	    imgFile = new  File(myRef);
		
		/*Bitmap bitmap  = getIntent().getExtras().getParcelable("name");
        ImageView showImg = (ImageView) findViewById(R.id.view_photo);
        showImg.setImageBitmap(bitmap);*/
		
        /*Display Image*/
        if(imgFile.exists()){
        	Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
       		ImageView showImg = (ImageView) findViewById(R.id.view_photo);
        	showImg.setImageBitmap(myBitmap);
        	}
        button = (Button)findViewById(R.id.button3);
		button.setOnClickListener(new OnClickListener() {
		    	@Override
		        public void onClick(View v) { 
		    		picUri=Uri.fromFile(imgFile);
		        	//call the standard crop action intent (the user device may not support it)
		        	Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
		        	    //indicate image type and Uri
		        	cropIntent.setDataAndType(picUri, "image/*");
		        	    //set crop properties
		        	cropIntent.putExtra("crop", "true");
		        	    //indicate aspect of desired crop
		        	cropIntent.putExtra("aspectX", 1);
		        	cropIntent.putExtra("aspectY", 1);
		        	    //indicate output X and Y
		        	cropIntent.putExtra("outputX", 256);
		        	cropIntent.putExtra("outputY", 256);
		        	    //retrieve data on return
		        	cropIntent.putExtra("return-data", true);
		        	    //start the activity - we handle returning in onActivityResult
		        	startActivityForResult(cropIntent, PIC_CROP);
		        	 }
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // TODO Auto-generated method stub
     super.onActivityResult(requestCode, resultCode, data);
	 if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
		if(data!=null){
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
       		ImageView showImg = (ImageView) findViewById(R.id.view_photo);
       		showImg.setImageBitmap(myBitmap);
       		}
	 }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}