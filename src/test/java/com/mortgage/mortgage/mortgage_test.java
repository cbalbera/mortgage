package com.mortgage.mortgage;

import java.lang.Math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mortgage.mortgage.mortgage;
import org.junit.Test;

public class mortgage_test
{
    @Test
    public void monthly_pmt_test()
    {
        //TODO: test for "garbage" values such as negatives in any input
        assertEquals(3060,Math.round(mortgage.monthly_mortgage_pmt(600000,30000,.05,30)));
        assertEquals(0, Math.round(mortgage.monthly_mortgage_pmt(0,0,.05,30)));
        assertEquals(0, Math.round(mortgage.monthly_mortgage_pmt(600000,30000,.00,30)));
        assertEquals(5002, Math.round(mortgage.monthly_mortgage_pmt(600000,30000,.10,30)));
    }

    @Test
    public void yearly_amort_schedule_test()
    {
        double[][] first_mortgage = mortgage.yearly_amort_schedule(600000,30000,.05,30,30);
        assertEquals(570000,Math.round(first_mortgage[0][0]));
        assertEquals(28309,Math.round(first_mortgage[0][1]));
        assertEquals(8410,Math.round(first_mortgage[0][2]));
        assertEquals(561590,Math.round(first_mortgage[0][3]));
        assertTrue(Math.round(first_mortgage[12][1])+Math.round(first_mortgage[12][2]) < 36719 && Math.round(first_mortgage[12][1])+Math.round(first_mortgage[12][2]) > 36717);
        assertEquals(0,Math.round(first_mortgage[29][3]));
        double[][] second_mortgage = mortgage.yearly_amort_schedule(600000,30000,.05,30,10);
        assertEquals(570000,Math.round(second_mortgage[0][0]));
        assertEquals(28309,Math.round(second_mortgage[0][1]));
        assertEquals(8410,Math.round(second_mortgage[0][2]));
        assertEquals(561590,Math.round(second_mortgage[0][3]));
        assertEquals(476826,Math.round(second_mortgage[9][2]));
        assertEquals(0,Math.round(second_mortgage[9][3]));
    }

    @Test
    public void get_home_value_test()
    {
        double[] growth = new double[] {.04,.03,.02,.05,-.02,0};
        assertEquals(104074,Math.round(mortgage.home_value(100000,growth,12)));
        assertEquals(107240,Math.round(mortgage.home_value(100000,growth,24)));
        assertEquals(109404,Math.round(mortgage.home_value(100000,growth,36)));
        assertEquals(115002,Math.round(mortgage.home_value(100000,growth,48)));
        assertEquals(112722,Math.round(mortgage.home_value(100000,growth,60)));
        assertEquals(112722,Math.round(mortgage.home_value(100000,growth,72)));
        assertEquals(116151,Math.round(mortgage.home_value(100000,growth,84)));
        assertEquals(119684,Math.round(mortgage.home_value(100000,growth,96)));
    }
}
