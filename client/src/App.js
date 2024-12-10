import React, { useState, useEffect } from "react";
import axios from "axios";
import ConfigurationForm from "./components/ConfigurationForm";
import LogDisplay from "./components/LogDisplay";
import TicketStatus from "./components/TicketStatus";

const App = () => {
  const [simulationStatus, setSimulationStatus] = useState("");
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const fetchLogs = async () => {
      try {
        const response = await axios.get("http://localhost:8080/ticketing/logs");
        console.log("Fetched logs:", response.data); // Check the fetched logs
        setMessages(response.data); // Update the logs with the fetched data
        console.log("Updated messages:", messages); // Check if the state is updated
      } catch (error) {
        console.error("Error fetching logs:", error);
      }
    };

    const intervalId = setInterval(() => {
      console.log("Fetching logs...");
      fetchLogs();
    }, 2000); // Poll every 2 seconds

    return () => clearInterval(intervalId); // Cleanup interval on unmount
  }, []);

  useEffect(() => {
    console.log("Updated messages:", messages); // This will run when `messages` changes.
  }, [messages]); // Triggered every time `messages` changes


  const startSimulation = async (formData) => {

    try {
      const response = await axios.post("http://localhost:8080/ticketing/start", formData);
      setSimulationStatus(response.data);
      console.log("Starting simulation with:", JSON.stringify(formData, null, 2));
      // Update simulation status
    } catch (error) {
      console.error("Error starting simulation:", error);
    }
  };

  const stopSimulation = async () => {
    try {
      const response = await axios.post("http://localhost:8080/ticketing/stop");
      setSimulationStatus(response.data); // Update simulation status
    } catch (error) {
      console.error("Error stopping simulation:", error);
    }
  };

  return (
      <div style={{ fontFamily: "Arial, sans-serif", padding: "20px" }}>
        <h1>Event Ticketing System</h1>
        <ConfigurationForm
            onStart={startSimulation}
            onStop={stopSimulation}
            simulationStatus={simulationStatus}
        />
        <TicketStatus />
        <LogDisplay logs={messages} />
      </div>
  );
};

// main page
export default App;
