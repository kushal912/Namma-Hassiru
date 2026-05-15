# Namma-Hasiru — Setup Guide
### MindMatrix VTU Internship | Kushal Reddy R

---

## What's inside this zip

When you unzip `NammaHasiru.zip`, you get a folder called `NammaHasiru/` with this structure:

```
NammaHasiru/
├── build.gradle              ← project-level gradle
├── settings.gradle
├── gradle.properties
├── gradle/wrapper/
│   └── gradle-wrapper.properties
└── app/
    ├── build.gradle          ← app-level dependencies
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/nammahasiru/
        │   ├── MainActivity.kt
        │   ├── data/
        │   │   ├── Plant.kt
        │   │   ├── PlantDao.kt
        │   │   ├── PlantDatabase.kt
        │   │   └── PlantRepository.kt
        │   ├── ui/
        │   │   ├── PlantViewModel.kt
        │   │   ├── home/
        │   │   │   ├── HomeFragment.kt
        │   │   │   └── PlantAdapter.kt
        │   │   ├── addplant/AddPlantFragment.kt
        │   │   ├── map/MapFragment.kt
        │   │   ├── alerts/AlertsFragment.kt
        │   │   └── guide/GuideFragment.kt
        │   └── utils/ReminderWorker.kt
        └── res/
            ├── drawable/     ← card_background, nav icons, notif icon
            ├── layout/       ← all 7 XML layout files
            ├── menu/         ← bottom_nav_menu.xml
            └── values/       ← colors.xml, strings.xml, themes.xml
```

All files are already in the correct place. No manual copying needed.

---

## Option A — Open in VS Code (browse code only)

VS Code cannot build or run Android apps, but you can browse and edit the code here.

1. Unzip `NammaHasiru.zip`
2. Open VS Code → **File → Open Folder** → select the `NammaHasiru` folder
3. Install the **Kotlin** extension (by fwcd) for syntax highlighting
4. You can now read and edit all `.kt` and `.xml` files

> To actually run the app on a phone, you need Android Studio (see Option B).

---

## Option B — Open and run in Android Studio

This is the correct way to build and run the app.

### Step 1 — Open the project

1. Open Android Studio
2. Click **"Open"** (not "New Project")
3. Navigate to the unzipped `NammaHasiru` folder
4. Click **OK**
5. Wait for Gradle sync to finish (bottom progress bar)
   - First time may take 3–5 minutes, it downloads dependencies

### Step 2 — Add your Google Maps API key

The Map screen needs a real API key or it will crash.

1. Go to https://console.cloud.google.com
2. Create a project → enable **Maps SDK for Android**
3. Create an API Key
4. Open `app/src/main/AndroidManifest.xml`
5. Find this line:
   ```xml
   android:value="YOUR_API_KEY_HERE"
   ```
6. Replace `YOUR_API_KEY_HERE` with your actual key

> Without this, the Map tab will crash. Home, Add Plant, Alerts, and Guide tabs work fine without it.

### Step 3 — Run on a real device

1. On your Android phone: Settings → About Phone → tap **Build Number** 7 times → go back → Developer Options → enable **USB Debugging**
2. Connect phone via USB → allow the popup on your phone
3. In Android Studio, select your device from the dropdown at the top
4. Press the green **▶ Run** button

### Step 3 (alternative) — Run on emulator

1. In Android Studio: **Tools → Device Manager → Create Virtual Device**
2. Pick any Pixel model → select API 34 system image → Finish
3. Press the green **▶ Run** button and select the emulator

---

## What works out of the box

| Feature | Status |
|---------|--------|
| Home screen with plant count + survival % | ✅ |
| Add plant with GPS capture | ✅ |
| Room database storing all records | ✅ |
| 90-day WorkManager reminders | ✅ |
| Alerts tab (plants needing checkup) | ✅ |
| Species guide (Karnataka plants) | ✅ |
| Map with plant markers | ✅ (needs API key) |

---

## Common errors and fixes

**Gradle sync fails**
- Check internet connection (first sync downloads ~200MB)
- File → Invalidate Caches → Invalidate and Restart

**`Cannot find symbol` errors after sync**
- Build → Clean Project → Build → Rebuild Project

**Map tab crashes immediately**
- You haven't added the Google Maps API key yet (see Step 2 above)

**Location not working on emulator**
- Emulator → three dots (⋮) → Location → set a manual location

---

*Namma-Hasiru | MindMatrix VTU Internship 2024*
