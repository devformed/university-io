package com.lockermat.model.entity;

import jakarta.persistence.*;
import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcel_id", nullable = false)
    private ParcelEntity parcel;

    @Column(name = "reservation_from", nullable = false)
    private Instant reservationFrom;

    @Column(name = "reservation_to", nullable = false)
    private Instant reservationTo;
}