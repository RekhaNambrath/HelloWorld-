package com.example.helloworld;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import com.example.helloworld.R;


public class MainActivity extends ActionBarActivity {
	
	public Uri fileUri;
	ImageView showImg,camera,gallery;
	public static File fileName;
	private String selectedImagePath;
	public static File imageStorageDir;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		camera = (ImageView)findViewById(R.id.viewImage);
		gallery=(ImageView)findViewById(R.id.viewImage1);
	    camera.setOnClickListener(new OnClickListener() {
	    	@Override
	        public void onClick(View v) { open();}
	    });
	    
	    gallery.setOnClickListener(new View.OnClickListener() {
	    	@Override
	    	public void onClick(View v) { /*Open Gallery*/
	    	Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    	startActivityForResult(Intent.createChooser(intent,"Complete action using"), 2);
	    	}
	    });
	}
	public void open(){/*Open Camera*/
	      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	      fileUri = getOutputImageFileUri(); // create a file to save the image
		  intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
		  if (intent.resolveActivity(getPackageManager()) != null) {
		  	startActivityForResult(intent, 1);
		  }
	}
	private static Uri getOutputImageFileUri(){
		fileName=getOutputImageFile();
		return Uri.fromFile(fileName);
	}
	/** Create a File for saving the image */
	private static File getOutputImageFile(){
	    imageStorageDir = new File(Environment.getExternalStorageDirectory(),"Vaani");
	    if (!imageStorageDir.exists())
        {
            imageStorageDir.mkdirs();// Create the storage directory if it does not exist
        }
	    // Create a media file name
	    String imgName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File imageFile;
	    imageFile = new File(imageStorageDir.getPath() + File.separator + "IMG_"+ imgName +".png");
	    return imageFile;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
		 if (requestCode==1 && resultCode == RESULT_OK) { 
			 if(null == data) {
	               	Intent first = new Intent(this, SecondActivity.class);
	               	first.putExtra("name", fileName.toString() );
                    startActivity(first);
              }
		 }
		 else if(requestCode==2 && resultCode == RESULT_OK) {
			 if(null!=data){
				 Uri selectedImageUri = data.getData();
	             selectedImagePath = getPath(selectedImageUri);
	             Intent first = new Intent(this, SecondActivity.class);
				 first.putExtra("name", selectedImagePath);
				 startActivity(first);
			 }
		 }
	}
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        return cursor.getString(column_index);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
