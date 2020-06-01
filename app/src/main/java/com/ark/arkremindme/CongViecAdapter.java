package com.ark.arkremindme;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CongViecAdapter extends BaseAdapter {

    private TodoList context;
    private int layout;
    private List<CongViec> congViecList;
    private ArrayList<CongViec> arrayList;

    public CongViecAdapter(TodoList context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;

        this.arrayList = new ArrayList<CongViec>();
        this.arrayList.addAll(congViecList);
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return congViecList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
        CheckBox cbCongViec;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textviewTCV);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.tododelete);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.todoedit);
            holder.cbCongViec = (CheckBox) convertView.findViewById(R.id.checkBoxCV);
            convertView.setTag(holder);

            holder.cbCongViec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    CongViec congViec = (CongViec) cb.getTag();
                    congViec.setSelected(cb.isChecked());
                }
            });

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CongViec congViec = congViecList.get(position);

        holder.cbCongViec.setChecked(congViec.isSelected());
        holder.cbCongViec.setTag(congViec);

        holder.txtTen.setText(congViec.getTenCV());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCV(congViec.getTenCV(), congViec.getIdCV());

            }
        });

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        congViecList.clear();
        if (charText.length() == 0) {
            congViecList.addAll(arrayList);
        } else {
            for (CongViec congviec : arrayList) {
                if (congviec.getTenCV().toLowerCase(Locale.getDefault()).contains(charText)) {
                    congViecList.add(congviec);
                }
            }
        }
        notifyDataSetChanged();
    }
}
