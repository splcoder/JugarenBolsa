package com.example.jugarenbolsa.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jugarenbolsa.R;

public class SplashActivity extends AppCompatActivity {

	private final int SPLASH_DURATION = 3000; // 3 seconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable(){
			public void run(){
				// Go to ListNotesActivity
				Intent intent = new Intent( SplashActivity.this, MainActivity.class );
				startActivity( intent );
				finish();
			};
		}, SPLASH_DURATION);

	}
}
