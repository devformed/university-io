package com.lockermat.model.entity.base;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Anton Gorokh
 */
@IdGeneratorType(SequencePerTableGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SequencePerTableGeneratorType {
    String name() default "id_sequence_gen";
    String sequenceSuffix() default "id_seq";
    String sequenceIncrementSize() default "1";
}
