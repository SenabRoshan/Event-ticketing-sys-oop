package lk.ac.iit.ticketing_system_cli;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TicketingConfigure {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public int getTotalTickets() {
        return totalTickets;
    }

    public void decreaseTotalTickets() {
        if (totalTickets > 0) {
            totalTickets--;
        }
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter Total Tickets: ");
                String configInput = scanner.nextLine();
                this.totalTickets = Integer.parseInt(configInput);

                if (totalTickets > 0) {
                    break;
                } else {
                    System.out.println("Please Ensure the Total Tickets is greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter a Valid Integer.");
            }
        }
    }

    public void setTicketReleaseRate(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter Ticket Release Rate (in seconds): ");
                String configInput = scanner.nextLine();
                this.ticketReleaseRate = Integer.parseInt(configInput);
                if (ticketReleaseRate > 0) {
                    break;
                } else {
                    System.out.println("Please Ensure the Ticket Release Rate is greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter a Valid Integer.");
            }
        }
    }

    public void setCustomerRetrievalRate(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter Customer Retrieval Rate (in seconds): ");
                String configInput = scanner.nextLine();
                this.customerRetrievalRate = Integer.parseInt(configInput);
                if (customerRetrievalRate > 0) {
                    break;
                } else {
                    System.out.println("Please Ensure the Customer Retrieval Rate is greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter a Valid Integer.");
            }
        }
    }

    public void setMaxTicketCapacity(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter Maximum Ticket Capacity: ");
                String configInput = scanner.nextLine();
                this.maxTicketCapacity = Integer.parseInt(configInput);

                if (maxTicketCapacity <= 0) {
                    System.out.println("Please Ensure the Maximum Ticket Capacity is greater than 0.");
                } else if (maxTicketCapacity <= totalTickets) {
                    break; // Break if both conditions are satisfied
                } else {
                    System.out.println("Maximum Ticket Capacity must be less than Total Number of Tickets.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter a Valid Integer.");
            }
        }
    }

    public void saveConfiguration() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("configuration.json")) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved successfully to config.json!");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    public static TicketingConfigure loadConfiguration() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("configuration.json")) {
            return gson.fromJson(reader, TicketingConfigure.class);
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            return null;
        }
    }

}
