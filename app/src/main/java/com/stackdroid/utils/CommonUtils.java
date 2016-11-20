package com.stackdroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatterBuilder;

import timber.log.Timber;

/**
 * Created by aditlal on 16/04/16.
 */
public class CommonUtils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static Snackbar displaySnackBar(View view, String message) {
        if (view != null && message != null) {
            return Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        }
        return null;
    }

    public static void displayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static String toRelativeTime(final DateTime dateTime) {

        long nowLngTime = System.currentTimeMillis();
        DateTime now = new DateTime();
        Timber.tag("AGO").d(now.toString());
        long difference = Math.abs(CommonUtils.convertDateTimeToLocal(dateTime).getMillis() - now.getMillis());
        Timber.tag("AGO").d("Difference is " + difference);

        Period period = new Period(CommonUtils.convertDateTimeToLocal(dateTime), now);
        PeriodFormatterBuilder formatterBuilder = new PeriodFormatterBuilder();
        if (difference > DateUtils.YEAR_IN_MILLIS) {
            formatterBuilder.appendYears().appendSuffix(" year");
        } else if (difference > DateUtils.DAY_IN_MILLIS * 30) {
            formatterBuilder.appendMonths().appendSuffix(" month");
        } else if (difference > DateUtils.WEEK_IN_MILLIS) {
            formatterBuilder.appendWeeks().appendSuffix(" week");
        } else if (difference > DateUtils.DAY_IN_MILLIS) {
            formatterBuilder.appendDays().appendSuffix(" day");
        } else if (difference > DateUtils.HOUR_IN_MILLIS) {
            formatterBuilder.appendHours().appendSuffix(" hour");
        } else if (difference > DateUtils.MINUTE_IN_MILLIS) {
            formatterBuilder.appendMinutes().appendSuffix(" minute");
        } else if (difference > DateUtils.SECOND_IN_MILLIS) {
            formatterBuilder.appendSeconds().appendSuffix(" sec");
        }
        String ends = formatterBuilder.printZeroNever().toFormatter().print(period);
        String plural = ends.startsWith("1 ") ? "" : "s";
        ends = ends + plural + " " + "ago";
        Timber.tag("Elapsed").d(ends);
        Timber.tag("Elapsed").d(PeriodFormat.getDefault().print(period));
        return ends;
    }

    private static DateTime convertDateTimeToLocal(DateTime eventDateTime) {

        // get your local timezone
        DateTimeZone localTZ = DateTimeZone.getDefault();

        // convert the input parse datetime to local
        long eventMillsInParseTimeZone = localTZ.convertUTCToLocal(eventDateTime.getMillis());

        return new DateTime(eventMillsInParseTimeZone);
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void openLink(Context context, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((String) view.getTag()));
        context.startActivity(intent);
    }

}
