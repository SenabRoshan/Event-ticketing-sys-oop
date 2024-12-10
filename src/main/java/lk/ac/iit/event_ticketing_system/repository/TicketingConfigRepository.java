package lk.ac.iit.event_ticketing_system.repository;

import lk.ac.iit.event_ticketing_system.model.TicketingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketingConfigRepository extends JpaRepository<TicketingConfig, Long> {

}
