# Car Appointment Application

** prerequisites needed for application **

	- java jdk 1.8
	- docker
	- maven
	
** System Design **

The maven application is built using java spring boot with a backend postgres database as the data store. When booted up, both the application will run in its own docker container. An appointment table is created in the database with the license plate of the car as the ID. Other columns include appointment_start_time, appointment_end_time, price, and status. Default port used for application is 8080.

** Rest Endpoints **

** Get appointment (Get request) **

http://localhost:8080/api/carappointments/getappointment/{licensePlate}

EX:
http://localhost:8080/api/carappointments/getappointment/123456

** Get apointments within time range and sort by price in descending order (Get request) **

http://localhost:8080/api/carappointments/sortappointmentsbytimeprice?startTime={startTime}&endTime={endTime}

Ex:
http://localhost:8080/api/carappointments/sortappointmentsbytimeprice?startTime=2018-08-10 17:30:42&endTime=2020-08-10 17:30:42

** Delete an appointment (Delete request) **

http://localhost:8080/api/carappointments/deleteappointment/{licensePlate}

Ex:
http://localhost:8080/api/carappointments/deleteappointment/123456

**Create new Appointment (Create Request) **

http://localhost:8080/api/carappointments/create

EX:
http://localhost:8080/api/carappointments/create
with request body
{
  "licensePlate": "123456",
  "appoinmentStartTime": "2019-08-10T17:30:42",
  "appointmentEndTime": "2019-08-10T18:30:42",
  "status": "Booked",
  "price": 100
}

**Create random time appointment (Create Request)**

http://localhost:8080/api/carappointments/create/{licensePlate}

Ex:
http://localhost:8080/api/carappointments/create/123456

**Update the status of existing appointment (Put Request)**

http://localhost:8080/api/carappointments/update/{licensePlate}?status={status}

Ex:
http://localhost:8080/api/carappointments/update/123456?status=free

** To Run Tests **

	- mvn clean build


** To boot up application with posgres database as data store **

switch to root directory
	
	- docker-compose up
