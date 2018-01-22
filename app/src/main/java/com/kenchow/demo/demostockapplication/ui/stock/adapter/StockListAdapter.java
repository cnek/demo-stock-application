package com.kenchow.demo.demostockapplication.ui.stock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kenchow.demo.demostockapplication.R;
import com.kenchow.demo.demostockapplication.ui.stock.StockModel;
import com.kenchow.demo.demostockapplication.ui.stock.StockPresenterImpl;

import java.util.ArrayList;

/**
 * Created by user on 19/1/2018.
 */

public class StockListAdapter extends RecyclerView.Adapter<StockListViewHolder> implements StockListViewHolder.OnItemClickListener {

    private ArrayList<StockModel> stockModels = new ArrayList<>();
    private StockPresenterImpl stockPresenter;

    private final static int RAISE = 1;
    private final static int FALL = 2;

    public StockListAdapter(StockPresenterImpl stockPresenter) {
        this.stockPresenter = stockPresenter;
    }

    @Override
    public void onClick(int position) {
        stockPresenter.onItemClick(position);
    }

    @Override
    public StockListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == RAISE) {
            return new StockListViewHolder(layoutInflater.inflate(R.layout.item_trade, parent, false),this);
        } else {
            return new StockListViewHolder(layoutInflater.inflate(R.layout.item_trade_loss, parent, false),this);
        }
    }

    @Override
    public void onBindViewHolder(StockListViewHolder holder, int position) {
        StockModel stockModel = stockModels.get(position);
        holder.tvCode.setText(stockModel.getCode());
        holder.tvName.setText(stockModel.getName());
        holder.tvTotal.setText(stockModel.getTotal());
        holder.tvDiff.setText(stockModel.getDiff());
        holder.tvDiffPercent.setText(stockModel.getDiffPercent());
    }

    @Override
    public int getItemCount() {
        return stockModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return stockModels.get(position).isRaise() ? RAISE : FALL;
    }

    public void setStockModels(ArrayList<StockModel> stockModels) {
        this.stockModels.clear();
        this.stockModels.addAll(stockModels);
        notifyDataSetChanged();
    }
}
