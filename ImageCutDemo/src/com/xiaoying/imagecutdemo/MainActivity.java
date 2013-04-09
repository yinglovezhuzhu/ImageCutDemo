package com.xiaoying.imagecutdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaoying.imagecutdemo.util.LogUtil;
import com.xiaoying.imagecutdemo.widget.ImageCutView;

public class MainActivity extends Activity {

	private String tag = MainActivity.class.getSimpleName();

	private ImageCutView mImageCutView = null;
	
	private DisplayMetrics mMetrics = new DisplayMetrics();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
		
		mImageCutView = (ImageCutView) findViewById(R.id.icv_imageCutView);
		
//		findViewById(R.id.btn_button).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
				// TODO Auto-generated method stub
				BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeStream(getResources().openRawResource(R.raw.pic3), null, options);
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.pic2), null, options);
				mImageCutView.setImageBitmap(bitmap);
//			}
//		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_cut:
			LogUtil.e(tag, "ImageCut");
			mImageCutView.getCutImageBitmap();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
