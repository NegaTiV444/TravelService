#!/bin/bash

docker build --force-rm -t negativ444/travel-bot-telegram-maven .
docker run negativ444/travel-bot-telegram-maven