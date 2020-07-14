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



public class GhiChuAdapter extends BaseAdapter implements Filterable {

    private Note context;
    private int layout;
    private List<GhiChu> ghiChuList;
    private List<GhiChu> noteListAll;
    CustomFilter filter;

    public GhiChuAdapter(Note context, int layout, List<GhiChu> ghiChuList) {
        this.context = context;
        this.layout = layout;
        this.ghiChuList = ghiChuList;
        this.noteListAll = ghiChuList;
    }

    @Override
    public int getCount() {
        return ghiChuList.size();
    }

    @Override
    public Object getItem(int position) {
        return ghiChuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toUpperCase();
            FilterResults results = new FilterResults();
            List<GhiChu> filters = new ArrayList<>();

            if (constraint != null && constraint.length() > 0) {
                for (int i = 0; i < noteListAll.size(); i++) {
                    if (noteListAll.get(i).getTenGC().toUpperCase().contains(constraint)) {
                        GhiChu gc = new GhiChu(context, noteListAll.get(i).getIdGC(),noteListAll.get(i).getTenGC(), noteListAll.get(i).getDate(), noteListAll.get(i).getTime(), false,"");
                        filters.add(gc);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = noteListAll.size();
                results.values = noteListAll;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ghiChuList = (List<GhiChu>) results.values;
            notifyDataSetChanged();
        }
    }


    private class ViewHolder {
        TextView txtTen, txtNgay, txtThoiGian;
        ImageView imgDelete, imgEdit;
        CheckBox cbGhiChu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textView);
            holder.txtNgay = (TextView) convertView.findViewById(R.id.textView8);
            holder.txtThoiGian = (TextView) convertView.findViewById(R.id.textView9);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.cbGhiChu = (CheckBox) convertView.findViewById(R.id.checkBoxGC);
            convertView.setTag(holder);

            holder.cbGhiChu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    GhiChu ghiChu = (GhiChu) cb.getTag();
                    ghiChu.setSelected(cb.isChecked());
                }
            });

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GhiChu ghiChu = ghiChuList.get(position);

        holder.cbGhiChu.setChecked(ghiChu.isSelected());
        holder.cbGhiChu.setTag(ghiChu);

        holder.txtTen.setText(ghiChu.getTenGC());
        holder.txtNgay.setText(ghiChu.getDate());
        holder.txtThoiGian.setText(ghiChu.getTime());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCongViec(ghiChu.getTenGC(), ghiChu.getIdGC());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print(ghiChu.getTag());
                context.DialogXoaCV(ghiChu.getTenGC(), ghiChu.getIdGC());

            }
        });

        return convertView;
    }

}
