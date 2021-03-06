/*
 * Copyright 2016 Alireza Eskandarpour Shoferi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.meness.roozh;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * This class contains methods for converting Jalali (Solar) and Gregorian dates
 * into each other based Kazimierz M. Borkowski paper about Jalali date.
 *
 * @author Kaveh Shahbazian
 * @author Alireza Eskandarpour Shoferi
 * @see <a href="https://github.com/meNESS/Roozh/">Roozh on Github</a>
 * @see <a href="http://www.astro.uni.torun.pl/~kb/Papers/EMP/PersianC-EMP.htm">The Persian calendar for 3000 years</a>
 * @since 0.0.1-alpha
 */

public class Roozh {
    public static final String AM = "\u0642\u002e\u0638";
    public static final String PM = "\u0628\u002e\u0638";
    private int iDayOfMonth, iMonth, iYear;
    // used only for time
    private Calendar timeCalendar;
    private int iJY, iJM, iJD;
    private int iGY, iGM, iGD;
    private int iLeap, iMarch;

    /**
     * Get manipulated day
     *
     * @return Day as <code>int</code>
     */
    public int getDayOfMonth() {
        return iDayOfMonth;
    }

    /**
     * Get manipulated month
     *
     * @return Month as <code>int</code>
     */
    public int getMonth() {
        return iMonth;
    }

    public int getMillisecond() {
        return timeCalendar.get(Calendar.MILLISECOND);
    }

    public int getSecond() {
        return timeCalendar.get(Calendar.SECOND);
    }

    public int getMinute() {
        return timeCalendar.get(Calendar.MINUTE);
    }

    public int getHour() {
        return timeCalendar.get(Calendar.HOUR);
    }

    public int getHourOfDay() {
        return timeCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getAmPm() {
        return timeCalendar.get(Calendar.AM_PM);
    }

    /**
     * Get manipulated year
     *
     * @return Year as <code>int</code>
     */
    public int getYear() {
        return iYear;
    }

    /**
     * Convert current Gregorian to Persian
     *
     * @return this
     */
    public Roozh gregorianToPersian() {
        gregorianToPersian(System.currentTimeMillis());
        return this;
    }

    /**
     * Convert Gregorian time to Persian
     *
     * @param time Time
     * @return this
     */
    public Roozh gregorianToPersian(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return gregorianToPersian(calendar);
    }

    public Roozh gregorianToPersian(Calendar calendar) {
        // time zone set for time calendar field
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));
        timeCalendar = calendar;
        // months start from 0
        gregorianToPersian(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        return this;
    }

    /**
     * Converts Gregorian date to Persian(Jalali) date
     *
     * @param year  <code>int</code>
     * @param month <code>int</code>
     * @param day   <code>int</code>
     */
    public Roozh gregorianToPersian(int year, int month, int day) {
        int jd = JG2JD(year, month, day, 0);
        JD2Jal(jd);
        this.iYear = iJY;
        this.iMonth = iJM;
        this.iDayOfMonth = iJD;

        return this;
    }

    /**
     * Calculates the Julian Day number (JG2JD) from Gregorian or Julian
     * calendar dates. This integer number corresponds to the noon of the date
     * (i.e. 12 hours of Universal Time). The procedure was tested to be good
     * since 1 March, -100100 (of both the calendars) up to a few millions
     * (10**6) years into the future. The algorithm is based on D.A. Hatcher,
     * Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by me (K.M.
     * Borkowski, Post.Astron. 25(1987), 275-279).
     *
     * @param year  <code>int</code>
     * @param month <code>int</code>
     * @param day   <code>int</code>
     * @param J1G0  to be set to 1 for Julian and to 0 for Gregorian calendar
     * @return Julian Day number
     */
    private int JG2JD(int year, int month, int day, int J1G0) {
        int jd = (1461 * (year + 4800 + (month - 14) / 12)) / 4
                + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day
                - 32075;

        if (J1G0 == 0)
            jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;

        return jd;
    }

    /**
     * Converts the Julian Day number to a date in the Jalali calendar
     *
     * @param JDN the Julian Day number
     */
    private void JD2Jal(int JDN) {
        JD2JG(JDN, 0);

        iJY = iGY - 621;
        JalCal(iJY);

        int JDN1F = JG2JD(iGY, 3, iMarch, 0);
        int k = JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                iJM = 1 + k / 31;
                iJD = (k % 31) + 1;
                return;
            } else {
                k = k - 186;
            }
        } else {
            iJY = iJY - 1;
            k = k + 179;
            if (iLeap == 1)
                k = k + 1;
        }

        iJM = 7 + k / 30;
        iJD = (k % 30) + 1;
    }

    /**
     * Calculates Gregorian and Julian calendar dates from the Julian Day number
     * (JD) for the period since JD=-34839655 (i.e. the year -100100 of both the
     * calendars) to some millions (10**6) years ahead of the present. The
     * algorithm is based on D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55
     * slightly modified by me (K.M. Borkowski, Post.Astron. 25(1987), 275-279).
     *
     * @param JD   Julian day number as <code>int</code>
     * @param J1G0 to be set to 1 for Julian and to 0 for Gregorian calendar
     */
    private void JD2JG(int JD, int J1G0) {
        int i, j;

        j = 4 * JD + 139361631;

        if (J1G0 == 0) {
            j = j + (4 * JD + 183187720) / 146097 * 3 / 4 * 4 - 3908;
        }

        i = (j % 1461) / 4 * 5 + 308;
        iGD = (i % 153) / 5 + 1;
        iGM = ((i / 153) % 12) + 1;
        iGY = j / 1461 - 100100 + (8 - iGM) / 6;
    }

    /**
     * This procedure determines if the Jalali (Persian) year is leap (366-day
     * long) or is the common year (365 days), and finds the day in March
     * (Gregorian calendar) of the first day of the Jalali year (jY)
     *
     * @param jY Jalali calendar year (-61 to 3177)
     */
    private void JalCal(int jY) {
        iMarch = 0;
        iLeap = 0;

        int[] breaks = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
                1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};

        iGY = jY + 621;
        int leapJ = -14;
        int jp = breaks[0];

        int jump;
        for (int j = 1; j <= 19; j++) {
            int jm = breaks[j];
            jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;

                if ((jump % 33) == 4 && (jump - N) == 4)
                    leapJ = leapJ + 1;

                int leapG = (iGY / 4) - (iGY / 100 + 1) * 3 / 4 - 150;

                iMarch = 20 + leapJ - leapG;

                if ((jump - N) < 6)
                    N = N - jump + (jump + 4) / 33 * 33;

                iLeap = ((((N + 1) % 33) - 1) % 4);

                if (iLeap == -1)
                    iLeap = 4;
                break;
            }

            leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4;
            jp = jm;
        }
    }

    /**
     * Converts Persian(Jalali) date to Gregorian date
     *
     * @param year  <code>int</code>
     * @param month <code>int</code>
     * @param day   <code>int</code>
     */
    public void persianToGregorian(int year, int month, int day) {
        int jd = Jal2JD(year, month, day);
        JD2JG(jd, 0);
        this.iYear = iGY;
        this.iMonth = iGM;
        this.iDayOfMonth = iGD;
    }

    /**
     * Converts a date of the Jalali calendar to the Julian Day Number
     *
     * @param jY Jalali year as <code>int</code>
     * @param jM Jalali month as <code>int</code>
     * @param jD Jalali day as <code>int</code>
     * @return Julian day number
     */
    private int Jal2JD(int jY, int jM, int jD) {
        JalCal(jY);
        return JG2JD(iGY, 3, iMarch, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7)
                + jD - 1;
    }

    public enum Months {
        FARVARDIN("\u0641\u0631\u0648\u0631\u062f\u06cc\u0646"), ORDIBEHESHT("\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a"), KHORDAD("\u062e\u0631\u062f\u0627\u062f"), TIR("\u062a\u06cc\u0631"), MORDAD("\u0645\u0631\u062f\u0627\u062f"), SHAHRIVAR("\u0634\u0647\u0631\u06cc\u0648\u0631"), MEHR("\u0645\u0647\u0631"), ABAN("\u0622\u0628\u0627\u0646"), AZAR("\u0622\u0630\u0631"), DEY("\u062f\u06cc"), BAHMAN("\u0628\u0647\u0645\u0646"), ESFAND("\u0627\u0633\u0641\u0646\u062f");
        private final String sName;

        Months(String name) {
            this.sName = name;
        }

        public static String getName(int i) {
            // starts from 0
            return Months.values()[i - 1].getName();
        }

        public String getName() {
            return sName;
        }

        public enum Short {
            FARVARDIN("\u0641\u0631\u0648"), ORDIBEHESHT("\u0627\u0631\u062f"), KHORDAD("\u062e\u0631\u062f"), TIR("\u062a\u06cc\u0631"), MORDAD("\u0645\u0631\u062f"), SHAHRIVAR("\u0634\u0647\u0631"), MEHR("\u0645\u0647\u0631"), ABAN("\u0622\u0628\u0627"), AZAR("\u0622\u0630\u0631"), DEY("\u062f\u06cc"), BAHMAN("\u0628\u0647\u0645"), ESFAND("\u0627\u0633\u0641");
            private final String sName;

            Short(String name) {
                this.sName = name;
            }

            public static String getName(int i) {
                // starts from 0
                return Months.Short.values()[i - 1].getName();
            }

            public String getName() {
                return sName;
            }
        }
    }
}
