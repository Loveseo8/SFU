package com.alicearnautova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    List<String> elements = new ArrayList<>();
    LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, List<String> elements){

        this.context = context;
        this.elements = elements;

    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textElement;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        textElement = (TextView) itemView.findViewById(R.id.element_title);
        textElement.setText(elements.get(position));

        return itemView;
    }
}
