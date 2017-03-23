/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.BoxMultipleSeriesDataset;
import org.achartengine.model.BoxSeries;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import demo.chart.IDemoChart;

public class GeneratedChartDemo extends ListActivity {
  private static final int SERIES_NR = 2;

  private String[] mMenuText;

  private String[] mMenuSummary;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // I know, I know, this should go into strings.xml and accessed using
    // getString(R.string....)
    mMenuText = new String[] { "Line chart", "Scatter chart", "Time chart", "Bar chart" ,"Box chart"};
    mMenuSummary = new String[] { "Line chart with randomly generated values",
        "Scatter chart with randomly generated values",
        "Time chart with randomly generated values", "Bar chart with randomly generated values" ,"Box Chart"};
    setListAdapter(new SimpleAdapter(this, getListValues(), android.R.layout.simple_list_item_2,
        new String[] { IDemoChart.NAME, IDemoChart.DESC }, new int[] { android.R.id.text1, android.R.id.text2 }));
  }

  private List<Map<String, String>> getListValues() {
    List<Map<String, String>> values = new ArrayList<Map<String, String>>();
    int length = mMenuText.length;
    for (int i = 0; i < length; i++) {
      Map<String, String> v = new HashMap<String, String>();
      v.put(IDemoChart.NAME, mMenuText[i]);
      v.put(IDemoChart.DESC, mMenuSummary[i]);
      values.add(v);
    }
    return values;
  }

  private XYMultipleSeriesDataset getDemoDataset() {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    final int nr = 10;
    Random r = new Random();
    for (int i = 0; i < SERIES_NR; i++) {
      XYSeries series = new XYSeries("Demo series " + (i + 1));
      for (int k = 0; k < nr; k++) {
        series.add(k, 20 + r.nextInt() % 100);
      }
      dataset.addSeries(series);
    }
    return dataset;
  }

  private XYMultipleSeriesDataset getDateDemoDataset() {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    final int nr = 10;
    long value = new Date().getTime() - 3 * TimeChart.DAY;
    Random r = new Random();
    for (int i = 0; i < SERIES_NR; i++) {
      TimeSeries series = new TimeSeries("Demo series " + (i + 1));
      for (int k = 0; k < nr; k++) {
        series.add(new Date(value + k * TimeChart.DAY / 4), 20 + r.nextInt() % 100);
      }
      dataset.addSeries(series);
    }
    return dataset;
  }

  private XYMultipleSeriesDataset getBarDemoDataset() {
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    final int nr = 10;
    Random r = new Random();
    for (int i = 0; i < SERIES_NR; i++) {
      CategorySeries series = new CategorySeries("Demo series " + (i + 1));
      for (int k = 0; k < nr; k++) {
        series.add(100 + r.nextInt() % 100);
      }
      dataset.addSeries(series.toXYSeries());
    }
    return dataset;
  }

  private XYMultipleSeriesRenderer getDemoRenderer() {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setPointSize(5f);
    renderer.setMargins(new int[] {20, 30, 15, 0});
    XYSeriesRenderer r = new XYSeriesRenderer();
    r.setColor(Color.BLUE);
    r.setPointStyle(PointStyle.SQUARE);
    r.setFillBelowLine(true);
    r.setFillBelowLineColor(Color.WHITE);
    r.setFillPoints(true);
    renderer.addSeriesRenderer(r);
    r = new XYSeriesRenderer();
    r.setPointStyle(PointStyle.CIRCLE);
    r.setColor(Color.GREEN);
    r.setFillPoints(true);
    renderer.addSeriesRenderer(r);
    renderer.setAxesColor(Color.DKGRAY);
    renderer.setLabelsColor(Color.LTGRAY);
    return renderer;
  }

  public XYMultipleSeriesRenderer getBarDemoRenderer() {
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    renderer.setAxisTitleTextSize(16);
    renderer.setChartTitleTextSize(20);
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[] {20, 30, 15, 0});
    XYSeriesRenderer r = new XYSeriesRenderer();
    r.setColor(Color.BLUE);
    renderer.addSeriesRenderer(r);
    r = new XYSeriesRenderer();
    r.setColor(Color.GREEN);
    renderer.addSeriesRenderer(r);
    return renderer;
  }

  private void setChartSettings(XYMultipleSeriesRenderer renderer) {
    renderer.setChartTitle("Chart demo");
    renderer.setXTitle("x values");
    renderer.setYTitle("y values");
    renderer.setXAxisMin(0.5);
    renderer.setXAxisMax(10.5);
    renderer.setYAxisMin(0);
    renderer.setYAxisMax(210);
  }

  private BoxMultipleSeriesDataset getBoxDemoDataset() {
    BoxMultipleSeriesDataset dataset = new BoxMultipleSeriesDataset();
    final int nr = 10;
    BoxSeries series = new BoxSeries("第一个box");
    series.add(new double[]{12.0, 45.9, 40.00, 22.05});
    BoxSeries series1 = new BoxSeries("第二个box");
    series1.add(new double[]{10.0, 55.9, 40.00, 69.05,55});
    BoxSeries series2 = new BoxSeries("第三个box");
    series2.add(new double[]{30.0, 44.9, 40.00, 39.05,25});
    BoxSeries series3 = new BoxSeries("第四个box");
    series3.add(new double[]{11.0, 34.9, 50.00, 49.05,35});
    BoxSeries series4 = new BoxSeries("第五个box");
    series4.add(new double[]{41.0, 14.9, 20.00, 39.05,25});
    dataset.addSeries(series);
    dataset.addSeries(series1);
    dataset.addSeries(series2);
    dataset.addSeries(series3);
    dataset.addSeries(series4);
    return dataset;
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    switch (position) {
    case 0:
      Intent intent = ChartFactory.getLineChartIntent(this, getDemoDataset(), getDemoRenderer());
      startActivity(intent);
      break;
    case 1:
      intent = ChartFactory.getScatterChartIntent(this, getDemoDataset(), getDemoRenderer());
      startActivity(intent);
      break;
    case 2:
      intent = ChartFactory.getTimeChartIntent(this, getDateDemoDataset(), getDemoRenderer(), null);
      startActivity(intent);
      break;
    case 3:
      XYMultipleSeriesRenderer renderer = getBarDemoRenderer();
      setChartSettings(renderer);
      intent = ChartFactory.getBarChartIntent(this, getBarDemoDataset(), renderer, Type.DEFAULT);
      startActivity(intent);
      break;
      case 4:
        intent = ChartFactory.getBoxChartIntent(this,getBoxDemoDataset(),getDemoRenderer(),"ss");
        startActivity(intent);

    }

  }
}