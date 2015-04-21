package com.anioutkazharkova.lifewallpaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ThemeAdapter extends ArrayAdapter<String> {

	String[] themes;
	Context mContext;
	
	public ThemeAdapter(Context context, int resource,String[] objects) {
		 super(context, resource, objects);
		themes=objects;
		mContext=context;
	}
	@Override
	public View getDropDownView(int position, View convertView,
	    ViewGroup parent) {
	    return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

	LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
	
	View row=inflater.inflate(R.layout.color_item, null);
	
	ImageView image=(ImageView)row.findViewById(R.id.imColor);
	LinearLayout layoutMulti=(LinearLayout)row.findViewById(R.id.layoutMulticolor);
	Theme theme=new Theme(position);
	
	if (position<4)
	{
		layoutMulti.setVisibility(View.INVISIBLE);
		image.setVisibility(View.VISIBLE);
		image.setBackgroundColor(mContext.getResources().getColor(theme.getSchemeColor()));
	}
	else
	{
		layoutMulti.setVisibility(View.VISIBLE);
		image.setVisibility(View.INVISIBLE);
	}
	return row;
	}
}
