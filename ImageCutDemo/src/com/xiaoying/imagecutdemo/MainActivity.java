package com.xiaoying.imagecutdemo;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(getResources().openRawResource(R.raw.pic1), null, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = options.outWidth / mMetrics.widthPixels;
		Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.pic1), null, options);
		mImageCutView.setImageBitmap(bitmap);
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
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				try {
					String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image.jpg";
					mImageCutView.cutImageBitmap(path);
					Toast.makeText(this, "已保存至" + path, Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException ex){
					ex.printStackTrace();
					Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "请插入Sdcard", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
