package pers.shezm.calcite.dialect;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.config.NullCollation;
import org.apache.calcite.interpreter.Context;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;

/**
 * @author Kerr
 * @create 2024-12-28 11:06
 */
public class DorisDialect extends MysqlSqlDialect {
    public static final SqlDialect.Context DEFAULT_CONTEXT = SqlDialect.EMPTY_CONTEXT
            .withDatabaseProduct(SqlDialect.DatabaseProduct.MYSQL)
            .withIdentifierQuoteString("`")
            .withUnquotedCasing(Casing.UNCHANGED)
            .withNullCollation(NullCollation.LOW);
    public static final SqlDialect DEFAULT = new DorisDialect(DEFAULT_CONTEXT);
    public DorisDialect() {
        this(DEFAULT_CONTEXT);
    }
    public DorisDialect(Context context) {
        super(context);
    }

    @Override
    public void quoteStringLiteral(StringBuilder buf, String charsetName, String val) {
        buf.append(this.literalQuoteString);
        buf.append(val.replace(this.literalEndQuoteString, this.literalEscapedQuote));}
}
