package com.suricate.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.suricate.R;
import com.suricate.activities.CopyBadgeActivity;
import com.suricate.activities.MainActivity;
import com.suricate.activities.ShowBadgeActivity;
import com.suricate.adapters.AccesAdapter;
import com.suricate.pojo.Acces;
import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;
import com.suricate.ws.CallerServer;
import com.suricate.ws.RequestServer;
import com.suricate.ws.ServerAsyncTask;
import com.suricate.ws.WSMethod;

public class BadgesListFragment extends Fragment implements CallerServer {

	private ListView badges_listview;
	private ListView digicodes_listview;
	private String URL_CALLED;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_badges_list,
				container, false);

		getActivity().setTitle(R.string.title_activity_badges);
		getActivity().setTitleColor(Color.WHITE);

		badges_listview = (ListView) rootView
				.findViewById(R.id.badges_listview);
		updateNFCAccessList();

		badges_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object o = badges_listview.getItemAtPosition(position);
				Acces bdg = (Acces) o;// As you are using Default String
										// Adapter
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());

				if (bdg != null) {
					builder.setTitle("Badge " + (position + 1) + " - "
							+ bdg.getOwner());
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

		// List<Acces> listDigicodes = new ArrayList<Acces>();
		// if (ApplicationValues.getInstance().listOfDigicodes.isEmpty()) {
		// listDigicodes.add(new Acces("Alexandre", Boolean.FALSE,
		// Boolean.FALSE, new java.util.Date(), "1234"));
		// listDigicodes.add(new Acces("Etienne", Boolean.FALSE,
		// Boolean.FALSE, new java.util.Date(), "1234"));
		// ApplicationValues.getInstance().listOfDigicodes = listDigicodes;
		// } else {
		// listDigicodes = ApplicationValues.getInstance().listOfDigicodes;
		// }

		return rootView;
	}

	private void updateNFCAccessList() {
		URL_CALLED = Constantes.URL_LIST_NFC_BADGE;
		ServerAsyncTask task = new ServerAsyncTask(this);
		RequestServer requestServer = new RequestServer();
		requestServer.setUrlSuffix(URL_CALLED);
		requestServer.setMethod("GET");
		requestServer.setRequestObject("");
		task.execute(requestServer);
	}

	@Override
	public void onPostExecuteServer(String json) {
		if (!"".equals(json) && json != null) {
			Log.d("WS-OUT json", json);
			if (Constantes.URL_LIST_NFC_BADGE.equals(URL_CALLED)) {
				JsonElement jelement = new JsonParser().parse(json);
				JsonObject jobject = jelement.getAsJsonObject();
				JsonArray jarray = jobject.getAsJsonArray("badges");
				if (jarray.size() > 0) {
					ApplicationValues.getInstance().listOfBadges.clear();
				}
				for (int i = 0; i < jarray.size(); i++) {
					JsonObject jsonBadge = jarray.get(i).getAsJsonObject();
					String[] splits = jsonBadge.get("nfccode").getAsString()
							.split(":");
					Acces acces = new Acces(jsonBadge.get("owner")
							.getAsString(), Boolean.TRUE, jsonBadge
							.get("validity").getAsString().equals("1"),
							new Date(), splits[0]);
					ApplicationValues.getInstance().listOfBadges.add(acces);
				}
				AccesAdapter adapterdDigicode = new AccesAdapter(
						this.getActivity(),
						ApplicationValues.getInstance().listOfBadges);
				badges_listview.setAdapter(adapterdDigicode);
				badges_listview.invalidateViews();

			}
		}

	}
}
