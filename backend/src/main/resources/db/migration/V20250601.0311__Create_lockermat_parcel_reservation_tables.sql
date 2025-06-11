-- Create lockermat, parcel, and reservation tables
-- Uses timestamp (without time zone) for reservation window
CREATE TABLE lockermat
(
    id       BIGSERIAL PRIMARY KEY,
    address  TEXT                  NOT NULL,
    location geometry(Point, 4326) NOT NULL
);

-- Spatial index for efficient distance queries
CREATE INDEX idx_lockermat_location ON lockermat USING GIST (location);

CREATE TABLE parcel
(
    id           BIGSERIAL PRIMARY KEY,
    lockermat_id BIGINT      NOT NULL REFERENCES lockermat (id) ON DELETE CASCADE,
    size         VARCHAR(10) NOT NULL
);

CREATE TABLE reservation
(
    id               BIGSERIAL PRIMARY KEY,
    parcel_id        BIGINT    NOT NULL REFERENCES parcel (id) ON DELETE CASCADE,
    reservation_from TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reservation_to   TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Indexes to speed up reservation lookups
CREATE INDEX idx_reservation_parcel_id ON reservation (parcel_id);
CREATE INDEX idx_reservation_window ON reservation (reservation_from, reservation_to);