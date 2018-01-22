package com.kenchow.demo.demostockapplication.ui.stock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kenchow.demo.demostockapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 19/1/2018.
 */

public class StockListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvCode)
    TextView tvCode;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvTotal)
    TextView tvTotal;

    @BindView(R.id.tvDiff)
    TextView tvDiff;

    @BindView(R.id.tvDiffPercent)
    TextView tvDiffPercent;

    interface OnItemClickListener{
        void onClick(int position);
    }

    public StockListViewHolder(final View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(getAdapterPosition());
            }
        });
    }
}
