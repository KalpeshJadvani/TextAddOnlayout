package com.example.kalpesh.textaddview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by omsai on 9/27/2016.
 */
public class Contenor_font extends BaseAdapter {
    private static LayoutInflater inflater;
    Context fontContext;
    ArrayList<String> arr;
    ArrayList<Typeface> FontPath;
    private LayoutInflater mInflater;

    public Contenor_font(Context context, ArrayList<String> object,ArrayList<Typeface> PathOffont) {

        this.fontContext = context;
        arr = object;
        FontPath = PathOffont;
        mInflater = LayoutInflater.from(fontContext);
        inflater = (LayoutInflater) fontContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi = view;

        if (view == null) {
            vi = inflater.inflate(R.layout.custom_contact_details, null);
        }

         final TextView cname = (TextView) vi.findViewById(R.id.fontname);
         cname.setText(arr.get(i));
         Typeface typeface= FontPath.get(i);
         cname.setTypeface(typeface);
        return vi;
    }
}
