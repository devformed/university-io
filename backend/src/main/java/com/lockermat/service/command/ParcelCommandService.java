package com.lockermat.service.command;

import com.lockermat.model.repository.ParcelRepository;
import com.lockermat.model.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ParcelCommandService {

	private final double remoteOpenDistanceMaxMeters;
	private final ParcelRepository parcelRepository;

	@Autowired
	public ParcelCommandService(@Value("${com.lockermat.parcel.open-remotely.max-distance-meters:15}") double remoteOpenDistanceMaxMeters, ParcelRepository parcelRepository) {
		this.remoteOpenDistanceMaxMeters = remoteOpenDistanceMaxMeters;
		this.parcelRepository = parcelRepository;
	}

	@Transactional
	public void openRemotely(Long parcelId, BigDecimal latitude, BigDecimal longitude) {
		if (!parcelRepository.isWithinDistance(parcelId, longitude.doubleValue(), latitude.doubleValue(), remoteOpenDistanceMaxMeters)) {
			doThrowNotCloseToParcel();
		}
		doOpenRemotely();
	}

	private void doOpenRemotely() {
		// to be implemented
	}

	private void doThrowNotCloseToParcel() {
		// commented out for demonstration purposes
//		throw new IllegalArgumentException("Position is too far from parcel");
	}
}