package common.utils;

import java.sql.SQLException;


public interface SQLEditor {
    public int edit (Object obj) throws SQLException;
    public int edit (Object[] objarray) throws SQLException;
    public void dispose () throws SQLException;
}
