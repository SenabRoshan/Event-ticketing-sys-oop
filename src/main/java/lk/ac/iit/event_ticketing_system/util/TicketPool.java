package lk.ac.iit.event_ticketing_system.util;

import lk.ac.iit.event_ticketing_system.model.TicketingConfig;
import lk.ac.iit.event_ticketing_system.model.Ticket;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TicketPool {

    private final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private int maxCapacity;
    private int totalTickets;
    private final List<String> logs = new ArrayList<>();

    private int soldTickets = 0;
    /**
     * Initializes the ticket pool with configuration parameters.
     *
     * @param config the configuration object contains configuration parameters
     */
    public void initialize(TicketingConfig config) {
        this.maxCapacity = config.getMaxTicketPoolCapacity();
        this.totalTickets = config.getTotalTickets();
    }

    /**
     * Adds a ticket to the ticket pool if space available and tickets remain.
     * synchronized to ensure thread safety in a multi-threaded environment.
     *
     * @param ticket the ticket to be added to the pool.
     * @param vendorName the name of the vendor
     * @return true if the ticket was successfully added,  false if the pool is full
     *         or no tickets available to add.
     */

    public synchronized boolean addTicket(Ticket ticket,String vendorName) throws InterruptedException {
        if (totalTickets == 0) {
            System.out.println("No more tickets available to add.");
            return false;
        }
//        if (tickets.size() >= maxCapacity) {
//            addSystemLog("Pool is full. Cannot add more tickets.");
//            System.out.println("Pool is full. Cannot add ticket.");
//            return false;
//        }
        while (tickets.size() >= maxCapacity) {
            wait(); // Wait until space is available
        }

        tickets.add(ticket);
        totalTickets--; // Reduce total tickets available
        addSystemLog(vendorName + " added a ticket. Remaining Total Tickets: " + totalTickets);
        System.out.println(vendorName + " added Ticket. Remaining Total Tickets: " + totalTickets );
        notifyAll();
        return true;
    }

    public synchronized Ticket retrieveTicket(String customerName) throws InterruptedException {
//        if (totalTickets == 0 && tickets.isEmpty()) {
//            addSystemLog("All the tickets successfully sold. No more tickets available for retrieval.");
//            System.out.println("No more tickets available for retrieval. All tickets sold.");
//            return null;
//        }
//
//        if (tickets.isEmpty() && totalTickets > 0) {
//            addSystemLog("No more tickets available for retrieval. Ticket pool is empty.");
//            System.out.println("No tickets available in the pool. Ticket pool is empty.");
//            return null;
//        }
        while (tickets.isEmpty()) {
            if (totalTickets == 0) {
             //   addSystemLog("All tickets successfully sold. No more tickets available for retrieval.");
                return null;
            }
            addSystemLog(customerName + " waiting to buy a ticket");
            wait(); // Wait until tickets are available
        }

        Ticket ticket = tickets.remove(0);
        soldTickets++;
        addSystemLog(customerName + " retrieved a ticket: Event Name - " + ticket.getEventName() + ", Ticket Price - " + ticket.getPrice() + ", Tickets in pool now: " + tickets.size());
        System.out.println(customerName+ " retrieved Ticket: 'Event Name'-" + ticket.getEventName()+ "' Ticket Price-'" + ticket.getPrice());
//        notifyAll();
        return ticket;
    }

    public int getTicketCount() {
        return tickets.size();
    }

    public boolean canVendorAdd() {
        return tickets.size() < maxCapacity && totalTickets > 0;
    }

    public boolean canCustomerRetrieve() {
        return !tickets.isEmpty() || totalTickets > 0;
    }

    public List<String> getLogs() {
        synchronized (logs) {
            return new ArrayList<>(logs); // Return a copy to avoid concurrent modifications.
        }
    }

    public void addSystemLog(String message) {
        synchronized (logs) {
            logs.add(message);
            System.out.println("Log added: " + message);
        }
    }

    public int getSoldTickets() {
        return soldTickets;
    }

}

