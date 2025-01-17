#!/bin/bash

# Stop and remove all containers, networks, and volumes created by docker-compose
docker-compose down -v

# Build and start the containers
docker-compose up --build
