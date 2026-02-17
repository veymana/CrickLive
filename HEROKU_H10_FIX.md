# 🔧 HEROKU DEPLOYMENT FIX - H10 Error Resolution

## ✅ Issue Fixed!

**Problem:** The `stage` task was running in wrong order: `build` → `clean` → `stage`
- This caused the JAR file to be deleted after building!

**Solution:** Changed task order to: `clean` → `build` → `stage`
- Also added explicit `mainClass` to `bootJar` configuration

---

## 🚀 Redeploy to Heroku (Fix the H10 Error)

### Option 1: Using Heroku CLI (From any machine with Git + Heroku CLI)

```bash
# Navigate to backend directory
cd d:\Veymana\CrickLive\backend\livecrick

# If git not initialized yet:
git init
git add .
git commit -m "Fix stage task order for Heroku deployment"

# Connect to existing Heroku app
heroku git:remote -a livecrick-1cc3f9e18231

# Push and deploy
git push heroku master --force
```

### Option 2: Using Heroku Dashboard (No CLI needed)

1. Go to: https://dashboard.heroku.com/apps/livecrick-1cc3f9e18231
2. Click on **"Deploy"** tab
3. Under **Deployment method**, choose **GitHub** or **Heroku Git**
4. Connect your repository and deploy the `master` branch

### Option 3: Manual Deployment via Heroku Git

If you have the Heroku app but no local git:

```bash
# Install Git first (if not installed)
# Download from: https://git-scm.com/downloads

# Navigate to backend
cd d:\Veymana\CrickLive\backend\livecrick

# Initialize git
git init

# Add all files
git add .

# Commit
git commit -m "Fix Heroku deployment - corrected stage task order"

# Add Heroku remote
heroku git:remote -a livecrick-1cc3f9e18231

# Deploy
git push heroku master
```

---

## 📊 Check Deployment Status

### View Heroku Logs (Install Heroku CLI first)

```bash
# Install Heroku CLI from: https://devcenter.heroku.com/articles/heroku-cli

# Login
heroku login

# View real-time logs
heroku logs --tail --app livecrick-1cc3f9e18231

# View last 100 lines
heroku logs -n 100 --app livecrick-1cc3f9e18231
```

### Check App Status

```bash
# Check if app is running
heroku ps --app livecrick-1cc3f9e18231

# Restart app
heroku restart --app livecrick-1cc3f9e18231

# View app info
heroku apps:info --app livecrick-1cc3f9e18231
```

---

## 🔍 What Was Wrong?

### Before (WRONG):
```gradle
task stage(dependsOn: ['build', 'clean']) {  // ❌ Build, then clean
    // JAR gets deleted!
}
```

**Execution order:**
1. `build` - Creates JAR → ✅ livecrick.jar created
2. `clean` - Deletes build/ → ❌ livecrick.jar DELETED
3. `stage` - Runs (but JAR is gone!)
4. Heroku tries to start → ❌ H10 error (JAR not found)

### After (CORRECT):
```gradle
task stage(dependsOn: ['clean', 'build']) {  // ✅ Clean, then build
    // JAR remains!
}

bootJar {
    enabled = true
    archiveFileName = 'livecrick.jar'
    mainClass = 'com.crick.livecrick.LivecrickApplication'  // ✅ Explicit main class
}
```

**Execution order:**
1. `clean` - Deletes old build/
2. `build` - Creates fresh JAR → ✅ livecrick.jar created
3. `stage` - Runs (JAR exists!)
4. Heroku starts app → ✅ SUCCESS

---

## ✅ Verify Local Build

Before redeploying, verify locally:

```bash
cd d:\Veymana\CrickLive\backend\livecrick

# Run the stage task
.\gradlew clean stage --no-daemon

# Verify JAR exists
ls build\libs\

# Should show: livecrick.jar (27.6 MB)
```

---

## 🌐 After Successful Deployment

### Test Your API

```bash
# Health check (create this endpoint if it doesn't exist)
curl https://livecrick-1cc3f9e18231.herokuapp.com/api/health

# Or test existing endpoints
curl https://livecrick-1cc3f9e18231.herokuapp.com/api/auth/login
```

### Update Frontend Configuration

Update your Angular environment files:

**frontend/src/environments/environment.prod.ts:**
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://livecrick-1cc3f9e18231.herokuapp.com/api',
  wsUrl: 'wss://livecrick-1cc3f9e18231.herokuapp.com/ws'
};
```

### Update CORS Settings on Heroku

```bash
# Set CORS after deploying frontend
heroku config:set CORS_ORIGINS=https://your-frontend.herokuapp.com,https://your-frontend.netlify.app --app livecrick-1cc3f9e18231

# Set WebSocket CORS
heroku config:set WS_CORS_ORIGINS=https://your-frontend.herokuapp.com --app livecrick-1cc3f9e18231
```

---

## 🆘 Still Having Issues?

### Check Config Vars

```bash
heroku config --app livecrick-1cc3f9e18231
```

Should show:
- `JWT_SECRET`
- `JWT_EXPIRATION`
- `CORS_ORIGINS`
- `WS_CORS_ORIGINS`

### Set Missing Config Vars

```bash
heroku config:set JWT_SECRET=your-super-secret-key --app livecrick-1cc3f9e18231
heroku config:set JWT_EXPIRATION=3600000 --app livecrick-1cc3f9e18231
heroku config:set CORS_ORIGINS=http://localhost:4200 --app livecrick-1cc3f9e18231
```

### Common Issues

1. **H10 Error** → App crashed during startup
   - Check logs: `heroku logs --tail`
   - Verify JAR exists in build
   - Check main class is correct

2. **R10 Error** → Boot timeout
   - App took too long to start (>60s)
   - Check if app is trying to connect to missing resources

3. **H14 Error** → No web dynos running
   - Run: `heroku ps:scale web=1`

---

## 📞 Quick Reference Commands

```bash
# Deploy
git push heroku master

# View logs
heroku logs --tail --app livecrick-1cc3f9e18231

# Restart app
heroku restart --app livecrick-1cc3f9e18231

# Scale dynos
heroku ps:scale web=1 --app livecrick-1cc3f9e18231

# Open in browser
heroku open --app livecrick-1cc3f9e18231

# Run bash on Heroku
heroku run bash --app livecrick-1cc3f9e18231
```

---

**The fix is complete! Redeploy to Heroku and your app should start successfully. 🎉**
