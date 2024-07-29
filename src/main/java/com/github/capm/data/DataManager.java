package com.github.capm.data;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository
public class DataManager {
    NamedParameterJdbcTemplate template;

    private static final String OLD_QUERY_SQL = "select * from :type t, objects o where o.name = :name " +
            "and o.type = ':type' and t.id = o.id and t.ts BETWEEN :start and :end ;";

    private static final String QUERY_SQL = "select * from data_of(:table, ':type', ':name', ':start', ':end') ;";

    private static final String SELECT_ALL = "SELECT * FROM " ;
    private static final String WHERE_NAME_MATCHES = " t, capm.objects o where o.name = :name " +
            "and o.type = (parse_ident(:type))[2] and t.id = o.id and t.ts BETWEEN :start and :end ;" ;

    private Map<String, String> queryMap = new HashMap<String, String>(){
        {
            put("cpu", "select * from cpu t, objects o where o.name = :name " +
                    "and o.type = ':type' and t.id = o.id and t.ts BETWEEN :start and :end ;");
            put("mem", "select * from mem t, objects o where o.name = :name " +
                    "and o.type = ':type' and t.id = o.id and t.ts BETWEEN :start and :end ;");}
    };

    private static Pattern safePattern = Pattern.compile("[.a-zA-Z0-9_-]+") ;

    public DataManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<String> query(String name, String type, LocalDateTime start, LocalDateTime end) {
        Map<String, Object> params = new HashMap<>();
        // params.put("table", "NULL::" + type);
        params.put("type", type);
        params.put("name", name);
        params.put("start", start);
        params.put("end", end);

        String sanitizedTable = (safePattern.matcher(type).matches() ? type : null) ;

        if (sanitizedTable == null) {
            throw new IllegalArgumentException("Table not found: '" + type + "'");
        }

        String querySql = SELECT_ALL + sanitizedTable + WHERE_NAME_MATCHES ;

        return template.query(querySql, params, (rs, rowNum) -> {
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
