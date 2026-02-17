# CrickLive Backend - Heroku Deployment Guide

## 📋 Prerequisites

1. Heroku CLI installed ([Download](https://devcenter.heroku.com/articles/heroku-cli))
2. Git installed
3. Heroku account created

## ✅ Configuration Files Created

The following files have been created/updated for Heroku deployment:

### 1. `build.gradle`
- Added `stage` task (required by Heroku)
- Configured `bootJar` to create executable JAR
- JAR filename: `livecrick.jar`

### 2. `Procfile`
- Defines how Heroku runs your application
- Uses dynamic `$PORT` environment variable

### 3. `system.properties`
- Specifies Java 17 runtime

### 4. `application.properties`
- Updated to use environment variables
- Configurable PORT, CORS, JWT settings

## 🚀 Deployment Steps

### Step 1: Initialize Git Repository (if not already done)

```bash
cd d:\Veymana\CrickLive\backend\livecrick
git init
git add .
git commit -m "Prepare for Heroku deployment"
```

### Step 2: Login to Heroku

```bash
heroku login
```

### Step 3: Create Heroku App

```bash
# Replace 'your-app-name' with your desired app name (must be unique)
heroku create your-cricklive-backend
```

**Note:** If you don't specify a name, Heroku will generate a random one.

### Step 4: Set Environment Variables

```bash
# Set JWT secret (use a strong random string in production)
heroku config:set JWT_SECRET=your-super-secret-jwt-key-change-this-to-something-very-secure

# Set JWT expiration (1 hour = 3600000ms)
heroku config:set JWT_EXPIRATION=3600000

# Set CORS origins (update after deploying frontend)
heroku config:set CORS_ORIGINS=https://your-frontend-app.herokuapp.com,http://localhost:4200

# Set WebSocket CORS origins
heroku config:set WS_CORS_ORIGINS=https://your-frontend-app.herokuapp.com,http://localhost:4200
```

### Step 5: Add Buildpack (if needed)

```bash
# Heroku should auto-detect Gradle, but if not:
heroku buildpacks:set heroku/gradle
```

### Step 6: Deploy to Heroku

```bash
git push heroku main
```

If your branch is named `master` instead of `main`:
```bash
git push heroku master
```

### Step 7: Check Deployment Status

```bash
# View logs
heroku logs --tail

# Check app status
heroku ps

# Open app in browser
heroku open
```

## 🔍 Verify Deployment

Once deployed, your backend will be available at:
```
https://your-app-name.herokuapp.com
```

Test the health endpoint:
```
https://your-app-name.herokuapp.com/api/health
```

## 🔧 Troubleshooting

### View Logs
```bash
heroku logs --tail
```

### Restart App
```bash
heroku restart
```

### Check Config Variables
```bash
heroku config
```

### Run Commands on Heroku
```bash
heroku run bash
```

## 📱 Update Frontend Configuration

After deployment, update your Angular frontend environment files:

**`frontend/src/environments/environment.prod.ts`**:
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://your-cricklive-backend.herokuapp.com/api',
  wsUrl: 'wss://your-cricklive-backend.herokuapp.com/ws'
};
```

## 🔄 Redeploy After Changes

Whenever you make code changes:

```bash
git add .
git commit -m "Your commit message"
git push heroku main
```

## 📊 Monitor Your App

```bash
# View metrics
heroku metrics

# View dyno information
heroku ps

# Scale dynos (if needed)
heroku ps:scale web=1
```

## 💡 Important Notes

1. **Free Tier Limitations:**
   - App sleeps after 30 minutes of inactivity
   - First request after sleep takes ~10 seconds
   - Limited to 550-1000 free dyno hours/month

2. **CORS Configuration:**
   - Update `CORS_ORIGINS` config var after deploying frontend
   - Include both production and localhost URLs for testing

3. **JWT Secret:**
   - Always use a strong, random secret in production
   - Never commit secrets to version control
   - Use `heroku config:set` to update

4. **Logs:**
   - Heroku stores only last 1500 lines
   - Consider adding a logging service (Papertrail, etc.)

## 🎯 Next Steps

1. ✅ Deploy backend to Heroku
2. ⬜ Deploy frontend (Netlify/Vercel/Heroku)
3. ⬜ Update CORS configuration with frontend URL
4. ⬜ Test end-to-end integration
5. ⬜ Set up custom domain (optional)

## 📞 Support Commands

```bash
# Get app info
heroku apps:info

# View releases
heroku releases

# Rollback to previous release
heroku rollback

# Access database (if PostgreSQL addon added)
heroku pg:psql
```

---

**Your backend is now ready for Heroku deployment! 🚀**
