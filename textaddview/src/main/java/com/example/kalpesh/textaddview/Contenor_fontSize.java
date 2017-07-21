package com.example.kalpesh.textaddview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by omsai on 9/27/2016.
 */
public class Contenor_fontSize extends BaseAdapter {
    private static LayoutInflater inflater;
    Context fontContext;

    int[] arr;


    private LayoutInflater mInflater;

    public Contenor_fontSize(Context context, int[] arry) {

        this.fontContext = context;
        arr=arry;
        mInflater = LayoutInflater.from(fontContext);
        inflater = (LayoutInflater) fontContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int position) {
        return arr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (view == null) {
            vi = inflater.inflate(R.layout.font_size_layout, null);
        }
         final TextView cname = (TextView) vi.findViewById(R.id.fontsize);

         String text= Integer.toString(arr[i]);
         cname.setText(text);
         return vi;
    }
}
