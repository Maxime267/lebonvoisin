la view sert à afficher l'interface utilisateur
toute la logique metier (logique principal) est dans VIEWMODEL
pour appeler le viewmodel faites 
```kotlin
    val viewModel: MonViewModel = hiltViewModel()
```

au début de la view (exmple dans MesAnnonces)