import React from 'react';
import { InputValues } from 'Store/types';
import './styles/LockerMatForm.scss';
import { LockermatParcelSizes } from 'Enums/LockerMatParcelSizes';
import { LockerMatFormPropsTypes } from './types/LockerMatFormPropsType';


const LockerMatForm = ({ valuesObj, onChange }: LockerMatFormPropsTypes) => {
  return (
    <div className="locker-mat-form">
      <h3 className="locker-mat-form__title">Wyszukaj punkt</h3>
      <input
        className="locker-mat-form__input"
        placeholder="Szukaj..."
        value={valuesObj.fulltext || ''}
        onChange={(e) => onChange({ fulltext: e.target.value })}
      />
      <input
        className="locker-mat-form__input"
        type="datetime-local"
        value={valuesObj.availableFrom || ''}
        onChange={(e) => onChange({ availableFrom: e.target.value })}
      />
      <input
        className="locker-mat-form__input"
        type="datetime-local"
        value={valuesObj.availableTo || ''}
        onChange={(e) => onChange({ availableTo: e.target.value })}
      />
      <div className="locker-mat-form__checkboxes">
        {Object.values(LockermatParcelSizes).map((size) => (
          <label key={size}>
            <input
              type="checkbox"
              checked={valuesObj.sizes?.includes(size)}
              onChange={(e) => {
                const newSizes = new Set(valuesObj.sizes);
                e.target.checked ? newSizes.add(size) : newSizes.delete(size);
                onChange({ sizes: Array.from(newSizes) });
              }}
            />
            {size}
          </label>
        ))}
      </div>
    </div>
  );
};

export default LockerMatForm;