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

//    abstraction happens here without the abstract key word that internal details whats happening in ticket storage and syncronization hidden from other parts of the system
//    where vendor and customer class only implementing the methods , they don't know whats happening inside the method.

    public boolean isShouldExit() {
        return shouldExit;
    }

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
                logger.log(Level.INFO, "{0} released ticket {1}. Current count: {2}",
                        new Object[]{vendorID, newTicket, ticketsStorage.size()});
                notifyAll();
            }else {
                logger.log(Level.SEVERE, "No more tickets available in inventory to add to the pool.");
            }
        }else {
            logger.log(Level.WARNING, "Ticket Pool reached its maximum capacity of {0} tickets.", maxPoolCapacity);
        }
    }

    public synchronized void retrieveTicket() {
        if (ticketsStorage.isEmpty() && ticketingConfigure.getTotalTickets() == 0) {
            shouldExit = true;
            isFinalState = true;
            System.out.println("All tickets sold.. Enter 'STOP' to terminate the system.");
            return;
        }
        if (!ticketsStorage.isEmpty()) {
            ticketsStorage.remove(0);  // removes the first ticket
            logger.log(Level.INFO, "Ticket removed. Current count: {0}",
                    new Object[]{ticketsStorage.size()});
            notifyAll();
        }else {
            logger.log(Level.SEVERE, "Ticket Pool is empty. No tickets available.");
        }
    }

    public int getTicketCount() {
        return ticketsStorage.size();
    }

    public boolean isFinalState() {
        return isFinalState;
    }

}
