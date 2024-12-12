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
     * synchronized to ensure thread safety in a multithreaded environment.
     *
     * @param ticket the ticket to be added to the pool.
     * @param vendorName the name of the vendor
     * @return true if the ticket was successfully added,  false if the pool is full
     *         or no tickets available to add.
     * @throws InterruptedException If the thread is interrupted while waiting for a ticket.
     *
     */

    public synchronized boolean addTicket(Ticket ticket,String vendorName) throws InterruptedException {
        if (totalTickets == 0) {
            System.out.println("No more tickets available to add.");
            return false;
        }

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

    /**
     * Retrieves a ticket from the pool for the specified customer.
     *
     * @param customerName The name of the customer requesting a ticket.
     *
     * @return The retrieved ticket, or `null` if no tickets are available.
     *
     * @throws InterruptedException If the thread is interrupted while waiting for a ticket.
     */
    public synchronized Ticket retrieveTicket(String customerName) throws InterruptedException {

        while (tickets.isEmpty()) {
            if (totalTickets == 0) {
                return null;
            }
            addSystemLog(customerName + " waiting to buy a ticket");
            wait(); // Wait until tickets are available
        }

        Ticket ticket = tickets.remove(0);
        notifyAll();
        soldTickets++;
        addSystemLog(customerName + " retrieved a ticket: Event Name - " + ticket.getEventName() + ", Ticket Price - " + ticket.getPrice() + ", Tickets in pool now: " + tickets.size());
        System.out.println(customerName+ " retrieved Ticket: 'Event Name'-" + ticket.getEventName()+ "' Ticket Price-'" + ticket.getPrice());
        return ticket;
    }

    /**
     * Gets the current number of tickets available in the pool.
     *
     * @return The number of tickets in the pool.
     */
    public int getTicketCount() {
        return tickets.size();
    }

    /**
     * Checks if a vendor can add more tickets to the pool.
     *
     * @return `true` if the pool is not full and there are total tickets remaining, `false` otherwise.
     */
    public boolean canVendorAdd() {
        return tickets.size() < maxCapacity && totalTickets > 0;
    }

    /**
     * Retrieves a copy of the simulation logs.
     *
     * @return A list of strings representing the simulation logs.
     */
    public List<String> getLogs() {
        synchronized (logs) {
            return new ArrayList<>(logs); // Return a copy to avoid concurrent modifications.
        }
    }

    /**
     * Adds a new log message to the simulation log.
     *
     * @param message The log message to be added.
     */
    public void addSystemLog(String message) {
        synchronized (logs) {
            logs.add(message);
            System.out.println("Log added: " + message);
        }
    }


//     Gets the total number of tickets sold.
//     @return The number of tickets sold.

    public int getSoldTickets() {
        return soldTickets;
    }

}

