package com.suricate.activities;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.suricate.R;
import com.suricate.pojo.Acces;
import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;
import com.suricate.ws.CallerServer;
import com.suricate.ws.RequestServer;
import com.suricate.ws.ServerAsyncTask;
import com.suricate.ws.WSMethod;

public class AddBadgeActivity extends Activity {

	private Boolean isNFC;
	private Boolean isPermanent;
	private EditText owner;
	private EditText pinCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_badge);

		owner = (EditText) findViewById(R.id.edit_owner);
		pinCode = (EditText) findViewById(R.id.edit_pin);

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

		if (isNFC) {
			Acces acces = new Acces(owner.getText().toString(), isNFC,
					isPermanent, null,(ApplicationValues.getInstance().listOfBadges.size()+1)+":"+ UUID.randomUUID().toString()
							.substring(0, 19));
			String json = acces.toNFCString();
			WSMethod.getInstance().addNFCAccess(json);
		} else {
			Acces acces = new Acces(owner.getText().toString(), isNFC,isPermanent, null, pinCode.getText().toString());
			String json = acces.toDigicodeString();
			WSMethod.getInstance().addDigicode(json);
		}
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
				findViewById(R.id.pin_code).setVisibility(View.VISIBLE);
				findViewById(R.id.edit_pin).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.radio_nfc:
			if (checked) {
				isNFC = Boolean.TRUE;
				findViewById(R.id.pin_code).setVisibility(View.INVISIBLE);
				findViewById(R.id.edit_pin).setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.radio_permanent:
			if (checked) {
				isPermanent = Boolean.FALSE;
			}
			break;
		case R.id.radio_temporary:
			if (checked) {
				isPermanent = Boolean.TRUE;
			}
			break;
		}
	}

}
