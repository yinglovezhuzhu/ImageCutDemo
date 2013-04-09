package com.xiaoying.imagecutdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;

import com.xiaoying.imagecutdemo.widget.ImageCutView;

public class MainActivity extends Activity {

	private String tag = MainActivity.class.getSimpleName();

	private ImageCutView mImageCutView = null;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mImageCutView = (ImageCutView) findViewById(R.id.icv_imageCutView);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		 Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.pic3), null, options);
		 mImageCutView.setImageBitmap(bitmap);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
