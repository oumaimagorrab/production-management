export interface Maintenance {
  id: number;
  date: string;
  type: TypeMaintenance;
  idMachine: number;
  idTechnicien: number;
}

export enum TypeMaintenance {
  PREVENTIVE = 'PREVENTIVE',
  CORRECTIVE = 'CORRECTIVE',
  PREDICTIVE = 'PREDICTIVE'
}