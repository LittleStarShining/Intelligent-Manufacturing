package com.gene.IM.mapper;


import org.springframework.jdbc.core.RowMapper;

import com.gene.IM.entity.Record;

import java.sql.ResultSet;
import java.sql.SQLException;

// 之前任务需要，后续可删除
public class RecordMapper implements RowMapper<Record> {


    @Override
    public Record mapRow(ResultSet resultSet, int i) throws SQLException {
        Record record = new Record();
        record.setStatus(resultSet.getString("status"));
        record.setTemp(resultSet.getInt("temp"));
        record.setWet(resultSet.getInt("wet"));
        record.setTime(resultSet.getTime("time"));
        record.setDate(resultSet.getDate("date"));
        record.setRecordID(resultSet.getInt("record_id"));
        return record;
    }
}
