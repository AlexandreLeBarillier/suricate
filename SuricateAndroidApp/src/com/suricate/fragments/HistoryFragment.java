package com.suricate.fragments;

import java.util.Date;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.suricate.R;
import com.suricate.adapters.DigicodeAdapter;
import com.suricate.adapters.HistoryAdapter;
import com.suricate.pojo.Acces;
import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;
import com.suricate.ws.CallerServer;
import com.suricate.ws.RequestServer;
import com.suricate.ws.ServerAsyncTask;

public class HistoryFragment extends Fragment implements CallerServer {

	private ListView history_listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_history, container,
				false);

		history_listview = (ListView) rootView
				.findViewById(R.id.history_listview);
		getActivity().setTitle(R.string.title_activity_history);
		getActivity().setTitleColor(Color.WHITE);

		updateHistoryList();

		return rootView;
	}

	private void updateHistoryList() {
		ServerAsyncTask task = new ServerAsyncTask(this);
		RequestServer requestServer = new RequestServer();
		requestServer.setMethod("GET");
		requestServer.setUrlSuffix(Constantes.URL_LIST_HISTORY);
		requestServer.setRequestObject("");
		task.execute(requestServer);
	}

	@Override
	public void onPostExecuteServer(String json) {
		if (!"".equals(json) && json != null) {
			Log.d("WS-OUT json", json);
			JsonElement jelement = new JsonParser().parse(json);
			JsonObject jobject = jelement.getAsJsonObject();
			JsonArray jarray = jobject.getAsJsonArray("accesslogs");

			if (jarray.size() > 0) {
				ApplicationValues.getInstance().listOfHistory.clear();
			}
			for (int i = 0; i < jarray.size(); i++) {
				JsonObject jsonBadge = jarray.get(i).getAsJsonObject();
				Acces acces = new Acces(jsonBadge.get("content").getAsString(),
						Boolean.TRUE, Boolean.TRUE, new Date(jsonBadge.get(
								"date").getAsLong()), "");
				ApplicationValues.getInstance().listOfHistory.add(acces);
			}
			HistoryAdapter adapterdDigicode = new HistoryAdapter(
					this.getActivity(),
					ApplicationValues.getInstance().listOfHistory);
			history_listview.setAdapter(adapterdDigicode);
			history_listview.invalidateViews();
		}
	}

}
