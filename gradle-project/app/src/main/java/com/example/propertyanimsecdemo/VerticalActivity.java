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
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class VerticalActivity extends Activity {

    protected static final String TAG = "VerticalActivity";
    private ImageView mFlower;
    private ValueAnimator mValueAnimator;
    private View mBezierView;
    private int mWidthPixels;
    private int mHeightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);

        mFlower = (ImageView) findViewById(R.id.flower);
        mBezierView = findViewById(R.id.bezierView);
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //第二个属性动画效果，逐渐变
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(mFlower, "scaleX", 1.0f, 2f);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(mFlower, "scaleY", 1.0f, 2f);
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(anim1).with(anim2).with(mValueAnimator);
                animSet.setDuration(2000);
                animSet.start();

                Log.i(TAG, "onClick: width:" + mWidthPixels + " height:" + mHeightPixels);
            }
        });

        //获取屏幕宽高
        mWidthPixels = getResources().getDisplayMetrics().widthPixels;
        mHeightPixels = getResources().getDisplayMetrics().heightPixels;
        Log.i(TAG, "width:" + mWidthPixels + "   height:" + mHeightPixels);

        //TitleBar 高
        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
        Log.i(TAG, "onCreate: actionbar height:" + actionBarHeight);

        //状态栏高
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG, "onCreate: status bar height:" + statusBarHeight);

        mHeightPixels = mHeightPixels - actionBarHeight - statusBarHeight;
        Log.i(TAG, "width:" + mWidthPixels + "   end height:" + mHeightPixels);

        mValueAnimator = ValueAnimator.ofObject(new BezierEvaluator(), new PointF(mWidthPixels / 2, mHeightPixels), new PointF(mWidthPixels / 2, mHeightPixels / 3));
        mValueAnimator.setDuration(2000);
        mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //在次对目标view做修改
                PointF pointF = (PointF) animation.getAnimatedValue();
                mFlower.setX(pointF.x - mFlower.getWidth() / 2);
                mFlower.setY(pointF.y - mFlower.getHeight() / 2);
            }
        });
//		mValueAnimator.setTarget(mFlower);  该方法目前不知道有何作用
        mValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart");
                mFlower.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd");
                mFlower.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel");

            }
        });
//		mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    }


    public void showBezier(View view) {
        mBezierView.setVisibility(mBezierView.isShown() ? View.GONE : View.VISIBLE);
    }

    class BezierEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            final float t = fraction;
//			Log.i(TAG, "fraction:"+fraction);
            float oneMinusT = 1.0f - t;
            PointF point = new PointF();    //返回计算好的点

            //startValue:	开始出现的点
            //endValue:	    结束终点

            PointF controlPoint = new PointF();    //贝塞尔曲线控制点
            controlPoint.set(mWidthPixels / 2 + 600, mHeightPixels / 3 - 100);//  324,893), new PointF(270,193)


            //B0(t) = (1-t)2P0 + 2(1-t)tC1 + t2P1    (0 ≤ t ≤ 1) 二次贝塞尔曲线方程

            point.x = oneMinusT * oneMinusT * (startValue.x) + 2 * oneMinusT * t * (controlPoint.x) + t * t * (endValue.x);
            point.y = oneMinusT * oneMinusT * (startValue.y) + 2 * oneMinusT * t * (controlPoint.y) + t * t * (endValue.y);
            return point;
        }
    }

}
