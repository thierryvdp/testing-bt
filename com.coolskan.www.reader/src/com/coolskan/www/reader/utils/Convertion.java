package com.coolskan.www.reader.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class Convertion {
	private final static Logger logger = Logger.getLogger(Convertion.class);

	/** A global decimal formatter :  new DecimalFormat("##0.00");<br>Used for prices */
	public final static DecimalFormat decimalFormatter = new DecimalFormat("##0.00");
	/** A global decimal formatter :  new DecimalFormat("##0.00 €");<br>Used for prices */
	public final static DecimalFormat decimalEuroFormatter = new DecimalFormat("##0.00 €");
	/** A global decimal formatter :  new DecimalFormat("###");*/
	public final static DecimalFormat longDecimalFormatter = new DecimalFormat("###");
	/** A global decimal formatter :  new DecimalFormat("##0.00 %");<br>Used for percents */
	public final static DecimalFormat percentFormater=new DecimalFormat("##0.00 %");

	// DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	/** A global decimal formatter :  new SimpleDateFormat("yyyy-MM-dd"); */
	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/** A global decimal formatter :  new SimpleDateFormat("dd/MM/yyyy"); */
	public final static SimpleDateFormat dateFormatter_dd_MM_yyyy = new SimpleDateFormat("dd/MM/yyyy");
	/** A global decimal formatter :  new SimpleDateFormat("yyyyMMdd"); */
	public final static DateFormat dateFormatter_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	/** A global decimal formatter :  new SimpleDateFormat("yyMMdd"); */
	public final static DateFormat dateFormatter_yyMMdd = new SimpleDateFormat("yyMMdd");
	/** A global decimal formatter :  new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); */
	public final static SimpleDateFormat dateFormatter_dd_MM_yyyy_hh_mm_ss = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	/** A global decimal formatter :  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); */
	public final static SimpleDateFormat dateFormatter_dd_MM_yyyy_HH_mm_ss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	/** A global decimal formatter :  new SimpleDateFormat("yyyy"); */
	public final static Format dateFormatter_yyyy= new SimpleDateFormat("yyyy");
	/** A global decimal formatter :  new SimpleDateFormat("dd-MMM"); */
	public final static DateFormat dateFormatter_dd_MMM = new SimpleDateFormat("dd-MMM");
	/** A global decimal formatter :  new SimpleDateFormat("KK:mm:ss"); */
	public final static DateFormat dateFormatter_KK_mm_ss = new SimpleDateFormat("KK:mm:ss");
	/** A global decimal formatter :  new SimpleDateFormat("dd/MM/yyyy HH:mm"); */
	public final static DateFormat dateFormatter_dd_MM_yyyy_HH_mm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	/** A global decimal formatter :  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); */
	public final static DateFormat dateFormatter_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static String cleanString(String chaine) {
		String step1=chaine.replaceAll("&", "&amp;")
				.replaceAll("'", "&apos;")
				.replaceAll("\"", "&quot;")
				.replaceAll(">", "&gt;")
				.replaceAll("<", "&lt;");
		return step1;

	}

	public static String left(String string,int nbChar) {
		if(string==null)
			return "";
		int size = string.length();
		if(size<=nbChar)
			return string;
		return string.substring(0,nbChar);
	}

	public static String toStringSansNull(Object objet) {
		if(objet==null)
			return "";
		return objet.toString();
	}

	public static String substr(String string,int numMorceau,int tailleMorceau) {
		int size = string.length();
		int startMorceau=tailleMorceau*(numMorceau-1);
		if(size<startMorceau)
			return "";
		int stopMorceau=tailleMorceau*numMorceau;
		if(size<=stopMorceau)
			return string.substring(startMorceau, size);
		return string.substring(startMorceau,stopMorceau);
	}

	public static double bigDecimal2Double (BigDecimal bD){
		if (bD == null)
			return 0;
		return bD.doubleValue();
	}

	public static BigDecimal double2BigDecimal (double db) {
		return new BigDecimal(db);
	}

	public static long toLong(String chaine)  {
		long tempVal=0L;
		try {
			tempVal=Long.parseLong(chaine.trim());
		} catch (Exception e) {
			tempVal=0L;
		}
		return tempVal;
	}

	public static BigDecimal toBigDecimal(String chaine)  {
		BigDecimal tempVal=new BigDecimal(0L);
		try {
			tempVal=new BigDecimal(chaine.trim());
		} catch (Exception e) {
			tempVal=new BigDecimal(0L);
		}
		return tempVal;
	}

	public static Double toDouble(String chaine)  {
		return toFormatedDouble(chaine, "##.##;-##.##");
	}

	public static Double toFormatedDouble(String string, String format)  {
		if(string==null||string.isEmpty()) return new Double(0L);
		DecimalFormat decimalFormater = new DecimalFormat(format);
		Double tempVal=new Double(0L);

		String str = string.trim();
		// If str contains . and , it's probably  as US Locale,
		// We want to remove Thousands separator
		if (str.contains(".") && str.contains(","))
			str = str.replace(",", "");
		// In every case we replace common Decimal separators by Locale Separator
		str = str.replaceAll("[.,]", String.valueOf(decimalFormater.getDecimalFormatSymbols().getDecimalSeparator()));

		//		if(chaine.contains(".")){
		//		tempVal = Double.valueOf(chaine);
		//		}else{
		try {
			tempVal = new Double(decimalFormater.parse(str).doubleValue());
		} catch (ParseException e) {
			logger.warn("cannot parse str " + str + " returning " + tempVal, e);
		}
		//		}

		return tempVal;
	}

	public static int toInt(String chaine)  {
		int tempVal=0;
		try {
			tempVal=Integer.parseInt(chaine.trim());
		} catch (Exception e) {
			tempVal=0;
		}
		return tempVal;
	}

	public static Date toDate(String date,String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date maDate = new Date();
		try {
			maDate=df.parse(date.trim());
		} catch (ParseException e) {
			logger.error("", e);
		}
		return maDate;
	}

	public static Date toDate(String date) {
		DateFormat df=null;
		String separ="/";
		StringBuffer dateBuf=new StringBuffer(date);
		for(int i=0;i<dateBuf.length();i++){
			if(!Character.isDigit(dateBuf.charAt(i))) {
				separ=String.valueOf(dateBuf.charAt(i));
				break;
			}
		}
		df = new SimpleDateFormat("dd"+separ+"MM"+separ+"yyyy");
		Date maDate = new Date();
		try {
			maDate=df.parse(date.trim());
			return maDate;
		} catch (ParseException e) {
			/* Avoid empty block warning */
		}
		df = new SimpleDateFormat("yyyy"+separ+"MM"+separ+"dd");
		try {
			maDate=df.parse(date.trim());
			return maDate;
		} catch (ParseException e) {
			/* Avoid empty block warning */
		}
		return new Date();
	}


	public static String date2String(Date date,String format) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String date2FormatedString(Date date) {
		if (date == null)
			return null;
		//		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormatter_dd_MM_yyyy_HH_mm.format(date);
	}

	public static String doubleToCentimes(Double v){
		Double value = new Double(v.doubleValue() * 100);
		DecimalFormat myFormatter = new DecimalFormat("######");
		myFormatter.setMinimumFractionDigits(0);
		String centimes = myFormatter.format(value);
		return centimes;
	}

	public static String UpperCaseFirstChar( String toUpper ) {
		if(toUpper==null) return null;
		return toUpper.substring(0,1).toUpperCase() + toUpper.substring(1);
	}
	public static String LowerCaseFirstChar(String toLo) {
		if(toLo==null) return null;
		return toLo.substring(0,1).toLowerCase() + toLo.substring(1);
	}

	public static String fetchPath(String _daoFullPath) {
		if(_daoFullPath==null) return null;
		String[] paths=_daoFullPath.split("\\.");
		if(paths.length<=1) return null;
		String daoFullPath="";
		for (int i = 0; i < paths.length-1; i++) {
			daoFullPath+=paths[i]+".";
		}
		return daoFullPath.substring(0, daoFullPath.length()-1);
	}

	public static String beanToDao(String _bean) {
		if(_bean==null) return null;
		String[] beans=_bean.split("\\.");
		String dao="";
		for(String bean:beans) {
			dao+=LowerCaseFirstChar(bean)+".";
		}
		return dao.substring(0, dao.length()-1);
	}

	public static String daoToBean(String _dao) {
		if(_dao==null) return null;
		String[] daos=_dao.split("\\.");
		String bean="";
		for(String dao:daos) {
			bean+=LowerCaseFirstChar(dao)+".";
		}
		return bean.substring(0, bean.length()-1);
	}

	public static String daoClientToDao(String daoClientClassName) {
		return daoClientClassName.replace("Client", "").replace(".data.", ".dao.");
	}

	public static String daoName(String _dao) {
		if(_dao==null) return null;
		String[] daos=_dao.split("\\.");
		String dao=daos[daos.length-1];
		return dao.replace("Client", "");
	}

	public static Date currentDateTime() {
		Calendar laDate = Calendar.getInstance();
		return laDate.getTime();
	}

	public static Date currentDate() {
		Calendar laDate = Calendar.getInstance();
		return resetHour(laDate).getTime();
	}

	public static Date dateAddDays(Date _date,int days) {

		Calendar date = resetHour(Calendar.getInstance());
		date.setTime(_date);
		date.add(Calendar.DAY_OF_MONTH, days);
		return date.getTime();

	}

	public static Date endOfTime() {
		Calendar laDate = Calendar.getInstance();
		laDate.set(2020, 0, 01, 0, 0, 0);
		laDate.set(Calendar.MILLISECOND, 0);
		return laDate.getTime();
	}

	public static boolean sameDay(Date date1,Date date2) {
		long madate=date1.getTime()/86400000;
		long maolddate=date2.getTime()/86400000;
		return madate==maolddate;
	}

	public static String sEncrypt(String clearString){
		// String pwe = "";
		StringBuffer buffer = new StringBuffer();
		try {
			java.security.MessageDigest d =null;
			d = java.security.MessageDigest.getInstance("SHA-1");
			d.reset();
			d.update(clearString.getBytes());
			byte[] b =  d.digest();
			for (int i = 0; i < b.length; i++) {
				// pwe = pwe + b[i];
				buffer.append(b[i]);
			}
		} catch (Exception e) {
			System.out.println("sEncrypt Exceptione :" + e);
		}
		return buffer.toString();
		//		return pwe;
	}

	public static double roundDouble(double value) {
		return Math.rint(value * 100) / 100d;
	}

	public static float roundFloat(float value) {
		Double rounded=new Double(Math.rint(value * 100) / 100d);
		return rounded.floatValue();
	}




	public static byte[] hexStringtoByteArray(String txtInHex) {
		if (txtInHex == null || txtInHex.length() == 0 || txtInHex.length() % 2 != 0)
			return null;
		byte [] txtInByte = new byte [txtInHex.length() / 2];
		int j = 0;
		for (int i = 0; i < txtInHex.length(); i += 2)
		{
			byte lb = (byte) (Byte.parseByte(txtInHex.substring(i, i+1), 16)<<4);
			byte rb = Byte.parseByte(txtInHex.substring(i+1, i+2), 16);
			txtInByte[j++] = (byte) (lb|rb);
		}
		return txtInByte;
	}

	public static String byteArrayToHexString(byte[] in) {
		// TODO le jdk propose en standard des outils pour faire ce genre de conversion
		int ch = 0x00;
		String pseudo[] = {"0", "1", "2",
				"3", "4", "5", "6", "7", "8",
				"9", "A", "B", "C", "D", "E", "F"};

		StringBuffer out = new StringBuffer(in.length*2);
		for (int i = 0; i < in.length; i++) {
			ch = ((in[i] & 0xF0) >> 4);
			ch = (ch & 0x0F);
			out.append(pseudo[ch]);
			ch = (in[i] & 0x0F);
			out.append(pseudo[ch]);
		}
		return new String(out);
	}

	public static Calendar resetHour(Calendar cal) {
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar resetHour(Date _date) {
		if(_date==null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(_date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Date computeEcheance(Date _startEcheance,int paymentTermDays,int _paymentTermDelayOnDay) {
		Date startEcheance = _startEcheance == null ? currentDateTime() : _startEcheance;
		Calendar echeance= Calendar.getInstance();
		echeance.setTime(startEcheance);
		echeance.add(Calendar.DAY_OF_MONTH, paymentTermDays);
		int paymentTermDelayOnDay = _paymentTermDelayOnDay;
		if(paymentTermDelayOnDay>=100) {
			paymentTermDelayOnDay=paymentTermDelayOnDay-100;
			echeance.set(Calendar.DAY_OF_MONTH,1);
			echeance.add(Calendar.MONTH,1);
			if(paymentTermDelayOnDay==0)
				echeance.add(Calendar.DAY_OF_MONTH, -1);
			else {
				echeance.set(Calendar.DAY_OF_MONTH,paymentTermDelayOnDay);
			}
		}
		else {
			if(paymentTermDelayOnDay!=0&&paymentTermDelayOnDay<echeance.get(Calendar.DAY_OF_MONTH)) {
				echeance.add(Calendar.MONTH, 1);
			}
			if(paymentTermDelayOnDay!=0) echeance.set(Calendar.DAY_OF_MONTH, paymentTermDelayOnDay);
		}
		return resetHour(echeance).getTime();

	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$#" + n + "s", s);
	}

	public static boolean isEmptyOrNull(String str) {
		return str == null || str.isEmpty();
	}

	public static String formatPrice(Double amount) {
		String prix = customFormat("### ##0.00;-### ##0.00", amount.doubleValue());
		return prix;
	}
	public static String formatPrice(BigDecimal amount) {
		String prix = customFormat("### ##0.00;-### ##0.00", amount.doubleValue());
		return prix;
	}
	
	public static String customFormat(String pattern, BigDecimal value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		myFormatter.setMinimumFractionDigits(2);
		return myFormatter.format(value);
	}
	

	public static String customFormat(String pattern, double value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}
	
	public static String customFormat(String pattern, float value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}

	/**
	 * Format the price and insert spaces at the left of the price. The amount
	 * of space is the padding,
	 * @param price the price to format
	 * @param padding the padding
	 * @return the formatted price as a String
	 * @throws IllegalArgumentException if the specified padding is negative
	 */
	public final static String formatPriceWithLeftPadding(double price, int padding)
			throws IllegalArgumentException {
		if (padding < 0) {
			throw new IllegalArgumentException(
					"Padding can be less than 0 : " + padding);
		}
		String toReturn = decimalFormatter.format(price);
		int p = toReturn.startsWith("-") ? padding - 1:11;
		while (toReturn.length() < p) {
			toReturn = " " + toReturn;
		}
		return toReturn;
	}

	public static Object stringToClass(Class<?> _targetClass,String _objectToCast) {
		if(_targetClass.getSimpleName().equals("int") || _targetClass.getSimpleName().equals("Integer")) {
			return new Integer(Convertion.toInt(_objectToCast));
		}
		else if (_targetClass.getSimpleName().equals("long") || _targetClass.getSimpleName().equals("Long")) {
			return new Long(Convertion.toLong(_objectToCast));
		}
		else if (_targetClass.getSimpleName().equals("String")) {
			return _objectToCast;
		}
		return null;

	}

	public static boolean toBoolean(String value) {
		if(value==null) return false;
		return value.toLowerCase().equals("true");
	}

	public static String charSeparator(int intSeparator) {
		String separator=null;
		if(intSeparator==124) {
			separator=""+(char)intSeparator;
		}
		else {
			separator = ""+((char)intSeparator);
		}
		return separator;
	}

	public static String charSeparatorForSplit(int intSeparator) {
		String separator=null;
		if(intSeparator==124) {
			separator="\\"+(char)intSeparator;
		}
		else {
			separator = ""+((char)intSeparator);
		}
		return separator;
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

	public static String clearChar(String string2clear,char char2clear) {
		String cleared="";
		for (int i = 0; i < string2clear.length(); i++) {
			if(string2clear.charAt(i)!=char2clear) {
				cleared=cleared.concat(string2clear.substring(i, i+1));
			}
		}
		return cleared;
	}

}
