package gest.connection;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.RowSetProvider;

import gest.enums.DBCrendecial;

public final class DBRowSet {

    public static final JdbcRowSet openJdbc() {
        try (JdbcRowSet jst = RowSetProvider.newFactory().createJdbcRowSet()) {
            jst.setUrl(DBCrendecial.URL.code());
            jst.setUsername(DBCrendecial.USER.code());
            jst.setPassword(DBCrendecial.PASSWORD.code());
            return jst;
        } catch (SQLException e) {
            return null;
        }
    }

    public static final CachedRowSet openCached() {
        try (CachedRowSet cst = RowSetProvider.newFactory().createCachedRowSet()) {
            cst.setUrl(DBCrendecial.URL.code());
            cst.setUsername(DBCrendecial.USER.code());
            cst.setPassword(DBCrendecial.PASSWORD.code());
            return cst;
        } catch (SQLException e) {
            return null;
        }
    }

    public static final FilteredRowSet openFilter() {
        try (FilteredRowSet fst = RowSetProvider.newFactory().createFilteredRowSet()) {
            fst.setUrl(DBCrendecial.URL.code());
            fst.setUsername(DBCrendecial.USER.code());
            fst.setPassword(DBCrendecial.PASSWORD.code());
            return fst;
        } catch (SQLException e) {
            return null;
        }
    }

    public static final JoinRowSet openJoin() {
        try (JoinRowSet fst = RowSetProvider.newFactory().createJoinRowSet()) {
            fst.setUrl(DBCrendecial.URL.code());
            fst.setUsername(DBCrendecial.USER.code());
            fst.setPassword(DBCrendecial.PASSWORD.code());
            return fst;
        } catch (SQLException e) {
            return null;
        }
    }

}
