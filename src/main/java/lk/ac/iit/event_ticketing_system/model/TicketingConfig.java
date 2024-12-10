package lk.ac.iit.event_ticketing_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TicketingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long configuration_id;

    private int totalTickets;

    private int ticketReleaseRate;

    private int ticketRetrievalRate;

    private int maxTicketPoolCapacity;

    public TicketingConfig(int totalTickets, int ticketReleaseRate, int ticketRetrievalRate, int maxTicketPoolCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketRetrievalRate = ticketRetrievalRate;
        this.maxTicketPoolCapacity = maxTicketPoolCapacity;
    }


    public Long getConfiguration_id() {
        return configuration_id;
    }

    public void setConfiguration_id(Long configuration_id) {
        this.configuration_id = configuration_id;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public void setTicketRetrievalRate(int ticketRetrievalRate) {
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public int getMaxTicketPoolCapacity() {
        return maxTicketPoolCapacity;
    }

    public void setMaxTicketPoolCapacity(int maxTicketPoolCapacity) {
        this.maxTicketPoolCapacity = maxTicketPoolCapacity;
    }
}
