import React, { useState } from "react";
import axios from "axios";

const ConfigurationForm = ({ onStart, onStop, simulationStatus}) => {
    const [formData, setFormData] = useState({
        totalTickets: "",
        ticketReleaseRate: "",
        ticketRetrievalRate: "",
        maxTicketPoolCapacity: "",
    });

    const [errors, setErrors] = useState({});
    const [isSimulationRunning, setIsSimulationRunning] = useState(false);

    // Validation logic
    const validateForm = () => {
        const newErrors = {};
        const { totalTickets, releaseRate, retrievalRate, maxPoolCapacity } = formData;

        if (!totalTickets) newErrors.totalTickets = "Total Tickets is required.";
        if (!releaseRate) newErrors.releaseRate = "Ticket Release Rate is required.";
        if (!retrievalRate) newErrors.retrievalRate = "Ticket Retrieval Rate is required.";
        if (!maxPoolCapacity) newErrors.maxPoolCapacity = "Maximum Pool Capacity is required.";

        if (totalTickets && (!/^\d+$/.test(totalTickets) || parseInt(totalTickets) <= 0)) {
            newErrors.totalTickets = "Must be a positive whole number.";
        }
        if (releaseRate && (!/^\d+$/.test(releaseRate) || parseInt(releaseRate) <= 0)) {
            newErrors.releaseRate = "Must be a positive whole number.";
        }
        if (retrievalRate && (!/^\d+$/.test(retrievalRate) || parseInt(retrievalRate) <= 0)) {
            newErrors.retrievalRate = "Must be a positive whole number.";
        }
        if (maxPoolCapacity && (!/^\d+$/.test(maxPoolCapacity) || parseInt(maxPoolCapacity) <= 0)) {
            newErrors.maxPoolCapacity = "Must be a positive whole number.";
        }
        if (maxPoolCapacity && totalTickets && parseInt(maxPoolCapacity) > parseInt(totalTickets)) {
            newErrors.maxPoolCapacity = "Cannot exceed Total Tickets.";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // Start Simulation
    const handleStartSimulation = async () => {
        if (validateForm()) {
            try {
                const formattedData = {
                    totalTickets: parseInt(formData.totalTickets, 10),
                    ticketReleaseRate: parseInt(formData.releaseRate, 10),
                    ticketRetrievalRate: parseInt(formData.retrievalRate, 10),
                    maxTicketPoolCapacity: parseInt(formData.maxPoolCapacity, 10),
                };
                //await axios.post("http://localhost:8080/ticketing/start", formData); // Send config and start simulation
                onStart(formattedData)
                console.log("Sending start simulation data:", formattedData);
                setIsSimulationRunning(true);
                setErrors({});
            } catch (error) {
                console.error("Error starting simulation:", error);
            }
        }
    };

    // Stop Simulation
    const handleStopSimulation = async () => {
        try {
           // await axios.post("http://localhost:8080/ticketing/stop"); // Stop simulation
            onStop()
            setIsSimulationRunning(false);
        } catch (error) {
            console.error("Error stopping simulation:", error);
        }
    };

    return (
        <div style={{ padding: "20px", maxWidth: "400px", margin: "0 auto" }}>
            <h2 style={{color: "#36454F"}}>Configuration Form</h2>
            <div style={{ marginBottom: "10px" }}>
                <label>Total Tickets:</label>
                <input
                    type="text"
                    name="totalTickets"
                    value={formData.totalTickets}
                    onChange={(e) => setFormData({ ...formData, totalTickets: e.target.value })}
                    style={{ width: "100%", padding: "8px", margin: "5px 0" }}
                />
                {errors.totalTickets && <span style={{ color: "red" }}>{errors.totalTickets}</span>}
            </div>
            <div style={{ marginBottom: "10px" }}>
                <label>Ticket Release Rate:</label>
                <input
                    type="text"
                    name="releaseRate"
                    value={formData.releaseRate}
                    onChange={(e) => setFormData({ ...formData, releaseRate: e.target.value })}
                    style={{ width: "100%", padding: "8px", margin: "5px 0" }}
                />
                {errors.releaseRate && <span style={{ color: "red" }}>{errors.releaseRate}</span>}
            </div>
            <div style={{ marginBottom: "10px" }}>
                <label>Ticket Retrieval Rate:</label>
                <input
                    type="text"
                    name="retrievalRate"
                    value={formData.retrievalRate}
                    onChange={(e) => setFormData({ ...formData, retrievalRate: e.target.value })}
                    style={{ width: "100%", padding: "8px", margin: "5px 0" }}
                />
                {errors.retrievalRate && <span style={{ color: "red" }}>{errors.retrievalRate}</span>}
            </div>
            <div style={{ marginBottom: "10px" }}>
                <label>Maximum Pool Capacity:</label>
                <input
                    type="text"
                    name="maxPoolCapacity"
                    value={formData.maxPoolCapacity}
                    onChange={(e) => setFormData({ ...formData, maxPoolCapacity: e.target.value })}
                    style={{ width: "100%", padding: "8px", margin: "5px 0" }}
                />
                {errors.maxPoolCapacity && <span style={{ color: "red" }}>{errors.maxPoolCapacity}</span>}
            </div>

            <div style={{ display: "flex", justifyContent: "space-between" }}>
                <button
                    onClick={handleStartSimulation}
                    style={{
                        padding: "10px 20px",
                        background: isSimulationRunning ? "gray" : "green",
                        color: "white",
                        borderRadius: "10px",
                        border: "none",
                        cursor: isSimulationRunning ? "not-allowed" : "pointer",
                    }}
                    disabled={isSimulationRunning}
                >
                    Start Simulation
                </button>
                <button
                    onClick={handleStopSimulation}
                    style={{
                        padding: "10px 20px",
                        background: "red",
                        color: "white",
                        borderRadius: "10px",
                        border: "none",
                    }}
                >
                    Stop Simulation
                </button>
            </div>
        </div>
    );
};

export default ConfigurationForm;
