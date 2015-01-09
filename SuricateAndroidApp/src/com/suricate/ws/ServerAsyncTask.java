package com.suricate.ws;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.suricate.utils.ApplicationValues;
import com.suricate.utils.Constantes;

public class ServerAsyncTask extends AsyncTask<RequestServer, Void, String> {

	private CallerServer caller;

	public ServerAsyncTask(CallerServer c) {
		caller = c;
	}

	@Override
	protected String doInBackground(RequestServer... arg0) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			StringEntity se = new StringEntity(arg0[0].getRequestObject());
			Log.d("WS json",arg0[0].getRequestObject() );

			if(arg0[0].getMethod().equals("GET")){
				// url with the post data
				HttpGet httpGet = new HttpGet(ApplicationValues.getInstance().WS_URL+ arg0[0].getUrlSuffix());
			
				// Handles what is returned from the page
				ResponseHandler responseHandler = new BasicResponseHandler();
				return httpclient.execute(httpGet, responseHandler);
			}else{
				// url with the post data
				HttpPost httpost = new HttpPost(ApplicationValues.getInstance().WS_URL+ arg0[0].getUrlSuffix());
				// passes the results to a string builder/entity

				// sets the post request as the resulting string
				httpost.setEntity(se);
				// sets a request header so the page receving the request
				// will know what to do with it
				httpost.setHeader("Accept", "application/json");
				httpost.setHeader("Content-type", "application/json");

				// Handles what is returned from the page
				ResponseHandler responseHandler = new BasicResponseHandler();
				return httpclient.execute(httpost, responseHandler);
			}
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(String json) {
		caller.onPostExecuteServer(json);
	}
}
