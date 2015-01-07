package com.suricate.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suricate.R;

public class BadgesAdapter extends ArrayAdapter<String> {

	private static class Badge{
		private View color;
		private TextView name;
		private TextView last_usage;
	}
	public BadgesAdapter(Context context, List<String> badges) {
		super(context, R.layout.adapter_item_badge, badges);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		List<String> colors = Arrays.asList("#F62459","#52B3D9","#87D37C","#F7CA18");
		
		// Get the data item for this position
		String identifiant = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		Badge viewBadge; // view lookup cache stored in tag
		if (convertView == null) {
			viewBadge = new Badge();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.adapter_item_badge, null);
			viewBadge.name = (TextView) convertView.findViewById(R.id.badge_name);
			viewBadge.last_usage = (TextView) convertView.findViewById(R.id.badge_last_usage);
			viewBadge.color = (View) convertView.findViewById(R.id.badge_color);
			convertView.setTag(viewBadge);
		} else {
			viewBadge = (Badge) convertView.getTag();
		}
		// Populate the data into the template view us<	ing the data object
		viewBadge.name.setText(identifiant);
		viewBadge.last_usage.setText("Utilisé le : 07/01/2015 14:25");
		viewBadge.color.setBackgroundColor(Color.parseColor(colors.get(position)));

		// Return the completed view to render on screen
		return convertView;
	}
}
