package com.lockermat.model.entity.lockermat;

import com.lockermat.model.PersistenceUtils;
import com.lockermat.model.dto.Position;
import com.lockermat.model.entity.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class LockermatEntity extends AbstractEntity {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.GEOMETRY)
    private Point location;

    @Builder
    public LockermatEntity(Long id, String address, Position position) {
        super(id);
        this.address = address;
        setPosition(position);
    }

    public Position getPosition() {
        return new Position(BigDecimal.valueOf(location.getX()), BigDecimal.valueOf(location.getY()));
    }

    public LockermatEntity setPosition(Position position) {
        this.location = PersistenceUtils.toPoint(position);
        return this;
    }
}