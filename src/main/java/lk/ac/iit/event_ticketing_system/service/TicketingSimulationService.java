package lk.ac.iit.event_ticketing_system.service;

import lk.ac.iit.event_ticketing_system.model.Customer;
import lk.ac.iit.event_ticketing_system.model.TicketingConfig;
import lk.ac.iit.event_ticketing_system.model.Vendor;
import lk.ac.iit.event_ticketing_system.repository.TicketingConfigRepository;
import lk.ac.iit.event_ticketing_system.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class TicketingSimulationService {
    private final TicketPool ticketPool;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();

    @Autowired
    public TicketingSimulationService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    private boolean simulationRunning = false;

    /**
     * Starts the simulation process.
     *
     * @param savedConfig The configuration object containing all necessary parameters for the simulation.
     *
     * @throws RuntimeException If the simulation is already running.
     */
    public void startSimulation(TicketingConfig savedConfig) {
        if (simulationRunning) {
            throw new RuntimeException("Simulation is already running.");
        }
        simulationRunning = true;
        System.out.println("Simulation starting...");
        ticketPool.addSystemLog("Simulation started...");

        ticketPool.initialize(savedConfig);  // savedConfig object has all the parameter details

        int ticketReleaseRate = savedConfig.getTicketReleaseRate();
        int ticketRetrievalRate = savedConfig.getTicketRetrievalRate();

        for (int i = 1; i <= 5; i++) {
            String vendorName = "Vendor - " + i;
            Vendor vendor = new Vendor(vendorName);

            VendorService vendorService = new VendorService(ticketPool, ticketReleaseRate, "Music Fest", 500, vendor.getVendorName());
            Thread vendorThread = new Thread(vendorService, "Vendor-" + i);
            vendorThreads.add(vendorThread);
            System.out.println("Vendor thread starting");
            vendorThread.start();
            System.out.println("Vendor thread started");
        }


        for (int i = 1; i <= 10; i++) {
            String customerName = "Customer - " + i;
            Customer customer = new Customer(customerName);
            CustomerService customerService = new CustomerService(ticketPool, ticketRetrievalRate, customer.getCustomerName());
            Thread customerThread = new Thread(customerService, "Customer-" + i);
            customerThreads.add(customerThread);
            System.out.println("Customer thread starting");
            customerThread.start();
            System.out.println("Customer thread started");
        }
    }

    /**
     * Stops the ongoing simulation.
     * Interrupts all vendor and customer threads and clears the thread lists.
     */
    public void stopSimulation() {
        // Interrupt all vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }

        // Interrupt all customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }

        // Clear thread lists
        vendorThreads.clear();
        customerThreads.clear();
        ticketPool.addSystemLog("Total tickets Sold: " + ticketPool.getSoldTickets());
        ticketPool.addSystemLog("Simulation stopped.");

    }

//      Checks if the simulation is currently running.
//      @return `true` if the simulation is running, `false` otherwise.

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    /**
     * Retrieves the list of logs generated during the simulation.
     *
     * @return A list of strings representing the simulation logs.
     */
    public List<String> getLogs() {
        return ticketPool.getLogs();
    }

}



