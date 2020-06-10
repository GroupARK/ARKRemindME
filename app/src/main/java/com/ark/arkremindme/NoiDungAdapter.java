package com.ark.arkremindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class NoiDungAdapter extends BaseAdapter {

    private Noidungvieclam context;
    private int layout;
    private List<NoiDung> noiDungList;

    public NoiDungAdapter(Noidungvieclam context, int layout, List<NoiDung> noiDungList) {
        this.context = context;
        this.layout = layout;
        this.noiDungList = noiDungList;
    }

    @Override
    public int getCount() {
        return noiDungList.size();
    }

    @Override
    public Object getItem(int position) {
        return noiDungList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHoler {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
        CheckBox checkBox;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler holder;

        if (convertView == null) {
            holder = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = convertView.findViewById(R.id.textviewND);
            holder.imgDelete = convertView.findViewById(R.id.thungrac);
            holder.imgEdit = convertView.findViewById(R.id.butchi);
            holder.checkBox = convertView.findViewById(R.id.checkboxND);
            convertView.setTag(holder);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    NoiDung noiDung = (NoiDung) cb.getTag();
                    noiDung.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHoler) convertView.getTag();
        }

        final NoiDung noiDung = noiDungList.get(position);

        holder.checkBox.setChecked(noiDung.isSelected());
        holder.checkBox.setTag(noiDung);

        holder.txtTen.setText(noiDung.getTenND());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCV(noiDung.getTenND(), noiDung.getId());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCongViec(noiDung.getTenND(), noiDung.getId());
            }
        });


        return convertView;
    }
}
