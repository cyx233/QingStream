package com.java.chenyuxiang.dataUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.java.chenyuxiang.R;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    LineChart chart;
    String label;
    List<Integer> xs;
    List<Integer> ys;

    public static ChartFragment newInstance(String label, ArrayList<Integer> xs, ArrayList<Integer> ys) {
        ChartFragment fragment = new ChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("label", label);
        bundle.putIntegerArrayList("xs", xs);
        bundle.putIntegerArrayList("ys", ys);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            label = b.getString("label");
            xs = b.getIntegerArrayList("xs");
            ys = b.getIntegerArrayList("ys");
        } else {
            chart = new LineChart(this.getContext());
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.fragment_chart, container, false);
        setChartData((LineChart) root.findViewById(R.id.line_chart));
        return root;
    }

    private void setChartData(LineChart chart) {
//        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);
//
        chart.getAxisRight().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
//
        // enable touch gestures
        chart.setTouchEnabled(true);

//        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
//
//        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.getLegend().setEnabled(false);

        // set x formatter
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int date = (int) value;
                return String.format("%d-%d", date / 100, date % 100 + 1);
            }
        });

        // set data
        List<Entry> entries = new ArrayList<>();
        int len = xs.size();
        chart.getXAxis().setGranularity(200);
        for (int i = 0; i < len; i++) {
            entries.add(new Entry(xs.get(i), ys.get(i)));
        }
        LineDataSet dataset = new LineDataSet(entries, label);
        dataset.setDrawCircleHole(false);
        dataset.setDrawCircles(false);
        chart.setData(new LineData(dataset));
        chart.invalidate();
    }

}

