package com.suricate.adapters;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suricate.R;
import com.suricate.pojo.Acces;

public class HistoryAdapter extends ArrayAdapter<Acces> {

	private static class AccesView {
		private TextView name;
		private TextView last_usage;
	}

	public HistoryAdapter(Context context, List<Acces> badges) {
		super(context, R.layout.adapter_item_history, badges);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get the data item for this position
		Acces item = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		AccesView viewAcces; // view lookup cache stored in tag

		if (convertView == null) {
			viewAcces = new AccesView();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.adapter_item_history, null);
			viewAcces.name = (TextView) convertView
					.findViewById(R.id.badge_name);
			viewAcces.last_usage = (TextView) convertView
					.findViewById(R.id.badge_last_usage);
			convertView.setTag(viewAcces);
		} else {
			viewAcces = (AccesView) convertView.getTag();
		}
		
		// Populate the data into the template view using the data object
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		viewAcces.name.setText(item.getOwner());
		viewAcces.last_usage.setText("Utilisé le : "+sdf.format(item.getLastUsage()));

		// Return the completed view to render on screen
		return convertView;
	}
}
