package com.suricate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.suricate.R;

public class ValidEnterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_valid_enter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_valid_enter, menu);
		return true;
	}

}
