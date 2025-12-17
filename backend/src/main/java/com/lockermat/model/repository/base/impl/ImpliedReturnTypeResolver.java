package com.lockermat.model.repository.base.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.metamodel.mapping.BasicValuedMapping;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.sqm.produce.function.FunctionReturnTypeResolver;
import org.hibernate.query.sqm.sql.SqmToSqlAstConverter;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Anton Gorokh
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImpliedReturnTypeResolver implements FunctionReturnTypeResolver {

	public final static ImpliedReturnTypeResolver INSTANCE = new ImpliedReturnTypeResolver();

	@Override
	public BasicValuedMapping resolveFunctionReturnType(Supplier<BasicValuedMapping> impliedTypeAccess, List<? extends SqlAstNode> arguments) {
		return impliedTypeAccess.get();
	}

	// bypass recursive supplier hell causing stack overflow
	@Override
	public ReturnableType<?> resolveFunctionReturnType(ReturnableType<?> impliedType, SqmToSqlAstConverter converter,
													   List<? extends SqmTypedNode<?>> arguments, TypeConfiguration typeConfiguration) {
		return impliedType != null ? impliedType
				: typeConfiguration.getBasicTypeRegistry().resolve(StandardBasicTypes.OBJECT_TYPE);
	}
}