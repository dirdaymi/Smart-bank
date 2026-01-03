@echo off
echo ========================================================
echo GENERATION DE L'ARCHITECTURE FRONTEND (Structure Prof)
echo ========================================================

:: 1. Création des dossiers de base (Services, Models, Guards)
echo.
echo [1/5] Generation du coeur (Services, Models, Guards)...
:: Les services pour communiquer avec Spring Boot
call ng generate service _services/auth
call ng generate service _services/user
call ng generate service _services/chatbot
call ng generate service _services/product

:: Les interfaces (Models) pour typer les données
call ng generate interface _models/user
call ng generate interface _models/product
call ng generate interface _models/message

:: La sécurité (Guards & Interceptors)
call ng generate guard _guards/auth
call ng generate guard _guards/admin
call ng generate interceptor _interceptors/jwt

:: 2. Création des composants de Layout (Partagés)
echo.
echo [2/5] Generation du Layout (Navbar, Footer)...
call ng generate component components/layout/navbar
call ng generate component components/layout/footer
call ng generate component components/layout/sidebar

:: 3. Création de la partie ADMIN
echo.
echo [3/5] Generation de l'espace Admin...
call ng generate component components/admin/admin-dashboard
call ng generate component components/admin/user-management
call ng generate component components/admin/product-management

:: 4. Création de la partie USER (Ordinaire)
echo.
echo [4/5] Generation de l'espace User...
call ng generate component components/user/user-home
call ng generate component components/user/user-profile

:: 5. Création des composants Partagés (Chatbot, etc.)
echo.
echo [5/5] Generation des widgets partages (Chatbot)...
call ng generate component components/shared/chatbot
call ng generate component components/shared/loader

echo.
echo ========================================================
echo TERMINE ! La structure est prete.
echo Verifie le dossier src/app/
echo ========================================================
pause