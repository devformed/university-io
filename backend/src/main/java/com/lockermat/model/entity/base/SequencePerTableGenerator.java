package com.lockermat.model.entity.base;

import org.hibernate.MappingException;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.generator.GeneratorCreationContext;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author Anton Gorokh
 */
public class SequencePerTableGenerator extends SequenceStyleGenerator {

    @Override
    public void configure(GeneratorCreationContext creationContext, Properties props) throws MappingException {
        setGeneratorProperties(props, creationContext.getServiceRegistry());
        String sequenceName = props.getProperty(PersistentIdentifierGenerator.TABLE) + props.getProperty(DEF_SEQUENCE_SUFFIX);
        props.setProperty(SEQUENCE_PARAM, sequenceName);
        super.configure(creationContext, props);
    }

    private void setGeneratorProperties(Properties props, ServiceRegistry serviceRegistry) {
        Class<?> entityClass = serviceRegistry.requireService(ClassLoaderService.class)
                .classForName(props.getProperty(ENTITY_NAME));

        SequencePerTableGeneratorType config = traverseGeneratorConfig(entityClass);
        if (config == null) {
            throw new IllegalStateException("No @%s annotation found in %s".formatted(SequencePerTableGeneratorType.class.getName(), entityClass.getName()));
        }

        props.setProperty(DEF_SEQUENCE_SUFFIX, config.sequenceSuffix());
        props.setProperty(INCREMENT_PARAM, config.sequenceIncrementSize());
        props.setProperty(GENERATOR_NAME, config.name());
    }

    private SequencePerTableGeneratorType traverseGeneratorConfig(Class<?> clazz) {
        if (clazz == null) return null;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(SequencePerTableGeneratorType.class)) {
                return field.getAnnotation(SequencePerTableGeneratorType.class);
            }
        }
        return traverseGeneratorConfig(clazz.getSuperclass());
    }
}