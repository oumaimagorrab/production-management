import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Technicien } from '../models/technicien.model';

@Injectable({
  providedIn: 'root'
})
export class TechnicienService {

  private apiUrl = 'http://localhost:8080/api/techniciens';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Technicien[]> {
    return this.http.get<Technicien[]>(this.apiUrl);
  }

  getById(id: number): Observable<Technicien> {
    return this.http.get<Technicien>(`${this.apiUrl}/${id}`);
  }

  getSansMachine(): Observable<Technicien[]> {
    return this.http.get<Technicien[]>(`${this.apiUrl}/sans-machine`);
  }

  create(technicien: Technicien): Observable<Technicien> {
    return this.http.post<Technicien>(this.apiUrl, technicien);
  }

  update(id: number, technicien: Technicien): Observable<Technicien> {
    return this.http.put<Technicien>(`${this.apiUrl}/${id}`, technicien);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}