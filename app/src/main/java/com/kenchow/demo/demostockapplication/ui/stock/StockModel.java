package com.kenchow.demo.demostockapplication.ui.stock;

import com.kenchow.demo.demostockapplication.pojo.StockPojo;

import java.util.List;

/**
 * Created by user on 19/1/2018.
 */

public class StockModel {

    String code;
    String name;
    String total;
    String diff;
    String diffPercent;
    boolean isRaise;
    String diffIndex;
    String index;
    StockPojo stockPojo;

    public StockModel(String code, String name, String total, String diff, String diffPercent, boolean isRaise, String diffIndex, String index, StockPojo stockPojo) {
        this.code = code;
        this.name = name;
        this.total = total;
        this.diff = diff;
        this.diffPercent = diffPercent;
        this.isRaise = isRaise;
        this.diffIndex = diffIndex;
        this.index = index;
        this.stockPojo = stockPojo;
    }

    @Override
    public String toString() {
        return "StockModel{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", total='" + total + '\'' +
                ", diff='" + diff + '\'' +
                ", diffPercent='" + diffPercent + '\'' +
                ", isRaise=" + isRaise +
                ", diffIndex='" + diffIndex + '\'' +
                ", index='" + index + '\'' +
                ", stockPojo=" + stockPojo +
                '}';
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getTotal() {
        return total;
    }

    public String getDiff() {
        return diff;
    }

    public String getDiffPercent() {
        return diffPercent;
    }

    public boolean isRaise() {
        return isRaise;
    }

    public String getDiffIndex() {
        return diffIndex;
    }

    public String getIndex() {
        return index;
    }

    public StockPojo getStockPojo() {
        return stockPojo;
    }
}
