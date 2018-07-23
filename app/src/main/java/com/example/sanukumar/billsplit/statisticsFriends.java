package com.example.sanukumar.billsplit;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class statisticsFriends extends Fragment {

    HorizontalBarChart barChart;

    public statisticsFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics_friends, container, false);

        barChart = v.findViewById(R.id.horizontalBarChart);

        initBarChart();

        barChart.setData(initBarChartData());
        barChart.setVisibleXRangeMaximum(4);
        barChart.invalidate();

        return v;
    }

    private void initBarChart() {
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);
        barChart.setDrawBorders(false);
        barChart.setDrawMarkers(false);
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);
        barChart.setHorizontalScrollBarEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.setElevation(14f);     //Does nothing as of now, research over it later

        barChart.getXAxis().setYOffset(-4f);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getXAxis().setTextSize(14f);

        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setScaleYEnabled(false);

        barChart.getXAxis().setGranularity(1f); //Extremely important step otherwise the labels will be float values

        final String[] days = new String[]{" ", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
//        barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return days[(int)value];
//            }
//
//        });

        barChart.animateY(1000, Easing.EasingOption.Linear);
        barChart.invalidate();
    }

    private BarData initBarChartData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, 40f, "Monday"));   //for stacked Bar chart, new BarEntry(1, new float[] {20f, 30f, 40f})
        barEntries.add(new BarEntry(2, -30f, "Tuesday"));
        barEntries.add(new BarEntry(3, 20f, "Wednesday"));
        barEntries.add(new BarEntry(4, -10f, "Thursday"));
        barEntries.add(new BarEntry(5, 40f, "Friday"));
        barEntries.add(new BarEntry(6, -30f, "Tuesday"));
        barEntries.add(new BarEntry(7, 20f, "Wednesday"));
        barEntries.add(new BarEntry(8, -10f, "Thursday"));
        barEntries.add(new BarEntry(9, 40f, "Friday"));

        BarDataSet barDataSet = new BarDataSet(barEntries, "DataSet1");
        barDataSet.setStackLabels(new String[]{"Food", "Accessories", "Entertainment"});
        barDataSet.setColors(Color.parseColor("#afafaf"), Color.parseColor("#595959"), Color.parseColor("#383838"));

        barDataSet.setValueTextSize(14f);


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);

        return barData;
    }

}
