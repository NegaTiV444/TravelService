#!/bin/bash

docker build --force-rm -t negativ444/travel-service-maven .
sudo docker-compose up -d