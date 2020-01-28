package com.analysis.core.api;

import joinery.DataFrame;

import java.math.BigDecimal;

public interface RankingService {

    Long getPosition(DataFrame dataFrame, String targetValue, Integer sortColumn,
                     Integer targetColumn, boolean isAscend);

    BigDecimal getPositionPercent(DataFrame dataFrame, String targetValue, Integer sortColumn,
                                  Integer targetColumn, boolean isAscend);

    Object getValueByPositionPercent(DataFrame dataFrame, BigDecimal targetPercent, Integer sortColumn,
                                  Integer targetColumn, boolean isAscend);

    Integer getGreaterAndEqualThanNumber(DataFrame dataFrame, Double targetValue, Integer targetColumn);

    Double getGreaterAndEqualThanPercent(DataFrame dataFrame, Double targetValue, Integer targetColumn);

    Integer getLessAndEqualThanNumber(DataFrame dataFrame, Double targetValue, Integer targetColumn);

    Double getLessAndEqualThanPercent(DataFrame dataFrame, Double targetValue, Integer targetColumn);
}
