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
	
	private static final String TAG = "com.example.propertyanimsecdemo.BezierCurveView";
		
	private Paint mPaint;
	private Path mPath;
	
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
		Log.v(TAG, "width = " + MeasureSpec.getSize(widthMeasureSpec) + "| height = " + MeasureSpec.getSize(heightMeasureSpec));
	}
		
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		mPath.reset();
		mPath.moveTo(324, 893);
		mPath.quadTo(480, 30, 270, 192);
		canvas.drawPath(mPath, mPaint);
	}

}
