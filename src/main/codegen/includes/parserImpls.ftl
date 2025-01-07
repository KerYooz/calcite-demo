<#--
// Licensed to the Apache Software Foundation (ASF) under one or more
// contributor license agreements.  See the NOTICE file distributed with
// this work for additional information regarding copyright ownership.
// The ASF licenses this file to you under the Apache License, Version 2.0
// (the "License"); you may not use this file except in compliance with
// the License.  You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
-->

<#--
  Add implementations of additional parser statements, literals or
  data types.

  Example of SqlShowTables() implementation:
  SqlNode SqlShowTables()
  {
    ...local variables...
  }
  {
    <SHOW> <TABLES>
    ...
    {
      return SqlShowTables(...)
    }
  }
-->
// 创建函数
SqlNode SqlCreateFunction() :
{
    // 声明变量
    SqlParserPos createPos;
    SqlParserPos propertyPos;
    SqlNode functionName = null;
    String className = null;
    String methodName = null;
    String comment = null;
    SqlNodeList properties = null;
}
{
    // create 关键字
    <CREATE>
    {
        // 获取当前token的行列位置
        createPos = getPos();
     }
    // function 关键字
    <FUNCTION>
    // 函数名
    functionName = CompoundIdentifier()
    // as关键字
    <AS>
    // 类名
    { className = StringLiteralValue(); }
    // if语句
    [
        // method关键字
        <METHOD>
        {
            // 方法名称
            methodName = StringLiteralValue();
        }
    ]
    // if
    [
        // property 关键字,设置初始化变量
        <PROPERTY>
            {
                // 获取关键字位置
                propertyPos = getPos();
                SqlNode property;
                properties = new SqlNodeList(propertyPos);
            }
        <LPAREN>
        [
            property = PropertyValue()
            {
                properties.add(property);
            }
            (
                <COMMA>
                {
                    property = PropertyValue();
                    properties.add(property);
                }
            )*
        ]
        <RPAREN>
    ]
    // if
    [
        <COMMENT> {
            // 备注
            comment = StringLiteralValue();
        }
    ]

    {
        return new SqlCreateFunction(createPos, functionName, className, methodName, comment, properties);
    }
}

JAVACODE String StringLiteralValue() {
    SqlNode sqlNode = StringLiteral();
    return ((NlsString) SqlLiteral.value(sqlNode)).getValue();
}



/**
 * 解析SQL中的key=value形式的属性值
 */
SqlNode PropertyValue() :
{
    SqlNode key;
    SqlNode value;
    SqlParserPos pos;
}
{
    key = StringLiteral()
    { pos = getPos(); }
    <EQ> value = StringLiteral()
    {
        return new SqlProperty(getPos(), key, value);
    }
}



SqlNode SqlLateralView() :
{
    final List<SqlLiteral> keywords = new ArrayList<SqlLiteral>();
    final SqlNodeList keywordList;
    List<SqlNode> selectList;
    final SqlNode fromClause;
    final SqlNode where;
    final SqlNodeList groupBy;
    final SqlNode having;
    final SqlNodeList windowDecls;
    final List<SqlNode> hints = new ArrayList<SqlNode>();
    final Span s;
    SqlNode explodeTblFunction;
}
{
    <SELECT>
    {
        s = span();
    }
    [
        <HINT_BEG>
        CommaSepatatedSqlHints(hints)
        <COMMENT_END>
    ]
    SqlSelectKeywords(keywords)
    (
        <STREAM> {
            keywords.add(SqlSelectKeyword.STREAM.symbol(getPos()));
        }
    )?
    (
        <DISTINCT> {
            keywords.add(SqlSelectKeyword.DISTINCT.symbol(getPos()));
        }
    |   <ALL> {
            keywords.add(SqlSelectKeyword.ALL.symbol(getPos()));
        }
    |   <ARRAY>{
            keywords.add(RichSqlSelectKeyword.ARRAY.symbol(getPos()));
        }
    )?
    {
        keywordList = new SqlNodeList(keywords, s.addAll(keywords).pos());
    }
    selectList = SelectList()
    (
        <FROM> fromClause = FromClause()
        where = WhereOpt()
        groupBy = GroupByOpt()
        having = HavingOpt()
        windowDecls = WindowOpt()
    |
        E() {
            fromClause = null;
            where = null;
            groupBy = null;
            having = null;
            windowDecls = null;
        }
    )
    (
        <LATERAL> <VIEW> {
                keywords.add(RichSqlSelectKeyword.LATERAL.symbol(getPos()));
                keywords.add(RichSqlSelectKeyword.VIEW.symbol(getPos()));
                explodeTblFunction=ExplodeTblFunction();
        }
    |
         E() {
            explodeTblFunction=null;
         }
    )

    {
        if(explodeTblFunction==null){
            return new SqlSelect(s.end(this), keywordList,
                        new SqlNodeList(selectList, Span.of(selectList).pos()),
                        fromClause, where, groupBy, having, windowDecls, null, null, null,
                        new SqlNodeList(hints, getPos()));
        }
        return new SqlLateralView(s.end(this), keywordList,
            new SqlNodeList(selectList, Span.of(selectList).pos()),
            fromClause, where, groupBy, having, windowDecls, null, null, null,
            new SqlNodeList(hints, getPos()),explodeTblFunction);
    }
}


SqlNode ExplodeTblFunction():
{
    SqlNode row;
    String tblName;
    String rowName;
    SqlParserPos pos;
}
{
    <EXPLODE>
    {
        pos=getPos();
    }
    (
    (
    <ARRAY>{

         }
    )?
    {
        row=SelectItem();
    }
    )
    {
         tblName=StringLiteralValue();
    }
    <AS>
    {
        rowName=StringLiteralValue();
    }
    {
        return new ExplodeTblFunction(pos, row,tblName,rowName);
    }
}