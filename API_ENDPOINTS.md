# LiveCrick API Endpoints Reference

## Base URL
```
http://192.168.0.86:8080
```

## Authentication
Most endpoints require JWT token in the Authorization header:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🔐 Authentication Endpoints

### Register New User
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "John Doe"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGc...",
  "email": "user@example.com",
  "role": "USER"
}
```

### Logout
```http
POST /api/auth/logout
Authorization: Bearer <token>
```

### Verify Token
```http
GET /api/auth/verify
Authorization: Bearer <token>

Response:
{
  "valid": true
}
```

### Refresh Token
```http
GET /api/auth/refresh
Authorization: Bearer <token>

Response:
{
  "token": "eyJhbGc...",
  "email": "user@example.com",
  "role": "USER"
}
```

---

## 👤 User Management

### Get User Profile
```http
GET /api/users/profile
Authorization: Bearer <token>

Response:
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "role": "USER",
  "createdAt": 1707955200000,
  "active": true
}
```

### Update User Profile
```http
PUT /api/users/profile
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "John Updated",
  "email": "newemail@example.com"
}
```

---

## 👨‍💼 Admin Endpoints (ADMIN role required)

### Get Admin Dashboard
```http
GET /api/admin/dashboard
Authorization: Bearer <token>

Response:
{
  "totalUsers": 10,
  "activeUsers": 8,
  "adminUsers": 2,
  "statistics": {...}
}
```

### Get All Users
```http
GET /api/admin/users
Authorization: Bearer <token>

Response:
[
  {
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe",
    "role": "USER",
    "active": true
  }
]
```

### Update User Role
```http
PATCH /api/admin/users/{id}/role
Authorization: Bearer <token>
Content-Type: application/json

{
  "role": "ADMIN"
}
```

### Delete User
```http
DELETE /api/admin/users/{id}
Authorization: Bearer <token>

Response:
{
  "message": "User deleted successfully"
}
```

---

## 🏏 Player Management

### Get All Players
```http
GET /api/players
GET /api/players?country=India
GET /api/players?role=BATSMAN
GET /api/players?teamId=1
GET /api/players?search=Kohli

Response:
[
  {
    "id": 1,
    "name": "Virat Kohli",
    "photo": "https://...",
    "dateOfBirth": "1988-11-05",
    "country": "India",
    "role": "BATSMAN",
    "battingStyle": "RIGHT_HAND",
    "bowlingStyle": "MEDIUM",
    "teamId": 1,
    "statistics": {
      "matches": 254,
      "runs": 12169,
      "average": 57.32,
      ...
    },
    "createdAt": 1707955200000,
    "updatedAt": 1707955200000
  }
]
```

### Get Player by ID
```http
GET /api/players/{id}

Response: Single player object
```

### Create Player (Admin)
```http
POST /api/players
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Virat Kohli",
  "photo": "https://example.com/virat.jpg",
  "dateOfBirth": "1988-11-05",
  "country": "India",
  "role": "BATSMAN",
  "battingStyle": "RIGHT_HAND",
  "bowlingStyle": "MEDIUM",
  "teamId": 1,
  "statistics": {
    "matches": 254,
    "innings": 250,
    "runs": 12169,
    "average": 57.32,
    "strikeRate": 93.17,
    "highestScore": 183,
    "hundreds": 43,
    "fifties": 62,
    "wickets": 4,
    "bowlingAverage": 166.25,
    "economy": 6.58,
    "bestBowling": "1/15"
  }
}
```

### Update Player (Admin)
```http
PUT /api/players/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Virat Kohli Updated",
  "statistics": {...}
}
```

### Delete Player (Admin)
```http
DELETE /api/players/{id}
Authorization: Bearer <token>

Response:
{
  "message": "Player deleted successfully"
}
```

### Get Player Statistics
```http
GET /api/players/{id}/statistics

Response: Player object with full statistics
```

### Search Players
```http
GET /api/players/search?q=kohli

Response: Array of matching players
```

---

## 🏆 Team Management

### Get All Teams
```http
GET /api/teams
GET /api/teams?country=India

Response:
[
  {
    "id": 1,
    "name": "India National Team",
    "shortName": "IND",
    "logo": "https://...",
    "country": "India",
    "playerIds": [1, 2, 3, 4, 5],
    "statistics": {
      "matchesPlayed": 100,
      "won": 65,
      "lost": 30,
      "tied": 3,
      "noResult": 2
    },
    "createdAt": 1707955200000,
    "updatedAt": 1707955200000
  }
]
```

### Get Team by ID
```http
GET /api/teams/{id}

Response: Single team object
```

### Create Team (Admin)
```http
POST /api/teams
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "India National Team",
  "shortName": "IND",
  "logo": "https://example.com/india.png",
  "country": "India",
  "playerIds": [1, 2, 3]
}
```

### Update Team (Admin)
```http
PUT /api/teams/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Team India",
  "playerIds": [1, 2, 3, 4, 5]
}
```

### Delete Team (Admin)
```http
DELETE /api/teams/{id}
Authorization: Bearer <token>

Response:
{
  "message": "Team deleted successfully"
}
```

### Get Team Players
```http
GET /api/teams/{id}/players

Response: Array of player objects
```

### Get Team Statistics
```http
GET /api/teams/{id}/statistics

Response:
{
  "matchesPlayed": 100,
  "won": 65,
  "lost": 30,
  "tied": 3,
  "noResult": 2
}
```

---

## 🏟️ Match Management

### Get All Matches
```http
GET /api/matches
GET /api/matches?status=LIVE
GET /api/matches?status=UPCOMING
GET /api/matches?status=COMPLETED
GET /api/matches?teamId=1

Response:
[
  {
    "id": 1,
    "team1Id": 1,
    "team2Id": 2,
    "team1Name": "India",
    "team2Name": "Australia",
    "matchType": "T20",
    "venue": "Wankhede Stadium, Mumbai",
    "matchDate": 1707955200000,
    "status": "LIVE",
    "tossWinner": "India",
    "tossDecision": "BAT",
    "result": null,
    "winnerTeamId": null,
    "innings": [...],
    "createdAt": 1707955200000,
    "updatedAt": 1707955200000
  }
]
```

### Get Live Matches
```http
GET /api/matches/live

Response: Array of live matches
```

### Get Upcoming Matches
```http
GET /api/matches/upcoming

Response: Array of upcoming matches (sorted by date)
```

### Get Match History
```http
GET /api/matches/history

Response: Array of completed matches (sorted by date DESC)
```

### Get Match by ID
```http
GET /api/matches/{id}

Response: Single match object with full details
```

### Get Match Scoreboard
```http
GET /api/matches/{id}/scoreboard

Response: Match object with innings and ball-by-ball data
```

### Create Match (Admin)
```http
POST /api/matches
Authorization: Bearer <token>
Content-Type: application/json

{
  "team1Id": 1,
  "team2Id": 2,
  "team1Name": "India",
  "team2Name": "Australia",
  "matchType": "T20",
  "venue": "Wankhede Stadium, Mumbai",
  "matchDate": 1707955200000
}
```

### Update Match (Admin)
```http
PUT /api/matches/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "venue": "New Venue",
  "status": "LIVE",
  "result": "India won by 6 wickets",
  "winnerTeamId": 1
}
```

### Update Match Status (Admin)
```http
PATCH /api/matches/{id}/status
Authorization: Bearer <token>
Content-Type: application/json

{
  "status": "LIVE"
}
```

### Delete Match (Admin)
```http
DELETE /api/matches/{id}
Authorization: Bearer <token>

Response:
{
  "message": "Match deleted successfully"
}
```

---

## 📊 Score Updates (Admin Only)

### Start Innings
```http
POST /api/matches/{matchId}/innings
Authorization: Bearer <token>
Content-Type: application/json

{
  "inningsNumber": 1,
  "battingTeamId": 1,
  "bowlingTeamId": 2
}

Response: Updated match object
```

### Record Ball
```http
POST /api/matches/{matchId}/ball
Authorization: Bearer <token>
Content-Type: application/json

{
  "inningsNumber": 1,
  "batsmanId": 5,
  "bowlerId": 12,
  "runs": 4,
  "isWicket": false,
  "wicketType": null,
  "extraType": null,
  "extraRuns": 0
}

Response: Updated match object
```

### Record Wicket
```http
POST /api/matches/{matchId}/wicket
Authorization: Bearer <token>
Content-Type: application/json

{
  "inningsNumber": 1,
  "batsmanId": 5,
  "bowlerId": 12,
  "wicketType": "BOWLED"
}

Response: Updated match object
```

### Record Extra
```http
POST /api/matches/{matchId}/extra
Authorization: Bearer <token>
Content-Type: application/json

{
  "inningsNumber": 1,
  "extraType": "WIDE",
  "runs": 1
}

Response: Updated match object
```

### Record Toss
```http
POST /api/matches/{matchId}/toss
Authorization: Bearer <token>
Content-Type: application/json

{
  "tossWinner": "India",
  "tossDecision": "BAT"
}

Response: Updated match object
```

---

## 📈 Statistics & Analytics

### Get Top Performers
```http
GET /api/statistics/players/top
GET /api/statistics/players/top?limit=10

Response:
{
  "topRunScorers": [
    {
      "playerId": 1,
      "playerName": "Virat Kohli",
      "value": 12169,
      "metric": "runs"
    }
  ],
  "topWicketTakers": [...],
  "bestAverages": [...]
}
```

---

## 🔍 Search & Filters

### Global Search
```http
GET /api/search?q=kohli
GET /api/search?q=india&type=player
GET /api/search?q=wankhede&type=match
GET /api/search?q=australia&type=team

Response:
[
  {
    "type": "player",
    "id": 1,
    "name": "Virat Kohli",
    "description": "BATSMAN - India"
  }
]
```

### Get Countries Filter
```http
GET /api/filters/countries

Response:
["Australia", "England", "India", "Pakistan", ...]
```

### Get Roles Filter
```http
GET /api/filters/roles

Response:
["BATSMAN", "BOWLER", "ALL_ROUNDER", "WICKET_KEEPER"]
```

### Get Venues Filter
```http
GET /api/filters/venues

Response:
["Eden Gardens", "Lords", "MCG", "Wankhede Stadium", ...]
```

### Get All Filters
```http
GET /api/filters

Response:
{
  "countries": ["Australia", "India", ...],
  "roles": ["BATSMAN", "BOWLER", ...],
  "venues": ["Eden Gardens", "MCG", ...]
}
```

---

## ❤️ Health Check

### Welcome Message
```http
GET /

Response:
{
  "message": "Welcome to LiveCrick API"
}
```

### Health Status
```http
GET /health

Response:
{
  "status": "UP"
}
```

### Readiness Check
```http
GET /health/ready

Response:
{
  "status": "READY"
}
```

---

## 🔧 Common Response Codes

| Code | Description |
|------|-------------|
| 200  | Success |
| 201  | Created |
| 400  | Bad Request |
| 401  | Unauthorized |
| 403  | Forbidden |
| 404  | Not Found |
| 500  | Internal Server Error |

---

## 📝 Notes

1. **Authentication**: Most endpoints require JWT token. Get token from `/api/auth/login`
2. **Admin Endpoints**: Require ADMIN role
3. **CORS**: Configured for `http://192.168.0.86:4200`
4. **Content-Type**: All POST/PUT/PATCH requests require `Content-Type: application/json`
5. **Timestamps**: All timestamps are in Unix epoch milliseconds
6. **Empty Collections**: Endpoints return empty arrays `[]` when no data exists
7. **Search**: Case-insensitive search across relevant fields
8. **Filters**: Can be combined with query parameters

---

## 🚀 Quick Test Commands (PowerShell)

```powershell
# Health check
Invoke-WebRequest -Uri "http://192.168.0.86:8080/health" -UseBasicParsing

# Get all players
Invoke-WebRequest -Uri "http://192.168.0.86:8080/api/players" -UseBasicParsing

# Search
Invoke-WebRequest -Uri "http://192.168.0.86:8080/api/search?q=test" -UseBasicParsing

# Get filters
Invoke-WebRequest -Uri "http://192.168.0.86:8080/api/filters/roles" -UseBasicParsing
```
