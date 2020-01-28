package com.analysis.core.impl;

import com.analysis.core.api.RankingService;
import joinery.DataFrame;

import java.math.BigDecimal;
import java.util.List;

public class RankingServiceImpl implements RankingService {
    @Override
    public Long getPosition(DataFrame dataFrame, String targetValue, Integer sortColumn,
                            Integer targetColumn, boolean isAscend) {
        Integer size = dataFrame.size();
        if(size==0){
            return 0L;
        }

        if(!isAscend){
            sortColumn = -sortColumn;
        }
        DataFrame df = dataFrame.sortBy(sortColumn);
        df.resetIndex();
        DataFrame selected = df.select(new DataFrame.Predicate<Object>() {
            @Override
            public Boolean apply(List<Object> value) {
                return targetValue.equals(String.valueOf(value.get(targetColumn)));
            }
        });
        Object[] row =selected.toArray();
        Object index = selected.map().get(0);

        return Long.valueOf(String.valueOf(selected.index().toArray()[0]));
    }

    @Override
    public BigDecimal getPositionPercent(DataFrame dataFrame, String targetValue, Integer sortColumn,
                                     Integer targetColumn, boolean isAscend) {
        Integer size = dataFrame.length();
        if(size==0){
            return new BigDecimal(0);
        }
        if(!isAscend){
            sortColumn = -sortColumn;
        }

        Long position = getPosition(dataFrame, targetValue, sortColumn, targetColumn, isAscend);

        return new BigDecimal(position).divide(new BigDecimal(size));
    }

    @Override
    public Object getValueByPositionPercent(DataFrame dataFrame, BigDecimal targetPercent, Integer sortColumn, Integer targetColumn, boolean isAscend) {
        Integer size = dataFrame.size();
        if(size==0){
            return null;
        }

        Integer targetPosition = targetPercent.multiply(
                new BigDecimal(dataFrame.size())).intValue();

        if(!isAscend){
            sortColumn = -sortColumn;
        }
        DataFrame df = dataFrame.sortBy(sortColumn);
        df.resetIndex();
        return dataFrame.get(targetPosition, targetColumn);
    }


    @Override
    public Integer getGreaterAndEqualThanNumber(DataFrame dataFrame, Double targetValue, Integer targetColumn) {
        Integer size = dataFrame.length();
        if(size==0){
            return 0;
        }

        DataFrame selected = dataFrame.select(new DataFrame.Predicate<Object>() {
            @Override
            public Boolean apply(List<Object> value) {
                return targetValue >= (Double) value.get(targetColumn);
            }
        });


        return selected.length();
    }

    @Override
    public Double getGreaterAndEqualThanPercent(DataFrame dataFrame, Double targetValue, Integer targetColumn) {
        Integer length = dataFrame.length();
        if(length==0){
            return 0.0;
        }

        Integer number = getGreaterAndEqualThanNumber(dataFrame, targetValue, targetColumn);

        return Double.valueOf(number)/Double.valueOf(length);

    }

    @Override
    public Integer getLessAndEqualThanNumber(DataFrame dataFrame, Double targetValue, Integer targetColumn) {
        Integer size = dataFrame.length();
        if(size==0){
            return 0;
        }

        DataFrame selected = dataFrame.select(new DataFrame.Predicate<Object>() {
            @Override
            public Boolean apply(List<Object> value) {
                return targetValue <= (Double) value.get(targetColumn);
            }
        });


        return selected.length();
    }

    @Override
    public Double getLessAndEqualThanPercent(DataFrame dataFrame, Double targetValue, Integer targetColumn) {
        Integer length = dataFrame.length();
        if(length==0){
            return 0.0;
        }

        Integer number = getLessAndEqualThanNumber(dataFrame, targetValue, targetColumn);

        return Double.valueOf(number)/Double.valueOf(length);
    }
}
