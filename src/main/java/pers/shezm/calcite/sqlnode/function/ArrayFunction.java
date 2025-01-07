package pers.shezm.calcite.sqlnode.function;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.type.SqlOperandTypeChecker;
import org.apache.calcite.sql.type.SqlOperandTypeInference;
import org.apache.calcite.sql.type.SqlReturnTypeInference;

import java.util.List;

/**
 * @author Kerr
 * @create 2025-01-02 18:54
 */
public class ArrayFunction extends SqlFunction {

    public ArrayFunction(String name,
                         SqlKind kind,
                         SqlReturnTypeInference returnTypeInference,
                         SqlOperandTypeInference operandTypeInference,
                         SqlOperandTypeChecker operandTypeChecker,
                         SqlFunctionCategory category) {
        super(name, kind, returnTypeInference, operandTypeInference, operandTypeChecker, category);
    }

    public ArrayFunction(SqlIdentifier sqlIdentifier,
                         SqlReturnTypeInference returnTypeInference,
                         SqlOperandTypeInference operandTypeInference,
                         SqlOperandTypeChecker operandTypeChecker,
                         List<RelDataType> paramTypes,
                         SqlFunctionCategory funcType) {
        super(sqlIdentifier,
                returnTypeInference,
                operandTypeInference,
                operandTypeChecker,
                paramTypes,
                funcType);
    }

    protected ArrayFunction(String name,
                            SqlIdentifier sqlIdentifier,
                            SqlKind kind,
                            SqlReturnTypeInference returnTypeInference,
                            SqlOperandTypeInference operandTypeInference,
                            SqlOperandTypeChecker operandTypeChecker,
                            List<RelDataType> paramTypes,
                            SqlFunctionCategory category) {
        super(name, sqlIdentifier, kind, returnTypeInference, operandTypeInference, operandTypeChecker, paramTypes, category);
//    SqlLiteral.createBoolean()
//        new SqlCollectionTypeNameSpec()
    }
}
