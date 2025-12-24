# PowerShell script to help set up the PostgreSQL database
# This script attempts to create the Fitness-Tracker database

Write-Host "=== Fitness Tracker Database Setup ===" -ForegroundColor Cyan
Write-Host ""

# Try to find psql in common PostgreSQL installation paths
$psqlPaths = @(
    "C:\Program Files\PostgreSQL\16\bin\psql.exe",
    "C:\Program Files\PostgreSQL\15\bin\psql.exe",
    "C:\Program Files\PostgreSQL\14\bin\psql.exe",
    "C:\Program Files\PostgreSQL\13\bin\psql.exe",
    "C:\Program Files\PostgreSQL\12\bin\psql.exe",
    "$env:ProgramFiles\PostgreSQL\*\bin\psql.exe",
    "$env:LOCALAPPDATA\Programs\postgresql\*\bin\psql.exe"
)

$psqlPath = $null
foreach ($path in $psqlPaths) {
    $resolved = Resolve-Path $path -ErrorAction SilentlyContinue
    if ($resolved) {
        $psqlPath = $resolved[0].Path
        Write-Host "Found PostgreSQL client at: $psqlPath" -ForegroundColor Green
        break
    }
}

if (-not $psqlPath) {
    Write-Host "PostgreSQL client (psql) not found in common locations." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Please create the database manually:" -ForegroundColor Yellow
    Write-Host "1. Open pgAdmin or psql command line" -ForegroundColor White
    Write-Host "2. Connect to PostgreSQL server (localhost:5432)" -ForegroundColor White
    Write-Host "3. Run: CREATE DATABASE `"Fitness-Tracker`";" -ForegroundColor White
    Write-Host ""
    Write-Host "Or use the SQL file: create_database.sql" -ForegroundColor White
    exit 1
}

# Set password environment variable
$env:PGPASSWORD = "123"

Write-Host "Attempting to create database 'Fitness-Tracker'..." -ForegroundColor Cyan

# Try to create the database (using single quotes to avoid PowerShell escaping issues)
$createDbQuery = 'CREATE DATABASE "Fitness-Tracker";'
$output = echo $createDbQuery | & $psqlPath -U postgres -h localhost 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "Database created successfully!" -ForegroundColor Green
} elseif ($output -match "already exists") {
    Write-Host "Database already exists. Continuing..." -ForegroundColor Yellow
} else {
    Write-Host "Error creating database:" -ForegroundColor Red
    Write-Host $output -ForegroundColor Red
    Write-Host ""
    Write-Host "Please create the database manually using the SQL file: create_database.sql" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Database setup complete! You can now start the Spring Boot application." -ForegroundColor Green

