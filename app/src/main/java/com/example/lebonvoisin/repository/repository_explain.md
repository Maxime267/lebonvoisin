# Repository 
Un **repository** est une couche qui gère l’accès aux données de l’application.

## Rôle
- Récupérer des données (Firestore, API, etc.)
- Envoyer des données
- Centraliser la logique liée aux données
- Éviter que le ViewModel parle directement à Firebase

## Schéma simple
UI → ViewModel → Repository → Source de données (Firebase / API)

## 💡 Exemple
```kotlin
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
    //ici on mets ce qui est nécessaire pour le repo (injection à l'aide de di/...  (module)
) {
    fun getUser(id: String) =
        firestore.collection("users").document(id)
}