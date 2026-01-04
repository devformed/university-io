package com.lockermat.model.repository.lockermat;

import com.lockermat.model.dto.lockermat.LockermatFilter;
import com.lockermat.model.entity.lockermat.LockermatEntity;
import com.lockermat.model.entity.lockermat.LockermatEntity_;
import com.lockermat.model.repository.base.JpaRepository2;
import com.lockermat.model.repository.base.Specs;
import com.lockermat.model.repository.base.postgres.PostgreSqlFunction;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface LockermatRepository extends JpaRepository2<LockermatEntity, Long>, LockermatCustomRepository {

	default List<LockermatEntity> findBy(LockermatFilter filter, @Nullable Pageable pageable) {
		var specs = new ArrayList<Specification<LockermatEntity>>();
		if (filter.fulltext() != null) {
			specs.add(Specs.fulltext(LockermatEntity_.address, filter.fulltext()));
		}
		if (filter.position() != null) {
			specs.add(Specs.orderBy((cb, cq, from) ->
					List.of(cb.asc(PostgreSqlFunction.stDistance(cb, from.get(LockermatEntity_.location), filter.position())))));
		}
		return pageable == null ? findAll(Specs.and(specs)) : findAll(Specs.and(specs), pageable).getContent();
	}
}