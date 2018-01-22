package com.kenchow.demo.demostockapplication.ui.stock;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kenchow.demo.demostockapplication.ui.chart.ChartFragment;
import com.kenchow.demo.demostockapplication.ui.stock.adapter.StockListAdapter;
import com.kenchow.demo.demostockapplication.view.DividerItemDecoration;
import com.kenchow.demo.demostockapplication.activity.MainActivity;
import com.kenchow.demo.demostockapplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StockFragment extends Fragment implements StockContract.View {

    @BindView(R.id.tvTotal)
    TextView tvTotal;

    @BindView(R.id.tvGainLoss)
    TextView tvGainLoss;

    @BindView(R.id.tvGainLossLabel)
    TextView tvGainLossLabel;

    @BindView(R.id.listTrade)
    RecyclerView listTrade;

    @BindView(R.id.retry_container)
    View retryContainer;

    @BindView(R.id.loading)
    ProgressBar loading;

    @OnClick(R.id.retry)
    public void onClick(View view) {
        stockPresenter.retrieveStockList();
    }

    StockPresenterImpl stockPresenter = new StockPresenterImpl();

    StockListAdapter stockListAdapter;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;

    public static StockFragment getInstance(){
        StockFragment stockFragment = new StockFragment();
        return stockFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stockPresenter.bind(this);

        stockListAdapter = new StockListAdapter(stockPresenter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        dividerItemDecoration = new DividerItemDecoration(getResources().getDrawable(R.drawable.item_stock_decorator),
                true,true);
        listTrade.setLayoutManager(linearLayoutManager);
        listTrade.setAdapter(stockListAdapter);
        listTrade.addItemDecoration(dividerItemDecoration);

        stockPresenter.retrieveStockList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stockPresenter.unbind();
    }

    @Override
    public void updateStockList(ArrayList<StockModel> stockList) {
        tvGainLossLabel.setVisibility(View.VISIBLE);
        stockListAdapter.setStockModels(stockList);
    }

    @Override
    public void showNoInternet(boolean isNoInternet) {
        retryContainer.setVisibility(isNoInternet ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStockSelected(StockModel stockModel) {
        if(getActivity() instanceof MainActivity){
            ChartFragment chartFragment = ChartFragment.getInstance(stockModel);
            ((MainActivity) getActivity()).goFragment(chartFragment,stockModel.getName(),stockModel.getCode()+" - NASDAQ");
        }
    }

    @Override
    public void onError(boolean isError, Throwable e) {
        if(e!=null)
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoading(boolean isLoading) {
        loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showGrossTotal(String grossTotal) {
        tvTotal.setText(grossTotal);
    }

    @Override
    public void showGrossGainLoss(String grossGainLoss,boolean gain) {
        tvGainLoss.setText(grossGainLoss);
        tvGainLoss.setTextColor(getResources().getColor(gain ? R.color.gain : R.color.loss));
    }

}
