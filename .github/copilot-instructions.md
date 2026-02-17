# Copilot Instructions

## Project Overview
- Spring Boot microservice using Gradle; Java 17 toolchain.
- Root project name: livecrick; base package: com.crick.livecrick.
- Main entrypoint: LivecrickApplication.
- 
## API Endpoints
  POST   /api/auth/register          - Register new user
  POST   /api/auth/login             - User login
  POST   /api/auth/logout            - User logout
  GET    /api/auth/verify            - Verify JWT token
  GET    /api/auth/refresh           - Refresh access token

GET    /api/users/profile          - Get current user profile
PUT    /api/users/profile          - Update user profile

GET    /api/admin/dashboard        - Get admin dashboard stats
GET    /api/admin/users            - Get all users (Admin)
PATCH  /api/admin/users/:id/role   - Update user role (Admin)
DELETE /api/admin/users/:id        - Delete user (Admin)

##Authentication & Security
-JWT-based authentication
-Role-based access control (USER, ADMIN)
-Rate limiting on public endpoints
-CORS configuration for 192.168.0.86:4200

-Content-Type: application/json
-Authorization: Bearer <JWT_TOKEN>

## Build, Run, Test
- Build: ./gradlew build
- Run: ./gradlew bootRun
- Test: ./gradlew test

## Code Layout
- Source: src/main/java/com/crick/livecrick
- Resources: src/main/resources
- Tests: src/test/java/com/crick/livecrick

## Coding Conventions
- Prefer constructor injection; keep controllers thin and delegate to services.
- Use DTOs for request/response; avoid exposing entities directly.
- Keep endpoints RESTful and consistent with existing routes.
- Add focused unit tests for services and web layer tests for controllers.

## Configuration
- Default app name is set in src/main/resources/application.properties.
- Do not commit secrets; use env vars or external config when needed.

## Notes
- Do not edit files under build/.

