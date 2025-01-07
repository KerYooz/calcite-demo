package pers.shezm.calcite.kkTest;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlUnresolvedFunction;
import org.apache.calcite.sql.fun.SqlArrayValueConstructor;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import pers.shezm.calcite.dialect.DorisDialect;
import pers.shezm.calcite.parser.impl.KeryoozSqlParserImpl;

/**
 * @author Kerr
 * @create 2025-01-02 13:10
 */
public class Test3 {
    public static void main(String[] args) throws SqlParseException {
        // 解析配置 - mysql设置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder()
                // 定义解析工厂
                .setParserFactory(KeryoozSqlParserImpl.FACTORY)
                .setLex(Lex.MYSQL)
                .build();
        // 创建解析器

        // Sql语句
//        String sql = "select ARRAY(select id from aa)";
        String sql = "SELECT * FROM person LATERAL VIEW EXPLODE(AsssRRAY(30, 60)) `tableName` AS `c_age`";
        SqlParser parser = SqlParser.create(sql, mysqlConfig);
        // 解析sql
//        new SqlUnresolvedFunction()
//        new SqlUnresolvedFunction()
//        SqlArrayValueConstructor
//        SqlParser
        SqlNode sqlNode = parser.parseQuery();
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(DorisDialect.DEFAULT));
    }
}
