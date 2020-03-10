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

        EntryDatabase db = EntryDatabase.getDbInstance(this);
        List<Entry> entries =  db.entryDao().getAll();

        ArrayList <String> dates = new ArrayList<String>();
        ArrayList <Number> pushups = new ArrayList<Number>();

        //ska lagra det högsta antalet armhävningar
        int highestNumberOfPushups = 0;

        for(Entry entry : entries){
            dates.add(entry.date.substring(5));
            pushups.add(entry.pushups);
            //det högsta antalet armhävningar räknas ut
            if(entry.pushups > highestNumberOfPushups)
            {highestNumberOfPushups = entry.pushups;}
        }

        //de datum som ska visas på x-axeln
        final String[] domainLabels = dates.toArray(new String[0]);
        //de tal som ska visas på y-axeln
        Number[] pushupFigures = pushups.toArray(new Number [0]);

        //vi använder det högsta antalet armhävningar för att räkna ut hur
        //stora steg värdena på y axeln ska ta
        int rangeStep = highestNumberOfPushups / 10;

        //antalet labels ska vara lika stor som samlingen
        int domainStep = domainLabels.length;

        //...men inte fler än 8
        if(domainStep > 8){
            domainStep = 8;
        }

        //plotinställningar
        plot = findViewById(R.id.plot);

        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, rangeStep);
        plot.setDomainStepValue(domainStep);

        //själva linjen som representerar datan
        XYSeries pushupLine = new SimpleXYSeries(
                Arrays.asList(pushupFigures), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Progress");

        //style för linjen
        LineAndPointFormatter pushupLineFormat = new LineAndPointFormatter(Color.DKGRAY, null, null, null);
        pushupLineFormat.getLinePaint().setStrokeWidth(8);

        plot.addSeries(pushupLine, pushupLineFormat);

        //formattera värdena på y axeln
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("0"));

        //formatterar datumvärdena på x-axeln
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setRotation(-90);
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {

            //returnerar ett x-axelns datum
            @Override
            public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition pos) {

                int i = Math.round(((Number) obj).floatValue());
                return stringBuffer.append(domainLabels[i]);
            }
            //denna metod måste override-as
            @Override
            public Object parseObject(String sourceString, ParsePosition pos) {
                return null;
            }
        });

    }
}
