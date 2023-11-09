import { type ICar } from '@/shared/model/car.model';

export interface IAgence {
  id?: number;
  nameAgence?: string | null;
  adressAgence?: string | null;
  telAgence?: number | null;
  cars?: ICar[] | null;
}

export class Agence implements IAgence {
  constructor(
    public id?: number,
    public nameAgence?: string | null,
    public adressAgence?: string | null,
    public telAgence?: number | null,
    public cars?: ICar[] | null,
  ) {}
}
