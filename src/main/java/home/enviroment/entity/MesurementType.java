package home.enviroment.entity;

public enum MesurementType {
	
	TEMPERATURE("temperature"),
	PRESSURE("pressure"),
	HUMIDITY("humidity");
	
	private MesurementType(String tag) {
		this.tag = tag;
	}
	
	private String tag;
	
	public String getTag() {
		return tag;
	}
}
