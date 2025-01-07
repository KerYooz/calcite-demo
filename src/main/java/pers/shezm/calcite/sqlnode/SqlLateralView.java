package pers.shezm.calcite.sqlnode;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParserPos;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Kerr
 * @create 2025-01-02 16:44
 */
public class SqlLateralView extends SqlSelect {

    private SqlNode tblFunction;

    public SqlLateralView(SqlParserPos pos,
                          SqlNodeList keywordList,
                          SqlNodeList selectList,
                          SqlNode from,
                          SqlNode where,
                          SqlNodeList groupBy,
                          SqlNode having,
                          SqlNodeList windowDecls,
                          SqlNodeList orderBy,
                          SqlNode offset,
                          SqlNode fetch,
                          SqlNodeList hints,
                          SqlNode tblFunction) {
        super(pos, keywordList, selectList, from, where, groupBy, having, windowDecls, orderBy, offset, fetch, hints);
        this.tblFunction=tblFunction;
//        SqlSelectKeyword.DISTINCT
    }

    @Override
    public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
        super.unparse(writer,leftPrec,rightPrec);
        writer.keyword("LATERAL");
        writer.keyword("VIEW");
        tblFunction.unparse(writer, leftPrec, rightPrec);
    }
}
