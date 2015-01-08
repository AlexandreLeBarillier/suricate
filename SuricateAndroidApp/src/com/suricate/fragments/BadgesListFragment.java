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
import com.suricate.adapters.AccesAdapter;
import com.suricate.pojo.Acces;
import com.suricate.utils.ApplicationValues;
import com.suricate.ws.CallerServer;
import com.suricate.ws.WSMethod;

public class BadgesListFragment extends Fragment implements CallerServer {

	private ListView badges_listview;
	private ListView digicodes_listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_badges_list,
				container, false);

		getActivity().setTitle(R.string.title_activity_badges);
		getActivity().setTitleColor(Color.WHITE);

//		List<Acces> listBadges = new ArrayList<Acces>();
		WSMethod.getInstance().updateNFCAccessList();
		
//		if (ApplicationValues.getInstance().listOfBadges.isEmpty()) {
//			listBadges.add(new Acces("Alexandre", Boolean.FALSE, Boolean.FALSE,
//					new java.util.Date(),"1:1234"));
//			listBadges.add(new Acces("Etienne", Boolean.FALSE, Boolean.FALSE,
//					new java.util.Date(),"1:1234"));
//			ApplicationValues.getInstance().listOfBadges = listBadges;
//		} else {
//			listBadges = ApplicationValues.getInstance().listOfBadges;
//		}

		AccesAdapter adapterBadges = new AccesAdapter(this.getActivity(),
				ApplicationValues.getInstance().listOfBadges);

		badges_listview = (ListView) rootView.findViewById(R.id.badges_listview);
		badges_listview.setAdapter(adapterBadges);

		badges_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object o = badges_listview.getItemAtPosition(position);
				Acces bdg = (Acces) o;// As you are using Default String
										// Adapter
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());

				if (bdg != null) {
					builder.setTitle("Badge " + (position+1) + " - " + bdg.getOwner());
				}
				builder.setPositiveButton("Utiliser", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent i = new Intent(getActivity()
								.getApplicationContext(),
								ShowBadgeActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("Copier", new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent i = new Intent(getActivity()
								.getApplicationContext(),
								CopyBadgeActivity.class);
						startActivity(i);
					}
				});
				builder.show();
			}
		});

		digicodes_listview = (ListView) rootView
				.findViewById(R.id.digicodes_listview);

		List<Acces> listDigicodes = new ArrayList<Acces>();
		if (ApplicationValues.getInstance().listOfDigicodes.isEmpty()) {
			listDigicodes.add(new Acces("Alexandre", Boolean.FALSE, Boolean.FALSE,
					new java.util.Date(),"1234"));
			listDigicodes.add(new Acces("Etienne", Boolean.FALSE, Boolean.FALSE,
					new java.util.Date(),"1234"));
			ApplicationValues.getInstance().listOfDigicodes = listDigicodes;
		} else {
			listDigicodes = ApplicationValues.getInstance().listOfDigicodes;
		}

		AccesAdapter adapterdDigicode = new AccesAdapter(this.getActivity(),
				listDigicodes);
		digicodes_listview.setAdapter(adapterdDigicode);

		return rootView;
	}

	@Override
	public void onPostExecuteServer(String json) {

	}

}
