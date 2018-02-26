/**
 * 
 */
package luxk.jdbt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author luxk
 * 
 */
public final class UtilDate {

	private static TimeZone timeZone = TimeZone.getDefault();

	/**
	 * format에 맞추어 현재 날짜를 String으로 반환한다 (예 : "yyyy-MM-dd HH:mm:ss:SSS")
	 * 
	 * @param format
	 *            날짜의 format
	 * @return 현재 날짜
	 */
	public static String getDateString(String format) {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);

		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.format(cal.getTime());
	}

	/**
	 * 현재 날짜를 반환한다(Date type).
	 * 
	 * @return 현재 날짜
	 */
	public static Date getDate() {

		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);

		return cal.getTime();
	}

	/**
	 * 특정 날짜를 반환한다(Date type).
	 * 
	 * @param year
	 *            년
	 * @param month
	 *            월
	 * @param day
	 *            일
	 * @param hour
	 *            시
	 * @param minute
	 *            분
	 * @param second
	 *            초
	 * @return 현재 날짜 object
	 */
	public static Date getDate(int year, int month, int day, int hour,
			int minute, int second) {

		GregorianCalendar cal = new GregorianCalendar(timeZone, Locale.KOREAN);
		cal.set(year, month - 1, day, hour, minute, second);
		return cal.getTime();
	}

	/**
	 * format에 맞추어 주어진 날짜를 String으로 반환한다. (예 : "yyyy-MM-dd HH:mm:ss:SSS")
	 * 
	 * @param date
	 *            String으로 표현할 날짜
	 * @param format
	 *            날짜의 format
	 * @return String으로 변환된 날짜
	 */
	public static String dateToString(Date date, String format) {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.format(date);
	}

	/**
	 * format에 맞추어 주어진 String을 Date로 변환한다.
	 * 
	 * @param dateString
	 *            String으로 표현된 날짜
	 * @return Date로 변환된 날짜
	 * @exception ParseException
	 *                Sring parsing 에러 발생시
	 */
	public static Date stringToDate(String dateString) throws ParseException {

		SimpleDateFormat simpleFormat = new SimpleDateFormat();
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.parse(dateString);
	}

	/**
	 * format에 맞추어 주어진 String을 Date로 변환한다.
	 * 
	 * @param dateString
	 *            String으로 표현된 날짜
	 * @param format
	 *            날짜의 format
	 * @return Date로 변환된 날짜
	 * @exception ParseException
	 *                Sring parsing 에러 발생시
	 */
	public static Date stringToDate(String dateString, String format)
			throws ParseException {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.parse(dateString);
	}

	/**
	 * 두 Date의 날짜 차이를 date1 - date2 방식으로 계산한다.
	 * 
	 * @param date1
	 *            operand date 1
	 * @param date2
	 *            operand date 2
	 * @return 날짜 차이
	 */
	public static int getAfterDays(Date date1, Date date2) {

		return (int) ((date1.getTime() - date2.getTime()) / 86400000);
	}
}
