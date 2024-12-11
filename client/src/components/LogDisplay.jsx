import React from "react";

const LogDisplay = ({ logs }) => {
    return (
        <div>
            <h2 style={{color: "#36454F"}}>Simulation Logs & Ticket Availability</h2>
            <div style={{ maxHeight: "200px", overflowY: "auto", border: "1px solid black", padding: "10px", borderRadius: "8px",boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)" }}>
                {logs.length === 0 ? (
                    <p>No logs available yet</p>
                ) : (
                    logs.map((log, index) => (
                        <div key={index}>{log}</div>
                    ))
                )}
            </div>
        </div>
    );
};

// this is real time logging page

export default LogDisplay;

