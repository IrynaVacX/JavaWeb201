package step.learning.dao;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dto.entities.CallMe;
import step.learning.services.db.DbProvider;

import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Singleton
public class CallMeDao
{
    private final DbProvider dbProvider;
    private final String dbPrefix;
    @Inject
    public CallMeDao(DbProvider dbProvider, @Named("db-prefix") String dbPrefix)
    {
        this.dbProvider = dbProvider;
        this.dbPrefix = dbPrefix;
    }

    public List<CallMe> getAll()
    {
        List<CallMe> ret = new ArrayList<>();
        String sql = "SELECT C.'id', C.'name', C.'phone', C.'moment' FROM " +
                dbPrefix + "call_me C";
        try(Statement statement = dbProvider.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql))
        {
            while(resultSet.next())
            {
                ret.add(new CallMe(resultSet));
            }
        }
        catch (SQLException ex)
        {
//            logger.log(Level.WARNING, ex.getMessage() + " " + sql);
            System.err.println(ex.getMessage());
        }

        return ret;
    }

}
