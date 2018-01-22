package com.kenchow.demo.demostockapplication.ui.stock;

import android.util.Log;

import com.kenchow.demo.demostockapplication.R;
import com.kenchow.demo.demostockapplication.api.ApiService;
import com.kenchow.demo.demostockapplication.api.RetrofitHelper;
import com.kenchow.demo.demostockapplication.pojo.StockPojo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by user on 19/1/2018.
 */

public class StockPresenterImpl implements StockContract.Presenter {

    private StockContract.View view;
    private ArrayList<StockModel> stockModels = new ArrayList<>();

    private static HashMap<String,Integer> portfolioCache;

    @Override
    public void bind(StockContract.View view) {
        this.view = view;
        addDummyPortfolio();
    }

    @Override
    public void unbind() {
        view = null;
    }

    @Override
    public void retrieveStockList() {

        if(!RetrofitHelper.getInstance().isOnline(view.getContext())){
            view.showNoInternet(true);
        }else{
            view.showNoInternet(false);
            view.onLoading(true);

            stockModels.clear();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            final String formatted = format1.format(cal.getTime());

            Observable<List<StockPojo>> observable = Observable.from(portfolioCache.keySet())
                    .flatMap(new Func1<String, Observable<StockPojo>>() {
                        @Override
                        public Observable<StockPojo> call(String code) {
                            String apiKey = view.getContext().getString(R.string.api_key);
                            return RetrofitHelper.retrofit.create(ApiService.class).getStockPrice(code,
                                    apiKey,
                                    formatted);
                        }
                    })
                    .toList();

            Observer<List<StockPojo>> observer = new Observer<List<StockPojo>>() {
                @Override
                public void onCompleted() {
                    if(view==null) return;
                    view.onLoading(false);
                    view.onError(false,null);
                }

                @Override
                public void onError(Throwable e) {
                    if(view==null) return;
                    view.onLoading(false);
                    view.onError(true,e);
                }

                @Override
                public void onNext(List<StockPojo> o) {
                    if(view==null) return;

                    BigDecimal grossPrice = BigDecimal.valueOf(0);
                    BigDecimal grossOldPrice = BigDecimal.valueOf(0);

                    for (StockPojo item : o) {

                        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(item.dataset.data.get(0).get(1)));
                        BigDecimal oldPrice = BigDecimal.valueOf(Double.parseDouble(item.dataset.data.get(1).get(1)));
                        Integer shares = portfolioCache.get(item.dataset.datasetCode);

                        String totalStr = "$" + CalculationUtils.getTotalAndRound(price,shares,2);

                        boolean isLargerOrEqual = CalculationUtils.isLargerOrEqual(price,oldPrice);
                        String diffStr = isLargerOrEqual ?
                                "+" + CalculationUtils.getDiffTotalAndRound(price,oldPrice,shares,2) :
                                CalculationUtils.getDiffTotalAndRound(price,oldPrice,shares,2).toString();

                        BigDecimal diffPercent = CalculationUtils.getPercentageDiffAndRound(price,oldPrice,2);
                        String diffPercentStr = "(" + diffPercent + "%)";

                        grossPrice = grossPrice.add(CalculationUtils.getTotal(price,shares));
                        grossOldPrice = grossOldPrice.add(CalculationUtils.getTotal(oldPrice,shares));

                        String diff = price.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

                        stockModels.add(
                                new StockModel(item.dataset.datasetCode,
                                        item.dataset.name.split(" Adjusted Stock Prices")[0],
                                        totalStr,
                                        diffStr,
                                        diffPercentStr,
                                        isLargerOrEqual,
                                        isLargerOrEqual? "+"+CalculationUtils.getDiffTotalAndRound(price,oldPrice,1,2):
                                                CalculationUtils.getDiffTotalAndRound(price,oldPrice,1,2).toString(),
                                        "$" + diff,
                                        item)
                        );

                    }

                    view.updateStockList(stockModels);
                    view.showGrossTotal("$" + grossPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                    String diff = grossPrice.subtract(grossOldPrice).setScale(2, BigDecimal.ROUND_HALF_UP).toString();

                    boolean isLargerOrEqual = CalculationUtils.isLargerOrEqual(grossPrice,grossOldPrice);
                    BigDecimal diffPercent = CalculationUtils.getPercentageDiffAndRound(grossPrice,grossOldPrice,2);
                    String diffPercentStr = isLargerOrEqual ?
                            "+" + diff + " (" + diffPercent + "%)" :
                            diff + " (" + diffPercent + "%)";

                    view.showGrossGainLoss(diffPercentStr, isLargerOrEqual);

                }
            };

            RetrofitHelper.getInstance().subscribe(observable, observer);
        }

    }

    @Override
    public void onItemClick(int position) {
        if(view==null)
            return;

        view.onStockSelected(stockModels.get(position));
    }

    private void addDummyPortfolio() {
        portfolioCache = new LinkedHashMap<>();
        portfolioCache.put("AAPL",100);
        portfolioCache.put("AAL",100);
        portfolioCache.put("ABAC_UADJ",100);
        portfolioCache.put("ACET",100);
        portfolioCache.put("AAXJ",100);

    }

}
