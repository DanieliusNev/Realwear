package com.example.realwearuuuhhhhhh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private String[][] mData;

    public TableAdapter(String[][] data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String[] row = mData[position];
        holder.column1.setText(row[0]);
        holder.column2.setText(row[1]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView column1;
        TextView column2;
        ViewHolder(View view) {
            super(view);
            column1 = (TextView) view.findViewById(R.id.column1);
            column2 = (TextView) view.findViewById(R.id.column2);
        }
    }
}
