package gest.persistence.filter_row_set.predicate;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public class SearchFilter implements Predicate {
    private Pattern pattern;
    private String attribuite;

    public SearchFilter(String attribuite, String regex) {
        if (regex != null && !regex.isEmpty()) {
            pattern = Pattern.compile(regex.toLowerCase());
            this.attribuite = attribuite;
        }
    }

    @Override
    public boolean evaluate(RowSet rs) {
        try {
            if (!rs.isAfterLast()) {
                String name = rs.getString(attribuite);
                Matcher matcher = pattern.matcher(name.toLowerCase());
                return matcher.matches();
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean evaluate(Object arg0, int arg1) throws SQLException {
        return true;
    }

    @Override
    public boolean evaluate(Object arg0, String arg1) throws SQLException {
        return true;
    }

}