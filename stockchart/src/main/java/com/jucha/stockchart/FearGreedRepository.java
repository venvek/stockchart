package com.jucha.stockchart;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FearGreedRepository {

    private final JdbcTemplate jdbcTemplate;

    public FearGreedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FearGreedData> findAll() {
        String sql = "SELECT date, index_value FROM fear_greed ORDER BY date";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) ->
                new FearGreedData(
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("index_value")
                ));
    }

    public FearGreedData findLatest() {
        String sql = "SELECT date, index_value FROM fear_greed ORDER BY date DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum) ->
                new FearGreedData(
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("index_value")
                ));
    }
}
