Test Spring Boot project: REST web-service + Telegram Bot

Service provides the ability to add, read, delete, edit information about cities.

Bot gets the name of the city and displays information about it.

Requirements:
	Docker

Deployment

	Web-service: 
		- run TravelService/deploy.sh 
		
	Telegram bot: 
		- open TelegramBot/src/main/resources/application.properties and change value of 'api.url' propery to URL where you started web-service ('host.docker.internal' if you run web-service and bot on the same device)
		- run  TelegramBot/run.sh

Web-service API:

	GET /api/city - get all cities
	GET /api/city/{name} - find city by name
	POST /api/city - add new city
	PUT /api/city - update city
	DELETE /api/city/{id} - delete city by id

Telegram bot usage:

	- Find EasyTravelBot in Telegram and press 'start'
	- Enter city name 
