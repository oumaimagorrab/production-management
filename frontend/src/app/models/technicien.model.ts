import { Maintenance } from './maintenance.model';

export interface Technicien {
  id: number;
  nom: string;
  competences: string[];
  idMachineAssignee: number | null;
  listeMaintenances: Maintenance[];
}