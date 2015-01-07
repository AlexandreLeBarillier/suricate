package com.suricate.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.suricate.R;
import com.suricate.activities.CopyBadgeActivity;
import com.suricate.activities.ShowBadgeActivity;
import com.suricate.adapters.BadgesAdapter;

public class BadgesListFragment extends Fragment {

	private ListView badges_listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_badges_list,
				container, false);

		getActivity().setTitle(R.string.title_activity_badges);
		getActivity().setTitleColor(Color.WHITE);

		List<String> arrayOfBadges = new ArrayList<String>();
		arrayOfBadges.add("Badge 1");
		arrayOfBadges.add("Badge 2");
		arrayOfBadges.add("Badge 3");
		arrayOfBadges.add("Badge 4");
		BadgesAdapter adapter = new BadgesAdapter(this.getActivity(),
				arrayOfBadges);

		badges_listview = (ListView) rootView
				.findViewById(R.id.badges_listview);
		badges_listview.setAdapter(adapter);

		badges_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object o = badges_listview.getItemAtPosition(position);
				String bdg = (String) o;// As you are using Default String
										// Adapter
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());

				if (bdg != null) {
					builder.setTitle(bdg);
				}
				builder.setPositiveButton("Utiliser", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent i = new Intent(getActivity().getApplicationContext(),
								ShowBadgeActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("Copier", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent i = new Intent(getActivity().getApplicationContext(),
								CopyBadgeActivity.class);
						startActivity(i);
					}
				});
				builder.show();
			}
		});
		return rootView;
	}

}
