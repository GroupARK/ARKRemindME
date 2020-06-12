package com.ark.arkremindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class ViecLamAdapter extends BaseAdapter implements Filterable {

    private Task context;
    private int layout;
    private List<ViecLam> viecLamList;
    private List<ViecLam> viecLamAll;
    CustomFilter filter;

    public ViecLamAdapter(Task context, int layout, List<ViecLam> viecLamList) {
        this.context = context;
        this.layout = layout;
        this.viecLamList = viecLamList;
        this.viecLamAll = viecLamList;
    }

    @Override
    public int getCount() {
        return viecLamList.size();
    }

    @Override
    public Object getItem(int position) {
        return viecLamList.get(position);
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
            List<ViecLam> filters = new ArrayList<>();
            if (constraint != null && constraint.length() > 0) {
                for (int i = 0; i < viecLamAll.size(); i++) {
                    if (viecLamAll.get(i).getTenVL().contains(constraint)) {
                        ViecLam vl = new ViecLam(context, viecLamAll.get(i).getTenVL(), viecLamAll.get(i).getIdVL(), false) ;
                        filters.add(vl);
                    }
                }
                results.count = filters.size();
                Toast.makeText(context, "sss" + results.count, Toast.LENGTH_SHORT).show();
                results.values = filters;
            } else {
                results.count = viecLamAll.size();
                results.values = viecLamAll;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            viecLamList = (List<ViecLam>) results.values;
            notifyDataSetChanged();
        }
    }

    private class ViewHoler {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
        CheckBox cbViecLam;
        ImageButton imgButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler holder;

        if (convertView == null) {
            holder = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = (TextView) convertView.findViewById(R.id.textviewVL);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.deleteVL);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.editVL);
            holder.imgButton = (ImageButton) convertView.findViewById(R.id.imageButtonVL);
            holder.cbViecLam = (CheckBox) convertView.findViewById(R.id.checkBoxVL);
            convertView.setTag(holder);

            holder.cbViecLam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    ViecLam viecLam = (ViecLam) cb.getTag();
                    viecLam.setSelected(cb.isChecked());
                }
            });

        } else {
            holder = (ViewHoler) convertView.getTag();
        }

        final ViecLam viecLam = viecLamList.get(position);

        holder.cbViecLam.setChecked(viecLam.isSelected());
        holder.cbViecLam.setTag(viecLam);

        holder.txtTen.setText(viecLam.getTenVL());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCV(viecLam.getTenVL(), viecLam.getIdVL());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCongViec(viecLam.getTenVL(), viecLam.getIdVL());
            }
        });

        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.editContentVL(position);
            }
        });
        return convertView;
    }
}
