package com.example.propertyanimsecdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

public class LandscapeActivity extends Activity {
    protected static final String TAG = "LandscapeActivity";
    private ImageView mFlowerImg;
    private ValueAnimator mValueAnimator;
    private ImageView mNumberImg;
    private int mWidthPixels;
    private int mHeightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape);


        mFlowerImg = (ImageView) findViewById(R.id.flower);
        mNumberImg = (ImageView) findViewById(R.id.number_im);


        final Button sendFlowers = (Button) findViewById(R.id.send_flowers_bt);
        sendFlowers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //scale动画和贝塞尔曲线动画一起
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFlowerImg, "scaleX", 1.0f, 2f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFlowerImg, "scaleY", 1.0f, 2f);
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(scaleX).with(scaleY).with(mValueAnimator);
                animSet.setDuration(2000);
                animSet.start();

            }
        });

        //获取屏幕宽高
        mWidthPixels = getResources().getDisplayMetrics().widthPixels;
        mHeightPixels = getResources().getDisplayMetrics().heightPixels;
        Log.i(TAG, "width:" + mWidthPixels + "   height:" + mHeightPixels);

        mValueAnimator = ValueAnimator.ofObject(new BezierEvaluator()
                , new PointF(mWidthPixels, mHeightPixels), new PointF(mWidthPixels / 2, mHeightPixels / 2));//第一个pointF：开始点，第二个PointF：终点
        mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //根据计算好的点不断更新View的位置
                PointF pointF = (PointF) animation.getAnimatedValue();
                mFlowerImg.setX(pointF.x - mFlowerImg.getWidth() / 2);
                mFlowerImg.setY(pointF.y - mFlowerImg.getHeight() / 2);
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart");
                mFlowerImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd");
                mFlowerImg.setVisibility(View.GONE);

                mNumberImg.setVisibility(View.VISIBLE);

                //数字加1的动画效果组合有：位移动画从指定坐标点移动到指定目标坐标点，并带有透明度变化的属性动画
                PropertyValuesHolder ofFloaty = PropertyValuesHolder
                        .ofFloat("y", mHeightPixels / 2, mHeightPixels / 2 - 150f);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
                PropertyValuesHolder ofFloatX = PropertyValuesHolder
                        .ofFloat("x", mWidthPixels / 2, mWidthPixels / 2);

                PropertyValuesHolder alphaProperty = PropertyValuesHolder.ofFloat("alpha", 1f, 0.1f);//设置透明度的动画属性，过渡到0.1f透明度
                ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(mNumberImg, ofFloatX, ofFloaty, alphaProperty);//创建动画对象，把所有属性拼起来
                //动画效果：目标View逐步变大，X轴和Y轴两个方向
                ObjectAnimator x = ObjectAnimator.ofFloat(mNumberImg, "scaleX", 1.0f, 1.3f);
                ObjectAnimator y = ObjectAnimator.ofFloat(mNumberImg, "scaleY", 1.0f, 1.3f);
                //动画集合，把多个动画组合到一起
                AnimatorSet set = new AnimatorSet();
                set.play(animEnd).with(x).with(y);
                set.setDuration(900);
                set.start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mNumberImg.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    class BezierEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float oneMinusT = 1.0f - fraction;

            //startValue;    //开始出现的点
            //endValue;      //结束终点

            PointF controlPoint = new PointF();    //贝塞尔曲线控制点
            controlPoint.set(mWidthPixels / 2 + 600, mHeightPixels / 2 - 300);


            //B0(t) = (1-t)2P0 + 2(1-t)tC1 + t2P1    (0 ≤ t ≤ 1) 二次贝塞尔曲线方程

            PointF point = new PointF();    //返回计算好的点
            point.x = oneMinusT * oneMinusT * (startValue.x) + 2 * oneMinusT * fraction * (controlPoint.x) + fraction * fraction * (endValue.x);
            point.y = oneMinusT * oneMinusT * (startValue.y) + 2 * oneMinusT * fraction * (controlPoint.y) + fraction * fraction * (endValue.y);
            return point;
        }
    }

}
