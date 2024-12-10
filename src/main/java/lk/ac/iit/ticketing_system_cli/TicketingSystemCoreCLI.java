package lk.ac.iit.ticketing_system_cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TicketingSystemCoreCLI {
    private static boolean isSysRunning = false;
    private static final Logger logger = Logger.getLogger(TicketingSystemCoreCLI.class.getName());
    private static List<Thread> vendorThreads = new ArrayList<>();
    private static List<Thread> customerThreads = new ArrayList<>();
    private static TicketingConfigure ticketingConfigure = new TicketingConfigure();

    public static void main(String[] args) throws InterruptedException {
        logger.setLevel(Level.INFO);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to load the previous configuration? (yes/no)");
        String loadResponse = scanner.nextLine().trim().toLowerCase();

        if (loadResponse.equals("yes")) {
            TicketingConfigure loadedConfig = TicketingConfigure.loadConfiguration();
            if (loadedConfig != null) {
                ticketingConfigure = loadedConfig;
                // Log other parameters to ensure they are set properly
                System.out.println("Loaded Ticket Release Rate: " + ticketingConfigure.getTicketReleaseRate());
                System.out.println("Loaded Customer Retrieval Rate: " + ticketingConfigure.getCustomerRetrievalRate());
                System.out.println("Loaded Max Ticket Capacity: " + ticketingConfigure.getMaxTicketCapacity());
                System.out.println("Loaded Total Tickets: " + ticketingConfigure.getTotalTickets());
                System.out.println("Configuration loaded successfully!");
            } else {
                System.out.println("Failed to load configuration. You can configure manually.");
                configParameters(scanner);
            }
        } else {
            System.out.println("No configuration loaded. Please Configure manually.");
            configParameters(scanner);
        }

        // Prompt user to save the current configuration
        System.out.println("Do you want to save the current configuration? (yes/no)");
        String saveResponse = scanner.nextLine().trim().toLowerCase();

        if (saveResponse.equals("yes")) {
            ticketingConfigure.saveConfiguration();
        }

        TicketPool ticketPool = new TicketPool(ticketingConfigure);

        System.out.println("1. 'START' to start simulation\n"
                + "2. 'PAUSE' to pause the simulation\n"
                + "3. 'STOP' to stop the simulation \n");

        System.out.println("Enter an action:");

        while (true) {

            String action = scanner.nextLine().toUpperCase().trim();
            if (ticketPool.isFinalState() && !action.equals("STOP")) {
                logger.log(Level.WARNING, "All the threads are completed. You can only enter 'EXIT' to terminate the system.");
                continue;
            }

            switch (action) {
                case "START":
                    if (isSysRunning) {
                        logger.log(Level.WARNING, "System is already running!");
                    } else {
                        logger.log(Level.INFO, "Starting ticketing system...");
                        isSysRunning = true;


                        // Start vendor threads
                        for (int i = 1; i <= 5; i++) {
                            Vendor vendor = new Vendor(ticketPool, ticketingConfigure.getTicketReleaseRate(), "Vendor-" + i);
                            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
                            vendorThreads.add(vendorThread);
                            vendorThread.start();
                        }

                        // Start customer threads
                        for (int i = 1; i <= 10; i++) {
                            Customer customer = new Customer(ticketPool, ticketingConfigure.getCustomerRetrievalRate(), "Customer-" + i);
                            Thread customerThread = new Thread(customer, "Customer-" + i);
                            customerThreads.add(customerThread);
                            customerThread.start();
                        }
                    }
                    break;

                case "PAUSE":
                    if (!isSysRunning) {
                        logger.log(Level.WARNING, "System is not running! Cannot pause...");
                    } else {
                        logger.log(Level.INFO, "Pausing ticketing system...");
                        systemStop();
                        logger.log(Level.INFO, "Ticketing System paused!");
                    }
                    break;

                case "STOP":
                    if (!ticketPool.isShouldExit()) {
                        systemStop();
                        System.out.println("Total tickets Sold:" + ticketPool.getSoldTickets());
                        logger.log(Level.INFO, "Stopping ticketing system...");
                    } else {
                        waitForThreadsToFinish();
                        logger.log(Level.INFO, "Stopping ticketing system...");
                    }
                    return;

                default:
                    logger.log(Level.WARNING, "Invalid action. Use 'START', 'PAUSE', or 'STOP'");
                    break;
            }
        }
    }

    /**
     * Configures ticketing system parameters using user input.
     * Prompts the user for parameters and sets the ticketing configuration accordingly.
     *
     * @param scanner the scanner to read user input from the console.
     */
    public static void configParameters(Scanner scanner) {
        ticketingConfigure.setTotalTickets(scanner);
        ticketingConfigure.setTicketReleaseRate(scanner);
        ticketingConfigure.setCustomerRetrievalRate(scanner);
        ticketingConfigure.setMaxTicketCapacity(scanner);
    }

    /**
     * Waits for all vendor and customer threads to complete execution.
     * Clears the thread lists after completion.
     */
    public static void waitForThreadsToFinish() {
        for (Thread thread : vendorThreads) {
            try {
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        vendorThreads.clear();

        for (Thread thread : customerThreads) {
            try {
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        customerThreads.clear();
    }

    /**
     * Stops the system by interrupting all vendor and customer threads and waiting for them to finish.
     */
    public static void systemStop() {
        isSysRunning = false;
        // Interrupt vendor threads
        for (Thread thread : vendorThreads) {
            if (thread != null) {
                thread.interrupt();
            }
        }

        // Interrupt customer threads
        for (Thread thread : customerThreads) {
            if (thread != null) {
                thread.interrupt();
            }
        }
        // Wait for threads to finish
        waitForThreadsToFinish();
    }
}
