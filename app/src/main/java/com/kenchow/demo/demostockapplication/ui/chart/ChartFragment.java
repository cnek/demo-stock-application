package com.kenchow.demo.demostockapplication.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kenchow.demo.demostockapplication.R;
import com.kenchow.demo.demostockapplication.ui.stock.StockModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac on 20/1/2018.
 */

public class ChartFragment extends Fragment {

    @BindView(R.id.chart)
    LineChart mChart;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvDiff)
    TextView tvDiff;

    @BindView(R.id.tvDiffPercent)
    TextView tvDiffPercent;

    StockModel stockModel;

    final String OLD_FORMAT = "yyyy-MM-dd";
    final String NEW_FORMAT = "d/MM";

    public static ChartFragment getInstance(StockModel stockModel){
        ChartFragment chartFragment = new ChartFragment();
        chartFragment.setStockModel(stockModel);
        return chartFragment;
    }

    public void setStockModel(StockModel stockModel) {
        this.stockModel = stockModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
        drawChart();
    }

    private void setView() {
        mChart.setNoDataText("Creating Chart");
        tvName.setText(stockModel.getIndex());
        tvDiff.setText(stockModel.getDiffIndex());
        tvDiff.setTextColor(getResources().getColor(stockModel.isRaise() ? R.color.gain : R.color.loss));
        tvDiffPercent.setText(stockModel.getDiffPercent());
        tvDiffPercent.setTextColor(getResources().getColor(stockModel.isRaise() ? R.color.gain : R.color.loss));
    }

    private void drawChart() {

        SimpleDateFormat sdf;;

        final ArrayList<String> xValues = new ArrayList<String>();
        final ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        final ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        for(int i=0;i<stockModel.getStockPojo().dataset.data.size();i++){
            try{
                Float index = Float.parseFloat(stockModel.getStockPojo().dataset.data.get(i).get(1));
                yVals1.add(0,new Entry(stockModel.getStockPojo().dataset.data.size()-1-i, index));

                String oldDate = stockModel.getStockPojo().dataset.data.get(i).get(0);
                sdf = new SimpleDateFormat(OLD_FORMAT);
                Date d = sdf.parse(oldDate);
                sdf.applyPattern(NEW_FORMAT);
                xValues.add(0,sdf.format(d));

            }catch (Exception e){
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        mChart.setBackgroundColor(Color.WHITE);
        mChart.setGridBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(true);
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDrawBorders(false);
        mChart.setPinchZoom(true);
        mChart.setViewPortOffsets(0f,0f,0f,0f);

        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value==0 || value>=xValues.size()-1)
                    return "";
                return xValues.get((int) value % xValues.size());
            }
        });

        YAxis leftAxis = mChart.getAxisRight();
        leftAxis.setLabelCount(3, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10f,4f,0);
        leftAxis.setGridColor(Color.GRAY);
        leftAxis.setAxisLineColor(Color.GRAY);

        mChart.getAxisLeft().setEnabled(false);

        LineDataSet set1 = new LineDataSet(yVals1, "DataSet 1");

        set1.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set1.setColor(getResources().getColor(R.color.chart));
        set1.setDrawCircles(false);
        set1.setLineWidth(0.5f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(50);
        set1.setDrawFilled(true);
        set1.setFillColor(getResources().getColor(R.color.chart_fill));
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        dataSets.add(set1);

        LineData datab = new LineData(dataSets);
        datab.setDrawValues(false);

        mChart.setData(datab);

        mChart.setViewPortOffsets(0f, 15f, 0f, 15f);
        float xMax = mChart.getData().getDataSetByIndex(0).getXMax();
        float xMin = 0;
        xAxis.setAxisMaximum(xMax);
        xAxis.setAxisMinimum(xMin);
    }
}
