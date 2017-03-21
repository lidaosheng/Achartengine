package demo.chart;

/**
 * Created by lifei on 2017/3/19.
 */
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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;



/**
 * Average temperature demo chart.
 */
public class TestBox extends AbstractDemoChart {

    public String getName() {
        return "Test Box";
    }

    public String getDesc() {
        return "The Box Test";
    }

    public Intent execute(Context context) {

        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
                PointStyle.TRIANGLE, PointStyle.SQUARE };
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }


//        XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
//        XYSeries series = dataset.getSeriesAt(0);
//        series.addAnnotation("Vacation", 6, 30);

        XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
        r.setAnnotationsColor(Color.GREEN);
        r.setAnnotationsTextSize(15);
        r.setAnnotationsTextAlign(Paint.Align.CENTER);
        Intent intent = ChartFactory.getBoxChartIntent(context, null, renderer,
                "Test Box");
        return intent;
    }

}
