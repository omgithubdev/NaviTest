package com.omagrahari.navitest.util

import com.omagrahari.navitest.util.Constant.INPUT_DATE_FORMAT
import com.omagrahari.navitest.util.Constant.OUTPUT_DATE_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getFormattedDate(
        time: String?,
        inputFormat: String = INPUT_DATE_FORMAT,
        outputFormat: String = OUTPUT_DATE_FORMAT
    ): String? {
        val inputPattern = inputFormat
        val outputPattern = outputFormat
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }
}