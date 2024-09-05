# Bus Planner

A bus trip search application. Using Spring Web MVC, Hibernate, and ReactJS.

## Setup

>**Server Side**

- Download ApacheTomcat 9, NetBeans 17 and Java 17
- Import busapp.sql in MySQL to get database
- Edit JDBC configuration in `bus_management_system\BusPlanner\src\main\resources\hibernateConfig.properties`
- Add a properties file named *config.properties* at `\bus_management_system\BusPlanner\src\main\resources` and write a line of data like `google.api.key=YOUR_GOOGLE_MAPS_API_KEY`
  (Replace YOUR_GOOGLE_MAPS_API_KEY with your Google Maps API key)
- Build and Run *BusPlanner* Project

>**Client Side**

- Download Visual Studio Code and Node.js
- Open *busweb* folder with Visual Studio Code
- Open Command Prompt at `\bus_management_system` and run the command 
```bash
npm install -g yarn
```
then
```commandline
yarn install
```
- At `\bus_management_system\busweb` run this command to run *busweb* Project
```commandline
yarn start
```

## Usage
