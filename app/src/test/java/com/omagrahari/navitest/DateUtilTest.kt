package com.omagrahari.navitest

import com.omagrahari.navitest.util.DateUtils
import org.junit.Assert
import org.junit.Test

class DateUtilTest {

    @Test
    fun getFormattedDate_DataTime_isCorrect() {
        val date = DateUtils.getFormattedDate("2022-03-23T23:05:00Z")
        Assert.assertEquals(date, "23-Mar-2022 11:05 pm")
    }

    @Test
    fun getFormattedDate_Date_isCorrect() {
        val date = DateUtils.getFormattedDate("2022-03-23T23:05:00Z", outputFormat = "dd-MMM-yyyy")
        Assert.assertEquals(date, "23-Mar-2022")
    }

    @Test
    fun getFormattedDate_DateTime_isInCorrect() {
        val date = DateUtils.getFormattedDate("2022-03-23T23:05:00Z", outputFormat = "dd-MMM-yyyy HH:mm")
        Assert.assertNotEquals(date, "23-Mar-2022")
    }
}