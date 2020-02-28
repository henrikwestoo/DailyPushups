package com.example.dailypushups;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_plot);

        ArrayList<String> data = new ArrayList<>();
        EntryDatabase db = EntryDatabase.getDbInstance(this);
        List<Entry> entries =  db.entryDao().getAll();

        ArrayList <String> dates = new ArrayList<String>();
        ArrayList <Number> pushups = new ArrayList<Number>();

        for(Entry entry : entries){
            dates.add(entry.date);
            pushups.add(entry.pushups);
        }

        final String[] domainLabels = dates.toArray(new String[0]);
        //final String[] domainLabels = {"2020-02-28", "2020-02-29", "2020-02-30", "2020-02-31"};
        Number[] series1Numbers = pushups.toArray(new Number [0]);
        //Number[] series1Numbers = {5, 7, 8, 10};

        //plotinställningar
        plot = (XYPlot) findViewById(R.id.plot);
        //hur stora stegen ska vara på y axeln
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 2);
        //hur många labels det ska finnas på x axeln
        plot.setDomainStepValue(domainLabels.length);
        //formattera värdena på y axeln
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("0"));
        //rotera värdena på x axeln
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setRotation(-90);

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Progress");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.BLUE, null, null, null);

        plot.addSeries(series1, series1Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());

                return toAppendTo.append(domainLabels[i]);

            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

    }
}
