package com.ark.arkremindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShowTagNoteAdapter extends BaseAdapter {

    private ShowTagActivity context;
    private int layout;
    private List<GhiChu> Note;



    public ShowTagNoteAdapter(ShowTagActivity context, int layout, List<GhiChu> Note) {
        this.context = context;
        this.layout = layout;
        this.Note = Note;
    }

    @Override
    public int getCount() {
        return Note.size();
    }

    @Override
    public Object getItem(int position) {
        return Note.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        TextView textName;
        ImageView imgAdd;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ShowTagNoteAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new ShowTagNoteAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textName = convertView.findViewById(R.id.textViewaddElement);
            holder.imgAdd = convertView.findViewById(R.id.imageAddElement);
            convertView.setTag(holder);
        } else {
            holder = (ShowTagNoteAdapter.ViewHolder) convertView.getTag();
        }

        final GhiChu ghiChu = Note.get(position);

        holder.textName.setText(ghiChu.getTenGC());
        holder.imgAdd.setImageResource(R.drawable.thungrac);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteElementTag(ghiChu.getIdGC(), 1);
            }
        });
        return convertView;
    }
}
