package edu.wright.crowningkings.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by csmith on 11/16/16.
 */

public class TablesListArrayAdapter<String> extends ArrayAdapter{
    List<String> tables;

    public TablesListArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        tables = objects;
    }

    public TablesListArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        tables = objects;
    }
    public void addTable(String table) {
        System.out.println("TablesListArrayAdapter.addTable");
        this.tables.add(table);
        this.notifyDataSetChanged();
        System.out.println("TablesListArrayAdapter tables=" + tables.toString());
    }
}
