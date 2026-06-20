# lebonvoisin


## Overview
This app uses **MVVM architecture** with **Firebase** as backend to ensure a clean, scalable and maintainable codebase.

# Utilisation
## User
maxime@gmail.com   mdp : 12345678
rayan@gmail.com    mdp : 12345678
amine@gmail.com    mdp : 12345678


## Architecture

## View (UI)
- Screens only
- Displays data
- Sends user actions to ViewModel
- No business logic

## ViewModel
- Handles UI state (loading, error, data)
- Contains presentation logic
- Calls repositories

## Repositories (Handling database)
- Bridge between ViewModel and Firebase
- Handles data operations
- Keeps Firebase isolated from UI logic

## Firebase

- Firestore database
- Firebase authentification

## Navigation
- Handle the link between views

## Hilt

Hilt est utilisé pour appliqué la forme MVVM afin de facilité le rapport entre la View, ViewModel et Repository
-> répository est directement associé à ViewModel
->Il suffit de faire
```kotlin
    val viewModel: MonViewModel = hiltViewModel()
```
pour utiliser le viewModel dans la view (sans passage par fonction qui créé rapidement des confusions)

### Di

Module pour hilt qui permettent le @Inject constructor
