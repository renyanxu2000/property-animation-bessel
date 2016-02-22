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

public class LandscapeBezierCurveView extends View{
	
	private static final String TAG = "com.example.propertyanimsecdemo.BezierCurveView";
		
	private Paint paint;
	
	private Path path;
	
	public LandscapeBezierCurveView(Context context, AttributeSet attrs){
		super(context,attrs);
		init();
	}
	
	public LandscapeBezierCurveView(Context context){
		super(context);
		init();
	}
	
	private void init(){		
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(1);
		
		path = new Path();
	}
	
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.v(TAG, "width = " + MeasureSpec.getSize(widthMeasureSpec) + "| height = " + MeasureSpec.getSize(heightMeasureSpec));
	}
		
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		path.reset();
		path.moveTo(883, 215);		//开始起点
		path.quadTo(432, 20, 432, 215);	// 控制点、终点
		canvas.drawPath(path, paint);
	}

}
