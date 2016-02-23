package com.example.propertyanimsecdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class VerticalActivity extends Activity {

	protected static final String TAG = "VerticalActivity";
	private ImageView flower;
	private ValueAnimator valueAnimator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vertical);
		
		flower = (ImageView) findViewById(R.id.flower);
		final Button button = (Button) findViewById(R.id.button1);		
		button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				
				//第二个属性动画效果，逐渐变
				ObjectAnimator anim1 = ObjectAnimator.ofFloat(flower, "scaleX",1.0f, 2f);
				ObjectAnimator anim2 = ObjectAnimator.ofFloat(flower, "scaleY",1.0f, 2f);
				AnimatorSet animSet = new AnimatorSet();
				animSet.play(anim1).with(anim2).with(valueAnimator);
				animSet.setDuration(2000);
				animSet.start();
				
				
			}
		});
		
		
		valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(),new PointF(324,893), new PointF(270,193));
		valueAnimator.setDuration(2000);		
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {			
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
                //在次对目标view做修改
				PointF pointF = (PointF)animation.getAnimatedValue();
				flower.setX(pointF.x-flower.getWidth()/2);
				flower.setY(pointF.y-flower.getHeight()/2);
			}
		});
//		valueAnimator.setTarget(flower);  该方法目前不知道有何作用
		valueAnimator.addListener(new AnimatorListenerAdapter() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				Log.i(TAG, "onAnimationStart");
				flower.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				Log.i(TAG, "onAnimationRepeat");
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				Log.i(TAG, "onAnimationEnd");
				flower.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				Log.i(TAG, "onAnimationCancel");
				
			}
		});
//		valueAnimator.setRepeatMode(ValueAnimator.RESTART);
	}
	
	
	class BezierEvaluator implements TypeEvaluator<PointF>{

		@Override
		public PointF evaluate(float fraction, PointF startValue,
				PointF endValue) {
			final float t = fraction;	
			Log.e(TAG, "fraction:"+fraction);
			float oneMinusT = 1.0f - t;
			PointF point = new PointF();	//返回计算好的点
			
			PointF point0 = startValue;	//开始出现的点
			
			PointF point1 = new PointF();	//贝塞尔曲线控制点
			point1.set(480, 30);//  324,893), new PointF(270,193)
			
			PointF point3 = endValue;	//结束终点
			
			//B0(t) = (1-t)2P0 + 2(1-t)tC1 + t2P1    (0 ≤ t ≤ 1) 二次贝塞尔曲线方程
			
			point.x = oneMinusT*oneMinusT*(point0.x)+2*oneMinusT*t*(point1.x)+t*t*(point3.x);
			point.y = oneMinusT*oneMinusT*(point0.y)+2*oneMinusT*t*(point1.y)+t*t*(point3.y);
			return point;
		}	
	}

}
