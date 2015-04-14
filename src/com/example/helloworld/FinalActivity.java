package com.example.helloworld;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView; 
import com.googlecode.tesseract.android.TessBaseAPI;

public class FinalActivity extends ActionBarActivity {
	TextView recognised,translated;
	public static File imgFile;
	
	 //public static String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "Vaani123";
	 public static final String lang="eng";
	 private static final String TAG = "FinalActivity.java";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);
		String myRef = this.getIntent().getStringExtra("name");
	    imgFile = new  File(myRef);
	    //String choice = this.getIntent().getStringExtra("choice");
	    
	    
	    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	    recognised=(TextView)findViewById(R.id.textView1);
	    translated=(TextView)findViewById(R.id.textView2);
	    translated.setText("HEre you will get text");
	    //ContextWrapper appContext = null; 
		
	    String tessdata=Environment.getExternalStorageDirectory().toString()+"/Vanidata";
	    
	    String[] paths = new String[] { tessdata, tessdata + "/tessdata/" };
	    for (String path : paths) {
	    	File dir = new File(path);
	    	if (!dir.exists()) {
               dir.mkdirs();// Create the storage directory if it does not exist
	    	}
	    }
	    if (!(new File(tessdata + "/tessdata/" + lang + ".traineddata")).exists()) {
	    	try {
		    	  AssetManager assetManager = getAssets();
		          InputStream in = assetManager.open("/tessdata/eng.traineddata");
		          OutputStream out = new FileOutputStream(tessdata +"/tessdata/eng.traineddata");

		          // Transfer bytes from in to out
		          byte[] buf = new byte[1024];
		          int len;
		          while ((len = in.read(buf)) > 0) {
		        	  out.write(buf, 0, len);
		          }
		          in.close();
		          out.close();
		          //boolean installSuccess=false;
		    	 
		    		Log.v(TAG, "Copied " + lang + " traineddata");
		    	} 
		    	catch (IOException e) {
		    		Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
		    	}
	    }
	    
		    TessBaseAPI baseApi = new TessBaseAPI();
		    
		    String  DATA_PATH=tessdata + "/tessdata/";
		    baseApi.init(DATA_PATH,lang);
		    baseApi.setImage(bitmap);
		    String recognizedText = baseApi.getUTF8Text();
		    recognised.setText(recognizedText);
		    baseApi.end();
		    Log.v(TAG, "OCR Result: " + recognizedText);
		    
		    
	        // clean up and show
	        if (lang.equalsIgnoreCase("eng")) {
	            recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
	        }
	        if (recognizedText.length() != 0) {
	        	recognised.setText(recognizedText.trim());
	        }
	        
	 
	}
}
