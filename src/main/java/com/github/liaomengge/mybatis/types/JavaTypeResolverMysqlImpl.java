package com.github.liaomengge.mybatis.types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.math.BigInteger;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by liaomengge on 2019/9/6.
 */
public class JavaTypeResolverMysqlImpl extends JavaTypeResolverDefaultImpl {

    private static final String UNSIGNED = "UNSIGNED";
    private static final Map<Integer, FullyQualifiedJavaType> UNSIGNED_JAVA_TYPE_MAP = new HashMap<>(16);

    static {
        UNSIGNED_JAVA_TYPE_MAP.put(Types.TINYINT, new FullyQualifiedJavaType(Short.class.getName()));
        UNSIGNED_JAVA_TYPE_MAP.put(Types.SMALLINT, new FullyQualifiedJavaType(Integer.class.getName()));
        UNSIGNED_JAVA_TYPE_MAP.put(Types.INTEGER, new FullyQualifiedJavaType(Long.class.getName()));
        UNSIGNED_JAVA_TYPE_MAP.put(Types.BIGINT, new FullyQualifiedJavaType(BigInteger.class.getName()));
    }

    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column,
                                                         FullyQualifiedJavaType defaultType) {
        if (isUnsignedType(column)) {
            FullyQualifiedJavaType answer = defaultType;
            switch (column.getJdbcType()) {
                case Types.BIT:
                    answer = super.calculateBitReplacement(column, defaultType);
                    break;
                case Types.DATE:
                    answer = super.calculateDateType(column, defaultType);
                    break;
                case Types.DECIMAL:
                case Types.NUMERIC:
                    answer = super.calculateBigDecimalReplacement(column, defaultType);
                    break;
                case Types.TIME:
                    answer = super.calculateTimeType(column, defaultType);
                    break;
                case Types.TIMESTAMP:
                    answer = super.calculateTimestampType(column, defaultType);
                    break;
                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                case Types.BIGINT:
                    answer = this.calculateUnsignedType(column, defaultType);
                    break;
                default:
                    break;
            }
            return answer;
        }
        return super.overrideDefaultType(column, defaultType);
    }

    private boolean isUnsignedType(IntrospectedColumn column) {
        String actualTypeName = column.getActualTypeName();
        if (Objects.nonNull(actualTypeName) && !"".equalsIgnoreCase(actualTypeName.trim())) {
            return actualTypeName.toUpperCase().contains(UNSIGNED);
        }
        return false;
    }

    private FullyQualifiedJavaType calculateUnsignedType(IntrospectedColumn column,
                                                         FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType javaType = UNSIGNED_JAVA_TYPE_MAP.get(column.getJdbcType());
        if (Objects.nonNull(javaType)) {
            return javaType;
        }
        return defaultType;
    }
}
