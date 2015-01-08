package com.suricate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

import com.suricate.R;
import com.suricate.fragments.BadgesListFragment;

public class AddBadgeActivity extends Activity {

	private Boolean isNFC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_badge);

		findViewById(R.id.ajouter_acces).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						addAcces();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_badge, menu);
		return true;
	}

	private void addAcces() {
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radio_digicode:
			if (checked) {
				isNFC = Boolean.FALSE;
			}
			break;
		case R.id.radio_nfc:
			if (checked) {
				isNFC = Boolean.TRUE;
			}
			break;
		}
	}
}
