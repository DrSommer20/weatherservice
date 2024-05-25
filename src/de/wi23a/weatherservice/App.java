package de.wi23a.weatherservice;

/**
 * Die Klasse erstellt mehrere Threads von Publisher und Subscriber, startet diese und wartet bis alle Threads fertig durchgelaufen sind.
 * Nach Beenden aller Threads wird die Statisitk des MessageBrokers ausgegeben
 * @author Mauritz Giesinger, Luca Schmid, Ardian Ismaili, Paula Bauer, Tim Sommer
 */
public class App {
	
    /**
     * Erstellung und Managment der Threads der Publisher und Subscriber
     */
    public static void main(String[] args) {
        //Erstelle von funf Publisher-Threads
        Thread t1 = new Thread(new Publisher());
        Thread t2 = new Thread(new Publisher());
        Thread t3 = new Thread(new Publisher());
        Thread t4 = new Thread(new Publisher());
        Thread t5 = new Thread(new Publisher());

        //Erstellen von 5 Subscriber-Threads
        Thread t6 = new Thread(new Subscriber());
        Thread t7 = new Thread(new Subscriber());
        Thread t8 = new Thread(new Subscriber());
        Thread t9 = new Thread(new Subscriber());
        Thread t10 = new Thread(new Subscriber());

        //Starten der Threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();


        //Warten auf den Abschluss aller Threads
        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
            t8.join();
            t9.join();
            t10.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        //Ausgeben der Statistik des MessageBrokers
        MessageBroker.printStatistik();
    }
}
