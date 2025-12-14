package com.lockermat.model.entity.base;

import com.lockermat.util.Identifiable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Anton Gorokh
 */
@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEntity implements Identifiable<Long> {

	@Id
	@SequencePerTableGeneratorType
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
}