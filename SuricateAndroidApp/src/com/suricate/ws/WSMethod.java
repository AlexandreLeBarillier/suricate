package com.suricate.ws;

import java.util.Date;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.suricate.pojo.Acces;
import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;

public class WSMethod implements CallerServer {

	private static WSMethod _instance;
	private static String URL_CALLED;

	public static WSMethod getInstance() {
		if (_instance == null) {
			_instance = new WSMethod();
		}
		return _instance;
	}

	public static void addNFCAccess(Acces acces) {
		URL_CALLED = Constantes.URL_ADD_NFC_BADGE;
		ServerAsyncTask task = new ServerAsyncTask(_instance);
		RequestServer requestServer = new RequestServer();
		requestServer.setUrlSuffix(URL_CALLED);
		requestServer.setMethod("POST");
		requestServer.setRequestObject(acces.toNFCString());
		task.execute(requestServer);
		ApplicationValues.getInstance().listOfBadges.add(acces);
	}

	public static void addDigicode(Acces acces) {
		URL_CALLED = Constantes.URL_ADD_DIGICODE;
		ServerAsyncTask task = new ServerAsyncTask(_instance);
		RequestServer requestServer = new RequestServer();
		requestServer.setUrlSuffix(URL_CALLED);
		requestServer.setMethod("POST");
		requestServer.setRequestObject(acces.toDigicodeString());
		task.execute(requestServer);
		ApplicationValues.getInstance().listOfDigicodes.add(acces);
	}

	@Override
	public void onPostExecuteServer(String json) {
		if (!"".equals(json) && json != null) {
			Log.d("WS-OUT json", json);
			if (Constantes.URL_LIST_NFC_BADGE.equals(URL_CALLED)) {
				JsonElement jelement = new JsonParser().parse(json);
				JsonObject jobject = jelement.getAsJsonObject();
				JsonArray jarray = jobject.getAsJsonArray("badges");
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
			} else {
				if (Constantes.URL_LIST_DIGICODE.equals(URL_CALLED)) {
					JsonElement jelement = new JsonParser().parse(json);
					JsonObject jobject = jelement.getAsJsonObject();
					JsonArray jarray = jobject.getAsJsonArray("digicodes");
				}
			}
		}
	}
}
