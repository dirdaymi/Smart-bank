# Rapport Détaillé : Projet Smart Bank - Application Bancaire Full Stack

**Auteur** : Abdel-hamid MAHAMAT LOUKI

---

## 📊 Vue d'ensemble du projet

Smart Bank est une application bancaire complète full-stack avec :
- **Backend** : Spring Boot (Java) avec base de données H2
- **Frontend** : Angular 17 (standalone components)
- **Fonctionnalités** : Gestion clients/comptes, opérations bancaires, tableau de bord admin, assistant IA

---

## 🏗️ Architecture Technique

### Backend - Spring Boot
```mermaid
graph TB
    A[Client Angular<br/>localhost:4200] --> B[Spring Boot API<br/>localhost:8080];
    B --> C[Spring Security + JWT];
    B --> D[Couche Service];
    B --> E[Couche Repository];
    E --> F[Base H2 in-memory];
    B --> G[Swagger UI<br/>Documentation API];
```

### Frontend - Angular 17
```mermaid
graph TB
    A[Utilisateur] --> B[AppComponent];
    B --> C[Router Standalone];
    C --> D[Auth Module];
    C --> E[Features Modules<br/>Customers/Accounts/Operations];
    C --> F[Admin Dashboard];
    E --> G[Services HTTP];
    G --> H[API Spring Boot];
    B --> I[Assistant IA Chat];
```

---

## 📸 Analyse Détaillée des Captures

### 1. Tableau de Bord Admin - Gestion des Comptes
![capture (1)](captures/capture%20(1).png)


---

### 2. Problème de Sérialisation JSON - Erreur Critique
![capture (2)](captures/capture%20(2).png)


---

### 3. Test API Réussi - Crédit de Compte
![capture (3)](captures/capture%20(3).png)


---

### 4. Assistant IA Multicanal - Bot Telegram
![capture (4)](captures/capture%20(4).png)


---

### 5. Base de Données H2 - Structure des Comptes
![capture (5)](captures/capture%20(5).png)


---

### 6. Table des Opérations Vide - Problème de Persist
![capture (6)](captures/capture%20(6).png)


---

### 7. Données Actualisées dans H2
![capture (9)](captures/capture%20(9).png)

---

### 8. Documentation API - Swagger UI
![capture (10)](captures/capture%20(10).png)


---

### 9. Test Endpoint Accounts
![capture (11)](captures/capture%20(11).png)


---

