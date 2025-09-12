package app.DTO;

import java.math.BigDecimal;

public class Phone extends Product {

	private String screen;
	private String processor;
	private String ram;
	private String storage;
	private String rearCamera;
	private String battery;
	private String operatingSystem;
	private String chargingPort;
	private String refreshRate;
	private String waterAndDustResistance;
	private String weight;
	private String material;
	private String speaker;
	private String fastCharging;
	private String specialFeatures;

	// Constructors

	public Phone() {
		super();
	}

	// Parameterized constructor
	public Phone(int productId, String productName, String brand, BigDecimal importPrice, int categoryId,
			String imageUrl, String description, int status, BigDecimal salePrice, String screen, String processor,
			String ram, String storage, String rearCamera, String battery, String operatingSystem, String chargingPort,
			String refreshRate, String waterAndDustResistance, String weight, String material, String speaker,
			String fastCharging, String specialFeatures) {
		super(productId, productName, brand, importPrice, categoryId, imageUrl, description, status, salePrice);
		this.screen = screen;
		this.processor = processor;
		this.ram = ram;
		this.storage = storage;
		this.rearCamera = rearCamera;
		this.battery = battery;
		this.operatingSystem = operatingSystem;
		this.chargingPort = chargingPort;
		this.refreshRate = refreshRate;
		this.waterAndDustResistance = waterAndDustResistance;
		this.weight = weight;
		this.material = material;
		this.speaker = speaker;
		this.fastCharging = fastCharging;
		this.specialFeatures = specialFeatures;
	}

	// Getters and Setters for all new fields
	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getRearCamera() {
		return rearCamera;
	}

	public void setRearCamera(String rearCamera) {
		this.rearCamera = rearCamera;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getChargingPort() {
		return chargingPort;
	}

	public void setChargingPort(String chargingPort) {
		this.chargingPort = chargingPort;
	}

	public String getRefreshRate() {
		return refreshRate;
	}

	public void setRefreshRate(String refreshRate) {
		this.refreshRate = refreshRate;
	}

	public String getWaterAndDustResistance() {
		return waterAndDustResistance;
	}

	public void setWaterAndDustResistance(String waterAndDustResistance) {
		this.waterAndDustResistance = waterAndDustResistance;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getFastCharging() {
		return fastCharging;
	}

	public void setFastCharging(String fastCharging) {
		this.fastCharging = fastCharging;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}
}
