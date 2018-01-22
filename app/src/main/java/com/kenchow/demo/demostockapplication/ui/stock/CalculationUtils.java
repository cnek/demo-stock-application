package com.kenchow.demo.demostockapplication.ui.stock;

import java.math.BigDecimal;

/**
 * Created by user on 22/1/2018.
 */

public class CalculationUtils {

    public static BigDecimal getPercentageDiffAndRound(BigDecimal newPrice, BigDecimal oldPrice,Integer round){
        return newPrice.subtract(oldPrice).multiply(BigDecimal.valueOf(100)).divide(oldPrice, round, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getDiffTotalAndRound(BigDecimal newPrice, BigDecimal oldPrice,Integer shares,Integer round){
        return newPrice.subtract(oldPrice).multiply(BigDecimal.valueOf(shares)).setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getTotalAndRound(BigDecimal price,Integer shares,Integer round){
        return price.multiply(BigDecimal.valueOf(shares)).setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getTotal(BigDecimal price,Integer shares){
        return price.multiply(BigDecimal.valueOf(shares));
    }

    public static boolean isLargerOrEqual(BigDecimal lhs,BigDecimal rhs){
        return lhs.compareTo(rhs) >= 0;
    }


}
