package com.suricate.fragments;

import com.suricate.R;
import com.suricate.R.layout;
import com.suricate.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about,
				container, false);

		getActivity().setTitle(R.string.title_activity_about);
		getActivity().setTitleColor(Color.WHITE);

		return rootView;
	}
}
