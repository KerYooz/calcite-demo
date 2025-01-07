package pers.shezm.calcite.test;

import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlWriterConfig;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql.pretty.SqlPrettyWriter;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import pers.shezm.calcite.dialect.DorisDialect;

/**
 * @author Kerr
 * @create 2024-12-28 10:55
 */
public class Test7 {
    public static void main(String[] args) throws Exception{
        String sql="select * from t0 where id=1";
         SqlParser.Config config = SqlParser.configBuilder().setParserFactory(SqlParserImpl::new)
                .setCaseSensitive(Boolean.FALSE)
                .setUnquotedCasing(Casing.UNCHANGED)
                 . setConformance(SqlConformanceEnum.MYSQL_5)
                .setQuotedCasing(Casing.UNCHANGED).build();
         SqlNode sqlNode = SqlParser.create(sql,config).parseQuery();
        System.out.println(sqlNode);
        SqlWriterConfig sqlWriterConfig = SqlPrettyWriter.config().withAlwaysUseParentheses(true)
                .withUpdateSetListNewline(false)
                 . withQuoteAllIdentifiers(false)
                .withFromFolding(SqlWriterConfig.LineFolding.TALL)
                .withIndentation(0)
                .withKeywordsLowerCase(true)
                .withDialect(DorisDialect.DEFAULT);
        System.out.println(sqlNode.toSqlString(r -> sqlWriterConfig));

    }
}
