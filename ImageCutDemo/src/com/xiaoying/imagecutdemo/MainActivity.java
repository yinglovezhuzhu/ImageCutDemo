package com.xiaoying.imagecutdemo;

import java.io.InputStream;

import com.example.imagecutdemo.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String tag = MainActivity.class.getSimpleName();

	private int mScreenWidth = 400;
	
	private int mScreenHeight = 800;
	
	ImageView mImageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		mScreenWidth = metrics.widthPixels;
//		mScreenHeight = metrics.heightPixels;
//		
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic3);
//		mImageView = (ImageView) findViewById(R.id.iv_image);
//		mImageView.setClickable(true);
//		mImageView.setScaleType(ScaleType.CENTER);
////		imageView.setScaleX(3);
////		imageView.setScaleY(3);
////		mImageView.layout(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.pic3));
//		mImageView.setOnTouchListener(new View.OnTouchListener() {
//			int lastX0 = 0;
//			int lastY0 = 0;
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					lastX0 = (int) event.getRawX();
//					lastY0 = (int) event.getRawY();
//					
////					lastX0 = (int) event.getX(0) - v.getLeft();
////					lastY0 = (int) event.getY(0) - v.getTop();
////					lastX0 = lastX0 > 0 ? lastX0 : 0;
////					lastY0 = lastY0 > 0 ? lastY0 : 0;
//					
//					Log.i(tag, "Point down++++++++++++++++++++");
//					break;
//				case MotionEvent.ACTION_MOVE:
//					if(event.getPointerCount() > 1) {
////						Log.i(tag, "Point move ++++++++>>>>>>Point0(" + event.getX(0) + ", " + event.getY(0) 
////								+ ")+++Point1(" + event.getX(1) + ", " + event.getY(1) + ")");
////						float nw = Math.abs(event.getX(1) - event.getX(0));
////						float nh =  Math.abs(event.getY(1) - event.getY(0));
////						int lw = Math.abs(lastX1 - lastX0);
////						int lh = Math.abs(lastY1 - lastY0);
////						float sx = (nw - lw) / mScreenWidth;
////						float sy = (nh - lh) / mScreenHeight;
////						float scale = Math.max((nw - lw) / mScreenWidth, (nh - lh) / mScreenHeight);
//////						v.setScaleX(scale);
//////						v.setScaleY(scale);
////						
////						lastX1 = (int) event.getX();
////						lastY1 = (int) event.getY();
//					} else {
//						Log.i(tag, "Point===============(" + event.getX() + ", " + event.getY() + ")");
//						Log.i(tag, "Raw point++++++++++++++++(" + event.getRawX() + ", " + event.getRawY() + ")");
//						Log.i(tag, "New Point>>>>>>>>>>>>>>>>(" + (event.getX() - v.getLeft()) + ", " 
//						+ (event.getY() - v.getTop()) + ")");
//						int dx = (int) event.getRawX() - lastX0;
//						int dy = (int) event.getRawY() - lastY0;
//						
////						int dx = (int) event.getX(0) - v.getLeft() - lastX0;
////						int dy = (int) event.getY(0) - v.getTop() - lastY0;
////						dx = dx > 0 ? dx : 0;
////						dy = dy > 0 ? dy : 0;
//						
//						int l = v.getLeft() + dx;
//						int t = v.getTop() + dy;
//						int r = v.getRight() + dx;
//						int b = v.getBottom() + dy;
//						// 下面判断移动是否超出屏幕
//						if (l < 0) {
//							l = 0;
//							r = l + v.getWidth();
//						}
//						
//						if (t < 0) {
//							t = 0;
//							b = t + v.getHeight();
//						}
//						
//						if (r > mScreenWidth) {
//							r = mScreenWidth;
//							l = r - v.getWidth();
//						}
//						
//						if (b > mScreenHeight) {
//							b = mScreenHeight;
//							t = b - v.getHeight();
//						}
//						v.layout(l, t, r, b);
//						
//					}
//					lastX0 = (int) event.getRawX();
//					lastY0 = (int) event.getRawY();
//					
////					lastX0 = (int) event.getX(0) - v.getLeft();
////					lastY0 = (int) event.getY(0) - v.getTop();
////					lastX0 = lastX0 > 0 ? lastX0 : 0;
////					lastY0 = lastY0 > 0 ? lastY0 : 0;
//					v.postInvalidate();
//					break;
//				case MotionEvent.ACTION_UP:
//
//					break;
//				default:
//					break;
//				}
//				return false;
//			}
//		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
