package de.wi23a.weatherservice;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Subscriber empfängt Nachrichten von bestimmten Topics und verarbeitet sie.
 * Jeder Subscriber abonniert zufällig eine Anzahl an Themen und wartet auf neue Nachrichten, die von einem Publisher gesendet werden.
 * @author Mauritz Giesinger, Luca Schmid, Ardian Ismaili, Paula Bauer, Tim Sommer
 */
public class Subscriber implements Runnable {

	private String name;// Name des Subsciber, sowas wie"Subscriber_01"o.ä.
	private static int subscriberQuantity = 0;
	private Queue<WeahterMessage> nachrichtenQueue = new ArrayBlockingQueue<>(20);

	/**
	 * Konstruktor für die Klasse Subscriber.
	 * Initialisiert den Namen des Subscribers und abonniert zufällig eine Anzahl von Topics.
	 */
	public Subscriber(){
		subscriberQuantity++;
		this.name = "Susbcriber " + subscriberQuantity;
		subscribeToTopics();	
	}

	/**
	 * Diese Methode wir aufgerufen, wenn der Subscriber eine neue Nachricht erhält.
	 * Die Nachricht wird dann der Nachrichtenwarteschlange hinzugefügt.
	 * @param mg Die erhaltene Nachricht.
	 */
	public void newMessage(WeahterMessage mg){
		nachrichtenQueue.add(mg);
	}

	/**
	 * Gibt die erste Nachricht in der Warteschlange aus und entfernt sie aus der Warteschlange.
	 */
	private void printMessage() {
		WeahterMessage weahterMessage = nachrichtenQueue.poll();
		System.out.println(name + " erhält die Topic: " + weahterMessage.getTopic() + " | Message: " + weahterMessage.getMessage());
	}

	/**
	 * Meldet den Subscriber für eine zufällige Anzahl von Themen an.
	 */
	private void subscribeToTopics(){
		int randomSubscripitionAnzahl = 1+(int)(Math.random()*4.0);
		int randomStartTopic = (int)(Math.random()*4.0);
		
		for(int i =	0; i < randomSubscripitionAnzahl; i++){
			int topicIndex = (i+randomStartTopic) % 5;
			String s = Publisher.getTopics().get(topicIndex);
			MessageBroker.getInstance().subscribeTo(s, this);
		}
	}

	/**
	 * Gibt den Namen des Subscribers zurück
	 * @return name Der Name des Subscribers
	 */
	public String getName(){
		return name;
	}

	/**
	 * Die Run Methode wird ausgeführt, wenn der Thread gestarte wird.
	 * Sie wartet darauf, dass neue Nachrichten in der Warteschlange vorhanden sind und ruft dann die printMessage ethode auf, um die nachricht zu verarbeiten.
	 */
	public void run() {
		while(!(MessageBroker.isMaxNachrichtenErreicht() && nachrichtenQueue.peek() == null)){
				if(nachrichtenQueue.peek() != null){
					printMessage();
				}
		}
	}

}