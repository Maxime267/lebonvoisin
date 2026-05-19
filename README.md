# lebonvoisin


## Overview
This app uses **MVVM architecture** with **Firebase** as backend to ensure a clean, scalable and maintainable codebase.


## Architecture

## Layers

### View (UI)
- Screens only
- Displays data
- Sends user actions to ViewModel
- No business logic

### ViewModel
- Handles UI state (loading, error, data)
- Contains presentation logic
- Calls repositories

### Repositories (Handling database)
- Bridge between ViewModel and Firebase
- Handles data operations
- Keeps Firebase isolated from UI logic

### Firebase
- Authentication
- Firestore database
- Storage (files/images)

### Navigation
- Handle the link between views
