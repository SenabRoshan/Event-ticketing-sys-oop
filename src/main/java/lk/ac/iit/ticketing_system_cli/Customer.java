package lk.ac.iit.ticketing_system_cli;

public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int ticketRetrievalRate;
    private String customerID;

    public Customer(TicketPool ticketPool, int ticketRetrievalRate, String customerID) {
        this.ticketPool = ticketPool;
        this.ticketRetrievalRate = ticketRetrievalRate;
        this.customerID = customerID;
    }

    /**
     Overrides the run method of runnable interface
     Executes logic for customer thread to retrieve ticket from ticket pool at a specific rate
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (ticketPool.isShouldExit()) {
                // Exit the loop if all tickets are sold
                break;
            }
            ticketPool.retrieveTicket(customerID);
            try {
                Thread.sleep(ticketRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
