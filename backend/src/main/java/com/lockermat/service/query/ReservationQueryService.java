package com.lockermat.service.query;

import com.lockermat.model.dto.lockermat.parcel.reservation.ReservationEntry;
import com.lockermat.model.repository.lockermat.ReservationRepository;
import com.lockermat.service.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Anton Gorokh
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryService {

	private final ReservationRepository reservationRepo;

	public List<ReservationEntry> findAll() {
		return ReservationMapper.INSTANCE.toEntries(reservationRepo.findAll());
	}
}