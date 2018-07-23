package com.example.sanukumar.billsplit;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class statisticsFragment extends Fragment {

    //PieChart pieChart;
    BarChart barChart;

    public statisticsFragment() {

        //To-Do: Get data from daily statistics table and pass it as internal data here

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_statistics, container, false);

//        pieChart = v.findViewById(R.id.pieChart);
//
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5, 5, 5, 5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f);
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleColor(Color.WHITE);
//
//        pieChart.setTransparentCircleRadius(61f);
//
//        ArrayList<PieEntry> yValues = new ArrayList<>();
//
//        yValues.add(new PieEntry(34f, "China"));
//        yValues.add(new PieEntry(23f, "USA"));
//        yValues.add(new PieEntry(14f, "UK"));
//        yValues.add(new PieEntry(35f, "India"));
//        yValues.add(new PieEntry(70f, "Russia"));
//
//        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//
//        PieData pieData = new PieData(dataSet);
//        pieData.setValueTextSize(10f);
//        pieData.setValueTextColor(Color.WHITE);
//
//        pieChart.setData(pieData);
//
//        pieChart.animateY(1000, Easing.EasingOption.Linear);

        barChart = v.findViewById(R.id.barChart);

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

        barChart.setElevation(14f);     //Does nothing as of now, research over it later

        barChart.getXAxis().setYOffset(-4f);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setTextSize(14f);

        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setScaleYEnabled(false);

        barChart.getLegend().setEnabled(false);

        barChart.getXAxis().setGranularity(1f); //Extremely important step otherwise the labels will be float values

        final String[] days = new String[]{" ", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return days[(int)value];
            }

        });

        barChart.animateY(1000, Easing.EasingOption.Linear);
        barChart.invalidate();
    }

    private BarData initBarChartData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, new float[]{40f, 10f, 5f}, "Monday"));   //for stacked Bar chart, new BarEntry(1, new float[] {20f, 30f, 40f})
        barEntries.add(new BarEntry(2, new float[]{24f, 14f, 30f}, "Tuesday"));
        barEntries.add(new BarEntry(3, new float[]{30f, 10f, 35f}, "Wednesday"));
        barEntries.add(new BarEntry(4, new float[]{30f, 10f, 15f}, "Thursday"));
        barEntries.add(new BarEntry(5, new float[]{40f, 10f, 5f}, "Friday"));
        barEntries.add(new BarEntry(6, new float[]{40f, 10f, 5f}, "Monday"));
        barEntries.add(new BarEntry(7, new float[]{40f, 10f, 5f}, "Tuesday"));
        barEntries.add(new BarEntry(8, new float[]{40f, 10f, 5f}, "Wednesday"));
        barEntries.add(new BarEntry(9, new float[]{40f, 10f, 5f}, "Thursday"));

        BarDataSet barDataSet = new BarDataSet(barEntries, "DataSet1");
        barDataSet.setStackLabels(new String[]{"Food", "Accessories", "Entertainment"});
        barDataSet.setColors(Color.parseColor("#afafaf"), Color.parseColor("#595959"), Color.parseColor("#383838"));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.6f);

        return barData;
    }

}
