import { OrdreFabrication } from './ordre-fabrication.model';

export interface Produit {
  id: number;
  nom: string;
  type: string;
  stock: number;
  fournisseur: string;
  listeOrdres: OrdreFabrication[];
}