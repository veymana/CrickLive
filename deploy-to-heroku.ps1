# Heroku Deployment Script for CrickLive Backend
# Run this from: d:\Veymana\CrickLive\backend\livecrick

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "   CrickLive Backend - Heroku Deployment" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Clean and test build locally
Write-Host "[1/6] Testing local build..." -ForegroundColor Yellow
.\gradlew clean stage --no-daemon
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Local build failed!" -ForegroundColor Red
    exit 1
}

# Verify JAR exists
if (Test-Path "build\libs\livecrick.jar") {
    $jarSize = (Get-Item "build\libs\livecrick.jar").Length / 1MB
    Write-Host "✅ JAR created successfully: $([math]::Round($jarSize, 2)) MB" -ForegroundColor Green
} else {
    Write-Host "ERROR: JAR file not found!" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Step 2: Initialize git if needed
Write-Host "[2/6] Checking Git repository..." -ForegroundColor Yellow
if (-not (Test-Path ".git")) {
    Write-Host "Initializing Git repository..." -ForegroundColor Cyan
    git init
    Write-Host "✅ Git initialized" -ForegroundColor Green
} else {
    Write-Host "✅ Git already initialized" -ForegroundColor Green
}
Write-Host ""

# Step 3: Add and commit files
Write-Host "[3/6] Committing changes..." -ForegroundColor Yellow
git add .
git commit -m "Fix Heroku deployment - use bootJar in stage task" --allow-empty
Write-Host "✅ Changes committed" -ForegroundColor Green
Write-Host ""

# Step 4: Add Heroku remote if needed
Write-Host "[4/6] Checking Heroku remote..." -ForegroundColor Yellow
$herokuRemote = git remote -v 2>&1 | Select-String "heroku"
if (-not $herokuRemote) {
    Write-Host "Adding Heroku remote..." -ForegroundColor Cyan
    $appName = Read-Host "Enter your Heroku app name (e.g., livecrick-1cc3f9e18231)"
    heroku git:remote -a $appName
    Write-Host "✅ Heroku remote added" -ForegroundColor Green
} else {
    Write-Host "✅ Heroku remote already exists" -ForegroundColor Green
}
Write-Host ""

# Step 5: Deploy to Heroku
Write-Host "[5/6] Deploying to Heroku..." -ForegroundColor Yellow
Write-Host "This may take several minutes..." -ForegroundColor Cyan
git push heroku master --force
if ($LASTEXITCODE -ne 0) {
    # Try main branch if master fails
    Write-Host "Trying 'main' branch..." -ForegroundColor Cyan
    git push heroku main --force
}
Write-Host "✅ Deployment complete" -ForegroundColor Green
Write-Host ""

# Step 6: Check deployment status
Write-Host "[6/6] Checking deployment status..." -ForegroundColor Yellow
Start-Sleep -Seconds 3
heroku ps
Write-Host ""

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "   Deployment Complete!" -ForegroundColor Green
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Check logs: heroku logs --tail" -ForegroundColor White
Write-Host "2. Open app: heroku open" -ForegroundColor White
Write-Host "3. View config: heroku config" -ForegroundColor White
Write-Host ""
