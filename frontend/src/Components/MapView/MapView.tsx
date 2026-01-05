import React, { useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet';
import L from 'leaflet';
import { MapViewPropsTypes } from './types/MapViewPropsTypes';
import { LockerMatPoint } from 'Store/types';

import 'leaflet/dist/leaflet.css';
import './styles/MapView.scss';

// Fix for default marker icons in Leaflet
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';

const DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
});

L.Marker.prototype.options.icon = DefaultIcon;

// Component to fit bounds when markers change
const MapBounds = ({ points }: { points: LockerMatPoint[] }) => {
  const map = useMap();

  useEffect(() => {
    if (points.length === 0) return;

    const bounds = L.latLngBounds(
      points.map(point => [point.position.latitude, point.position.longitude])
    );

    map.fitBounds(bounds, { padding: [50, 50], maxZoom: 12 });
  }, [points, map]);

  return null;
};

const MapView = ({ lockerMatPoints, reservations, showOnlyReservations = false }: MapViewPropsTypes) => {
  const center: [number, number] = [50.0647, 19.9450]; // Kraków

  console.log('MapView render - showOnlyReservations:', showOnlyReservations);

  // Znajdź punkt dla danej rezerwacji
  const getPointById = (lockermatId: number) => {
    return lockerMatPoints.find(p => p.id === lockermatId);
  };

  // Znajdź rezerwacje dla danego punktu
  const getReservationsForPoint = (pointId: number) => {
    if (!reservations) return [];
    return reservations.filter(r => r.lockermatId === pointId);
  };

  return (
    <div className="map-view">
      <MapContainer
        center={center}
        zoom={6}
        className="map-view__container"
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        
        {showOnlyReservations ? (
          // Tryb tylko rezerwacje - pokaż markery tylko jeśli są rezerwacje
          reservations && reservations.length > 0 ? (
            reservations.map((reservation, index) => {
              const point = getPointById(reservation.lockermatId);
              if (!point) return null;

              const offset = index * 0.0001;
              
              return (
                <Marker
                  key={`reservation-${reservation.id}`}
                  position={[
                    point.position.latitude + offset,
                    point.position.longitude + offset
                  ]}
                >
                  <Popup>
                    <div className="popup">
                      <div className="popup__title">{point.address}</div>
                      <div className="popup__reservation">
                        <div className="popup__info">
                          <span className="popup__label">Numer rezerwacji:</span> {reservation.id}
                        </div>
                        <div className="popup__info">
                          <span className="popup__label">Od:</span> {new Date(reservation.from).toLocaleString()}
                        </div>
                        <div className="popup__info">
                          <span className="popup__label">Do:</span> {new Date(reservation.to).toLocaleString()}
                        </div>
                      </div>
                    </div>
                  </Popup>
                </Marker>
              );
            })
          ) : null
        ) : (
          // Tryb zwykły - pokazuj wszystkie punkty
          lockerMatPoints.map((point: LockerMatPoint) => {
            const pointReservations = getReservationsForPoint(point.id);
            const hasReservations = pointReservations.length > 0;

            return (
              <Marker
                key={point.id}
                position={[point.position.latitude, point.position.longitude]}
              >
                <Popup>
                  <div className="popup">
                    <div className="popup__title">{point.address}</div>
                    
                    {hasReservations ? (
                      // Wyświetl dane rezerwacji
                      <>
                        {pointReservations.map(res => (
                          <div key={res.id} className="popup__reservation">
                            <div className="popup__info">
                              <span className="popup__label">Numer rezerwacji:</span> {res.id}
                            </div>
                            <div className="popup__info">
                              <span className="popup__label">Od:</span> {new Date(res.from).toLocaleString()}
                            </div>
                            <div className="popup__info">
                              <span className="popup__label">Do:</span> {new Date(res.to).toLocaleString()}
                            </div>
                          </div>
                        ))}
                      </>
                    ) : (
                      // Wyświetl dostępne rozmiary
                      <>
                        <div className="popup__info">
                          <span className="popup__label">ID:</span> {point.id}
                        </div>
                        <div className="popup__info">
                          <span className="popup__label">Dostępne rozmiary:</span>
                        </div>
                        <div className="popup__sizes">
                          {point.sizes.map(size => (
                            <span key={size} className="popup__size-badge">{size}</span>
                          ))}
                        </div>
                      </>
                    )}
                  </div>
                </Popup>
              </Marker>
            );
          })
        )}

        <MapBounds points={lockerMatPoints} />
      </MapContainer>
    </div>
  );
};

export default MapView;
