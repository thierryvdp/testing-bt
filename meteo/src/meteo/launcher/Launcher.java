package meteo.launcher;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import meteo.bean.Observations;
import meteo.utils.DateServiceUtil;
import meteo.utils.FileUtil;
import meteo.utils.SshUtil;

public class Launcher {

	private final static Logger	logger						= LogManager.getLogger(Launcher.class);
	/** https://api.weather.com/v2/pws/history/hourly?stationId=ILASAL49&format=json&units=m&apiKey=85ee1fc2d76c4cecae1fc2d76cfcec17&date=20230409 */
	private static String		WUNDERGROUND_URL			= "https://api.weather.com";
	private static String		WUNDERGROUND_HISTORY_API	= "/v2/pws/history/hourly?";
	private static String		WUNDERGROUND_VAR__STATION	= "stationId=ILASAL49";
	private static String		WUNDERGROUND_VAR__APIKEY	= "apiKey=85ee1fc2d76c4cecae1fc2d76cfcec17";
	private static String		WUNDERGROUND_VAR__FORMAT	= "format=json";
	private static String		WUNDERGROUND_VAR__UNIT		= "units=m";
	private static String		WUNDERGROUND_VAR__DATE		= "date=";

	public static void main(String[] args) {
		try {
			Date from = DateServiceUtil.currentDate();
			Date to = from;
			if (args.length == 1) {
				from = DateServiceUtil.toDate(args[0]);
				to = from;
			}
			else if (args.length > 1) {
				from = DateServiceUtil.toDate(args[0]);
				to = DateServiceUtil.toDate(args[1]);
			}
			if (to.before(from)) {
				Date temp = to;
				to = from;
				from = temp;
			}

			String response = getHistory(from);
			//			String response = getTestHistory();
			logger.info(response);

			ObjectMapper mapper = new ObjectMapper();

			//JSON from String to Object
			Observations observations = mapper.readValue(response, Observations.class);

			logger.info(observations);

		} catch (Exception e) {
			logger.error("", e);
		}

	}

	private static String getTestHistory() {
		String response = "{\r\n" +
				"	\"observations\": [\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-08T22:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 00:59:56\",\r\n" +
				"			\"epoch\": 1680994796,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 91,\r\n" +
				"			\"humidityLow\": 89,\r\n" +
				"			\"humidityAvg\": 89,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -3,\r\n" +
				"				\"tempLow\": -4,\r\n" +
				"				\"tempAvg\": -3,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -4,\r\n" +
				"				\"dewptLow\": -5,\r\n" +
				"				\"dewptAvg\": -4,\r\n" +
				"				\"windchillHigh\": -3,\r\n" +
				"				\"windchillLow\": -4,\r\n" +
				"				\"windchillAvg\": -3,\r\n" +
				"				\"heatindexHigh\": -3,\r\n" +
				"				\"heatindexLow\": -4,\r\n" +
				"				\"heatindexAvg\": -3,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-08T23:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 01:59:55\",\r\n" +
				"			\"epoch\": 1680998395,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 92,\r\n" +
				"			\"humidityLow\": 91,\r\n" +
				"			\"humidityAvg\": 91,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -3,\r\n" +
				"				\"tempLow\": -4,\r\n" +
				"				\"tempAvg\": -4,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -4,\r\n" +
				"				\"dewptLow\": -5,\r\n" +
				"				\"dewptAvg\": -5,\r\n" +
				"				\"windchillHigh\": -3,\r\n" +
				"				\"windchillLow\": -4,\r\n" +
				"				\"windchillAvg\": -4,\r\n" +
				"				\"heatindexHigh\": -3,\r\n" +
				"				\"heatindexLow\": -4,\r\n" +
				"				\"heatindexAvg\": -4,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T00:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 02:59:55\",\r\n" +
				"			\"epoch\": 1681001995,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 93,\r\n" +
				"			\"humidityLow\": 91,\r\n" +
				"			\"humidityAvg\": 92,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -4,\r\n" +
				"				\"tempLow\": -4,\r\n" +
				"				\"tempAvg\": -4,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -5,\r\n" +
				"				\"dewptLow\": -5,\r\n" +
				"				\"dewptAvg\": -5,\r\n" +
				"				\"windchillHigh\": -4,\r\n" +
				"				\"windchillLow\": -4,\r\n" +
				"				\"windchillAvg\": -4,\r\n" +
				"				\"heatindexHigh\": -4,\r\n" +
				"				\"heatindexLow\": -4,\r\n" +
				"				\"heatindexAvg\": -4,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T01:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 03:59:55\",\r\n" +
				"			\"epoch\": 1681005595,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 94,\r\n" +
				"			\"humidityLow\": 93,\r\n" +
				"			\"humidityAvg\": 93,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -4,\r\n" +
				"				\"tempLow\": -5,\r\n" +
				"				\"tempAvg\": -5,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -5,\r\n" +
				"				\"dewptLow\": -6,\r\n" +
				"				\"dewptAvg\": -5,\r\n" +
				"				\"windchillHigh\": -4,\r\n" +
				"				\"windchillLow\": -5,\r\n" +
				"				\"windchillAvg\": -5,\r\n" +
				"				\"heatindexHigh\": -4,\r\n" +
				"				\"heatindexLow\": -5,\r\n" +
				"				\"heatindexAvg\": -5,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T02:59:43Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 04:59:43\",\r\n" +
				"			\"epoch\": 1681009183,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 94,\r\n" +
				"			\"humidityLow\": 93,\r\n" +
				"			\"humidityAvg\": 93,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -5,\r\n" +
				"				\"tempLow\": -5,\r\n" +
				"				\"tempAvg\": -5,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -6,\r\n" +
				"				\"dewptLow\": -6,\r\n" +
				"				\"dewptAvg\": -6,\r\n" +
				"				\"windchillHigh\": -5,\r\n" +
				"				\"windchillLow\": -5,\r\n" +
				"				\"windchillAvg\": -5,\r\n" +
				"				\"heatindexHigh\": -5,\r\n" +
				"				\"heatindexLow\": -5,\r\n" +
				"				\"heatindexAvg\": -5,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T03:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 05:59:56\",\r\n" +
				"			\"epoch\": 1681012796,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 94,\r\n" +
				"			\"humidityLow\": 94,\r\n" +
				"			\"humidityAvg\": 94,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -5,\r\n" +
				"				\"tempLow\": -6,\r\n" +
				"				\"tempAvg\": -5,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -6,\r\n" +
				"				\"dewptLow\": -6,\r\n" +
				"				\"dewptAvg\": -6,\r\n" +
				"				\"windchillHigh\": -5,\r\n" +
				"				\"windchillLow\": -6,\r\n" +
				"				\"windchillAvg\": -5,\r\n" +
				"				\"heatindexHigh\": -5,\r\n" +
				"				\"heatindexLow\": -6,\r\n" +
				"				\"heatindexAvg\": -5,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1012.87,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T04:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 06:59:55\",\r\n" +
				"			\"epoch\": 1681016395,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 95,\r\n" +
				"			\"humidityLow\": 94,\r\n" +
				"			\"humidityAvg\": 94,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -5,\r\n" +
				"				\"tempLow\": -5,\r\n" +
				"				\"tempAvg\": -5,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -6,\r\n" +
				"				\"dewptLow\": -6,\r\n" +
				"				\"dewptAvg\": -6,\r\n" +
				"				\"windchillHigh\": -5,\r\n" +
				"				\"windchillLow\": -5,\r\n" +
				"				\"windchillAvg\": -5,\r\n" +
				"				\"heatindexHigh\": -5,\r\n" +
				"				\"heatindexLow\": -5,\r\n" +
				"				\"heatindexAvg\": -5,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1012.87,\r\n" +
				"				\"pressureTrend\": 1.02,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T05:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 07:59:55\",\r\n" +
				"			\"epoch\": 1681019995,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 95,\r\n" +
				"			\"humidityLow\": 95,\r\n" +
				"			\"humidityAvg\": 95,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -4,\r\n" +
				"				\"tempLow\": -5,\r\n" +
				"				\"tempAvg\": -5,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -5,\r\n" +
				"				\"dewptLow\": -6,\r\n" +
				"				\"dewptAvg\": -5,\r\n" +
				"				\"windchillHigh\": -4,\r\n" +
				"				\"windchillLow\": -5,\r\n" +
				"				\"windchillAvg\": -5,\r\n" +
				"				\"heatindexHigh\": -4,\r\n" +
				"				\"heatindexLow\": -5,\r\n" +
				"				\"heatindexAvg\": -5,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T06:59:59Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 08:59:59\",\r\n" +
				"			\"epoch\": 1681023599,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 96,\r\n" +
				"			\"humidityLow\": 95,\r\n" +
				"			\"humidityAvg\": 95,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": -2,\r\n" +
				"				\"tempLow\": -4,\r\n" +
				"				\"tempAvg\": -3,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -2,\r\n" +
				"				\"dewptLow\": -4,\r\n" +
				"				\"dewptAvg\": -3,\r\n" +
				"				\"windchillHigh\": -2,\r\n" +
				"				\"windchillLow\": -4,\r\n" +
				"				\"windchillAvg\": -3,\r\n" +
				"				\"heatindexHigh\": -2,\r\n" +
				"				\"heatindexLow\": -4,\r\n" +
				"				\"heatindexAvg\": -3,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T07:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 09:59:55\",\r\n" +
				"			\"epoch\": 1681027195,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 95,\r\n" +
				"			\"humidityLow\": 75,\r\n" +
				"			\"humidityAvg\": 90,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 7,\r\n" +
				"				\"tempLow\": -2,\r\n" +
				"				\"tempAvg\": 1,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": 3,\r\n" +
				"				\"dewptLow\": -2,\r\n" +
				"				\"dewptAvg\": 0,\r\n" +
				"				\"windchillHigh\": 7,\r\n" +
				"				\"windchillLow\": -2,\r\n" +
				"				\"windchillAvg\": 1,\r\n" +
				"				\"heatindexHigh\": 7,\r\n" +
				"				\"heatindexLow\": -2,\r\n" +
				"				\"heatindexAvg\": 1,\r\n" +
				"				\"pressureMax\": 1014.9,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T08:59:57Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 10:59:57\",\r\n" +
				"			\"epoch\": 1681030797,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 22,\r\n" +
				"			\"humidityHigh\": 77,\r\n" +
				"			\"humidityLow\": 35,\r\n" +
				"			\"humidityAvg\": 45,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 13,\r\n" +
				"				\"tempLow\": 7,\r\n" +
				"				\"tempAvg\": 11,\r\n" +
				"				\"windspeedHigh\": 9,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 2,\r\n" +
				"				\"windgustHigh\": 9,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 2,\r\n" +
				"				\"dewptHigh\": 4,\r\n" +
				"				\"dewptLow\": -3,\r\n" +
				"				\"dewptAvg\": 0,\r\n" +
				"				\"windchillHigh\": 13,\r\n" +
				"				\"windchillLow\": 7,\r\n" +
				"				\"windchillAvg\": 11,\r\n" +
				"				\"heatindexHigh\": 13,\r\n" +
				"				\"heatindexLow\": 7,\r\n" +
				"				\"heatindexAvg\": 11,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T09:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 11:59:55\",\r\n" +
				"			\"epoch\": 1681034395,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 25,\r\n" +
				"			\"humidityHigh\": 44,\r\n" +
				"			\"humidityLow\": 30,\r\n" +
				"			\"humidityAvg\": 35,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 14,\r\n" +
				"				\"tempLow\": 13,\r\n" +
				"				\"tempAvg\": 13,\r\n" +
				"				\"windspeedHigh\": 11,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 3,\r\n" +
				"				\"windgustHigh\": 11,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 3,\r\n" +
				"				\"dewptHigh\": 1,\r\n" +
				"				\"dewptLow\": -4,\r\n" +
				"				\"dewptAvg\": -2,\r\n" +
				"				\"windchillHigh\": 14,\r\n" +
				"				\"windchillLow\": 13,\r\n" +
				"				\"windchillAvg\": 13,\r\n" +
				"				\"heatindexHigh\": 14,\r\n" +
				"				\"heatindexLow\": 13,\r\n" +
				"				\"heatindexAvg\": 13,\r\n" +
				"				\"pressureMax\": 1014.9,\r\n" +
				"				\"pressureMin\": 1014.9,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T10:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 12:59:55\",\r\n" +
				"			\"epoch\": 1681037995,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 155,\r\n" +
				"			\"humidityHigh\": 36,\r\n" +
				"			\"humidityLow\": 25,\r\n" +
				"			\"humidityAvg\": 28,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 17,\r\n" +
				"				\"tempLow\": 14,\r\n" +
				"				\"tempAvg\": 16,\r\n" +
				"				\"windspeedHigh\": 9,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 3,\r\n" +
				"				\"windgustHigh\": 10,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 3,\r\n" +
				"				\"dewptHigh\": 2,\r\n" +
				"				\"dewptLow\": -4,\r\n" +
				"				\"dewptAvg\": -3,\r\n" +
				"				\"windchillHigh\": 17,\r\n" +
				"				\"windchillLow\": 14,\r\n" +
				"				\"windchillAvg\": 16,\r\n" +
				"				\"heatindexHigh\": 17,\r\n" +
				"				\"heatindexLow\": 14,\r\n" +
				"				\"heatindexAvg\": 16,\r\n" +
				"				\"pressureMax\": 1014.9,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": -1.02,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T11:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 13:59:56\",\r\n" +
				"			\"epoch\": 1681041596,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 141,\r\n" +
				"			\"humidityHigh\": 39,\r\n" +
				"			\"humidityLow\": 29,\r\n" +
				"			\"humidityAvg\": 32,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 18,\r\n" +
				"				\"tempLow\": 17,\r\n" +
				"				\"tempAvg\": 17,\r\n" +
				"				\"windspeedHigh\": 9,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 3,\r\n" +
				"				\"windgustHigh\": 9,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 3,\r\n" +
				"				\"dewptHigh\": 3,\r\n" +
				"				\"dewptLow\": -1,\r\n" +
				"				\"dewptAvg\": 1,\r\n" +
				"				\"windchillHigh\": 18,\r\n" +
				"				\"windchillLow\": 17,\r\n" +
				"				\"windchillAvg\": 17,\r\n" +
				"				\"heatindexHigh\": 18,\r\n" +
				"				\"heatindexLow\": 17,\r\n" +
				"				\"heatindexAvg\": 17,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T12:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 14:59:56\",\r\n" +
				"			\"epoch\": 1681045196,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 161,\r\n" +
				"			\"humidityHigh\": 40,\r\n" +
				"			\"humidityLow\": 30,\r\n" +
				"			\"humidityAvg\": 33,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 18,\r\n" +
				"				\"tempLow\": 17,\r\n" +
				"				\"tempAvg\": 18,\r\n" +
				"				\"windspeedHigh\": 13,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 4,\r\n" +
				"				\"windgustHigh\": 14,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 5,\r\n" +
				"				\"dewptHigh\": 4,\r\n" +
				"				\"dewptLow\": -1,\r\n" +
				"				\"dewptAvg\": 1,\r\n" +
				"				\"windchillHigh\": 18,\r\n" +
				"				\"windchillLow\": 17,\r\n" +
				"				\"windchillAvg\": 18,\r\n" +
				"				\"heatindexHigh\": 18,\r\n" +
				"				\"heatindexLow\": 17,\r\n" +
				"				\"heatindexAvg\": 18,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T13:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 15:59:56\",\r\n" +
				"			\"epoch\": 1681048796,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 163,\r\n" +
				"			\"humidityHigh\": 40,\r\n" +
				"			\"humidityLow\": 31,\r\n" +
				"			\"humidityAvg\": 34,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 18,\r\n" +
				"				\"tempLow\": 18,\r\n" +
				"				\"tempAvg\": 18,\r\n" +
				"				\"windspeedHigh\": 11,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 4,\r\n" +
				"				\"windgustHigh\": 12,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 4,\r\n" +
				"				\"dewptHigh\": 4,\r\n" +
				"				\"dewptLow\": 1,\r\n" +
				"				\"dewptAvg\": 2,\r\n" +
				"				\"windchillHigh\": 18,\r\n" +
				"				\"windchillLow\": 18,\r\n" +
				"				\"windchillAvg\": 18,\r\n" +
				"				\"heatindexHigh\": 18,\r\n" +
				"				\"heatindexLow\": 18,\r\n" +
				"				\"heatindexAvg\": 18,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T14:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 16:59:56\",\r\n" +
				"			\"epoch\": 1681052396,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 145,\r\n" +
				"			\"humidityHigh\": 42,\r\n" +
				"			\"humidityLow\": 32,\r\n" +
				"			\"humidityAvg\": 36,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 18,\r\n" +
				"				\"tempLow\": 16,\r\n" +
				"				\"tempAvg\": 17,\r\n" +
				"				\"windspeedHigh\": 9,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 4,\r\n" +
				"				\"windgustHigh\": 9,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 4,\r\n" +
				"				\"dewptHigh\": 3,\r\n" +
				"				\"dewptLow\": 1,\r\n" +
				"				\"dewptAvg\": 2,\r\n" +
				"				\"windchillHigh\": 18,\r\n" +
				"				\"windchillLow\": 16,\r\n" +
				"				\"windchillAvg\": 17,\r\n" +
				"				\"heatindexHigh\": 18,\r\n" +
				"				\"heatindexLow\": 16,\r\n" +
				"				\"heatindexAvg\": 17,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T15:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 17:59:55\",\r\n" +
				"			\"epoch\": 1681055995,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 145,\r\n" +
				"			\"humidityHigh\": 47,\r\n" +
				"			\"humidityLow\": 39,\r\n" +
				"			\"humidityAvg\": 43,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 16,\r\n" +
				"				\"tempLow\": 15,\r\n" +
				"				\"tempAvg\": 16,\r\n" +
				"				\"windspeedHigh\": 9,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 3,\r\n" +
				"				\"windgustHigh\": 9,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 3,\r\n" +
				"				\"dewptHigh\": 4,\r\n" +
				"				\"dewptLow\": 2,\r\n" +
				"				\"dewptAvg\": 3,\r\n" +
				"				\"windchillHigh\": 16,\r\n" +
				"				\"windchillLow\": 15,\r\n" +
				"				\"windchillAvg\": 16,\r\n" +
				"				\"heatindexHigh\": 16,\r\n" +
				"				\"heatindexLow\": 15,\r\n" +
				"				\"heatindexAvg\": 16,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T16:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 18:59:55\",\r\n" +
				"			\"epoch\": 1681059595,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 180,\r\n" +
				"			\"humidityHigh\": 52,\r\n" +
				"			\"humidityLow\": 45,\r\n" +
				"			\"humidityAvg\": 48,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 15,\r\n" +
				"				\"tempLow\": 14,\r\n" +
				"				\"tempAvg\": 14,\r\n" +
				"				\"windspeedHigh\": 11,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 3,\r\n" +
				"				\"windgustHigh\": 11,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 3,\r\n" +
				"				\"dewptHigh\": 4,\r\n" +
				"				\"dewptLow\": 3,\r\n" +
				"				\"dewptAvg\": 3,\r\n" +
				"				\"windchillHigh\": 15,\r\n" +
				"				\"windchillLow\": 14,\r\n" +
				"				\"windchillAvg\": 14,\r\n" +
				"				\"heatindexHigh\": 15,\r\n" +
				"				\"heatindexLow\": 14,\r\n" +
				"				\"heatindexAvg\": 14,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T17:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 19:59:55\",\r\n" +
				"			\"epoch\": 1681063195,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 202,\r\n" +
				"			\"humidityHigh\": 70,\r\n" +
				"			\"humidityLow\": 50,\r\n" +
				"			\"humidityAvg\": 58,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 14,\r\n" +
				"				\"tempLow\": 9,\r\n" +
				"				\"tempAvg\": 12,\r\n" +
				"				\"windspeedHigh\": 3,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 3,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": 5,\r\n" +
				"				\"dewptLow\": 3,\r\n" +
				"				\"dewptAvg\": 4,\r\n" +
				"				\"windchillHigh\": 14,\r\n" +
				"				\"windchillLow\": 9,\r\n" +
				"				\"windchillAvg\": 12,\r\n" +
				"				\"heatindexHigh\": 14,\r\n" +
				"				\"heatindexLow\": 9,\r\n" +
				"				\"heatindexAvg\": 12,\r\n" +
				"				\"pressureMax\": 1013.88,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T18:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 20:59:55\",\r\n" +
				"			\"epoch\": 1681066795,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 332,\r\n" +
				"			\"humidityHigh\": 83,\r\n" +
				"			\"humidityLow\": 69,\r\n" +
				"			\"humidityAvg\": 76,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 9,\r\n" +
				"				\"tempLow\": 4,\r\n" +
				"				\"tempAvg\": 7,\r\n" +
				"				\"windspeedHigh\": 3,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 3,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": 4,\r\n" +
				"				\"dewptLow\": 2,\r\n" +
				"				\"dewptAvg\": 3,\r\n" +
				"				\"windchillHigh\": 9,\r\n" +
				"				\"windchillLow\": 4,\r\n" +
				"				\"windchillAvg\": 7,\r\n" +
				"				\"heatindexHigh\": 9,\r\n" +
				"				\"heatindexLow\": 4,\r\n" +
				"				\"heatindexAvg\": 7,\r\n" +
				"				\"pressureMax\": 1014.9,\r\n" +
				"				\"pressureMin\": 1013.88,\r\n" +
				"				\"pressureTrend\": 1.02,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T19:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 21:59:55\",\r\n" +
				"			\"epoch\": 1681070395,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 86,\r\n" +
				"			\"humidityLow\": 83,\r\n" +
				"			\"humidityAvg\": 84,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 4,\r\n" +
				"				\"tempLow\": 2,\r\n" +
				"				\"tempAvg\": 3,\r\n" +
				"				\"windspeedHigh\": 2,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 2,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": 2,\r\n" +
				"				\"dewptLow\": 0,\r\n" +
				"				\"dewptAvg\": 1,\r\n" +
				"				\"windchillHigh\": 4,\r\n" +
				"				\"windchillLow\": 2,\r\n" +
				"				\"windchillAvg\": 3,\r\n" +
				"				\"heatindexHigh\": 4,\r\n" +
				"				\"heatindexLow\": 2,\r\n" +
				"				\"heatindexAvg\": 3,\r\n" +
				"				\"pressureMax\": 1014.9,\r\n" +
				"				\"pressureMin\": 1014.9,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T20:59:55Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 22:59:55\",\r\n" +
				"			\"epoch\": 1681073995,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 89,\r\n" +
				"			\"humidityLow\": 85,\r\n" +
				"			\"humidityAvg\": 87,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 2,\r\n" +
				"				\"tempLow\": 1,\r\n" +
				"				\"tempAvg\": 1,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": 0,\r\n" +
				"				\"dewptLow\": -1,\r\n" +
				"				\"dewptAvg\": -1,\r\n" +
				"				\"windchillHigh\": 2,\r\n" +
				"				\"windchillLow\": 1,\r\n" +
				"				\"windchillAvg\": 1,\r\n" +
				"				\"heatindexHigh\": 2,\r\n" +
				"				\"heatindexLow\": 1,\r\n" +
				"				\"heatindexAvg\": 1,\r\n" +
				"				\"pressureMax\": 1015.92,\r\n" +
				"				\"pressureMin\": 1014.9,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		},\r\n" +
				"		{\r\n" +
				"			\"stationID\": \"ILASAL49\",\r\n" +
				"			\"tz\": \"Europe/Paris\",\r\n" +
				"			\"obsTimeUtc\": \"2023-04-09T21:59:56Z\",\r\n" +
				"			\"obsTimeLocal\": \"2023-04-09 23:59:56\",\r\n" +
				"			\"epoch\": 1681077596,\r\n" +
				"			\"lat\": 43.549501,\r\n" +
				"			\"lon\": 2.759053,\r\n" +
				"			\"solarRadiationHigh\": null,\r\n" +
				"			\"uvHigh\": null,\r\n" +
				"			\"winddirAvg\": 338,\r\n" +
				"			\"humidityHigh\": 92,\r\n" +
				"			\"humidityLow\": 88,\r\n" +
				"			\"humidityAvg\": 90,\r\n" +
				"			\"qcStatus\": -1,\r\n" +
				"			\"metric\": {\r\n" +
				"				\"tempHigh\": 1,\r\n" +
				"				\"tempLow\": -1,\r\n" +
				"				\"tempAvg\": 0,\r\n" +
				"				\"windspeedHigh\": 0,\r\n" +
				"				\"windspeedLow\": 0,\r\n" +
				"				\"windspeedAvg\": 0,\r\n" +
				"				\"windgustHigh\": 0,\r\n" +
				"				\"windgustLow\": 0,\r\n" +
				"				\"windgustAvg\": 0,\r\n" +
				"				\"dewptHigh\": -1,\r\n" +
				"				\"dewptLow\": -2,\r\n" +
				"				\"dewptAvg\": -2,\r\n" +
				"				\"windchillHigh\": 1,\r\n" +
				"				\"windchillLow\": -1,\r\n" +
				"				\"windchillAvg\": 0,\r\n" +
				"				\"heatindexHigh\": 1,\r\n" +
				"				\"heatindexLow\": -1,\r\n" +
				"				\"heatindexAvg\": 0,\r\n" +
				"				\"pressureMax\": 1015.92,\r\n" +
				"				\"pressureMin\": 1015.92,\r\n" +
				"				\"pressureTrend\": 0.0,\r\n" +
				"				\"precipRate\": 0.0,\r\n" +
				"				\"precipTotal\": 0.0\r\n" +
				"			}\r\n" +
				"		}\r\n" +
				"	]\r\n" +
				"}\r\n" +
				"";
		return response;
	}

	private static String getHistory(Date date) {
		String response = null;
		String dateParam = DateServiceUtil.date2String(date, "yyyyMMdd");
		String fullApiCall = WUNDERGROUND_URL
				+ WUNDERGROUND_HISTORY_API
				+ WUNDERGROUND_VAR__STATION
				+ "&" + WUNDERGROUND_VAR__APIKEY
				+ "&" + WUNDERGROUND_VAR__FORMAT
				+ "&" + WUNDERGROUND_VAR__UNIT
				+ "&" + WUNDERGROUND_VAR__DATE + dateParam;
		logger.info(fullApiCall);
		try {
			// hack certif
			SshUtil.createTrustedManager();
			// HttpClient
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build())).build();
			// HttpGet Request
			HttpGet getRequest = new HttpGet(fullApiCall);
			getRequest.addHeader("Accept", "application/json");
			HttpResponse httpgetResponse = httpClient.execute(getRequest);
			response = FileUtil.dump(httpgetResponse.getEntity().getContent());
			logger.info(response);

		} catch (Exception e) {
			logger.error("", e);
		}
		return response;
	}

}
