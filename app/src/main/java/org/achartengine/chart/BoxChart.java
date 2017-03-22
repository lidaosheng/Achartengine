package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import org.achartengine.model.BoxMultipleSeriesDataset;
import org.achartengine.model.BoxSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lifei on 2017/2/27.
 */
public class BoxChart extends AbstractChart{
    /** The multiple series dataset. */
    protected BoxMultipleSeriesDataset mDataset;
    /** The multiple series renderer. */
    protected XYMultipleSeriesRenderer mRenderer;
    /** The visible chart area, in screen coordinates. */
    private Rect mScreenR;
    /** The calculated range. */
    private final Double[] mCalcRange = new Double[2];
    /** The constant to identify this chart type. */
    public static final String TYPE = "Box";

    protected BoxChart(){
    }
    /**
     * Builds a new box chart instance.
     * @param dataset the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public BoxChart(BoxMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        mDataset = dataset;
        mRenderer = renderer;
    }


    @Override
    public void draw(Canvas canvas, int x, int y, int width, int height, Paint paint) {
        paint.setAntiAlias(mRenderer.isAntialiasing());
        //---------------------------获取mScreenR部分的具体坐标-------------------------
        int legendSize = getLegendSize(mRenderer, height / 5, mRenderer.getAxisTitleTextSize());
        int[] margins = mRenderer.getMargins();
        int left = x + margins[1];
        int top = y + margins[0];
        int right = x + width - margins[3];
        int sLength = mDataset.getSeriesCount();
        String[] titles = new String[sLength];
        for (int i = 0; i < sLength; i++) {
            titles[i] = mDataset.getSeriesAt(i).getmTitle();
        }
        int bottom = y + height - margins[2]-legendSize;
        if (mScreenR == null) {
            mScreenR = new Rect();
        }
        mScreenR.set(left, top, right, bottom);
        //-------------------------------绘制整个的背景区域----------------------------------------------
        drawBackground(mRenderer, canvas, x, y, width, height, paint, false, DefaultRenderer.NO_COLOR);
        //----------------------------计算x,y刻度单元对应于屏幕上长度--------------------------------------
        double minY,maxY;
        boolean isMinYSet,isMaxYSet;

        minY = mRenderer.getYAxisMin(0); //double.max_value
        maxY = mRenderer.getYAxisMax(0);
        isMinYSet = mRenderer.isMinYSet(0);
        isMaxYSet = mRenderer.isMaxYSet(0);
        mCalcRange[0] = minY;//记录整个series集中最大Y值和最小Y值
        mCalcRange[1] = maxY;

        int xPixelsPerUnit=0;
        float yPixelsPerUnit=0;
        //更新上面的minY，maxY等
        for (int i = 0; i < sLength; i++) {
            System.out.println("-------------------------怎么越界sLenght："+sLength);
            BoxSeries series = mDataset.getSeriesAt(i); //获取第i个series数据
            Map<String,Double> stat = series.getStatistics();
            if (series.getItemCount() == 0) { //如果当前series中没数据，跳过这次循环
                continue;
            }
            if (!isMinYSet) {
                double minimumY = stat.get("min");
                minY = Math.min(minY, (float) minimumY);
                mCalcRange[0] = minY;
            }
            if (!isMaxYSet) {
                double maximumY = stat.get("max");
                maxY = Math.max(maxY, (float) maximumY);
                mCalcRange[1] = maxY;
            }
        }
        if (sLength != 0) {
            xPixelsPerUnit = (right - left) / (sLength+1); //
        }
        if (maxY - minY != 0) {
            float scaleForY = getScaleForY();
            yPixelsPerUnit = (float) ((bottom - top) / (scaleForY*(maxY - minY))); //y轴单位长度对应屏幕长度多少
        }
        //---------------------------将统计值转化为屏幕值----------------------------------
        for (int i = 0; i < sLength; i++){
//            XYMultipleSeriesRenderer seriesRenderer = mRenderer.getSeriesRendererAt(i);
            BoxSeries series = mDataset.getSeriesAt(i);
            if (series.getItemCount() == 0) {
                continue;
            }
//            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) mRenderer.getSeriesRendererAt(i);
            float yAxisValue = Math.min(bottom, (float) (bottom + yPixelsPerUnit * minY));
            synchronized (series) {
                Map<String,Double> stat = series.getStatistics();
                float min = (float)stat.get("min").doubleValue();
                float max = (float)stat.get("max").doubleValue();
                float Q1 = (float)stat.get("Q1").doubleValue();
                float Q2 = (float)stat.get("Q2").doubleValue();
                float Q3 = (float)stat.get("Q3").doubleValue();
                float min1 = (float)(bottom - (min-0)*yPixelsPerUnit);
                float max1 = (float)(bottom - (max-0)*yPixelsPerUnit);
                float Q11 = (float)(bottom - (Q1-0)*yPixelsPerUnit);
                float Q21 = (float)(bottom - (Q2-0)*yPixelsPerUnit);
                float Q31 = (float)(bottom - (Q3-0)*yPixelsPerUnit);
                float startX = left + (i+1) * xPixelsPerUnit ;//box的X点坐标
                drawSeries(canvas, paint,startX, min1,max1,Q11,Q21,Q31, null, yAxisValue);
                //sss

            }
        }




        //-------------------------空白区域填充----------------------------------
        drawBackground(mRenderer, canvas, x, bottom, width, height - bottom, paint, true,
                mRenderer.getMarginsColor());
        drawBackground(mRenderer, canvas, x, y, width, margins[0], paint, true,
                mRenderer.getMarginsColor());

        paint.setColor(Color.RED);
        canvas.drawLine(left, bottom, right, bottom, paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(left, top, left, bottom, paint);
    }
    //保证最高的那个box不会填充整个Y,缩放倍数
    private float getScaleForY() {
        return 2;
    }


    /**
     * @param canvas the canvas to paint to
     * @param paint the paint to be used for drawing
     * @param seriesRenderer the series renderer
     * @param yAxisValue the minimum value of the y axis
     */
    public void drawSeries(Canvas canvas,Paint paint,float startX,float min,float max,float Q1,float Q2,float Q3,
                           XYMultipleSeriesRenderer seriesRenderer,float yAxisValue) {
        paint.setStyle(Style.FILL);
        drawBox(canvas,startX,min,max,Q1,Q2,Q3,yAxisValue, getHalfDiffX(), paint);
    }

    public XYMultipleSeriesRenderer getRenderer() {
        return mRenderer;
    }


    private void drawBox(Canvas canvas,float startX,float min,float max,float Q1,float Q2,float Q3, float yAxisValue, float halfDiffX, Paint paint) {
//        float startX = xMin - seriesNr * halfDiffX + seriesIndex * 2 * halfDiffX; //
//        drawBox(canvas, startX, yMax, startX + 2 * halfDiffX, yMin, scale, seriesIndex, paint); //(canvas,
        int orginColor = paint.getColor();
        paint.setColor(Color.rgb(164,201,95));
        canvas.drawLine(startX-halfDiffX,max,startX+halfDiffX,max,paint); //胡须上
        canvas.drawLine(startX,max,startX,Q3,paint);
        canvas.drawLine(startX-halfDiffX,min,startX+halfDiffX,min,paint); //胡须下
        canvas.drawLine(startX,min,startX,Q1,paint);
        canvas.drawRect(startX-halfDiffX, Q3, startX+halfDiffX, Q1, paint); //盒子
        paint.setColor(Color.WHITE);
        canvas.drawLine(startX-halfDiffX,Q2,startX+halfDiffX,Q2,paint); //中位线
        paint.setColor(orginColor);
    }




    @Override
    public int getLegendShapeWidth(int seriesIndex) {
        return 0;
    }

    @Override
    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y, int seriesIndex, Paint paint) {
    }


    /**
     * get half of the box width
     * @return
     */
    private float getHalfDiffX() {
        return 10; //如果设置了bar之间的间距的话
    }

    /**
     *
     * @param realPoint
     * @param scale
     * @return
     */
//    public double[] toScreenPoint(double[] realPoint, int scale) {
//        double realMinX = mRenderer.getXAxisMin(scale); //获取当前缩放比例下的X轴最小值
//        double realMaxX = mRenderer.getXAxisMax(scale);
//        double realMinY = mRenderer.getYAxisMin(scale);
//        double realMaxY = mRenderer.getYAxisMax(scale);
//        if (!mRenderer.isMinXSet(scale) || !mRenderer.isMaxXSet(scale) || !mRenderer.isMinYSet(scale)
//                || !mRenderer.isMaxYSet(scale)) {
//            double[] calcRange = getCalcRange(scale);
//            realMinX = calcRange[0];
//            realMaxX = calcRange[1];
//            realMinY = calcRange[2];
//            realMaxY = calcRange[3];
//        }
//        if (mScreenR != null) {
//            return new double[] {
//                    (realPoint[0] - realMinX) * mScreenR.width() / (realMaxX - realMinX) + mScreenR.left,
//                    (realMaxY - realPoint[1]) * mScreenR.height() / (realMaxY - realMinY) + mScreenR.top };
//        } else {
//            return realPoint;
//        }
//    }
}
