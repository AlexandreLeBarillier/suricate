package com.suricate.activities;

import com.suricate.R;
import com.suricate.R.layout;
import com.suricate.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class InscriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_inscription, menu);
		return true;
	}

}
