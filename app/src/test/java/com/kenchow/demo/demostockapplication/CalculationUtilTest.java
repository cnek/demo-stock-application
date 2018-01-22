package com.kenchow.demo.demostockapplication;

import com.kenchow.demo.demostockapplication.ui.stock.CalculationUtils;

import org.junit.Test;

import java.math.BigDecimal;

import static com.google.common.truth.Truth.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculationUtilTest {

    @Test
    public void test_getPercentageDiffAndRound() {
        BigDecimal result = CalculationUtils.getPercentageDiffAndRound(BigDecimal.valueOf(169),BigDecimal.valueOf(185),2);
        assertThat(result).isEqualToIgnoringScale("-8.65");
    }

    @Test
    public void test_getDiffTotalAndRound() {
        BigDecimal result = CalculationUtils.getDiffTotalAndRound(BigDecimal.valueOf(34.052),BigDecimal.valueOf(29.763),135,2);
        assertThat(result).isEqualToIgnoringScale("579.02");
    }

    @Test
    public void test_getTotalAndRound() {
        BigDecimal result = CalculationUtils.getTotalAndRound(BigDecimal.valueOf(156.243162),25,2);
        assertThat(result).isEqualToIgnoringScale("3906.08");
    }

    @Test
    public void test_getTotal() {
        BigDecimal result = CalculationUtils.getTotal(BigDecimal.valueOf(156.25),2);
        assertThat(result).isEqualToIgnoringScale("312.5");
    }

    @Test
    public void test_isLargerOrEqual() {
        boolean result = CalculationUtils.isLargerOrEqual(BigDecimal.valueOf(156.26),BigDecimal.valueOf(156.25));
        assertThat(result).isTrue();
    }

}