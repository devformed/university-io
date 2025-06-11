package com.lockermat.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lockermat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class LockermatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point location;
}