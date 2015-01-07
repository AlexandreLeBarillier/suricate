package com.suricate.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suricate.R;

public class NavigationDrawerItemAdapter extends ArrayAdapter<String> {
	// View lookup cache
	private static class NDItem {
		ImageView icon;
		TextView label;
	}

	public NavigationDrawerItemAdapter(Context context, ArrayList<String> labels) {
		super(context, R.layout.adapter_navigation_drawer_item, labels);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		String label = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		NDItem viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new NDItem();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(
					R.layout.adapter_navigation_drawer_item, null);
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
			viewHolder.label = (TextView) convertView.findViewById(R.id.label);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (NDItem) convertView.getTag();
		}
		// Populate the data into the template view using the data object

		int iconValue = R.drawable.ic_action_view_as_grid;

		if (label.equals("Accès")) {
			iconValue = R.drawable.ic_action_secure;
		}
		if (label.equals("Historique")) {
			iconValue = R.drawable.ic_action_rewind;
		}
		if (label.equals("Paramètres")) {
			iconValue = R.drawable.ic_action_settings;
		}
		if (label.equals("Déconnexion")) {
			iconValue = R.drawable.ic_disconnect;
		}
		if (label.equals("A propos")) {
			iconValue = R.drawable.ic_action_about;
		}
		viewHolder.icon.setImageResource(iconValue);
		viewHolder.label.setText(label);

		// Return the completed view to render on screen
		return convertView;
	}
}