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


    // Initialize pool with simulation configuration
    public void initialize(TicketingConfig config) {
        this.maxCapacity = config.getMaxTicketPoolCapacity();
        this.totalTickets = config.getTotalTickets();
    }

    public synchronized boolean addTicket(Ticket ticket,String vendorName) {
        if (totalTickets == 0) {
            System.out.println("No more tickets available to add.");
            return false;
        }
        if (tickets.size() >= maxCapacity) {
            addSystemLog("Pool is full. Cannot add more tickets.");
            System.out.println("Pool is full. Cannot add ticket.");
            return false;
        }

        tickets.add(ticket);
        totalTickets--; // Reduce total tickets available
        addSystemLog(vendorName + " added a ticket. Remaining Total Tickets: " + totalTickets);
        System.out.println(vendorName + " added Ticket. Remaining Total Tickets: " + totalTickets );

        return true;
    }

    public synchronized Ticket retrieveTicket(String customerName) {
        if (totalTickets == 0 && tickets.isEmpty()) {
            System.out.println("No more tickets available for retrieval. All tickets sold.");
            return null;
        }

        if (tickets.isEmpty() && totalTickets > 0) {
            addSystemLog("No more tickets available for retrieval.");
            System.out.println("No tickets available in the pool. Ticket pool is empty.");
            return null;
        }

        Ticket ticket = tickets.remove(0);
        addSystemLog(customerName + " retrieved a ticket: Event Name - " + ticket.getEventName() + ", Ticket Price - " + ticket.getPrice());
        System.out.println(customerName+ " retrieved Ticket: 'Event Name'-" + ticket.getEventName()+ "' Ticket Price-'" + ticket.getPrice());
        return ticket;
    }

    public int getTicketCount() {
        return tickets.size();
    }

    public boolean canVendorAdd() {
        return tickets.size() < maxCapacity && totalTickets > 0;
    }

    public boolean canCustomerRetrieve() {
        return tickets.size() > 0;
    }

    public List<String> getLogs() {
        synchronized (logs) {
            System.out.println("Retrieving logs: " + logs);
            return new ArrayList<>(logs); // Return a copy to avoid concurrent modifications.
        }
    }

    public void addSystemLog(String message) {
        synchronized (logs) {
            logs.add(message);
            System.out.println("Log added: " + message);
            System.out.println("Current logs: " + logs);
        }
    }
}

