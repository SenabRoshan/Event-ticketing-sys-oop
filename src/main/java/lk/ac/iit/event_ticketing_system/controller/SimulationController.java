package lk.ac.iit.event_ticketing_system.controller;

import lk.ac.iit.event_ticketing_system.model.TicketingConfig;
import lk.ac.iit.event_ticketing_system.repository.TicketingConfigRepository;
import lk.ac.iit.event_ticketing_system.service.TicketingSimulationService;
import lk.ac.iit.event_ticketing_system.util.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticketing")
public class SimulationController {

    private final TicketingSimulationService simulationService;
    private final TicketingConfigRepository ticketingConfigRepository;
    private final TicketPool ticketPool;

    @Autowired
    public SimulationController(TicketingSimulationService simulationService, TicketingConfigRepository ticketingConfigRepository, TicketPool ticketPool) {
        this.simulationService = simulationService;
        this.ticketingConfigRepository = ticketingConfigRepository;
        this.ticketPool = ticketPool;
    }


    @PostMapping("/start")
    public ResponseEntity<String> startSimulation(@RequestBody TicketingConfig config) {
        if (simulationService.isSimulationRunning()) {
            System.out.println("Received simulation config: " + config);
            return ResponseEntity.status(HttpStatus.OK).body("Simulation is already running.");
        }
        // Step 1: Save the configuration to the database
        TicketingConfig savedConfig = ticketingConfigRepository.save(config);

        // Step 2: Start the simulation using the saved configuration
        simulationService.startSimulation(savedConfig);

        // Respond with a success message
        return ResponseEntity.ok("Simulation started with saved configuration.");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSimulation() {
        try {
            // Stop the simulation
            simulationService.stopSimulation();
            System.out.println("Simulation stopped.");
            return ResponseEntity.ok("Simulation stopped successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error stopping the simulation: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public String getSimulationStatus() {
        boolean isRunning = simulationService.isSimulationRunning();
        return isRunning ? "Simulation is running" : "Simulation is not running";
    }

    @GetMapping("/logs")
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs = simulationService.getLogs();
        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logs);
    }



}
