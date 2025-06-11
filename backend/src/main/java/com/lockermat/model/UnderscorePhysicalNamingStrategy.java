package com.lockermat.model;

import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Optional;

/**
 * @author Anton Gorokh
 */
public class UnderscorePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	@Override
	public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnv) {
		return convert(identifier);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnv) {
		return convert(identifier);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnv) {
		return convert(identifier);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnv) {
		return convert(identifier);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnv) {
		return convertEntity(identifier);
	}

	private Identifier convertEntity(Identifier identifier) {
		if (identifier == null || StringUtils.isEmpty(identifier.getText())) {
			return identifier;
		}
		return convert(identifier.getText().replaceAll("(?i)entity$", ""));
	}

	private Identifier convert(@Nullable Identifier identifier) {
		return convert(identifier != null ? identifier.getText() : null);
	}

	private Identifier convert(@Nullable String text) {
		return Optional.ofNullable(text)
				.filter(StringUtils::isNotEmpty)
				.map(t -> t.replaceAll("([a-z])([A-Z])", "$1_$2") + "_")
				.map(String::toLowerCase)
				.map(Identifier::toIdentifier)
				.orElse(null);
	}
}
