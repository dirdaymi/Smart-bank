import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app'; // <--- On importe AppComponent depuis le fichier app.ts

bootstrapApplication(AppComponent, appConfig) // <--- On démarre AppComponent
  .catch((err) => console.error(err));
