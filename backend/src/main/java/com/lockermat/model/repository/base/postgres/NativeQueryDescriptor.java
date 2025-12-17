package com.lockermat.model.repository.base.postgres;

import com.lockermat.model.repository.base.impl.ImpliedReturnTypeResolver;
import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators;
import org.hibernate.query.sqm.produce.function.StandardFunctionArgumentTypeResolvers;
import org.hibernate.sql.ast.SqlAstNodeRenderingMode;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.QueryLiteral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

final class NativeQueryDescriptor extends AbstractSqmSelfRenderingFunctionDescriptor {

    private final static Pattern PLACEHOLDER_REGEX = Pattern.compile("(?<!:):([a-zA-Z0-9_]+)");

    public NativeQueryDescriptor(FunctionContributions fc) {
        super(
                PostgreSqlFunction.NATIVE_QUERY.key(),
                FunctionKind.NORMAL,
                StandardArgumentsValidators.min(1),
                ImpliedReturnTypeResolver.INSTANCE,
                StandardFunctionArgumentTypeResolvers.NULL
        );
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> args, ReturnableType<?> returnType, SqlAstTranslator<?> translator) {
        Map<String, SqlAstNode> nameArgumentMap = getNameToArgumentMap(args);
        String query = getQuery(args);

        List<MatchResult> results = PLACEHOLDER_REGEX.matcher(query).results().toList();
        int lastMatchIndex = 0;
        for (MatchResult match : results) {
            sqlAppender.appendSql(query.substring(lastMatchIndex, match.start()));
            translator.render(nameArgumentMap.get(match.group(1).toLowerCase()), SqlAstNodeRenderingMode.DEFAULT);
            lastMatchIndex = match.end();
        }
        if (lastMatchIndex < query.length()) {
            sqlAppender.appendSql(query.substring(lastMatchIndex));
        }
    }

    @Override
    public String getArgumentListSignature() {
        return "(query, (name, argument)...)";
    }

    private String getQuery(List<? extends SqlAstNode> args) {
        return (String) ((QueryLiteral<?>) args.getFirst()).getLiteralValue();
    }

    private Map<String, SqlAstNode> getNameToArgumentMap(List<? extends SqlAstNode> args) {
        Map<String, SqlAstNode> map = new HashMap<>();
        for (int i = 1; i < args.size() - 1; i += 2) {
            String name = getLiteral(args.get(i), String.class).toLowerCase();
            map.put(name, args.get(i + 1));
        }
        return map;
    }

    private <T> T getLiteral(SqlAstNode node, Class<T> clazz) {
        return clazz.cast(((QueryLiteral<?>) node).getLiteralValue());
    }
}

