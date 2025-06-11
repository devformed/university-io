package com.lockermat.model.entity;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parcel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ParcelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lockermat_id", nullable = false)
    private LockermatEntity lockermat;

    @Column(name = "size", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParcelSize size;
}