package lk.ac.iit.ticketing_system_cli;


import static lk.ac.iit.ticketing_system_cli.TicketingSystemCoreCLI.systemStop;

public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int ticketRetrievalRate;

    public Customer(TicketPool ticketPool, int ticketRetrievalRate) {
        this.ticketPool = ticketPool;
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (ticketPool.isShouldExit()) {
                // Exit the loop if all tickets are sold
                break;
            }
            ticketPool.retrieveTicket();
            try {
                Thread.sleep(ticketRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
