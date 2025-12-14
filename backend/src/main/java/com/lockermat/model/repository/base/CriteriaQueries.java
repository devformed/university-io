package com.lockermat.model.repository.base;

import com.lockermat.util.Optionull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

import static com.lockermat.util.Optionull.optionull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CriteriaQueries {

    public static <E> Optionull<E> oneValue(@Nullable TypedQuery<E> typedQuery) {
        return optionull(typedQuery)
                .map(query -> query.setMaxResults(2)) // will throw ex if result is not unique
                .mapIgnoreException(TypedQuery::getSingleResult);
    }

    public static <E> Optionull<E> anyValue(@Nullable TypedQuery<E> typedQuery) {
        return optionull(typedQuery)
                .map(query -> query.setMaxResults(1)) // won't throw ex if result is not unique
                .mapIgnoreException(TypedQuery::getSingleResult);
    }

    public static boolean exists(@Nullable TypedQuery<?> query) {
        return query != null && !query.getResultList().isEmpty();
    }

    public static <E> TypedQuery<E> setEntityGraphHint(EntityManager entityManager, TypedQuery<E> query,
                                                       @Nullable String entityGraphName, @Nullable EntityGraph.EntityGraphType graphType) {
        if (entityGraphName != null && graphType != null) {
            query.setHint(graphType.getKey(), entityManager.getEntityGraph(entityGraphName));
        }
        return query;
    }
}
