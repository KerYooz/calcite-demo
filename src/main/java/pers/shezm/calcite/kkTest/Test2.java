package pers.shezm.calcite.kkTest;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlUnresolvedFunction;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import pers.shezm.calcite.dialect.DorisDialect;
import pers.shezm.calcite.parser.impl.KeryoozSqlParserImpl;

/**
 * @author Kerr
 * @create 2025-01-02 13:10
 */
public class Test2 {
    public static void main(String[] args) throws SqlParseException {
        // 解析配置 - mysql设置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder()
                // 定义解析工厂
                .setParserFactory(KeryoozSqlParserImpl.FACTORY)
                .setLex(Lex.MYSQL)
                .build();
        // 创建解析器
        SqlParser parser = SqlParser.create("", mysqlConfig);
        // Sql语句
//        String sql = "select ARRAY(30, 60)";
        String sql = "SELECT * FROM person LATERAL VIEW '1'";
//        String sql = "SELECT * FROM person";
//        String sql = "LATERAL VIEW '1'";
//        String sql = "create table aaa as select 1";
        // 解析sql
//        new SqlUnresolvedFunction()
        SqlNode sqlNode = parser.parseQuery(sql);
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(DorisDialect.DEFAULT));
    }
}
