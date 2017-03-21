package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifei on 2017/3/13.
 */
public class BoxMultipleSeriesDataset implements Serializable {
    /** The included series. */
    private List<BoxSeries> mSeries = new ArrayList<BoxSeries>();
    /**
     * Adds a new Box series to the list.
     *
     * @param series the Box series to add
     */
    public synchronized void addSeries(BoxSeries series) {
        mSeries.add(series);
    }

    /**
     * Adds a new Box series to the list.
     *
     * @param index the index in the series list
     * @param series the Box series to add
     */
    public synchronized void addSeries(int index, BoxSeries series) {
        mSeries.add(index, series);
    }

    /**
     * Adds all the provided Box series to the list.
     *
     * @param series the Box series to add
     */
    public synchronized void addAllSeries(List<BoxSeries> series) {
        mSeries.addAll(series);
    }

    /**
     * Removes the Box series from the list.
     *
     * @param index the index in the series list of the series to remove
     */
    public synchronized void removeSeries(int index) {
        mSeries.remove(index);
    }

    /**
     * Removes the Box series from the list.
     *
     * @param series the Box series to be removed
     */
    public synchronized void removeSeries(BoxSeries series) {
        mSeries.remove(series);
    }

    /**
     * Removes all the Box series from the list.
     */
    public synchronized void clear() {
        mSeries.clear();
    }

    /**
     * Returns the Box series at the specified index.
     *
     * @param index the index
     * @return the Box series at the index
     */
    public synchronized BoxSeries getSeriesAt(int index) {
        return mSeries.get(index);
    }

    /**
     * Returns the Box series count.
     *
     * @return the Box series count
     */
    public synchronized int getSeriesCount() {
        return mSeries.size();
    }

    /**
     * Returns an array of the Box series.
     *
     * @return the Box series array
     */
    public synchronized BoxSeries[] getSeries() {
        return mSeries.toArray(new BoxSeries[0]);
    }
}
