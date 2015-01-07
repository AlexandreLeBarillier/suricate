package com.suricate.fragments;

import com.suricate.R;
import com.suricate.R.layout;
import com.suricate.R.string;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings,
				container, false);

		getActivity().setTitle(R.string.title_activity_settings);
		getActivity().setTitleColor(Color.WHITE);

		return rootView;
	}
}
