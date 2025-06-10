import { Views } from 'Enums/Views';

export interface NavbarPropsTypes {
  onNavigate: (view: Views) => void;
}