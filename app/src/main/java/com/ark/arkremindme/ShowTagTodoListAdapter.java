package com.ark.arkremindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShowTagTodoListAdapter extends BaseAdapter {

    private ShowTagActivity context;
    private int layout;
    private List<CongViec> Todolist;



    public ShowTagTodoListAdapter(ShowTagActivity context, int layout, List<CongViec> Todolist) {
        this.context = context;
        this.layout = layout;
        this.Todolist = Todolist;
    }

    @Override
    public int getCount() {
        return Todolist.size();
    }

    @Override
    public Object getItem(int position) {
        return Todolist.get(position);
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

        final CongViec congViec = Todolist.get(position);

        holder.textName.setText(congViec.getTenCV());
        holder.imgAdd.setImageResource(R.drawable.thungrac);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteElementTag(congViec.getIdCV(),2);
            }
        });
        return convertView;
    }
}
