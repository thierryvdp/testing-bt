package meteo.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Observation {

	private String	stationID;
	private String	tz;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
	private Date	obsTimeUtc;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
	private Date	obsTimeLocal;
	private Float	epoch;
	private Float	lat;
	private Float	lon;
	private Float	solarRadiationHigh;
	private Float	uvHigh;
	private Float	winddirAvg;
	private Float	humidityHigh;
	private Float	humidityLow;
	private Float	humidityAvg;
	private Float	qcStatus;
	private Metric	metric;

	public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public Date getObsTimeUtc() {
		return obsTimeUtc;
	}

	public void setObsTimeUtc(Date obsTimeUtc) {
		this.obsTimeUtc = obsTimeUtc;
	}

	public Date getObsTimeLocal() {
		return obsTimeLocal;
	}

	public void setObsTimeLocal(Date obsTimeLocal) {
		this.obsTimeLocal = obsTimeLocal;
	}

	public Float getEpoch() {
		return epoch;
	}

	public void setEpoch(Float epoch) {
		this.epoch = epoch;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Float getSolarRadiationHigh() {
		return solarRadiationHigh;
	}

	public void setSolarRadiationHigh(Float solarRadiationHigh) {
		this.solarRadiationHigh = solarRadiationHigh;
	}

	public Float getUvHigh() {
		return uvHigh;
	}

	public void setUvHigh(Float uvHigh) {
		this.uvHigh = uvHigh;
	}

	public Float getWinddirAvg() {
		return winddirAvg;
	}

	public void setWinddirAvg(Float winddirAvg) {
		this.winddirAvg = winddirAvg;
	}

	public Float getHumidityHigh() {
		return humidityHigh;
	}

	public void setHumidityHigh(Float humidityHigh) {
		this.humidityHigh = humidityHigh;
	}

	public Float getHumidityLow() {
		return humidityLow;
	}

	public void setHumidityLow(Float humidityLow) {
		this.humidityLow = humidityLow;
	}

	public Float getHumidityAvg() {
		return humidityAvg;
	}

	public void setHumidityAvg(Float humidityAvg) {
		this.humidityAvg = humidityAvg;
	}

	public Float getQcStatus() {
		return qcStatus;
	}

	public void setQcStatus(Float qcStatus) {
		this.qcStatus = qcStatus;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

}
