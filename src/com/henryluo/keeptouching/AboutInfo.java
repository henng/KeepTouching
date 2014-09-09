package com.henryluo.keeptouching;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;


public class AboutInfo extends Activity {
	
    private ActionBar bar = null; 
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_info);
		
		bar = getActionBar();
		bar.hide();
		//bar.setDisplayHomeAsUpEnabled(true);
		//bar.setTitle("关于");

		
	}

}
