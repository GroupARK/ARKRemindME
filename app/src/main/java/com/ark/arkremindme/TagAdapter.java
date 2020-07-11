package com.ark.arkremindme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends BaseAdapter{

    private TagActivity context;
    private int layout;
    private List<Tag> TagList;

    public TagAdapter(TagActivity context, int layout, List<Tag> TagList) {
        this.context = context;
        this.layout = layout;
        this.TagList = TagList;
    }

    @Override
    public int getCount() {
        return TagList.size();
    }

    @Override
    public Object getItem(int position) {
        return TagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit, imageShow;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new TagAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textviewTag);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.thungracTag);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.butchitag);
            holder.imageShow = convertView.findViewById(R.id.imageShowTag);
            convertView.setTag(holder);

        } else {
            holder = (TagAdapter.ViewHolder) convertView.getTag();
        }

        final Tag tag = TagList.get(position);

        holder.txtTen.setText(tag.getContent());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCongViec(tag.getContent(), position);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCV(tag.getContent(), tag.getId());
            }
        });
        holder.imageShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.chonTag(tag.getId(),tag.getContent());
            }
        });

        return convertView;
    }

}
