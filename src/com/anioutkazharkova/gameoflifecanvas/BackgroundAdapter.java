package com.anioutkazharkova.gameoflifecanvas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BackgroundAdapter extends ArrayAdapter<String> {

	String[] backs;
	Context mContext;
	
	public BackgroundAdapter(Context context, int resource,String[] objects) {
		 super(context, resource, objects);
		backs=new String[]{"White","Dark"};
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
	
	layoutMulti.setVisibility(View.INVISIBLE);
	if (position==0)
		image.setBackgroundColor(mContext.getResources().getColor(R.color.smockie));
	else
		image.setBackgroundColor(mContext.getResources().getColor(R.color.black));
	return row;
	}
}

