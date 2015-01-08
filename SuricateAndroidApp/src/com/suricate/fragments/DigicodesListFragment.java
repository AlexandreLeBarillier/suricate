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
import com.suricate.adapters.DigicodeAdapter;
import com.suricate.pojo.Acces;
import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;
import com.suricate.ws.CallerServer;
import com.suricate.ws.RequestServer;
import com.suricate.ws.ServerAsyncTask;
import com.suricate.ws.WSMethod;

public class DigicodesListFragment extends Fragment implements CallerServer {

	private ListView badges_listview;
	private ListView digicodes_listview;
	private String URL_CALLED;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_digicodes_list,
				container, false);

		getActivity().setTitle(R.string.title_activity_digicodes);
		getActivity().setTitleColor(Color.WHITE);

		updateDigicodeList();

		digicodes_listview = (ListView) rootView
				.findViewById(R.id.digicodes_listview);
		AccesAdapter adapterdDigicode = new AccesAdapter(this.getActivity(),
				ApplicationValues.getInstance().listOfDigicodes);
		digicodes_listview.setAdapter(adapterdDigicode);

		return rootView;
	}

	private void updateDigicodeList() {
		URL_CALLED = Constantes.URL_LIST_DIGICODE;
		ServerAsyncTask task = new ServerAsyncTask(this);
		RequestServer requestServer = new RequestServer();
		requestServer.setMethod("GET");
		requestServer.setUrlSuffix(URL_CALLED);
		requestServer.setRequestObject("");
		task.execute(requestServer);
	}

	@Override
	public void onPostExecuteServer(String json) {
		if (!"".equals(json) && json != null) {
			Log.d("WS-OUT json", json);
			if (Constantes.URL_LIST_DIGICODE.equals(URL_CALLED)) {
				JsonElement jelement = new JsonParser().parse(json);
				JsonObject jobject = jelement.getAsJsonObject();
				JsonArray jarray = jobject.getAsJsonArray("pincodes");

				if (jarray.size() > 0) {
					ApplicationValues.getInstance().listOfDigicodes.clear();
				}
				for (int i = 0; i < jarray.size(); i++) {
					JsonObject jsonBadge = jarray.get(i).getAsJsonObject();
					Acces acces = new Acces(jsonBadge.get("owner")
							.getAsString(), Boolean.TRUE, jsonBadge
							.get("validity").getAsString().equals("1"),
							new Date(), jsonBadge.get("pincode").getAsString());
					ApplicationValues.getInstance().listOfDigicodes.add(acces);
				}
				DigicodeAdapter adapterdDigicode = new DigicodeAdapter(
						this.getActivity(),
						ApplicationValues.getInstance().listOfDigicodes);
				digicodes_listview.setAdapter(adapterdDigicode);
				digicodes_listview.invalidateViews();

			}
		}

	}
}
