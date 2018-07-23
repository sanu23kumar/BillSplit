package com.example.sanukumar.billsplit;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class statisticsSummary extends Fragment {

    PieChart pieChart;

    public statisticsSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_statistics_summary, container, false);


        pieChart = v.findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 5, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.parseColor("#647878"));
        pieChart.setHoleRadius(30f);

        pieChart.setTransparentCircleRadius(40f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(3400f, "Food"));
        yValues.add(new PieEntry(2300f, "Accessories"));
        yValues.add(new PieEntry(1400f, "Entertainment"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.parseColor("#afafaf"), Color.parseColor("#595959"), Color.parseColor("#383838"));

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.WHITE);

        pieChart.setData(pieData);

        pieChart.animateY(1000, Easing.EasingOption.Linear);

        return v;

    }

}
