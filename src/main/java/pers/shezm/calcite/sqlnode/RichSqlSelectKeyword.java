package pers.shezm.calcite.sqlnode;

import org.apache.calcite.sql.SqlLiteral;
import org.apache.calcite.sql.parser.SqlParserPos;

/**
 * @author Kerr
 * @create 2025-01-02 17:43
 */
public enum RichSqlSelectKeyword {
    LATERAL,
    ARRAY,
    VIEW;


    /**
     * Creates a parse-tree node representing an occurrence of this keyword at a particular position
     * in the parsed text.
     */
    public SqlLiteral symbol(SqlParserPos pos) {
        return SqlLiteral.createSymbol(this, pos);
    }
}
