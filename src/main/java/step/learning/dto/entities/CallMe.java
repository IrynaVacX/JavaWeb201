package step.learning.dto.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CallMe
{
    private String id;
    private String name;
    private String phone;
    private Date moment;
    private Date callMoment;

    public CallMe(ResultSet resultSet) throws SQLException
    {
        this.id = resultSet.getString("id");
        this.name = resultSet.getString("name");
        this.phone = resultSet.getString("phone");
        this.moment = new Date(resultSet.getTimestamp("moment").getTime());
        this.callMoment = new Date(resultSet.getTimestamp("call_moment").getTime());
    }

}
