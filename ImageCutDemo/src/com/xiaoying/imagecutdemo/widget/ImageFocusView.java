package com.xiaoying.imagecutdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.xiaoying.imagecutdemo.util.LogUtil;

public class ImageFocusView extends View {
	private String tag = ImageFocusView.class.getSimpleName();
	
	private int mFocusLeft = 0;
	private int mFocusTop = 0;
	private int mFocusRight = 0;
	private int mFocusBottom = 0;
	
	private int mHideColor = Color.argb(0xAF, 0x00, 0x00, 0x00);
	
	private int mFocusColor = Color.argb(0xFF, 0x80, 0x80, 0x80);
	
	private Paint mPaint = new Paint();
	
	private int mFocusWidth = 400;
	
	private float mStrokWidth = 3.0f;
	
	private Point mFocusMidPoint = new Point();
	

	public ImageFocusView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageFocusView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageFocusView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initData();
		mPaint.setColor(mFocusColor);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mStrokWidth);
		canvas.drawRect(mFocusLeft, mFocusTop, mFocusRight, mFocusBottom, mPaint);	//绘制焦点框
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(mHideColor);
		canvas.drawRect(getLeft(), getTop(), getRight(), mFocusTop, mPaint);	//绘制焦点框上边阴影
		canvas.drawRect(getLeft(), mFocusTop, mFocusLeft, mFocusBottom + mStrokWidth / 2, mPaint);	//绘制焦点框左边阴影
		canvas.drawRect(mFocusRight + mStrokWidth / 2, mFocusTop, getRight(), mFocusBottom + mStrokWidth / 2, mPaint);	//绘制焦点框右边边阴影
		canvas.drawRect(getLeft(), mFocusBottom + mStrokWidth / 2, getRight(), getBottom(), mPaint);	//绘制焦点框下边阴影
	}
	
	private void initData() {
		LogUtil.i(tag, "View content+++++(" + getLeft() + ", " + getTop() + ", " 
				+ getRight() + ", " + getBottom() + ")");
		mFocusMidPoint.set((getRight() - getLeft()) / 2, (getBottom() - getTop()) / 2);
		mFocusLeft = mFocusMidPoint.x - mFocusWidth / 2;
		mFocusTop = mFocusMidPoint.y - mFocusWidth / 2;
		mFocusRight = mFocusMidPoint.x + mFocusWidth / 2;
		mFocusBottom = mFocusMidPoint.y + mFocusWidth / 2;
		LogUtil.i(tag, "Focus content=====(" + getFocusLeft() + ", " + getFocusTop() + ", "
				+ getFocusRight() + ", " + getFocusBottom() + ")");
	}

	/**
	 * 返回焦点框左边位置
	 * @return
	 */
	public int getFocusLeft() {
		return mFocusLeft;
	}

	/**
	 * 返回焦点框上边位置
	 * @return
	 */
	public int getFocusTop() {
		return mFocusTop;
	}

	/**
	 * 返回焦点框右边位置
	 * @return
	 */
	public int getFocusRight() {
		return mFocusRight;
	}

	/**
	 * 返回焦点框下边位置
	 * @return
	 */
	public int getFocusBottom() {
		return mFocusBottom;
	}

	/**
	 * 返回焦点框中间点坐标
	 * @return
	 */
	public Point getFocusMidPoint() {
		return mFocusMidPoint;
	}

	/**
	 * 返回焦点框宽度
	 * @return
	 */
	public int getFocusWidth() {
		return mFocusWidth;
	}

	/**
	 * 返回阴影颜色
	 * @return
	 */
	public int getHideColor() {
		return mHideColor;
	}

	/**
	 * 返回焦点框边框颜色
	 * @return
	 */
	public int getFocusColor() {
		return mFocusColor;
	}

	/**
	 * 返回焦点框边框绘制宽度
	 * @return
	 */
	public float getStrokWidth() {
		return mStrokWidth;
	}
}
