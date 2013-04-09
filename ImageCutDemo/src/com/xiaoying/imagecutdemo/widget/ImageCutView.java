package com.xiaoying.imagecutdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class ImageCutView extends FrameLayout {

	/** 拖动模式 */
	private static final int MODE_DRAG = 1;
	/** 缩放模式 */
	private static final int MODE_ZOOM = 2;
	/** 没有模式 */
	private static final int MODE_NONE = 3;

	private int mMode = MODE_NONE;

	private ImageView mImageView = null;

	// private Bitmap mBitmap = null;

	private Matrix mMatrix = new Matrix();
	private Matrix mSavedMatrix = new Matrix();

	private PointF mStartPoint = new PointF();
	private PointF mMidPoint = new PointF();
	private float mOldDist = 1f;
	
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
		// this.mBitmap = bitmap;
		mImageView.setImageBitmap(bitmap);
	}

	private void initView(Context context) {

		mImageView = new ImageView(context);
		mImageView.setScaleType(ScaleType.MATRIX);
		addView(mImageView, new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		ImageFocusView ifv = new ImageFocusView(context);
		addView(ifv, new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		

		mImageView.setOnTouchListener(mOntouchListener);
	}
	
	View.OnTouchListener mOntouchListener = new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			ImageView imageView = (ImageView) v;
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				mMatrix.set(imageView.getImageMatrix());
				mSavedMatrix.set(mMatrix);
				mStartPoint.set(event.getX(), event.getY());
				mMode = MODE_DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				mOldDist = spacing(event);
				if (mOldDist > 10f) {
					mSavedMatrix.set(mMatrix);
					midPoint(mMidPoint, event);
					mMode = MODE_ZOOM;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				Log.w("FLAG", "ACTION_MOVE");
				if (mMode == MODE_DRAG) {
					mMatrix.set(mSavedMatrix);
					mMatrix.postTranslate(event.getX() - mStartPoint.x,
							event.getY() - mStartPoint.y);
					
				} else if (mMode == MODE_ZOOM) {
					float newDist = spacing(event);
					if (newDist > 10f) {
						mMatrix.set(mSavedMatrix);
						float scale = newDist / mOldDist;
						mMatrix.postScale(scale, scale, mMidPoint.x, mMidPoint.y);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mMode = MODE_NONE;
				float [] values = new float[9];
				mMatrix.getValues(values);
				Log.i(tag, "MSCALE_X = " + values[0] + "; MSKEW_X = " + values[1] + "; MTRANS_X = " + values[2]
						+ "; \nMSCALE_Y = " + values[4] + "; MSKEW_Y = " + values[3] + "; MTRANS_Y = " + values[5]
								 + "; \nMPERSP_0 = " + values[6] + "; MPERSP_1 = " + values[7] + "; MPERSP_2 = " + values[8]);
				break;
			}
			imageView.setImageMatrix(mMatrix);
			return true;
		}
	};

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}