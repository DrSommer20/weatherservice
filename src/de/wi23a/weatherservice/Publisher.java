package de.wi23a.weatherservice;

import java.util.List;

/**
 * Der Publisher generiert Wetternachrichten zu einem bestimmten Thema und übermittelt diese an den MessageBroker.
 * Jeder Publisher erstellt Nachrichten mit zufälligen Temperaturen für einen bestimmten Ort und sendet diese an den Messagebroker.
 * @author Mauritz Giesinger, Luca Schmid, Ardian Ismaili, Paula Bauer, Tim Sommer
 */
public class Publisher implements Runnable{
	
	private String name; //Name des Publisher, sowas wie "Publisher_01" o.ä.
	private String topic;
	private static int publisherQuantity = 0;
	private static List<String> topics = List.of("Mosbach", "Heidelberg", "Mannheim", "Berlin", "München");
	private double lastTemp = -100.0;
	
	private static MessageBroker mb = MessageBroker.getInstance();

	/**
	 * Konstruktor für die Klasse Publisher.
	 * Initialisiert den Publisher mit einem bestimmten Thema und einem Namen.
	 */
	public Publisher(){
		this.topic = topics.get(publisherQuantity % 5);
		publisherQuantity++;
		this.name = "Publisher " + publisherQuantity;
		System.out.println(name + " mit Topic " + topic + " erzeugt.");
	}

	/**
	 * Erstellt eine neue Wetternachricht und übergibt sie an den Messsagebroker.
	 */
	private void newMessage() {
		String tempString = getTemprature() + "°C";
		System.out.println(name + ": Nachricht erstellt");
		mb.newMessage(new WeahterMessage(topic, tempString));
	}

	/**
	 * Generiert eine zufällige Temperatur basierend auf der letzten Temperatur.
	 * @return double Temperaturwert
	*/
	private double getTemprature(){
		double temp = 0;
		if(lastTemp == -100.0){
			temp = ((70.0*Math.random())-30.0);
		}
		else if(lastTemp >= 40.0){
			temp = lastTemp - (Math.random()*3.0);
		}
		else if (lastTemp <= -30.0) {
			temp = lastTemp + (Math.random()*3.0);
		}
		else{
			temp = lastTemp + (3.0-(Math.random()*6.0));
		}

		temp = Math.round(temp*100.0)/100.0;
		lastTemp = temp;
		return temp;
	}

	/**
	 * Gibt die Liste der verfügbaren Themen zurück.
	 * @return topics Die Liste der Themen.
	 */
	public static List<String> getTopics(){
		return topics;
	}
	
	/**
	 * Die run-Methode wird ausgeführt, wenn der Thread gestartet wird.
	 * Sie erstellt fortlaufend neue Nachrichten und sendet sie an den Messagebroker, bis die maximale Anazahl von Nachrichten erreicht ist.
	 */
	public void run() {
		while(!MessageBroker.isMaxNachrichtenErreicht()) {
			this.newMessage();
			try {
				int randomWaitTime = 100*(1+((int)(Math.random()*5)));
				Thread.sleep(randomWaitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
