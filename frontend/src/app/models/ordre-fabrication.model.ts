export interface OrdreFabrication {
  id: number;
  quantite: number;
  date: string; // LocalDate → string ISO
  statut: StatutOrdre;
  idProduit: number;
  idMachine: number;
}

export enum StatutOrdre {
  PLANIFIE = 'PLANIFIE',
  EN_COURS = 'EN_COURS',
  TERMINE = 'TERMINE',
  ANNULE = 'ANNULE',
  EN_RETARD = 'EN_RETARD'
}