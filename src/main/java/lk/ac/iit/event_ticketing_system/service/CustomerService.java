package lk.ac.iit.event_ticketing_system.service;

import lk.ac.iit.event_ticketing_system.model.Ticket;
import lk.ac.iit.event_ticketing_system.util.TicketPool;

public class CustomerService implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private String customerName;


    public CustomerService(TicketPool ticketPool, int retrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.customerName = customerName;
    }


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                if (!ticketPool.canCustomerRetrieve()) {
                    return; // Stop the customer if tickets are unavailable
                }
                // Attempt to retrieve a ticket
                Ticket ticket = ticketPool.retrieveTicket(customerName);

                if (ticket == null) {
                    break;
                }
                Thread.sleep(retrievalRate * 1000L);
            }
        } catch (InterruptedException e) {
            //System.out.println(Thread.currentThread().getName() + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}

