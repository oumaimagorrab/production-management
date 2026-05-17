import { OrdreFabrication } from './ordre-fabrication.model';
import { Maintenance } from './maintenance.model';

export interface Machine {
  id: number;
  nom: string;
  etat: EtatMachine;
  maintenanceProchaine: string; // LocalDate en string ISO
  idTechnicienAssigne: number | null;
  listeOrdres: OrdreFabrication[];
  listeMaintenances: Maintenance[];
}

export enum EtatMachine {
  DISPONIBLE = 'DISPONIBLE',
  EN_PANNE = 'EN_PANNE',
  EN_PRODUCTION = 'EN_PRODUCTION',
  EN_MAINTENANCE = 'EN_MAINTENANCE'
}