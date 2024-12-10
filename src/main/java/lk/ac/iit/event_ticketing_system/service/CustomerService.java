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

    /**
    Overrides the run method of runnable interface
    Executes logic for customer thread to retrieve ticket from ticket pool at a specific rate
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Stop the customer if tickets are unavailable
                if (!ticketPool.canCustomerRetrieve()) {
                    return;
                }
                Ticket ticket = ticketPool.retrieveTicket(customerName);

                if (ticket == null) {
                    break;
                }
                Thread.sleep(retrievalRate * 1000L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

