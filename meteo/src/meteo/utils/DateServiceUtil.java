package meteo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateServiceUtil {
	private static Logger logger = LogManager.getLogger(DateServiceUtil.class);

	public static Date xml2Date(XMLGregorianCalendar xmlCal) {
		if (xmlCal == null) {
			return null;
		}
		return xmlCal.toGregorianCalendar().getTime();
	}

	public static List<Date> xml2Date(List<XMLGregorianCalendar> xmlCal) {
		List<Date> date = new ArrayList<>();
		if (xmlCal == null || xmlCal.size() == 0) {
			return date;
		}
		for (XMLGregorianCalendar cal : xmlCal) {
			date.add(xml2Date(cal));
		}
		return date;
	}

	public static XMLGregorianCalendar date2Xml(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar temp;
		DatatypeFactory factory;
		try {
			factory = DatatypeFactory.newInstance();
			temp = factory.newXMLGregorianCalendar().toGregorianCalendar();
			temp.setTime(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(temp);
		} catch (DatatypeConfigurationException e1) {
			logger.error(e1);
			return null;
		}
	}

	public static List<XMLGregorianCalendar> date2Xml(List<Date> date) {
		List<XMLGregorianCalendar> xmlCal = new ArrayList<>();
		if (xmlCal.size() == 0) {
			return xmlCal;
		}
		for (Date cal : date) {
			xmlCal.add(date2Xml(cal));
		}
		return xmlCal;
	}

	public static String xml2String(XMLGregorianCalendar xmlCal, DateFormat dateFormat) {
		return dateFormat.format(DateServiceUtil.xml2Date(xmlCal));
	}

	public static XMLGregorianCalendar string2Xml(String date, DateFormat dateFormat) {
		try {
			return DateServiceUtil.date2Xml(dateFormat.parse(date));
		} catch (ParseException e) {
			logger.error("", e);
			return null;
		}
	}

	public static long daysBetween(Calendar start, Calendar end) {
		// TODO : VERIFY IF THERE IS A BETTER WAY TO DO THIS
		final long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;
		// create copies
		GregorianCalendar startDate = new GregorianCalendar(Locale.getDefault());
		GregorianCalendar endDate = new GregorianCalendar(Locale.getDefault());

		// switch calendars to pure Julian mode for correct day-between calculation, from the Java API:
		// - To obtain a pure Julian calendar, set the change date to Date(Long.MAX_VALUE).
		startDate.setGregorianChange(new Date(Long.MAX_VALUE));
		endDate.setGregorianChange(new Date(Long.MAX_VALUE));

		// set them
		startDate.setTime(start.getTime());
		endDate.setTime(end.getTime());

		// force times to be exactly the same
		startDate.set(Calendar.HOUR_OF_DAY, 12);
		endDate.set(Calendar.HOUR_OF_DAY, 12);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		endDate.set(Calendar.MILLISECOND, 0);

		// now we should be able to do a "safe" millisecond/day caluclation to get the number of days
		long endMilli = endDate.getTimeInMillis();
		long startMilli = startDate.getTimeInMillis();

		// calculate # of days, finally
		long diff = (endMilli - startMilli) / MILLISECONDS_IN_DAY;

		return diff;
	}

	public static Date currentDate() {
		return DateServiceUtil.resetHour(currentCalendar()).getTime();
	}

	public static Date currentDateTime() {
		return currentCalendar().getTime();
	}

	public static long currentStamp() {
		return currentCalendar().getTimeInMillis();
	}

	public static Calendar currentCalendar() {
		return Calendar.getInstance();
		/* commenter-decommenter pour avoir une date système précise */
		//return setDate(Calendar.getInstance(), 2023, Calendar.JANUARY + 1, 12);

	}

	private static Calendar setDate(Calendar laDate, int year, int month, int day) {
		try {
			laDate.set(Calendar.YEAR, year);
			laDate.set(Calendar.MONTH, month - 1); /* la fonction compte à partir du mois 0 */
			laDate.set(Calendar.DAY_OF_MONTH, day);
		} catch (Exception e) {
			logger.error("_date:" + date2String(laDate.getTime(), "yyyy-MM-dd") + " year" + String.valueOf(year) + " month" + String.valueOf(month) + " day" + String.valueOf(day), e);
		}
		return laDate;
	}

	public final static SimpleDateFormat	dateFormat							= new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat	dateFormatter_yyyy					= new SimpleDateFormat("yyyy");
	public final static SimpleDateFormat	dateFormatter_yyyyMMdd				= new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat	dateFormatter_yyMMdd				= new SimpleDateFormat("yyMMdd");
	public final static SimpleDateFormat	dateFormatter_yyyy_MM_dd_HH_mm_ss	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public final static SimpleDateFormat	dateFormatter_yyyyMMddhhmmss		= new SimpleDateFormat("yyyyMMddhhmmss");
	public final static SimpleDateFormat	dateFormatter_dd_MMM				= new SimpleDateFormat("dd-MMM");
	public final static SimpleDateFormat	dateFormatter_dd_MM_yyyy_HH_mm		= new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public final static SimpleDateFormat	dateFormatter_dd_MM_yyyy			= new SimpleDateFormat("dd/MM/yyyy");
	public final static SimpleDateFormat	dateFormatter_dd_MM_yy				= new SimpleDateFormat("dd/MM/yy");
	public final static SimpleDateFormat	dateFormatter_dd_MM_yyyy_hh_mm_ss	= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	public final static SimpleDateFormat	dateFormatter_dd_MM_yyyy_HH_mm_ss	= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public final static SimpleDateFormat	dateFormatter_KK_mm_ss				= new SimpleDateFormat("KK:mm:ss");
	public final static SimpleDateFormat	dateFormatter_HH_mm					= new SimpleDateFormat("HH:mm");
	public final static SimpleDateFormat	dateFormatter_WeekYear				= new SimpleDateFormat("yyyy-w");

	private final static Date				creationCompufirst;
	static { // Pas la peine de recalculer 30000 fois un invariant !
		Calendar laDate = Calendar.getInstance();
		laDate.set(2008, 11, 31, 0, 0, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		creationCompufirst = laDate.getTime();
	}

	public static Date creationCompufirst() {
		return creationCompufirst;
	}

	public static Date toDate(String date, String format) {
		Date maDate = DateServiceUtil.toDateReturnNullIfException(date, format);
		if (maDate == null) {
			return currentDate();
		}
		return maDate;
	}

	public static Date toDateReturnNullIfException(String date, String format) {
		if (date == null || date.isEmpty() || date.trim().isEmpty()) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(date.trim());
		} catch (ParseException e) {
			logger.error("Can't parse date:" + date + " with format:" + format, e);
		}
		return null;
	}

	public static Date toDate(String date) {
		if (date != null && !date.isEmpty()) {
			DateFormat df = null;
			String format = "dd/MM/yyyy";
			if (date.contains("-")) {
				format = "yyyy-MM-dd";
			}
			df = new SimpleDateFormat(format);
			Date maDate = new Date();
			try {
				maDate = df.parse(date.trim());
				return maDate;
			} catch (Exception e) {
				logger.error("date:" + date, e);
			}
		}
		return new Date();
	}

	public static String date2String(Date date, String format) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String date2FormatedString(Date date) {
		if (date == null) {
			return null;
		}
		//		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormatter_dd_MM_yyyy_HH_mm.format(date);
	}

	/**
	 * convert local time to UTC
	 * if a ParseException occur the localDate is returned.
	 *
	 * @param localDate
	 * @return
	 */
	public static Date localDate2Utc(Date localDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		SimpleDateFormat parser = new SimpleDateFormat(format);

		/* Convert Local Time to UTC */
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String localTime = sdf.format(localDate);

		/* parse to date and return the date */
		try {
			Date utcTime = parser.parse(localTime);
			logger.debug("Convertion.localDate2Utc(Date localDate, String format) -> Local:" + localDate.toString() + "," + localDate.getTime() + " --> UTC time:" + utcTime.toString() + "-" + utcTime.getTime());

			return utcTime;
		} catch (ParseException e) {
			logger.error("", e);
		}
		return localDate;
	}

	public static Date utcDate2Local(Date utcDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getDefault());
		SimpleDateFormat parser = new SimpleDateFormat(format);
		parser.setTimeZone(TimeZone.getTimeZone("UTC"));

		/* Convert UTC Time to local */
		//		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
		String utcTime = sdf.format(utcDate);

		/* parse to date and return the date */
		try {
			Date localTime = parser.parse(utcTime);
			return localTime;
		} catch (ParseException e) {
			logger.error("", e);
		}

		return utcDate;
	}

	public static boolean isWorkingDay(Date _date) {
		Calendar laDate = Calendar.getInstance();
		laDate.setTime(_date);
		int dayOfWeek = laDate.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek != 1 && dayOfWeek != 7;
	}

	public static Date currentDateTimeRoundMinute() {
		Calendar laDate = Calendar.getInstance();
		laDate.set(Calendar.SECOND, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		return laDate.getTime();
	}

	public static Date roundDownDateTimeMinute(Date _date) {
		Calendar laDate = Calendar.getInstance();
		laDate.setTime(_date);
		laDate.set(Calendar.SECOND, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		return laDate.getTime();
	}

	public static Date roundUpDateTimeMinute(Date _date) {
		Calendar laDate = Calendar.getInstance();
		laDate.setTime(_date);
		laDate.set(Calendar.SECOND, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		laDate.add(Calendar.MINUTE, 1);
		return laDate.getTime();
	}

	public static double dateProrataPrice(double price, Date date1, Date date2) {
		long days = DateServiceUtil.dateDiff(date1, date2);
		if (days < 0) {
			days = Math.abs(days);
		}
		long totDays = DateServiceUtil.dateDiff(DateServiceUtil.dateSetDayOfMonth(DateServiceUtil.dateAddMonths(date1, 1), 1), DateServiceUtil.dateSetDayOfMonth(date1, 1));
		return Convertion.roundDouble(price * days / totDays);
	}

	public static Date dateNextPeriod(Date startOldPeriod, long numberOfMonth) {
		// ajouter le nombre de mois de la subscription
		Date nexDate = DateServiceUtil.dateAddMonths(startOldPeriod, (int) numberOfMonth);
		if (numberOfMonth == 1) {
			// Mensuel : booking 1er jour du mois suivant
			nexDate = DateServiceUtil.dateSetDayOfMonth(nexDate, 1);
		}
		// sinon rien à faire
		return nexDate;
	}

	public static String datePeriod(Date startPeriod, long numberOfMonth) {
		// ajouter le nombre de mois de la subscription
		String msg = "du " + date2String(startPeriod, "dd/MM/yyyy");
		// ajouter le nombre de mois de la période
		Date nextDate = DateServiceUtil.dateAddMonths(startPeriod, (int) numberOfMonth);

		nextDate = DateServiceUtil.dateAddDays(nextDate, -1);
		msg += " au " + date2String(nextDate, "dd/MM/yyyy");
		return msg;
	}

	public static long dateDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}
		long madate1 = DateServiceUtil.resetHour(date1).getTimeInMillis() / 86400000;
		long madate2 = DateServiceUtil.resetHour(date2).getTimeInMillis() / 86400000;
		return madate1 - madate2;
	}

	public static boolean dateSameDay(Date date1, Date date2) {
		long madate1 = DateServiceUtil.resetHour(date1).getTimeInMillis() / 86400000;
		long madate2 = DateServiceUtil.resetHour(date2).getTimeInMillis() / 86400000;
		return madate1 == madate2;
	}

	public static boolean dateSameMonth(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return dateSameDay(DateServiceUtil.dateSetDayOfMonth(date1, 1), DateServiceUtil.dateSetDayOfMonth(date2, 1));
	}

	public static int dateGetDayOfMonth(Date _date) {
		Calendar laDate = Calendar.getInstance();
		try {
			laDate.setTime(_date);
			return laDate.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd"), e);
		}
		return 0;
	}

	public static Date dateSetDayOfMonth(Date _date, int day) {
		Calendar laDate = Calendar.getInstance();
		try {
			laDate.setTime(_date);
			laDate.set(Calendar.DAY_OF_MONTH, day);
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd") + " day" + String.valueOf(day), e);
		}
		return laDate.getTime();
	}

	public static Date dateSetFirstDayOfMonth(Date _date, int _month) {
		Calendar laDate = Calendar.getInstance();
		try {
			laDate.setTime(_date);
			laDate.set(Calendar.DAY_OF_MONTH, 1);
			/* en java le premier mois c'est 0 ! */
			laDate.set(Calendar.MONTH, _month - 1);
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd") + " day" + String.valueOf(_month), e);
		}
		return laDate.getTime();
	}

	public static Date dateSetLastDayOfMonth(Date _date, int _month) {
		try {
			Date _lastDayOfMonth = dateSetFirstDayOfMonth(_date, _month);
			_lastDayOfMonth = dateAddMonths(_date, 1);
			return dateAddDays(_lastDayOfMonth, -1);
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd") + " day" + String.valueOf(_month), e);
		}
		return creationCompufirst;
	}

	/**
	 * utiliser pour calculer les dates de début et de fin de période d'abonnement pour les lignes de commandes CLOUD. 
	 * Elle est presque pareil que la méthode num 1 mais comme des modifs ont été faites dessus 
	 * et que bcp trop de méthodes appelent cette méthode alors une nouvelle a été faites pour eviter les effets de bord
	 * @param _date 
	 * @param _month donnez le mois à partir de la classe Calendar; exple : Calendar.JANUARY.   Ca évitera les décalages d'un mois.
	 * @return la date avec le dernier jour du mois donné
	 */
	public static Date dateSetLastDayOfMonth2(Date _date, int _month) {
		try {
			Date _lastDayOfMonth = dateSetFirstDayOfMonth(_date, _month);
			_lastDayOfMonth = dateAddMonths(_lastDayOfMonth, 1);
			return dateAddDays(_lastDayOfMonth, -1);
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd") + " day" + String.valueOf(_month), e);
		}
		return creationCompufirst;
	}

	public static int dateGetMonth(Date _date) {
		Calendar laDate = Calendar.getInstance();
		try {
			laDate.setTime(_date);
			return laDate.get(Calendar.MONTH) + 1; // il semble que ça commence à 0 !
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd"), e);
		}
		return 0;
	}

	public static int dateGetYear(Date _date) {
		Calendar laDate = Calendar.getInstance();
		try {
			laDate.setTime(_date);
			return laDate.get(Calendar.YEAR);
		} catch (Exception e) {
			logger.error("_date:" + date2String(_date, "yyyy-MM-dd"), e);
		}
		return 0;
	}

	public static Date dateAddMonths(Date _date, int months) {

		Calendar date = DateServiceUtil.resetHour(Calendar.getInstance());
		date.setTime(_date);
		date.add(Calendar.MONTH, months);
		return date.getTime();
	}

	public static Date dateAddYears(Date _date, int years) {

		Calendar date = DateServiceUtil.resetHour(Calendar.getInstance());
		date.setTime(_date);
		date.add(Calendar.YEAR, years);
		return date.getTime();
	}

	public static Date dateAddDays(Date _date, int days) {

		Calendar date = DateServiceUtil.resetHour(Calendar.getInstance());
		date.setTime(_date);
		date.add(Calendar.DAY_OF_MONTH, days);
		return date.getTime();
	}

	public static Date dateAddMinutes(Date _date, int minutes) {
		Calendar date = DateServiceUtil.resetHour(Calendar.getInstance());
		date.setTime(_date);
		date.add(Calendar.MINUTE, minutes);
		return date.getTime();
	}

	public static Date dateAddDaysYear(Date _date, int days) {

		Calendar date = DateServiceUtil.resetHour(Calendar.getInstance());
		date.setTime(_date);
		date.add(Calendar.DAY_OF_YEAR, days);
		return date.getTime();
	}

	public static Date endOfTime() {
		Calendar laDate = Calendar.getInstance();
		laDate.set(2050, 0, 01, 0, 0, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		return laDate.getTime();
	}

	public static Date endOfLifeUntilStock() {
		Calendar laDate = Calendar.getInstance();
		laDate.set(2999, 0, 01, 0, 0, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		return laDate.getTime();
	}

	public static Calendar resetHour(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar resetHour(Date _date) {
		Date date = _date == null ? creationCompufirst() : _date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date computeEcheance(Date _startEcheance, int paymentTermDays, int _paymentTermDelayOnDay) {
		Date startEcheance = _startEcheance == null ? currentDateTime() : _startEcheance;
		Calendar echeance = Calendar.getInstance();
		echeance.setTime(startEcheance);
		/** on ajoute paymentTermDays jours de delai de paiment **/
		echeance.add(Calendar.DAY_OF_MONTH, paymentTermDays);
		int paymentTermDelayOnDay = _paymentTermDelayOnDay;

		/** on va a la fin du mois pour payer le paymentTermDelayOnDay-100 **/
		if (paymentTermDelayOnDay >= 100) {
			paymentTermDelayOnDay = paymentTermDelayOnDay - 100;
			echeance.set(Calendar.DAY_OF_MONTH, 1);
			echeance.add(Calendar.MONTH, 1);
			if (paymentTermDelayOnDay == 0) {
				echeance.add(Calendar.DAY_OF_MONTH, -1);
			}
			else {
				echeance.set(Calendar.DAY_OF_MONTH, paymentTermDelayOnDay);
			}
		}

		/** on paye le paymentTermDelayOnDay*(-1) quoi qu'il arrive meme si on recule le jour **/
		else if (paymentTermDelayOnDay < 0) {
			echeance.set(Calendar.DAY_OF_MONTH, paymentTermDelayOnDay * -1);
		}

		/** on paye le paymentTermDelayOnDay sauf si dans le mois on est apres ce jour la car alors on paye le paymentTermDelayOnDay du mois suivant **/
		else if (paymentTermDelayOnDay != 0) {
			if (paymentTermDelayOnDay < echeance.get(Calendar.DAY_OF_MONTH)) {
				echeance.add(Calendar.MONTH, 1);
			}
			echeance.set(Calendar.DAY_OF_MONTH, paymentTermDelayOnDay);
		}
		return resetHour(echeance).getTime();

	}

	public static Date nextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setLenient(false);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static Date yesterday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setLenient(false);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static String milli2hhmmss(String _duration) {
		long duration = Convertion.toLong(_duration);
		long dsec = duration / 1000;
		long dmin = dsec / 60;
		long dheure = dmin / 60;
		dmin = dmin - dheure * 60;
		dsec = dsec - dheure * 60 - dmin * 60;
		return dheure + ":" + dmin + ":" + dsec;
	}

	/**
	 * calcule le prix proratisé pour une commande
	 *
	 * @param bookDate
	 * @param _startContrat
	 * @param _stopContrat
	 * @param normalPrice
	 * @param annual
	 * @return
	 */
	public static double computePriceProrata(Date bookDate, Date _startContrat, Date _stopContrat, double normalPrice, long numberOfMonth, boolean ressource, boolean autoSub) {
		if (numberOfMonth > 1) { // on ne proratise que le mensuel
			return normalPrice;
		}
		if (ressource) { // les ressources sont de la conso donc on facture l consomé quelque soit la periode
			return normalPrice;
		}
		if (bookDate != null && _startContrat != null && _stopContrat != null) {
			Date startContrat = resetHour(_startContrat).getTime();
			Date stopContrat = resetHour(_stopContrat).getTime();
			Date p1 = resetHour(dateAddDays(dateSetDayOfMonth(bookDate, 1), -1)).getTime();  // le dernier jour du mois precedent le mois de booking
			Date p2 = resetHour(dateSetDayOfMonth(dateAddMonths(bookDate, 1), 1)).getTime(); // le premier jour du mois suivant le mois de booking

			long nbJoursPeriode = dateDiff(p2, p1) - 1;
			if (startContrat.after(p1) && startContrat.before(p2) && stopContrat.after(p1) && stopContrat.before(p2)) { // on a meme pas fait un mois entier
				return Convertion.roundDouble(normalPrice * (dateDiff(stopContrat, startContrat) + 1) / nbJoursPeriode);
			}
			if (startContrat.after(p1) && startContrat.before(p2)) { // demarrage contrat
				return Convertion.roundDouble(normalPrice * dateDiff(p2, startContrat) / nbJoursPeriode);
			}
			if (stopContrat.after(p1) && stopContrat.before(p2)) { // fin contrat
				if (!autoSub) {
					return Convertion.roundDouble(normalPrice * dateDiff(stopContrat, p1) / nbJoursPeriode);
				}
				else {
					return normalPrice;
				}
			}
			if (stopContrat.before(bookDate)) { // le contrat s'est fini avant on doit plus payer
				return 0;
			}
		}
		return normalPrice;
	}

	public static double computePriceProrataRegleDeTrois(Date bookDate, Date _startContrat, Date _stopContrat, double normalPrice, boolean ressource) {
		if (ressource) { // les ressources sont de la conso donc on facture l consomé quelque soit la periode
			return normalPrice;
		}
		if (bookDate != null && _startContrat != null && _stopContrat != null) {
			long dureeContratJour = dateDiff(_stopContrat, _startContrat);
			long dureeProrataJour = dateDiff(_stopContrat, bookDate);
			return Convertion.roundDouble(normalPrice / dureeContratJour * dureeProrataJour);
		}
		return normalPrice;
	}

	public static boolean dateMustBeUpdated(Date newValue, Date OldValue) {
		return newValue != null && OldValue == null || newValue.compareTo(OldValue) != 0;
	}

	/**
	 * convertie un objet Date en objet LocalDate
	 * @param dateToConvert
	 * @return LocalDate converted
	 */
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * compare la date donnée en paramètre avec la datz courante + 1 an date à date
	 * On compare le jour, le mois et l'année. Pas les heures, minutes, ... 
	 * @param calendarToCompare 
	 * @return true si la date donnée est égal à la date courante + 1 an
	 * et false si c'est pas le cas.
	 */
	public static boolean isequalsTonextYear(Calendar calendarToCompare) {
		int day = DateServiceUtil.currentCalendar().get(Calendar.DAY_OF_MONTH);
		int month = DateServiceUtil.currentCalendar().get(Calendar.MONTH);
		int year = DateServiceUtil.currentCalendar().get(Calendar.YEAR) + 1;

		if (calendarToCompare.get(Calendar.DAY_OF_MONTH) == day &&
				calendarToCompare.get(Calendar.MONTH) == month &&
				calendarToCompare.get(Calendar.YEAR) == year) {
			return true;
		}
		return false;
	}

	/**
	 * calcul le nombre de mois entre 2 dates données 
	 * pour ce faire on se base pas par rapport aux nombres de jours, <br/>
	 *mais plutot par rapport aux nombre de mois <br/>
	 *(exemple : il y a 30 jours entre les 2 dates donc ça fait 1 mois : on ne fait pas ça ici<br/>
	 *on a une date le 14/05 et une 2e le 10/06 donc ça nous fait 1 mois : on fait ça ici 
	 * @param calendar1 date de début 
	 * @param calendar2 date de fin
	 * @return le nombre de mois entre les 2 dates données  en int
	 */
	public static int computeMonthRemaining(Calendar calendar1, Calendar calendar2) {

		if (calendar1.get(Calendar.DAY_OF_MONTH) > calendar2.get(Calendar.DAY_OF_MONTH)) {
			calendar1.setTime(dateSetFirstDayOfMonth(calendar1.getTime(), calendar1.get(Calendar.MONTH)));
			calendar2.setTime(dateSetFirstDayOfMonth(calendar2.getTime(), calendar2.get(Calendar.MONTH)));
		}

		Period diifDate = Period.between(convertToLocalDateViaInstant(calendar1.getTime()), convertToLocalDateViaInstant(calendar2.getTime()));
		int goodMonth = Math.abs(diifDate.getYears() * 12 + diifDate.getMonths());

		return goodMonth;
	}

}
