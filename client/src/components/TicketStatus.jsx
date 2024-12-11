import React from "react";

const TicketStatus = ({ simulationStatus }) => {
    return (
        <div>
            <h2 style={{color: "#36454F"}}>Simulation Status</h2>
            <p>{simulationStatus}</p>
        </div>
    );
};

export default TicketStatus;
