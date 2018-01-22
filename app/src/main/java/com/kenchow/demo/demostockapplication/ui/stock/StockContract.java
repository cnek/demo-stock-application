package com.kenchow.demo.demostockapplication.ui.stock;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by user on 19/1/2018.
 */

public interface StockContract {

    interface Presenter{
        void bind(View view);
        void unbind();
        void retrieveStockList();
        void onItemClick(int position);
    }

    interface View{
        void updateStockList(ArrayList<StockModel> stockList);
        void showNoInternet(boolean isNoInternet);
        void onStockSelected(StockModel stock);
        void onError(boolean isError, Throwable e);
        void onLoading(boolean isLoading);
        void showGrossTotal(String grossTotal);
        void showGrossGainLoss(String grossGainLoss,boolean gain);
        Context getContext();
    }

}
