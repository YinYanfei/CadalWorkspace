package common.utils;

import java.sql.SQLException;
import java.util.List;

public interface SQLFetcher {
    
    public void process (Object params) throws SQLException;
    public List fetch (Object params) throws SQLException;
    public void dispose ();

}
