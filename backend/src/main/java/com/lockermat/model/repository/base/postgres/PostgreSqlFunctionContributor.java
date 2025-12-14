package com.lockermat.model.repository.base.postgres;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;

import java.util.List;

public final class PostgreSqlFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions fc) {
        List.of(
                new NativeQueryDescriptor(fc)
        ).forEach(
                desc -> fc.getFunctionRegistry().register(desc.getName(), desc)
        );
    }
}