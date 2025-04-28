# Synchrony API Coding Challenge Project

## Overview

This is a backend RESTful application developed using Spring Boot 3.2.x and Core Java 17.  
It provides APIs to:

- Register users securely
- Upload images to Imgur
- Store uploaded images under user profiles
- View user profile with images
- Secure endpoints using OAuth2 Resource Server
- Publish Kafka event on every image upload

---

## Technologies Used

- Java 17
- Spring Boot 3.2.x
- Spring Security 6.1+
- Spring Data JPA
- H2 In-Memory Database
- Imgur Public API
- Kafka (Apache Kafka Local)
- Maven
- Docker (for running Kafka locally)

---

## Project Architecture

Controller → Service → Repository → Database (H2)

External Integration → Imgur API + Kafka