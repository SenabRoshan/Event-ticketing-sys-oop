package lk.ac.iit.event_ticketing_system.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendor_id;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    private String vendorName;

    public Vendor(String vendorName) {
        this.vendorName = vendorName;
    }


    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
