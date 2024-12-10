import React from "react";

const LogDisplay = ({ logs }) => {
    return (
        <div>
            <h2>Simulation Logs</h2>
            <div style={{ maxHeight: "200px", overflowY: "auto", border: "1px solid black", padding: "10px" }}>
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

export default LogDisplay;

