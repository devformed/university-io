package com.lockermat.model.entity.lockermat;

import com.lockermat.model.entity.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class ReservationEntity extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ParcelEntity parcel;

	@Column(nullable = false)
	private Instant from;

	@Column(nullable = false)
	private Instant to;

	@Builder
	public ReservationEntity(Long id, ParcelEntity parcel, Instant from, Instant to) {
		super(id);
		this.parcel = parcel;
		this.from = from;
		this.to = to;
	}
}