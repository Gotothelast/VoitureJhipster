import { type IMechanic } from '@/shared/model/mechanic.model';
import { type IOption } from '@/shared/model/option.model';

export interface ICar {
  id?: number;
  carName?: string | null;
  carModel?: string | null;
  carReference?: string | null;
  carYear?: number | null;
  carPrice?: number | null;
  mechanic?: IMechanic | null;
  options?: IOption[] | null;
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public carName?: string | null,
    public carModel?: string | null,
    public carReference?: string | null,
    public carYear?: number | null,
    public carPrice?: number | null,
    public mechanic?: IMechanic | null,
    public options?: IOption[] | null,
  ) {}
}
