package lk.ac.iit.event_ticketing_system.service;

import lk.ac.iit.event_ticketing_system.model.Ticket;
import lk.ac.iit.event_ticketing_system.util.TicketPool;


public class VendorService implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final String eventName;
    private final double price;
    private String vendorName;


    public VendorService(TicketPool ticketPool, int releaseRate, String eventName, double price, String vendorName) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.eventName = eventName;
        this.price = price;
        this.vendorName = vendorName;
    }


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (!ticketPool.canVendorAdd()) {
                    return; // Stop the vendor if adding tickets is no longer possible
                }

                Ticket ticket = new Ticket(eventName, price); // Create a new ticket
                boolean added= ticketPool.addTicket(ticket, vendorName);
//                System.out.println("Ticket added");

                if (!added) {
                    break; // Stop the vendor if adding is no longer possible
                }

                Thread.sleep(releaseRate * 1000L);
            }
        } catch (InterruptedException e) {
            //  System.out.println(Thread.currentThread().getName() + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }

}

