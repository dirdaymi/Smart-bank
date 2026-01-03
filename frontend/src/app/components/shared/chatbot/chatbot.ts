import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatbotService } from '../../../_services/chatbot';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.html',
  styleUrls: ['./chatbot.css']
})
export class ChatbotComponent {
  isOpen = false;
  userMessage = '';
  isLoading = false;
  messages: Array<{content: string, isUser: boolean}> = [];

  constructor(private chatbotService: ChatbotService) {}

  toggleChat() {
    this.isOpen = !this.isOpen;
  }

  sendMessage() {
    if (!this.userMessage.trim()) return;

    const msgToSend = this.userMessage;
    this.messages.push({ content: msgToSend, isUser: true });
    this.userMessage = '';
    this.isLoading = true;

    this.chatbotService.sendMessage(msgToSend).subscribe({
      next: (res: any) => {
        this.isLoading = false;
        // CORRECTION 3 : Votre Java renvoie une Map avec la clé "response"
        const botReply = res.response || "Pas de réponse";
        this.messages.push({ content: botReply, isUser: false });
      },
      error: (err: any) => {
        this.isLoading = false;
        this.messages.push({ content: "Erreur serveur : Vérifiez que la base de données est accessible.", isUser: false });
        console.error(err);
      }
    });
  }
}
