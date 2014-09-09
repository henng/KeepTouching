package com.henryluo.keeptouching;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@SuppressLint("NewApi")
public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanState) {
		super.onCreate(savedInstanState);
		
		addPreferencesFromResource(R.xml.settings);
		
	}
	

}
