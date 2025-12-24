# 🚀 Quick Start Guide - Fitness Tracker

## Current Status: ✅ BOTH SERVICES ARE RUNNING!

Your backend and frontend are currently running and ready to use!

---

## 🌐 Access Your Application

- **Frontend (User Interface)**: http://localhost:5173
- **Backend API**: http://localhost:8080

---

## 🎯 Quick Test

### 1. Test the Frontend
Open your browser and go to: http://localhost:5173

You should see the Intare Fitness Center landing page with:
- Navigation menu
- Hero section
- About section
- Training programs
- Contact form

### 2. Test Backend API
Open your browser and go to: http://localhost:8080/trainers

You should see an empty array `[]` (no trainers added yet)

---

## 👤 Create Your First Account

1. **Go to Registration Page**
   - Navigate to: http://localhost:5173/register
   - Or click "Join Now" on the login page

2. **Fill in the form:**
   - Email: your-email@example.com
   - Password: YourPassword123
   - Full Name: Your Name
   - Role: Select User, Trainer, or Admin

3. **Login**
   - Go to: http://localhost:5173/login
   - Enter your credentials
   - Select the same role you registered with
   - Check your email for 2FA code
   - Enter the 6-digit code

4. **Access the Dashboard**
   - **User**: Redirected to home page (/)
   - **Trainer**: Redirected to /trainerDashboard
   - **Admin**: Redirected to /dashboard

---

## 🔑 Default Test Credentials

Since this is a fresh installation, you need to register your first user. After registration:

### Create Admin Account
1. Register with role "ADMIN"
2. This gives you full access to manage users, trainers, programs, and workouts

### Create Trainer Account
1. Register with role "TRAINER"
2. This allows you to create and manage programs and workouts

### Create Regular User Account
1. Register with role "USER"
2. This allows you to browse programs, enroll in programs, and track workouts

---

## 📱 Main Features to Try

### As Admin (http://localhost:5173/dashboard)
- ✅ View all users, trainers, programs, workouts
- ✅ Create new trainers
- ✅ Create new programs
- ✅ Create new workouts
- ✅ Manage user assignments
- ✅ Export data

### As Trainer (http://localhost:5173/trainerDashboard)
- ✅ View your programs and workouts
- ✅ Create new programs
- ✅ Create new workouts
- ✅ View assigned users
- ✅ Track user progress

### As User (http://localhost:5173/)
- ✅ Browse available programs
- ✅ View trainers
- ✅ Enroll in programs
- ✅ Track completed workouts
- ✅ Update profile
- ✅ Upload avatar

---

## 🛠️ Services Management

### Currently Running
Both services are running in the background:
- Backend: Java process on port 8080
- Frontend: Node process on port 5173

### To Stop Services

**Stop Backend:**
```powershell
Get-Process | Where-Object {$_.ProcessName -eq "java"} | Stop-Process -Force
```

**Stop Frontend:**
```powershell
Get-Process | Where-Object {$_.ProcessName -eq "node"} | Stop-Process -Force
```

### To Restart Services

**Restart Backend:**
```powershell
cd backend_25982
mvn spring-boot:run
```

**Restart Frontend:**
```powershell
cd fronted_25982/fronted
npm run dev
```

---

## 🔍 Troubleshooting

### Backend Not Responding
1. Check if PostgreSQL is running
2. Verify database credentials in `backend_25982/src/main/resources/application.properties`
3. Check if port 8080 is available: `netstat -ano | findstr :8080`

### Frontend Not Loading
1. Check if port 5173 is available: `netstat -ano | findstr :5173`
2. Clear browser cache
3. Check browser console for errors (F12)

### 2FA Email Not Arriving
1. Check spam/junk folder
2. Verify email configuration in `application.properties`
3. Wait a few moments (can take 30-60 seconds)

### Database Connection Error
1. Make sure PostgreSQL is running
2. Database name: `Fitness-Tracker`
3. Default credentials: username=`postgres`, password=`Allah@2023`
4. Run the database setup script if needed: `backend_25982/setup_database.ps1`

---

## 📊 Test the API Directly

You can test API endpoints using PowerShell:

**Get all users:**
```powershell
Invoke-WebRequest -Uri http://localhost:8080/users -UseBasicParsing
```

**Get all trainers:**
```powershell
Invoke-WebRequest -Uri http://localhost:8080/trainers -UseBasicParsing
```

**Get all programs:**
```powershell
Invoke-WebRequest -Uri http://localhost:8080/programs -UseBasicParsing
```

**Get all workouts:**
```powershell
Invoke-WebRequest -Uri http://localhost:8080/workouts -UseBasicParsing
```

---

## 💡 Tips

1. **First-time setup**: Register an admin account first to have full control
2. **Email 2FA**: Make sure to check your email for the 2FA code after login
3. **Role selection**: Choose the correct role during login (must match registration)
4. **Browser**: Works best in Chrome, Firefox, or Edge
5. **Development mode**: Both services are running in development mode with hot reload

---

## 📖 Detailed Documentation

For complete project documentation, see: `PROJECT_STATUS_SUMMARY.md`

---

## ✅ Verification Checklist

- [x] Backend running on port 8080
- [x] Frontend running on port 5173
- [x] Database connected (PostgreSQL)
- [x] CORS configured
- [x] Authentication working
- [x] API endpoints responding
- [x] All routes accessible

---

## 🎉 You're All Set!

Your Fitness Tracker application is fully functional and ready to use!

**Start exploring at:** http://localhost:5173

**Happy Coding! 💪🏋️‍♂️**
