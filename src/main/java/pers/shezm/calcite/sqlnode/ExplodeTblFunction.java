package pers.shezm.calcite.sqlnode;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParserPos;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Kerr
 * @create 2025-01-02 16:07
 */
public class ExplodeTblFunction  extends SqlCall{
    private SqlNode row;
    private  String tblName;
    private String rowName;

    public SqlNode getRow() {
        return row;
    }

    public String getTblName() {
        return tblName;
    }

    public String getRowName() {
        return rowName;
    }

    public ExplodeTblFunction(SqlParserPos pos, SqlNode row, String tblName, String rowName) {
        super(pos);
        this.row=row;
        this.tblName=tblName;
        this.rowName=rowName;
    }

    @Override
    public SqlOperator getOperator() {
        return null;
    }

    @Override
    public List<SqlNode> getOperandList() {
        return null;
    }
    @Override
    public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
        writer.keyword("EXPLODE");
        row.unparse(writer, leftPrec, rightPrec);
        writer.newlineAndIndent();
        writer.print("`"+tblName+"`");
        writer.keyword("AS");
        writer.print("`" + rowName + "`");
    }
}
