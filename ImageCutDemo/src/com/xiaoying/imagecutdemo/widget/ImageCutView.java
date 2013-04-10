package com.xiaoying.imagecutdemo.widget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.xiaoying.imagecutdemo.util.LogUtil;

public class ImageCutView extends FrameLayout {

	/** 拖动模式 */
	private static final int MODE_DRAG = 1;
	/** 缩放模式 */
	private static final int MODE_ZOOM = 2;
	/** 没有模式 */
	private static final int MODE_NONE = 3;

	private int mMode = MODE_NONE;

	private ImageView mImageView = null;
	
	private ImageFocusView mImageFocusView = null;

	private Bitmap mBitmap = null;

	private Matrix mMatrix = new Matrix();
	private Matrix mSavedMatrix = new Matrix();

	private PointF mStartPoint = new PointF();
	private PointF mZoomPoint = new PointF();
	private float mOldDist = 1f;
	
	private float [] mMatrixValues = new float[9];
	
	private float mMiniScale = 1f;
	
	private String tag = ImageCutView.class.getSimpleName();
	
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

	public void setImageBitmap(Bitmap bitmap) {
		this.mBitmap = bitmap;
		mImageView.setScaleType(ScaleType.CENTER);
		mImageView.setImageBitmap(bitmap);
		setFitFocus(bitmap);
	}

	private void initView(Context context) {
		
		mImageView = new ImageView(context);
		addView(mImageView, new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		mImageFocusView = new ImageFocusView(context);
		LogUtil.w(tag, "Fuces width = " + mImageFocusView.getFocusWidth());
		addView(mImageFocusView, new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		mImageView.setOnTouchListener(mOntouchListener);
	}
	
	View.OnTouchListener mOntouchListener = new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				actionDown(event);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				actionPointerDown(event);
				break;
			case MotionEvent.ACTION_MOVE:
				actionMove(event);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mMode = MODE_NONE;
				
				mMatrix.getValues(mMatrixValues);
				Log.i(tag, "MSCALE_X = " + mMatrixValues[0] + "; MSKEW_X = " + mMatrixValues[1] + "; MTRANS_X = " + mMatrixValues[2]
						+ "; \nMSCALE_Y = " + mMatrixValues[4] + "; MSKEW_Y = " + mMatrixValues[3] + "; MTRANS_Y = " + mMatrixValues[5]
								 + "; \nMPERSP_0 = " + mMatrixValues[6] + "; MPERSP_1 = " + mMatrixValues[7] + "; MPERSP_2 = " + mMatrixValues[8]);
				break;
			}
			mImageView.setImageMatrix(mMatrix);
			return true;
		}
	};
	
	public void getCutImageBitmap() {
		int left = (int)((mImageFocusView.getFocusLeft() - mMatrixValues[2]) / mMatrixValues[0]);
		int top = (int)((mImageFocusView.getFocusTop() - mMatrixValues[5]) / mMatrixValues[4]);
		int width = (int)(mImageFocusView.getFocusWidth() / mMatrixValues[0]);
		int height = (int)(mImageFocusView.getFocusWidth() / mMatrixValues[4]);
		Bitmap bmp = Bitmap.createBitmap(mBitmap, 
				left,
				top, 
				width, 
				height);
		try {
			bmp.compress(CompressFormat.JPEG, 100, new FileOutputStream(new File("/sdcard/cutimage.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void actionDown( MotionEvent event) {
		mImageView.setScaleType(ScaleType.MATRIX);
		mMatrix.set(mImageView.getImageMatrix());
		mSavedMatrix.set(mMatrix);
		mStartPoint.set(event.getX(), event.getY());
		mMode = MODE_DRAG;
	}
	
	private void actionPointerDown(MotionEvent event) {
		mOldDist = spacing(event);
		if (mOldDist > 10f) {
			mSavedMatrix.set(mMatrix);
			midPoint(mZoomPoint, event);
//			mZoomPoint.set(mImageFocusView.getFocusMidPoint());
			mMiniScale = (float) mImageFocusView.getFocusWidth() / Math.min(mBitmap.getWidth(), mBitmap.getHeight());
			mMode = MODE_ZOOM;
		}
	}
	
	private void actionMove(MotionEvent event) {
		if (mMode == MODE_DRAG) {
			mMatrix.set(mSavedMatrix);
			float transX = event.getX() - mStartPoint.x;
			float transY = event.getY() - mStartPoint.y;
			mMatrix.getValues(mMatrixValues);
			float leftLimit = mImageFocusView.getFocusLeft() - mMatrixValues[2];
			float topLimit = mImageFocusView.getFocusTop() - mMatrixValues[5];
			float rightLimit = mImageFocusView.getFocusRight() - (mBitmap.getWidth() * mMatrixValues[0] + mMatrixValues[2]);
			float bottomLimit = mImageFocusView.getFocusBottom() - (mBitmap.getHeight() * mMatrixValues[0] + mMatrixValues[5]);
			if(transX > 0 && transX > leftLimit) {
				transX = leftLimit;
			}
			if(transY > 0 && transY > topLimit) {
				transY = topLimit;
			}
			if(transX < 0 && transX < rightLimit) {
				transX = rightLimit;
			}
			if(transY < 0 && transY < bottomLimit) {
				transY = bottomLimit;
			}
			mMatrix.postTranslate(transX, transY);
		} else if (mMode == MODE_ZOOM) {
			float newDist = spacing(event);
			if (newDist > 10f) {
				mMatrix.set(mSavedMatrix);
				mMatrix.getValues(mMatrixValues);
				float scale = newDist / mOldDist;
				if(mMatrixValues[0] * scale < mMiniScale) {
					scale = mMiniScale / mMatrixValues[0];
				}
				mMatrix.postScale(scale, scale, mZoomPoint.x, mZoomPoint.y);
			}
		}
	}
	
	private void setFitFocus(Bitmap bitmap) {
		mImageView.setScaleType(ScaleType.MATRIX);
		mMatrix.set(mImageView.getImageMatrix());
		float scale = (float) mImageFocusView.getFocusWidth() / Math.min(bitmap.getWidth(), bitmap.getHeight());
		if(scale > 1) {
			mMatrix.postScale(scale, scale);
		}
		LogUtil.e(tag, "==========" + getBottom());
		mImageView.setImageMatrix(mMatrix);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
//		如果在API8以下的版本使用，采用FloatMath.sqrt()会更快，但是在API8和以上版本，Math.sqrt()更快
//		原文：Use java.lang.Math#sqrt instead of android.util.FloatMath#sqrt() since it is faster as of API 8
//		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}