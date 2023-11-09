import { type ICar } from '@/shared/model/car.model';

export interface IOption {
  id?: number;
  nameOption?: string | null;
  priceOption?: number | null;
  car?: ICar | null;
}

export class Option implements IOption {
  constructor(
    public id?: number,
    public nameOption?: string | null,
    public priceOption?: number | null,
    public car?: ICar | null,
  ) {}
}
