package com.lockermat.model.entity.lockermat;

import com.lockermat.model.dto.lockermat.parcel.ParcelSize;
import com.lockermat.model.entity.AbstractEntity;
import com.lockermat.util.Identifiable;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParcelEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LockermatEntity lockermat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParcelSize size;

    @Builder
    public ParcelEntity(UUID id, LockermatEntity lockermat, ParcelSize size) {
        super(id);
        this.lockermat = lockermat;
        this.size = size;
    }
}