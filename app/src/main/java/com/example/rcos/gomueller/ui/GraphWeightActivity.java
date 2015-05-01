package com.example.rcos.gomueller.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rcos.gomueller.NavigationDrawer;
import com.example.rcos.gomueller.R;
import com.example.rcos.gomueller.UnitDate;
import com.example.rcos.gomueller.database.ExerciseCRUD;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GraphWeightActivity extends ActionBarActivity {

    private NavigationDrawer navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_weight);

        navigationDrawer = new NavigationDrawer(this);

        drawGraph();
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        drawGraph();
    }*/

    public String getAttributeValue(String currentDetailStr)
    {
        String[] splitString = currentDetailStr.split(" ");
        for (int i = 0; i < splitString.length - 1; i++)
        {
            if (splitString[i].equals("Weight:"))
                return String.valueOf(splitString[i + 1]);
        }

        return "";
    }

    public void drawGraph()
    {
        //Draw graph
        final ExerciseCRUD crudDetail = new ExerciseCRUD(this);
        ArrayList<String> detailArray = crudDetail.getWeightDetail();
        DataPoint[] dataPoints = new DataPoint[detailArray.size()];

        //fetch data
        for (int i = 0; i < detailArray.size(); i++)
        {
            //parse the string
            String[] splitString = detailArray.get(i).split(" ");
            String dateStr = splitString[0];
            String weightStr = getAttributeValue(detailArray.get(i));

            //convert date from yyyy/MM/dd to MM/dd/yyyy
            //then parse that date
            //this is for displaying data on graph
            String newFormat = UnitDate.convertFormatFromSortedToDisplay(dateStr);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = formatter.parse(newFormat);
                dataPoints[i] = new DataPoint(date, Integer.parseInt(weightStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        if (dataPoints.length > 0) {
            graph.getViewport().setMinX(dataPoints[0].getX());
            graph.getViewport().setMaxX(dataPoints[dataPoints.length - 1].getX());
            graph.getViewport().setXAxisBoundsManual(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph_weight, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (navigationDrawer.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.addMenu) {
            Intent addIntent = new Intent(this, NewWeightActivity.class);
            startActivity(addIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void onEditDataButton(View view) {
        Intent intent = new Intent(this, ExerciseDetailActivity.class);
        intent.putExtra("type", "weight");
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationDrawer.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navigationDrawer.onConfigurationChanged(newConfig);
    }
}