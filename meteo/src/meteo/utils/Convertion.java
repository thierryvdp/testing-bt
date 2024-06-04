package meteo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Convertion {
	private final static Logger			logger					= LogManager.getLogger(Convertion.class);

	public static final long			unJourMilli				= 86400000;

	public final static DecimalFormat	decimalFormatter		= new DecimalFormat("##0.00");
	public final static DecimalFormat	decimalEuroFormatter	= new DecimalFormat("##0.00 €");
	public final static DecimalFormat	decimalLongFormatter	= new DecimalFormat("###");
	public final static DecimalFormat	decimalPriceFormater	= new DecimalFormat("### ##0.00;-### ##0.00");
	public final static DecimalFormat	percentFormater			= new DecimalFormat("##0.00 %");
	public final static DecimalFormat	percentShortFormater	= new DecimalFormat("##0 %");

	public static String cleanString(String chaine) {
		String step1 = chaine.replaceAll("&", "&amp;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;").replaceAll(">", "&gt;").replaceAll("<", "&lt;");
		return step1;

	}

	public static String addUrlParam(String paramName, String paramValue) {
		try {
			return URLEncoder.encode(paramName, "UTF-8") + "=" + URLEncoder.encode(paramValue, "UTF-8");
		} catch (Exception e) {
			logger.error("paramName:" + paramName + " paramValue:" + paramValue, e);
		}
		return "";
	}

	public static String cleanCRLF(String chaine) {
		if (chaine == null) {
			return "";
		}
		return chaine.replaceAll("\n", "<br/>").replaceAll("\r", "").replaceAll("|", "");
	}

	public static String cleanSlashAndBSlash(String chaine) {
		if (chaine == null) {
			return "";
		}
		String resu = "";
		for (int i = 0; i < chaine.length(); i++) {
			char carac = chaine.charAt(i);
			int ascii = carac;
			if (ascii == 47 || ascii == 92) { //  ie / \
				resu += "_";
			}
			else {
				resu += carac;
			}
		}
		logger.debug(resu);
		return resu;
	}

	public static String cleanFileName(String chaine) {
		if (chaine == null) {
			return "";
		}
		String resu = "";
		for (int i = 0; i < chaine.length(); i++) {
			char carac = chaine.charAt(i);
			int ascii = carac;
			if (ascii == 45 || ascii == 46 || ascii == 47 || ascii == 92 || ascii == 95 || ascii == 126) { //  ie - . / \ _ ~
				resu += carac;
			}
			else if (ascii >= 48 && ascii <= 57 || ascii >= 65 && ascii <= 90 || ascii >= 97 && ascii <= 122) { // A-Z a-z
				resu += carac;
			}
			else if (carac == 'à') {
				resu += 'a';
			}
			else if (carac == 'â') {
				resu += 'a';
			}
			else if (carac == 'é') {
				resu += 'e';
			}
			else if (carac == 'è') {
				resu += 'e';
			}
			else if (carac == 'ê') {
				resu += 'e';
			}
			else if (carac == 'î') {
				resu += 'i';
			}
			else if (carac == 'ô') {
				resu += 'o';
			}
			else if (carac == 'ù') {
				resu += 'u';
			}
			else if (carac == 'û') {
				resu += 'u';
			}
			else if (carac == ' ') {
				resu += '_';
			}
			else if (carac == ':' && i == 1) {
				resu += ':'; // separateur de disque windows
			}
		}
		logger.debug(resu);
		return resu;
	}

	public static String left(String string, int nbChar) {
		if (string == null) {
			return "";
		}
		int size = string.length();
		if (size <= nbChar) {
			return string;
		}
		return string.substring(0, nbChar);
	}

	public static String toStringSansNull(Object objet) {
		if (objet == null) {
			return "";
		}
		return objet.toString().trim();
	}

	public static String getNotNullString(String chaine) {
		if (chaine == null || chaine.isEmpty()) {
			return "";
		}
		return chaine;
	}

	/**
	 *
	 * @param string
	 * @param numMorceau commence à 1
	 * @param tailleMorceau
	 * @return
	 */
	public static String substr(String string, int numMorceau, int tailleMorceau) {
		if (string == null) {
			return "";
		}
		int size = string.length();
		int startMorceau = tailleMorceau * (numMorceau - 1);
		if (size <= startMorceau) {
			return "";
		}
		int stopMorceau = tailleMorceau * numMorceau;
		if (size <= stopMorceau) {
			return string.substring(startMorceau, size);
		}
		return string.substring(startMorceau, stopMorceau);
	}

	public static double bigDecimal2Double(BigDecimal bD) {
		if (bD == null) {
			return 0;
		}
		return bD.doubleValue();
	}

	public static BigDecimal double2BigDecimal(double db) {
		try {
			return BigDecimal.valueOf(db);
		} catch (Exception e) {
			logger.error("double:" + db, e);
		}
		return BigDecimal.valueOf(0);
	}

	public static long toLong(String chaine) {
		if (chaine == null || chaine.isEmpty() || chaine.equals("null")) {
			return 0;
		}
		long tempVal = 0L;
		try {
			String[] splitted = normalizeStringNumber(chaine).split(split);
			tempVal = Long.parseLong(splitted[0].trim());
		} catch (Exception e) {
			logger.debug("cannot parse str " + chaine + " returning " + tempVal + "split=" + split, e);
		}
		return tempVal;
	}

	public final static String split = getSplitStringForNumber();

	private static String getSplitStringForNumber() {
		String _split = String.valueOf(normalizedFormat().getDecimalFormatSymbols().getDecimalSeparator());
		if (_split.equals(".")) {
			_split = "\\.";
		}
		return _split;
	}

	public static double centimesToEuroDouble(String centimes) {
		double cent = toDouble(centimes);
		cent = cent / 100;
		return cent;
	}

	public static BigDecimal centimesToEuroBigDecimal(String centimes) {
		return BigDecimal.valueOf(centimesToEuroDouble(centimes));
	}

	public static BigDecimal toBigDecimal(String chaine) {
		BigDecimal tempVal = BigDecimal.valueOf(0);

		if (chaine == null || chaine.isEmpty()) {
			return tempVal;
		}

		try {
			double doubleChaine = toDouble(chaine); // on passe par la pour eviter les problemes de separateurs ,.
			tempVal = BigDecimal.valueOf(doubleChaine);
		} catch (Exception e) {
			logger.warn("cannot parse str " + chaine + " returning " + tempVal, e);
		}
		return tempVal;
	}

	public static Byte toByte(String chaine) {
		try {
			if (chaine != null && !chaine.isEmpty()) {
				return new Byte(chaine);
			}
		} catch (Exception e) {
			logger.warn("cannot parse str " + chaine + " to Byte.", e);
		}
		return 0;
	}

	public static Double toDouble(String chaine) {
		return toFormatedDouble(chaine, normalizedFormat().toPattern());
	}

	public static Double toFormatedDouble(String string, String format) {
		if (string == null || string.isEmpty()) {
			return new Double(0L);
		}
		Double tempVal = new Double(0L);
		try {
			tempVal = new Double(normalizedFormat(format).parse(normalizeStringNumber(string)).doubleValue());
		} catch (Exception e) {
			logger.warn("cannot parse str " + string + " returning " + tempVal, e);
		}
		return tempVal;
	}

	public static int toInt(String chaine) {
		if (chaine == null || chaine.isEmpty() || chaine.equals("null")) {
			return 0;
		}
		int tempVal = 0;
		try {
			String[] splitted = normalizeStringNumber(chaine).split(split);
			tempVal = Integer.parseInt(splitted[0].trim());
		} catch (Exception e) {
			logger.info("cannot parse str " + chaine + " returning " + tempVal + "split=" + split, e);
		}
		return tempVal;
	}

	public static DecimalFormat normalizedFormat() {
		return normalizedFormat(null);
	}

	public static DecimalFormat normalizedFormat(String format) {
		if (format == null || format.isEmpty()) {
			return new DecimalFormat("##.####;-##.####");
		}
		return new DecimalFormat(format);
	}

	public static boolean isNumber(String _number) {
		return NumberUtils.isNumber(_number);
	}

	public static boolean isDigit(String _number) {
		return NumberUtils.isDigits(_number);
	}

	public static String normalizeStringNumber(String number) {
		if (!number.contains(".") && !number.contains(",")) {
			return number.trim();
		}
		String str = number.trim();
		// If str contains . and , it's probably  as US Locale,
		// We want to remove Thousands separator
		if (str.contains(".") && str.contains(",")) {
			str = str.replace(",", "");
		}
		// In every case we replace common Decimal separators by Locale Separator
		str = str.replaceAll("[.,]", String.valueOf(normalizedFormat().getDecimalFormatSymbols().getDecimalSeparator()));
		if (str.startsWith(String.valueOf(normalizedFormat().getDecimalFormatSymbols().getDecimalSeparator()))) {
			str = "0" + str;
		}
		return str;
	}

	public static String doubleToCentimes(Double v) {
		if (v == null) {
			return "0.00";
		}
		Double value = new Double(v.doubleValue() * 100);
		DecimalFormat myFormatter = new DecimalFormat("######");
		myFormatter.setMinimumFractionDigits(0);
		String centimes = myFormatter.format(value);
		return centimes;
	}

	public static String UpperCaseFirstChar(String toUpper) {
		if (toUpper == null) {
			return null;
		}
		return toUpper.substring(0, 1).toUpperCase() + toUpper.substring(1);
	}

	public static String LowerCaseFirstChar(String toLo) {
		if (toLo == null) {
			return null;
		}
		return toLo.substring(0, 1).toLowerCase() + toLo.substring(1);
	}

	public static String fetchPath(String _daoFullPath) {
		if (_daoFullPath == null) {
			return null;
		}
		String[] paths = _daoFullPath.split("\\.");
		if (paths.length <= 1) {
			return null;
		}
		String daoFullPath = "";
		for (int i = 0; i < paths.length - 1; i++) {
			daoFullPath += paths[i] + ".";
		}
		return daoFullPath.substring(0, daoFullPath.length() - 1);
	}

	public static String beanToDao(String _bean) {
		if (_bean == null) {
			return null;
		}
		String[] beans = _bean.split("\\.");
		String dao = "";
		for (String bean : beans) {
			dao += LowerCaseFirstChar(bean) + ".";
		}
		return dao.substring(0, dao.length() - 1);
	}

	public static String daoToBean(String _dao) {
		if (_dao == null) {
			return null;
		}
		String[] daos = _dao.split("\\.");
		String bean = "";
		for (String dao : daos) {
			bean += LowerCaseFirstChar(dao) + ".";
		}
		return bean.substring(0, bean.length() - 1);
	}

	public static String daoClientToDao(String daoClientClassName) {
		return daoClientClassName.replace("Client", "").replace(".data.", ".dao.");
	}

	public static String daoName(String _dao) {
		if (_dao == null) {
			return null;
		}
		String[] daos = _dao.split("\\.");
		String dao = daos[daos.length - 1];
		return dao.replace("Client", "");
	}

	public static String sEncrypt(String clearString) {
		// String pwe = "";
		StringBuffer buffer = new StringBuffer();
		try {
			java.security.MessageDigest d = null;
			d = java.security.MessageDigest.getInstance("SHA-1");
			d.reset();
			d.update(clearString.getBytes());
			byte[] b = d.digest();
			for (byte element : b) {
				// pwe = pwe + b[i];
				buffer.append(element);
			}
		} catch (Exception e) {
			System.out.println("sEncrypt Exceptione :" + e);
		}
		return buffer.toString();
		//		return pwe;
	}

	public static String officePwEncrypt(String pw) {
		byte[] ciphertext = null;
		StringBuffer buffer = new StringBuffer();

		try {
			Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aes.init(Cipher.ENCRYPT_MODE, generateSecretKey());
			ciphertext = aes.doFinal(pw.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Arrays.toString(ciphertext);
	}

	public static String officePwDecrypt(String encodedPw) {
		String cleartext = null;
		try {
			Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aes.init(Cipher.DECRYPT_MODE, generateSecretKey());
			byte[] ciphertext = hexStringToByteArray(encodedPw);
			cleartext = new String(aes.doFinal(ciphertext));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cleartext;
	}

	private static SecretKeySpec generateSecretKey() throws NoSuchAlgorithmException {
		String passphrase = "this is the passphrase to generate en secret key for our needs";
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(passphrase.getBytes());
		SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
		System.out.println(key.getEncoded());
		return key;
	}

	private static byte[] hexStringToByteArray(String s) {
		String[] byteValues = s.substring(1, s.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];

		for (int i = 0, len = bytes.length; i < len; i++) {
			bytes[i] = Byte.parseByte(byteValues[i].trim());
		}
		return bytes;
	}

	public static double roundDouble(double value) {
		//		return Math.rint(value * 100) / 100d;
		return roundDigit(value, 2);
	}

	public static double roundDigit(double value, int digit) {
		double digitDouble = Math.pow(10, digit);
		long digitInt = (long) digitDouble;
		long rounded = (long) Math.rint(value * digitInt);
		return rounded / digitDouble;
	}

	public static float roundFloat(float value) {
		Double rounded = new Double(Math.rint(value * 100) / 100d);
		return rounded.floatValue();
	}


	public static HashMap<String, String> getMethod2Map(Object o) {
		HashMap<String, String> methodeMap = new LinkedHashMap<>();
		if (o != null) {
			methodeMap.put("Class", o.getClass().getSimpleName());
			Class<?> oClazz = o.getClass();
			Method methods[] = oClazz.getMethods();
			Method method = null;
			for (Method method2 : methods) {
				method = method2;
				try {
					if (!method.getName().startsWith("get") && !method.getName().startsWith("is")) {
						continue;
					}
					if (method.getName().startsWith("getClass")) {
						continue;
					}
					methodeMap.put(method.getName(), String.valueOf(method.invoke(o, null)));
				} catch (Exception e) {
					methodeMap.put(method.getName(), "<Exception:" + e.toString() + ">");
				}
			}
		}
		return methodeMap;
	}


	public static byte[] hexStringtoByteArray(String txtInHex) {
		if (txtInHex == null || txtInHex.length() == 0 || txtInHex.length() % 2 != 0) {
			return null;
		}
		byte[] txtInByte = new byte[txtInHex.length() / 2];
		int j = 0;
		for (int i = 0; i < txtInHex.length(); i += 2) {
			byte lb = (byte) (Byte.parseByte(txtInHex.substring(i, i + 1), 16) << 4);
			byte rb = Byte.parseByte(txtInHex.substring(i + 1, i + 2), 16);
			txtInByte[j++] = (byte) (lb | rb);
		}
		return txtInByte;
	}

	public static String byteArrayToHexString(byte[] in) {
		// TODO le jdk propose en standard des outils pour faire ce genre de conversion
		int ch = 0x00;
		String pseudo[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

		StringBuffer out = new StringBuffer(in.length * 2);
		for (byte element : in) {
			ch = (element & 0xF0) >> 4;
			ch = ch & 0x0F;
			out.append(pseudo[ch]);
			ch = element & 0x0F;
			out.append(pseudo[ch]);
		}
		return new String(out);
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$#" + n + "s", s);
	}

	public static boolean isEmptyOrNull(String str) {
		return str == null || str.isEmpty() || str.equalsIgnoreCase("--");
	}

	public static String formatDouble(Double amount) {
		DecimalFormat myFormatter = new DecimalFormat("##0.00");
		return myFormatter.format(amount);
	}

	public static String formatDoubleNeg(Double amount) {
		DecimalFormat myFormatter = new DecimalFormat("-##0.00");
		return myFormatter.format(amount);
	}

	public static String formatPrice(Double amount) {
		return decimalPriceFormater.format(amount);
	}

	public static String formatPrice(BigDecimal amount) {
		return decimalPriceFormater.format(amount);
	}

	public static String formatMargin(Double amount) {
		return percentFormater.format(amount);
	}

	/**
	 * Format the price and insert spaces at the left of the price. The amount
	 * of space is the padding,
	 *
	 * @param price the price to format
	 * @param padding the padding
	 * @return the formatted price as a String
	 * @throws IllegalArgumentException if the specified padding is negative
	 */
	public final static String formatPriceWithLeftPadding(double price, int padding) throws IllegalArgumentException {
		if (padding < 0) {
			throw new IllegalArgumentException("Padding can be less than 0 : " + padding);
		}
		String toReturn = decimalFormatter.format(price);
		int p = toReturn.startsWith("-") ? padding - 1 : 11;
		while (toReturn.length() < p) {
			toReturn = " " + toReturn;
		}
		return toReturn;
	}

	public static Object stringToClass(Class<?> _targetClass, String _objectToCast) {
		if (_targetClass.getSimpleName().equals("int") || _targetClass.getSimpleName().equals("Integer")) {
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
		if (value == null) {
			return false;
		}
		return value.toLowerCase().equals("true");
	}

	public static String charSeparator(int intSeparator) {
		String separator = null;
		if (intSeparator == 124) {
			separator = "" + (char) intSeparator;
		}
		else {
			separator = "" + (char) intSeparator;
		}
		return separator;
	}

	public static String charSeparatorForSplit(int intSeparator) {
		String separator = null;
		if (intSeparator == 124) {
			separator = "\\" + (char) intSeparator;
		}
		else {
			separator = "" + (char) intSeparator;
		}
		return separator;
	}

	public static String getSplitOccurrence(String stringToSplit, String regex, int occurence) {
		String[] sp = stringToSplit.split(regex);
		if (sp.length > occurence) {
			return sp[occurence];
		}
		return null;
	}

	public static String getSplitLastMember(String stringToSplit, String regex) {
		if (stringToSplit == null) {
			return null;
		}
		String[] sp = stringToSplit.split(regex);
		return sp[sp.length - 1];
	}

	public static String clearChar(String string2clear, char char2clear) {
		String cleared = "";
		for (int i = 0; i < string2clear.length(); i++) {
			if (string2clear.charAt(i) != char2clear) {
				cleared = cleared.concat(string2clear.substring(i, i + 1));
			}
		}
		return cleared;
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
	}

	public static boolean isMac() {
		return System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
	}

	public static boolean isLinux() {
		return System.getProperty("os.name").toLowerCase().indexOf("sunos") >= 0;
	}

	public static boolean isSolaris() {
		return System.getProperty("os.name").toLowerCase().indexOf("nix") >= 0;
	}

	public static Object caste(Class _clazz, String value) {
		String clazz = _clazz.getName();
		if (clazz.equals("int") || clazz.endsWith("Integer")) {
			return Convertion.toInt(value);
		}
		else if (clazz.equals("long") || clazz.endsWith("Long")) {
			return Convertion.toLong(value);
		}
		else if (clazz.equals("byte") || clazz.endsWith("Byte")) {
			return Convertion.toByte(value);
		}
		else if (clazz.equals("double") || clazz.endsWith("Double")) {
			return Convertion.toDouble(value);
		}
		else if (clazz.endsWith("BigDecimal")) {
			return Convertion.toBigDecimal(value);
		}
		else if (clazz.endsWith("Date")) {
			return DateServiceUtil.toDate(value);
		}
		else if (clazz.equals("boolean") || clazz.endsWith("Boolean")) {
			return Convertion.toBoolean(value);
		}
		return value;
	}

	/**
	 * cette procedure crée une String xml
	 * si l'object est un bean standard avec des champs private des getters et des setters
	 * si il manque un setter ou un getter le champ ne sera pas présent
	 * il faut que la classe soit annotée avec @XmlRootElement()
	 * il faut que la classe ait un constructeur sans arguments
	 * attention ca plante sur les IClient quand un truc est pas fetché
	 *
	 * @param object
	 * @return
	 */
	public static String toXML(Object object) {
		if (object == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(object, out);
			return out.toString();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * cette procédure loade un classe à partir d'une string xml
	 * si l'object est un bean standard avec des champs private des getters et des setters
	 * si il manque un setter ou un getter ca load pas
	 * ça doit pas marcher pour un singleton
	 * il faut que la classe soit annotée avec @XmlRootElement()
	 * il faut que la classe ait un constructeur sans arguments
	 * attention ca plante sur les IClient quand un truc est pas fetché
	 *
	 * @param targetClazz
	 * @param xml
	 * @return
	 */
	public static Object fromXML(Class targetClazz, String xml) {
		if (isEmptyOrNull(xml)) {
			return null;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(targetClazz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}

	public static String debugMsg(String label, Object objet) {
		String white = "\n";
		if (label != null) {
			if (label.length() < 30) {
				for (int i = 0; i < 30 - label.length(); i++) {
					white += " ";
				}
				white += label + ":";
			}
			else {
				white += substr(label, 1, 30) + ":";
			}
		}
		else {
			white += "                              :";
		}
		if (objet != null) {
			white += objet.toString();
		}
		return white;
	}

	public static boolean isValidRib(String codebanque, String codeguichet, String numerocompte, String cle) {
		//		return isValidIban(getIBAN(codebanque, codeguichet, numerocompte, cle));
		try {
			if (codebanque == null || codeguichet == null || numerocompte == null || cle == null) {
				return false;
			}
			String rib = codebanque + codeguichet + numerocompte + cle;
			StringBuilder extendedRib = new StringBuilder(rib.length());
			for (char currentChar : rib.toCharArray()) {
				//Works on base 36
				int currentCharValue = Character.digit(currentChar, Character.MAX_RADIX);
				//Convert character to simple digit
				extendedRib.append(currentCharValue < 10 ? currentCharValue : (currentCharValue + (int) StrictMath.pow(2, (currentCharValue - 10) / 9)) % 10);
			}
			BigInteger fullRib = new BigInteger(extendedRib.toString());

			return (fullRib.remainder(IBANNUMBER_MAGIC_NUMBER).intValue()) == 0;
		} catch (Exception e) {
			logger.error("codebanque:" + codebanque + " codeguichet:" + codeguichet + " numerocompte:" + numerocompte + " cle:" + cle, e);
		}
		return false;
	}

	public static final int			IBANNUMBER_MIN_SIZE		= 15;
	public static final int			IBANNUMBER_MAX_SIZE		= 34;
	public static final BigInteger	IBANNUMBER_MAGIC_NUMBER	= new BigInteger("97");

	public static boolean isValidIban(String iban) {
		try {
			if (iban == null) {
				return false;
			}
			String newAccountNumber = iban.trim();

			// Check that the total IBAN length is correct as per the country. If not, the IBAN is invalid. We could also check
			// for specific length according to country, but for now we won't
			if (newAccountNumber.length() < IBANNUMBER_MIN_SIZE || newAccountNumber.length() > IBANNUMBER_MAX_SIZE) {
				return false;
			}

			// Move the four initial characters to the end of the string.
			newAccountNumber = newAccountNumber.substring(4) + newAccountNumber.substring(0, 4);

			// Replace each letter in the string with two digits, thereby expanding the string, where A = 10, B = 11, ..., Z = 35.
			StringBuilder numericAccountNumber = new StringBuilder();
			for (int i = 0; i < newAccountNumber.length(); i++) {
				numericAccountNumber.append(Character.getNumericValue(newAccountNumber.charAt(i)));
			}

			// Interpret the string as a decimal integer and compute the remainder of that number on division by 97.
			BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
			return ibanNumber.mod(IBANNUMBER_MAGIC_NUMBER).intValue() == 1;
		} catch (Exception e) {
			logger.error("iban:" + iban, e);
		}
		return false;
	}

	public static String getIBAN(String codebanque, String codeGuichet, String numerocompte, String cle) {

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String tmp = codebanque + codeGuichet + numerocompte + cle + "FR00";
		tmp = tmp.toUpperCase();
		for (char c : tmp.toCharArray()) {
			if (Character.isLetter(c)) {
				tmp = tmp.replaceAll(String.valueOf(c), String.valueOf(alphabet.indexOf(c) + 10));
			}
		}
		String ibanKey = String.valueOf(new BigDecimal(98).subtract(new BigDecimal(tmp).remainder(new BigDecimal(97))));
		if (ibanKey.length() == 1) {
			ibanKey = "0" + ibanKey;
		}
		return "FR" + ibanKey + codebanque + codeGuichet + numerocompte + cle;
	}

	public static String normalizedPhoneNumber(String _callerId) {
		String newCallerId = null;
		if (_callerId != null) {
			newCallerId = new String(_callerId.replace("+", "00").replace("0033", "0"));
			if (!newCallerId.startsWith("0")) {
				// aucun 0 en tête
				if (newCallerId.startsWith("33") && newCallerId.length() > 10) {
					// c'est un numéro français on vire le 33 et on met 0
					newCallerId = new String("0" + newCallerId.substring(2));
				}
				else if (isDomTom(newCallerId) && newCallerId.length() > 10) {
					// c'est un numéro des dom on vire l'indicatif sur 3 et on met 0
					newCallerId = new String("0" + newCallerId.substring(3));
				}
				else {
					// manque le 0 on l'ajoute
					newCallerId = new String("0" + newCallerId);
				}
			}
			else if (!newCallerId.startsWith("00")) {
				// un seul 0 en tête c'est un numéro français correct
			}
			else {
				// on commence par "00" c'est un numéro étranger
			}
		}
		return newCallerId;
	}

	public static boolean isDomTom(String _callerId) {
		return _callerId.startsWith("590")    // Guadeloupe                
				|| _callerId.startsWith("594")    // Guyane                    
				|| _callerId.startsWith("262")    // La reunion                
				|| _callerId.startsWith("596")    // Martinique                
				|| _callerId.startsWith("269")    // Mayotte                   
				|| _callerId.startsWith("687")    // Nouvelle-calédonie        
				|| _callerId.startsWith("689")    // Polynésie française       
				|| _callerId.startsWith("590")    // Saint-barthélemy          
				|| _callerId.startsWith("590")    // Saint-martin              
				|| _callerId.startsWith("508")    // Saint-pierre-et-miquelon  
				|| _callerId.startsWith("681");   // Wallis-et-futuna               

	}

	public static String fillZeroFront(String chaine, long longueur) {
		String retour = chaine;
		if (retour == null) {
			retour = "";
		}
		while (retour.length() < longueur) {
			retour = "0" + retour;
		}
		return new String(retour);
	}

	public static boolean stringMustBeUpdated(String newValue, String OldValue) {
		return newValue != null && OldValue == null || newValue.compareTo(OldValue) != 0;
	}

	public static boolean isValidPostalCode(String code) {
		return code.matches("[0-9]{5}");
	}

	public static String getFullStack(Exception e) {
		String fullStack = e.getMessage() + "\n";
		for (StackTraceElement element : e.getStackTrace()) {
			/* at com.compufirst.model.OmDaoImpl.generateNextCloudOrderDetailForContractDetail(OmDaoImpl.java:8356) */
			fullStack += "at " + element.getMethodName() + "(" + element.getClassName() + ":" + element.getLineNumber() + ")\n";
		}
		return fullStack;
	}

}
