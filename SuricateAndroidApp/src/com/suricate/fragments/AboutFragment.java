package com.suricate.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suricate.R;

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
