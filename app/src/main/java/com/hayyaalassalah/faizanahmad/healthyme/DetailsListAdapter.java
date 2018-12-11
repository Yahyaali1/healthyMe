package com.hayyaalassalah.faizanahmad.healthyme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Faizan Ahmad on 11/27/2016.
 */
public class DetailsListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> details;
    public DetailsListAdapter(Context context,int resource,ArrayList<String> details){
        super(context,resource,details);
        this.details = details;
    }

    public View getView(int position, View convertView,ViewGroup parent) {
        String detail = getItem(position);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detail_list_layout, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.detailItem);
        text.setText(detail);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
        if(detail.contains("running"))
        {
            image.setImageResource(R.drawable.runningicon);
        }
        else if(detail.contains("walking"))
        {
            image.setImageResource(R.drawable.walking);
        }
        return convertView;
    }
}
