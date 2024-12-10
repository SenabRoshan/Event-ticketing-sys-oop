package lk.ac.iit.ticketing_system_cli;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TicketPool {
    private final List<Integer> ticketsStorage;
    private final TicketingConfigure ticketingConfigure;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());
    private boolean shouldExit = false;
    private boolean isFinalState = false;

    public TicketPool( TicketingConfigure ticketingConfigure) {
        this.ticketingConfigure = ticketingConfigure;
        this.ticketsStorage = Collections.synchronizedList(new LinkedList<>());
    }

//    abstraction happens here without the abstract key word that internal details what's happening in ticket storage and syncronization hidden from other parts of the system
//    where vendor and customer class only implementing the methods , they don't know what's happening inside the method.

    // Checks whether the system should exit.
    // return true if the system should exit, false otherwise.

    public boolean isShouldExit() {
        return shouldExit;
    }

    private int soldTickets = 0;

    /**
     *  Adds a ticket to the pool if space is available and tickets remain in inventory.
     *  Synchronized for thread safety. Logs warnings if the pool is full or inventory is empty.
     *
     * @param vendorID vendor releases the ticket.
     */
    public synchronized void addTicket(String vendorID) {
        int maxPoolCapacity = ticketingConfigure.getMaxTicketCapacity();
        int totalTickets = ticketingConfigure.getTotalTickets();

      if (totalTickets == 0)
            return;

        if (ticketsStorage.size() < maxPoolCapacity) {
            if (totalTickets > 0){
                int newTicket = ticketsStorage.size() + 1;
                ticketsStorage.add(newTicket);
                ticketingConfigure.decreaseTotalTickets(); // reduce total tickets after releasing it to the pool
                notifyAll();
                logger.log(Level.INFO, "{0} released ticket {1}. Remaining Total Tickets: {2}",
                        new Object[]{vendorID, newTicket, ticketingConfigure.getTotalTickets()});
            }else {
                logger.log(Level.SEVERE, "No more tickets available in inventory to add to the pool.");
            }
        }else {
            logger.log(Level.WARNING, "Ticket Pool reached its maximum capacity of {0} tickets.", maxPoolCapacity);
        }
    }

    /**
     * Retrieves a ticket from the pool if ticket available.
     * Synchronized for thread safety. Logs actions or errors when the pool is empty.
     *
     * @param customerID  customer buys the ticket
     */
    public synchronized void retrieveTicket(String customerID) {
        if (ticketsStorage.isEmpty() && ticketingConfigure.getTotalTickets() == 0) {
            shouldExit = true;
            isFinalState = true;
            System.out.println("All tickets sold.. Enter 'STOP' to terminate the system.");
            return;
        }
        if (!ticketsStorage.isEmpty()) {
            ticketsStorage.remove(0);  // removes the first ticket
            soldTickets++;
            notifyAll();
            logger.log(Level.INFO, "{0} bought a ticket. Remaining tickets in Ticket pool: {1}",
                    new Object[]{customerID, ticketsStorage.size()});
        }else {
            logger.log(Level.SEVERE, "Ticket Pool is empty. No tickets available.");
        }
    }

    /**
     * Returns current number of tickets in the pool.
     *
     * @return the size of the ticket pool.
     */
    public int getTicketCount() {
        return ticketsStorage.size();
    }

    /**
     * Checks if the system has reached its final state.
     *
     * @return true if the system is in the final state, false otherwise.
     */
    public boolean isFinalState() {
        return isFinalState;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

}
