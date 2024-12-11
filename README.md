# event-ticketing-system
Real-Time Event Ticketing System with Advanced Producer-Consumer Implementation

Project Setup Instructions

This guide explains how to set up and run the project locally. Please ensure you have the following prerequisites before proceeding.

Prerequisites

1.Java Development Kit (JDK): Version 17 or later.
2.Node.js and npm: Latest stable version (check with node -v and npm -v).
3.IDE/Text Editor: IntelliJ IDEA (recommended for backend), VS Code (recommended for frontend), or any editor of your choice.
4.Git: Installed and configured (optional but recommended).
5.Spring Boot Dependencies: Managed using Maven.
6.React Dependencies: Managed using npm.

1.Run the Core Java CLI Application

The Core Java CLI part is a console-based application that demonstrates how the ticketing system works. To run it:

1.Navigate to the Core Java CLI directory:

cd src/main/java/lk.ac.iit.ticketing_system_cli
2.Open the project in your IDE.
3.Locate the main class (TicketingSystem.java or equivalent) inside the lk.ac.iit.ticketing_system_cli package.
4.Click the "Run" button in your IDE to execute the application.
5.The ticketing system simulation will run in the console.

2.Setup the Backend (Spring Boot)

1.Navigate to the backend directory:

cd src/main/java/lk.ac.iit.event_ticketing_system
2.Open the backend project in IntelliJ IDEA.

IntelliJ will automatically generate the .idea folder and import the Maven dependencies.
3.Build the project:

IntelliJ IDEA will automatically download and resolve dependencies using Maven.

Alternatively, you can use the terminal:

mvn clean install
4.Run the backend:

Use IntelliJ's "Run" button or execute the following command in the terminal:

mvn spring-boot:run
5.Confirm the backend is running:

The backend should be accessible at http://localhost:8080.

3.Setup the Frontend (React)

1.Navigate to the frontend directory:

cd client
2.Install dependencies:

npm install

This command will recreate the node_modules folder with all necessary dependencies.
3.Start the frontend:

npm start

4.Confirm the frontend is running:

The application should open automatically in your default browser at http://localhost:3000.

4.Testing the Project

After adding the configuration parameters Start and stop the simulation using the provided UI on the frontend.

Logs and ticket status will be displayed in the frontend.

If any issues arise, check the backend console logs or React development tools.

Troubleshooting

Dependencies not resolving:

For backend, run:

mvn clean install

For frontend, run:

npm install

Port conflicts:

Ensure no other applications are using ports 8080 (backend) or 3000 (frontend).


This README provides all the necessary steps for setting up, running, and troubleshooting the project.


