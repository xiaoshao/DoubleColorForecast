package com.filter;

import com.data.Record;

import java.util.List;

public class CompositeBallFilter implements BallFilter {

    private List<BallFilter> filters;

    public CompositeBallFilter(List<BallFilter> ballFilterList) {
        this.filters = ballFilterList;
    }

    @Override
    public boolean filter(Record record) {

        for (BallFilter filter : filters) {
            if (filter.filter(record)) {
                return true;
            }
        }
        return false;

    }
}
