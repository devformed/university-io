package com.lockermat.model.repository.lockermat;

import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface LockermatRepository extends BaseRepository<LockermatEntity> {

	@Query(value = QUERY_FIND_AVAILABLE, nativeQuery = true)
	List<LockermatEntity> findAvailable(@Param("lng") Double lng, @Param("lat") Double lat, @Param("availableFrom") Instant availableFrom, @Param("availableTo") Instant availableTo, @Param("sizes") Set<String> sizes, @Param("offset") int offset, @Param("limit") int limit);

	String QUERY_FIND_AVAILABLE = """
			SELECT *
			FROM lockermat_ l
			WHERE 1 <= (
				select count(1) from parcel_ p
				left join reservation_ r on r.parcel_id_ = p.id_
				where p.lockermat_id_ = l.id_
				and (r.to_ is null or r.to_ <= now())
				and ( :sizes IS NULL OR p.size_ IN :sizes )
			)
			ORDER BY CASE
				WHEN :lng IS NOT NULL THEN
					ST_Distance(
						l.location_::geography,
						ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography
					)
				ELSE 0 END
			OFFSET :offset LIMIT :limit
			""";
}