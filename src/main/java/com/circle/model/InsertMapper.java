package com.circle.model;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertMapper implements ResultSetMapper<InsertResult> {

    @Override
    public InsertResult map(int arg0, ResultSet arg1, StatementContext context) throws SQLException {
        return new InsertResult(arg1.getString("action"), arg1.getInt("oid"), arg1.getInt("modified"));
    }
    
}
