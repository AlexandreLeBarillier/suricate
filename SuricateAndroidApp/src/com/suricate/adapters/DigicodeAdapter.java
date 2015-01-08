package com.suricate.adapters;

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

public class DigicodeAdapter extends ArrayAdapter<Acces> {

	private static class AccesView {
		private View color;
		private TextView name;
		private TextView last_usage;
	}

	public DigicodeAdapter(Context context, List<Acces> badges) {
		super(context, R.layout.adapter_item_badge, badges);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		List<String> colors = Arrays.asList("#F62459", "#52B3D9", "#87D37C","#F7CA18");

		// Get the data item for this position
		Acces item = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		AccesView viewAcces; // view lookup cache stored in tag

		if (convertView == null) {
			viewAcces = new AccesView();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.adapter_item_badge, null);
			viewAcces.name = (TextView) convertView
					.findViewById(R.id.badge_name);
			viewAcces.last_usage = (TextView) convertView
					.findViewById(R.id.badge_last_usage);
			viewAcces.color = (View) convertView.findViewById(R.id.badge_color);
			convertView.setTag(viewAcces);
		} else {
			viewAcces = (AccesView) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewAcces.name.setText("Digicode " + (position+1) + " - " + item.getOwner());
		viewAcces.last_usage.setText("Utilisé le : 07/01/2015 14:25");
		viewAcces.color.setBackgroundColor(Color.parseColor(colors.get(position
				% colors.size())));

		// Return the completed view to render on screen
		return convertView;
	}
}
