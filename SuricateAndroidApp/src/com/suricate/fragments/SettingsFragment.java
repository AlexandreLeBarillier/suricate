package com.suricate.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.suricate.R;
import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;

public class SettingsFragment extends Fragment {

	private Switch switchview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);

		getActivity().setTitle(R.string.title_activity_settings);
		getActivity().setTitleColor(Color.WHITE);

		switchview = (Switch) rootView.findViewById(R.id.switch1);
		if(ApplicationValues.getInstance().WS_URL.equals(Constantes.WEBSERVICES_URL_PROD)){
			switchview.setChecked(true);
		}
		// attach a listener to check for changes in state
		switchview.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					ApplicationValues.getInstance().WS_URL = Constantes.WEBSERVICES_URL_PROD;
				} else {
					ApplicationValues.getInstance().WS_URL = Constantes.WEBSERVICES_URL_DEV;
				}

			}
		});

		return rootView;
	}
}
