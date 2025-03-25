# Using Docker with SSO Application

This document describes how to build and run the SSO application using Docker.

## Prerequisites

- Docker installed on your machine
- Docker Compose installed on your machine

## Building the Docker Image

You can build the Docker image in two ways:

### Option 1: Using Docker directly

```bash
# First, build the application with Maven
./mvnw clean package

# Then, build the Docker image
docker build -t sso-app .
```

### Option 2: Using Docker Compose

```bash
# First, build the application with Maven
./mvnw clean package

# Then, build and start the container
docker-compose up --build
```

## Running the Application

### Using Docker

```bash
docker run -p 8080:8080 sso-app
```

### Using Docker Compose

```bash
docker-compose up
```

To run it in the background:

```bash
docker-compose up -d
```

## Stopping the Application

If you started the application with Docker Compose in detached mode:

```bash
docker-compose down
```

## Environment Variables

You can override environment variables in the docker-compose.yml file or by using the `-e` flag with `docker run`.

Example:
```bash
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev sso-app
```

## Volumes (Optional)

If you need persistent storage for any data, you can add volumes in the docker-compose.yml file.

## Accessing the Application

Once the container is running, access the application at:

```
http://localhost:8080
```
