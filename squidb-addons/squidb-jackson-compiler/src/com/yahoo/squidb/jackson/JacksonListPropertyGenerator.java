/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the Apache 2.0 License.
 * See the accompanying LICENSE file for terms.
 */
package com.yahoo.squidb.jackson;

import com.yahoo.aptutils.model.DeclaredTypeName;
import com.yahoo.aptutils.utils.AptUtils;
import com.yahoo.aptutils.writer.expressions.Expression;
import com.yahoo.aptutils.writer.expressions.Expressions;
import com.yahoo.squidb.processor.data.ModelSpec;

import java.util.Set;

import javax.lang.model.element.VariableElement;

public class JacksonListPropertyGenerator extends JacksonPropertyGenerator {

    public JacksonListPropertyGenerator(ModelSpec<?> modelSpec, VariableElement field, DeclaredTypeName fieldType,
            AptUtils utils) {
        super(modelSpec, field, fieldType, utils);
    }

    @Override
    protected void registerAdditionalImports(Set<DeclaredTypeName> imports) {
        super.registerAdditionalImports(imports);
        imports.add(JacksonTypeConstants.LIST);
        imports.add(JacksonTypeConstants.ARRAY_LIST);
        imports.add(JacksonTypeConstants.COLLECTION_TYPE);
    }

    @Override
    protected DeclaredTypeName getJavaTypeForGetter() {
        return JacksonTypeConstants.COLLECTION_TYPE;
    }

    @Override
    protected Expression getJavaTypeInitializer() {
        return Expressions.staticReference(JacksonTypeConstants.SQUIDB_JACKSON_SUPPORT, "MAPPER")
                .callMethod("getTypeFactory")
                .callMethod("constructCollectionType",
                        Expressions.classObject(JacksonTypeConstants.ARRAY_LIST),
                        Expressions.classObject((DeclaredTypeName) fieldType.getTypeArgs().get(0)));
    }

}
