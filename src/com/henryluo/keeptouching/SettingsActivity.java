package com.henryluo.keeptouching;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity {

    private ActionBar bar = null; 
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    	
        bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("设置");
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case android.R.id.home:
			finish();
			return true;
			
		default:
			break;
			
		}
		
		return super.onOptionsItemSelected(item);
	}
    
	
	
	
}
