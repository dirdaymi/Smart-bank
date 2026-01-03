import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatbotService {

  // CORRECTION 1 : L'URL complète inclut "/api/ai" défini dans le Controller
  private apiUrl = 'http://localhost:8080/api/ai/chat';

  constructor(private http: HttpClient) {}

  sendMessage(message: string): Observable<any> {
    // CORRECTION 2 : On envoie "question" et un "accountId" bidon pour tester
    const payload = {
      question: message,
      accountId: "CPT-001" // ID temporaire pour que Java ne plante pas
    };

    return this.http.post<any>(this.apiUrl, payload);
  }
}
