package com.lockermat.model.entity.lockermat;

import com.lockermat.model.dto.lockermat.parcel.CellSize;
import com.lockermat.model.entity.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LockermatCellEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LockermatEntity lockermat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CellSize size;

    @Builder
    public LockermatCellEntity(Long id, LockermatEntity lockermat, CellSize size) {
        super(id);
        this.lockermat = lockermat;
        this.size = size;
    }
}