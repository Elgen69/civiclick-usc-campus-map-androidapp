# CiviClick – Campus Navigation App for Universities

**CiviClick** is a mobile-first navigation app designed to help students, visitors, and staff efficiently explore complex university campuses. Originally developed for the **University of San Carlos – Talamban Campus (USC-TC)**, it provides a complete digital experience for navigating both outdoor campus maps and indoor building floorplans.

While currently focused on the **Bunzel Building**, the app is built on a modular structure that allows rapid expansion to other USC buildings (e.g., SAFAD, LRC, OEHLER) and, eventually, other universities.

---

## 📱 Key Features

- 🗺️ **Interactive Campus Map**  
  Scrollable, zoomable static map of USC-TC with interactive markers for key buildings. Markers trigger building-specific pages or toast feedback if not yet supported.

- 🔍 **From–To Building Routing**  
  Choose any two buildings from a dropdown and play an animated path (dot-style overlay) representing the direction between them.

- 🏢 **Per-Building Floor Navigation (Bunzel & Scalable)**  
  Users can select a building, choose a floor (Basement to 4F), and search for a specific room. A video overlay shows the directional animation from entrance to room (currently implemented for Bunzel).

- 📶 **Offline First, Always Ready**  
  CiviClick is entirely offline by default. All assets — including maps, markers, and videos — are locally embedded for fast access. This ensures functionality even in low-signal or data-restricted environments.

- 🔄 **Scalable Architecture**  
  The app is designed to easily expand across more buildings. Future data may be pulled from a remote database (if Wi-Fi is available), enabling dynamic loading of new room lists or animations without bloating app size.

- 🧠 **Dynamic Marker Logic**  
  Marker visibility and interactivity adapt based on search inputs, route queries, and activity state. Video overlays are layered *below* markers for full interactivity even during animation playback.

---

## 🧭 Project Scope

- 🎯 **Target Audience**  
  - Primarily: New USC students, transferees, org reps, and visitors  
  - Secondarily: Event attendees, admins, and freshmen tours

- 🧱 **Future-Ready Stack**  
  - Add more buildings through structured layout folders  
  - Add room animations via offline video drops or dynamic fetch  
  - Upgrade animations to simulate real walking (optional)  
  - Extend to AR-based room guidance in future versions

- 🌐 **Deployment Strategy**
  - Pilot: USC-TC (starting with Bunzel Building)  
  - Phase 2: Other buildings like SAFAD, LRC, OEHLER  
  - Phase 3: Other USC campuses (e.g., Downtown)  
  - Phase 4: Multi-university rollout via config-based builds

---

## ⚙️ Technologies Used

- **Android (Java)** – core language
- **XML Layouts** – UI & layout handling
- **VideoView** – dynamic overlays for animations
- **ConstraintLayout + FrameLayout** – for layered map + video + markers
- **Offline asset packaging** – all videos/images are stored within `res` folders
- **Git-based teamwork** – multiple branches (`main`, `Elgen`, `shawn`, `celian4862`) for collaborative parallel development

---

## 🚀 Installation (Debug Mode)

Download the latest APK:  
➡️ [📦 Download APK](https://drive.google.com/your-link)

Or clone the project and run it in Android Studio:

```bash
git clone https://github.com/your-username/civiclick-campusnavigation.git

## 🤝 Contributing

We welcome collaboration!

If you're from USC and want to help map additional buildings, contribute video animations, or expand the backend:

- Fork the repo  
- Create a feature branch  
- Submit a pull request  
- Or simply open an issue with your suggestions  

> **Note:** Room naming conventions, video file formats, and marker positioning follow internal standards (see `CONTRIBUTING.md` — coming soon).

---

## 📄 License

This project is under the [MIT License](https://opensource.org/licenses/MIT).  
You’re free to use, adapt, or fork — with credit.

---

## 📣 Acknowledgments

- USC-TC Students & Professors  
- The Mobile Development Class of 2025  
- Expo Showcase Evaluators  
- Everyone who tested, pitched, or invested feedback into **CiviClick**

---

**CiviClick — built for students, by students.**
