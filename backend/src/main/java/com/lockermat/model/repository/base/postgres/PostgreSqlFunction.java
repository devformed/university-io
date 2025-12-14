package com.lockermat.model.repository.base.postgres;

import com.lockermat.model.dto.Position;
import com.lockermat.util.Collections2;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import org.locationtech.jts.geom.Point;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum PostgreSqlFunction {

    NATIVE_QUERY;

    public String key() {
        return "_" + name().toLowerCase();
    }

    public static Expression<Long> stDistance(CriteriaBuilder cb, Expression<Point> point, Position position) {
        Double lng = position.longitude().doubleValue();
        Double lat = position.latitude().doubleValue();
        String query = "st_distance(:point::geography, st_setsrid(st_makepoint(:lng, :lat), 4326)::geography)";
        return nativeQuery(cb, Long.class, query, Map.of("point", point, "lng", cb.literal(lng), "lat", cb.literal(lat)));
    }

    public static <T> Expression<T> nativeQuery(CriteriaBuilder cb, Class<T> clazz, String query, Map<String, Expression<?>> keyValueMap) {
        return cb.function(PostgreSqlFunction.NATIVE_QUERY.key(), clazz, flatmapArguments(cb, query, keyValueMap).toArray(Expression[]::new));
    }

    public static List<Expression<?>> flatmapArguments(CriteriaBuilder cb, String query, Map<String, Expression<?>> keyValueMap) {
        return keyValueMap.entrySet()
                .stream()
                .map(entry -> List.of(cb.literal(entry.getKey()), entry.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(() -> Collections2.newArrayList(cb.literal(query))));
    }
}

