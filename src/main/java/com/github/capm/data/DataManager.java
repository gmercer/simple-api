package com.github.capm.data;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    NamedParameterJdbcTemplate template;

    private static final String QUERY_SQL = "select * from :type t, objects o where o.name = :name " +
            "and o.type = ':type' and t.id = o.id and t.ts BETWEEN :start and :end ;";

    public DataManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<String> query(String name, String type, LocalDateTime start, LocalDateTime end) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("name", name);
        params.put("start", start);
        params.put("end", end);

        return template.query(QUERY_SQL, params, (rs, rowNum) -> {
            String retval = "";
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                retval += rs.getString(i);
                if (i != rs.getMetaData().getColumnCount()) {
                    retval += ",";
                }
            }
            return retval;
        });

    }
}
