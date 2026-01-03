import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ChatbotComponent } from './components/shared/chatbot/chatbot';
import { NavbarComponent } from './components/layout/navbar/navbar'; // <-- Import Navbar
import { FooterComponent } from './components/layout/footer/footer'; // <-- Import Footer

@Component({
  selector: 'app-root',
  standalone: true,
  // Ajoutez Navbar et Footer aux imports :
  imports: [RouterOutlet, ChatbotComponent, NavbarComponent, FooterComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent {
  title = 'smart_bank';
}
