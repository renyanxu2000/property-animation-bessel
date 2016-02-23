package com.example.propertyanimsecdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
//画一个贝塞尔曲线轨迹背景
public class BezierCurveView extends View{
	
	private static final String TAG = BezierCurveView.class.getSimpleName();
		
	private Paint mPaint;
	private Path mPath;
	private int mWidth;
	private int mHeight;

	public BezierCurveView(Context context, AttributeSet attrs){
		super(context,attrs);
		init();
	}
	
	public BezierCurveView(Context context){
		super(context);
		init();
	}
	
	private void init(){		
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(1);
		
		mPath = new Path();
	}
	
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);
		Log.i(TAG, "width = " + mWidth + "| height = " + mHeight);
	}
		
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.TRANSPARENT);
		mPath.reset();
		mPath.moveTo(mWidth/2, mHeight);	//开始起点
		mPath.quadTo(mWidth/2+600, mHeight/3-100, mWidth/2, mHeight/3);	//控制点，终点
		canvas.drawPath(mPath, mPaint);
	}

}
