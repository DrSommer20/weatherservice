package de.wi23a.weatherservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Der MessageBroker verwaltet die Kommunikation zwischen den Publishern und Subscribern.
 * Der MessageBroker empfängt Nachrichten von den Publishern und verteilt diese an die entsprechenden Subscriber
 * Darüber hinaus führt der MessageBroker Statistiken über die gesendeten und empfangenen Nachrichten.
 * @author Mauritz Giesinger, Luca Schmid, Ardian Ismaili, Paula Bauer, Tim Sommer
 */
public class MessageBroker {
	
	static MessageBroker mb;

	private List<List<Subscriber>> subscriberList;
	private static List<String> topics;
	private static volatile int maximaleNachrichten = 100;

	//Variablen für Statisiken
	private static volatile int empfangeneNachrichten = 0;
	private static int gesendeteNachrichten = 0;
	private static int subscribtions = 0;
	private static int[] nachrichtenNachTopics;

	/**
	 * Privater Konstruktor, um die Instanz zu erstellen.
	 * Initialiesiert die Themen und die Liste der Subscriber.
	 */
	private MessageBroker(){
		topics = Publisher.getTopics();
		nachrichtenNachTopics = new int[topics.size()];
		subscriberList = new ArrayList<>();

		for (int i = 0; i < topics.size(); i++) {
			List<Subscriber> topic = new ArrayList<>();
			subscriberList.add(topic);
		}
		
	}

	/**
	 * Gibt die Instanz des Messagebrokers zurück
	 * @return mb Die Instanz des Messagebrokers
	 */
	public static synchronized MessageBroker getInstance() {
        if (mb == null) {
        	mb = new MessageBroker();
        }
        return mb;
    }
	
	/**
	 * Empfängt eine neur Nachricht und verteilt diese an die entsprechenden Subscriber.
	 * Aktualisiert die Statistiken über empfangene Nachrichten.
	 * @param mg Die empfangene Nachricht.
	 */
	public synchronized void newMessage(WeahterMessage mg) {
		System.out.println("MessageBroker: Neue Nachricht erhalten");
		if(topics.contains(mg.getTopic())){
			empfangeneNachrichten++;
			pushMessage(mg);
			nachrichtenNachTopics[topics.indexOf(mg.getTopic())]++;
			
		}
	}
	
	/**
	 * Verteilt die Nachricht an alle Subscriber, die zu dem entsprechendm Thema gehören
	 * @param mg Die zu verteilende Nachricht
	 */
	private void pushMessage(WeahterMessage mg) {

		List<Subscriber> subsToSendMessage = subscriberList.get(topics.indexOf(mg.getTopic()));
		for (Subscriber subscriber : subsToSendMessage) {
			subscriber.newMessage(mg);
			gesendeteNachrichten++;
		}
		System.out.println("MessageBroker: Nachricht an alle Subscriber Versand");

	}

	/**
	 * Meldet einen Subscriber für ein bestimmtes Thema an.
	 * Aktualisiert die Statistik über erfolgreiche Abonnenments.
	 * @param topic Das Thema, das abonniert werden soll.
	 * @param subscriber Der Subscriber, der das Thema abonnieren möchte.
	 */
	public synchronized void subscribeTo(String topic, Subscriber subscriber) {
		if(topics.contains(topic)) {
			subscriberList.get(topics.indexOf(topic)).add(subscriber);
			System.out.println("MessageBroker: " + subscriber.getName() + " hat erfolgreich Topic " + topic + " abonniert");
			subscribtions++;
		}
	}
	
	/**
	 * Gibt die Statistiken des MessageBrokers aus.
	 * Zeigt sowohl empfangene und gesendete Nachrichten als auch erfolgreiche Abonnenments an.
	 */
	public static void printStatistik() {
			
		System.out.println("\n\n\n---------------Message Broker Statistik---------------");
		System.out.println("Empfangene Nachrichten Gesamt: " + empfangeneNachrichten);
		for(int i = 0; i < nachrichtenNachTopics.length; i++){
			System.out.println("Empfangene Nachrichten Topic "+ topics.get(i) + " : " + nachrichtenNachTopics[i]);
		}
		System.out.println("Gesendete Nachrichten: " + gesendeteNachrichten);
		System.out.println("Erfolgreiche Abonnements: " + subscribtions);	 
	}

	/**
	 * Überprüft, ob die maximale Anzahl der empfangenden Nachrichten erreicht wurde.
	 * @return true, wenn die maximale Anzahl erreicht wurde, sonst false.
	 */
	public static synchronized boolean isMaxNachrichtenErreicht(){
		return empfangeneNachrichten >= maximaleNachrichten;
	}
	
}
