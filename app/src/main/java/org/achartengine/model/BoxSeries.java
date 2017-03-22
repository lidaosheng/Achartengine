package org.achartengine.model;

import android.util.Log;

import org.achartengine.util.MathHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lifei on 2017/3/10.
 */
public class BoxSeries implements Serializable{
    private String mTitle;
    private List<Double> mValues = new ArrayList<Double>();

    public BoxSeries(String title){this.mTitle=title;}
    public String getmTitle(){return mTitle;}
    public void setmTitle(String title){this.mTitle=title;}
    public int getItemCount() {
        return mValues.size();
    }
    /**
     * add value to list mValues
     * @param value
     */
    public synchronized void add(double value) {
        mValues.add(value);
    }
    public synchronized void add(double[] value) {
        for(double i:value) {
            mValues.add(i);
        }
    }
    /**
     * calculate the statistics of the mValues,include min,max,Q1,Q2,Q3
     * @return the statistics
     */
    public Map<String,Double> getStatistics(){
        Map<String,Double> stat = new HashMap<String,Double>();
        stat = MathHelper.getStatistics(this.mValues);
        System.out.println("怎么越界了，this.mValue---------:"+this.mValues);
        return stat;
    }
}
