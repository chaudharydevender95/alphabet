package com.example.wolfknight.alphabet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lenovo on 5/17/2017.
 */

public class customList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> web;
    private final File directory;


    public customList(Activity context,
                      ArrayList<String> web, File reference) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.directory = reference;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.userName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.userImage);

        txtTitle.setText(web.get(position));
        select_Child.dataAccessClass.loadImageFromStorage(imageView, directory.getAbsolutePath(),web.get(position) );

        return rowView;
    }
}
