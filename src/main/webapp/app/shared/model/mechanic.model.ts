import { type ICar } from '@/shared/model/car.model';

export interface IMechanic {
  id?: number;
  motor?: string | null;
  power?: number | null;
  km?: number | null;
  car?: ICar | null;
}

export class Mechanic implements IMechanic {
  constructor(
    public id?: number,
    public motor?: string | null,
    public power?: number | null,
    public km?: number | null,
    public car?: ICar | null,
  ) {}
}
