package com.xiaoying.imagecutdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.xiaoying.imagecutdemo.R;

public class ImageCutView extends FrameLayout {

	/** 拖动模式 */
	private static final int MODE_DRAG = 1;	
	/** 缩放模式 */
	private static final int MODE_ZOOM = 2;
	/** 没有模式 */
	private static final int MODE_NONE = 3;
	
	private int mMode = MODE_NONE;

	int lastX0 = 0;
	int lastY0 = 0;
	
	int lastX1 = 0;
	int lastY1 = 0;
	
	private String tag = ImageCutView.class.getSimpleName();

	private ImageView mImageView = null;
	
	private Matrix mImageMatrix = null;
	
	private float mScale = 1.0f;

	public ImageCutView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public ImageCutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ImageCutView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context cotnext) {

		mImageView = new ImageView(getContext());
		mImageView.setScaleType(ScaleType.CENTER);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.pic2), null, options);
//		mImageView.setBackgroundResource(R.drawable.pic3);
		mImageView.setImageBitmap(bitmap);
		mImageMatrix = new Matrix();
		mImageView.setImageMatrix(mImageMatrix);
		addView(mImageView, new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT));
		ImageView iv = new ImageView(cotnext);
		iv.setBackgroundColor(Color.argb(150, 00, 00, 00));
		addView(iv, new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		
	}
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			lastX0 = (int) event.getX();
			lastY0 = (int) event.getY();
			mMode = MODE_DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			lastX0 = (int) event.getX(0);
			lastY0 = (int) event.getY(0);
			lastX1 = (int) event.getX(1);
			lastY1 = (int) event.getY(1);
			mMode = MODE_ZOOM;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mMode == MODE_DRAG) {
				int dx = (int) event.getX() - lastX0;
				int dy = (int) event.getY() - lastY0;
				int l = mImageView.getLeft() + dx;
				int t = mImageView.getTop() + dy;
				int r = mImageView.getRight() + dx;
				int b = mImageView.getBottom() + dy;
//				// 下面判断移动是否超出容器
//				if (l < 0) {
//					l = 0;
//					r = (int) (l + mImageView.getWidth() * mImageView.getScaleX());
//				}
//
//				if (t < 0) {
//					t = 0;
//					b = (int) (t + mImageView.getHeight() * mImageView.getScaleY());
//				}
//
//				if (r > getWidth()) {
//					r = getWidth();
//					l = (int) (r - mImageView.getWidth() * mImageView.getScaleX());
//				}
//
//				if (b > getHeight()) {
//					b = getHeight();
//					t = (int) (b - mImageView.getHeight() * mImageView.getScaleY());
//				}
				mImageView.layout(l, t, r, b);
				lastX0 = (int) event.getX();
				lastY0 = (int) event.getY();
			} else if(mMode == MODE_ZOOM) {
				int nowX0 = (int) event.getX(0);
				int nowY0 = (int) event.getY(0);
				int nowX1 = (int) event.getX(1);
				int nowY1 = (int) event.getY(1);
				float scale = mImageView.getScaleX();
				if(Math.abs(Math.abs(nowX1 - nowX0) - Math.abs(lastX1 - lastX0))
						> Math.abs(Math.abs(nowY1 - nowY0) - Math.abs(lastY1 - lastY0))) {
//					scale += (float) (Math.abs(nowX1 - nowX0) - Math.abs(lastX1 - lastX0)) / mImageView.getWidth() * 2;
					mScale += (float) (Math.abs(nowX1 - nowX0) - Math.abs(lastX1 - lastX0)) / mImageView.getWidth() * 2;
				} else {
//					scale += (float) (Math.abs(nowY1 - nowY0) - Math.abs(lastY1 - lastY0)) / mImageView.getHeight() * 2;
					mScale += (float) (Math.abs(nowY1 - nowY0) - Math.abs(lastY1 - lastY0)) / mImageView.getHeight() * 2;
				}
				/** ImageView的setScaleX和setScaleY只适合API11或以上版本 */
//				mImageView.setScaleX(scale);
//				mImageView.setScaleY(scale);
				mImageMatrix.postScale(mScale, mScale);
				mImageView.setImageMatrix(mImageMatrix);
				Log.e(tag, "Scale========" + mScale);
				lastX0 = (int) event.getX(0);
				lastY0 = (int) event.getY(0);
				lastX1 = (int) event.getX(1);
				lastY1 = (int) event.getY(1);
			}
			mImageView.postInvalidate();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mMode = MODE_NONE;
			/** ImageView的setScaleX和setScaleY只适合API11或以上版本 */
			Log.e(tag, "Scale=========================>>>>>>>>>>" + mImageView.getScaleX());
			Log.e(tag, "ImageView size++++++++++++++>>>>>>>（" + mImageView.getWidth() * mImageView.getScaleX() 
					+ ", " + mImageView.getHeight() * mImageView.getScaleY() + ")");
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}
}