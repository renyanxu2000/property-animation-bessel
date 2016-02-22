package com.example.propertyanimsecdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "MainActivity";
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);				
		
		
		initView();
	}

	private void initView() {
		Button bezierVerticalValueAnimator = (Button) findViewById(R.id.bezier_vertical_ValueAnimator);
		Button bezierLanscapeValueAnimator = (Button) findViewById(R.id.bezier_landscape_ValueAnimator);
		
		bezierVerticalValueAnimator.setOnClickListener(this);
		bezierLanscapeValueAnimator.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.bezier_vertical_ValueAnimator:
			intent = new Intent(this, VerticalActivity.class);
			startActivity(intent);
			break;
		case R.id.bezier_landscape_ValueAnimator:
			intent = new Intent(this, LandscapeActivity.class);
			startActivity(intent);
			break;

		}
	}
}
