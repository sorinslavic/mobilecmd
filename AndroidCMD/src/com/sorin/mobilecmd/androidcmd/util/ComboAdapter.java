package com.sorin.mobilecmd.androidcmd.util;

import java.util.List;

import com.sorin.mobilecmd.androidcmd.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComboAdapter<T extends NameValue> extends ArrayAdapter<T> {
	private final int resource;
	private final ValueFormatter formatter = new SimpleValueFormatter();

	public ComboAdapter(Context context, int resource, List<T> items) {
		super(context, resource, items);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout detailView;

		if (convertView == null) {
			detailView = new LinearLayout(getContext());
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			vi.inflate(resource, detailView, true);
		} else {
			detailView = (LinearLayout) convertView;
		}

		if (position % 2 > 0)
			detailView.setBackgroundColor(Color.WHITE);
		else
			detailView.setBackgroundColor(Color.argb(0xff, 240, 240, 240));

		NameValue detail = getItem(position);
		
		TextView detailValueView = (TextView) detailView.findViewById(R.id.detailValue);
		detailValueView.setText(
				Html.fromHtml(formatter.format(detail.getName())),
				TextView.BufferType.SPANNABLE);

		return detailView;
	}
}