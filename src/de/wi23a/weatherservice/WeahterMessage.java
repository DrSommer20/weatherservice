package de.wi23a.weatherservice;

/**
 * Klasse WeahterMessage repräsentiert eine Wetternachricht mit einem bestimmten Thema.
 * Eine Wetternachricht beseteht aus einem Thema und einer Nachricht.
 * @author Mauritz Giesinger, Luca Schmid, Ardian Ismaili, Paula Bauer, Tim Sommer
 */
public class WeahterMessage {
	
	private String message;
	private String topic;
	
	/**
	 * Konstuktor für die Klasse WeahterMessage.
	 * @param topic Das Thema der Nachricht
	 * @param message Der Inhalt der Nachricht.
	 */
	public WeahterMessage(String topic, String message) {
		this.topic = topic;
		this.message = message;
	}
	
	/**
	 * Gibt das Thema der Nachricht zurück
	 * @return topic Das Thema der Nachricht.
	 */
	public String getTopic() {
		return topic;
	}
	
	/**
	 * Gibt den Inhalt der Nachricht zurück.
	 * @return message Der Inhalt der Nachricht
	 */
	public String getMessage() {
		return message;
	}
}
