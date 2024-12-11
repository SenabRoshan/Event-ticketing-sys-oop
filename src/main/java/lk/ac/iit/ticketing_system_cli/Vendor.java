package lk.ac.iit.ticketing_system_cli;


import static lk.ac.iit.ticketing_system_cli.TicketingSystemCoreCLI.systemStop;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private String vendorID;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, String vendorID) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorID = vendorID;
    }

    /**
     Overrides the run method of runnable interface
     Executes logic for vendor thread to release ticket to ticket pool at a specific rate
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (ticketPool.isShouldExit()) {
                // Exit the loop if all tickets are sold
                return;
            }
            ticketPool.addTicket(vendorID);
            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
