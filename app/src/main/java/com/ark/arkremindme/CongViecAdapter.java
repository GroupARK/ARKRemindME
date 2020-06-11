package com.ark.arkremindme;

import android.content.Context;
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

public class CongViecAdapter extends BaseAdapter implements Filterable {

    private TodoList context;
    private int layout;
    private List<CongViec> congViecList;
    private CustomFilterToDoList filter;
    private List<CongViec> filterList;


    public CongViecAdapter(TodoList context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
        this.filterList = congViecList;

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

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilterToDoList();
        }
        return filter;
    }

    class CustomFilterToDoList extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toUpperCase();
            FilterResults results = new FilterResults();
            ArrayList<CongViec> filters = new ArrayList<>();

            if (constraint != null && constraint.length() > 0) {

                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getTenCV().toUpperCase().contains(constraint)) {
                        CongViec cv = new CongViec(context ,filterList.get(i).getIdCV(), filterList.get(i).getTenCV(), filterList.get(i).getDate(), filterList.get(i).getTime(), false);
                        filters.add(cv);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                for (int i = 0; i < filterList.size(); i++) {
                    CongViec cv = new CongViec(context ,filterList.get(i).getIdCV(), filterList.get(i).getTenCV(), filterList.get(i).getDate(), filterList.get(i).getTime(), false);
                    filters.add(cv);
                }
                results.count = filterList.size();
                System.out.print(results.count);
                results.values = filters;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            congViecList = (List<CongViec>) results.values;
            notifyDataSetChanged();

        }
    }

    private class ViewHolder {
        TextView txtTen, txtNgay, txtThoiGian;
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
            holder.txtNgay = (TextView) convertView.findViewById(R.id.textViewNgayCV);
            holder.txtThoiGian = (TextView) convertView.findViewById(R.id.textViewThoiGianCV);
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
        holder.txtNgay.setText(congViec.getDate());
        holder.txtThoiGian.setText(congViec.getTime());


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


}
