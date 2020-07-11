package com.ark.arkremindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AddElementAdapter extends BaseAdapter {

    private AddElementTagActivity context;
    private int layout;
    private List<GhiChu> Note;



    public AddElementAdapter(AddElementTagActivity context, int layout, List<GhiChu> Note) {
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textName = convertView.findViewById(R.id.textViewaddElement);
            holder.imgAdd = convertView.findViewById(R.id.imageAddElement);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GhiChu ghiChu = Note.get(position);

           holder.textName.setText(ghiChu.getTenGC());
           holder.imgAdd.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   context.addElementTag(ghiChu.getIdGC(), 1);
               }
           });
           return convertView;
    }
}
