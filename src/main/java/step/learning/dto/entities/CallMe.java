package step.learning.dto.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CallMe
{
    private String id;
    private String name;
    private String phone;
    private Date moment;
    private Date callMoment;
    private Date deleteMoment;

    public CallMe(ResultSet resultSet) throws SQLException
    {
        this.setId(resultSet.getString("id"));
        this.setName(resultSet.getString("name"));
        this.setPhone(resultSet.getString("phone"));
        this.setMoment(new Date(resultSet.getTimestamp("moment").getTime()));
        Timestamp callMoment = resultSet.getTimestamp("call_moment");
        this.setCallMoment(callMoment == null ? null : new Date(callMoment.getTime()));
    }

    // region accessors
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getMoment() {
        return moment;
    }
    public void setMoment(Date moment) {
        this.moment = moment;
    }

    public Date getCallMoment() {
        return callMoment;
    }
    public void setCallMoment(Date callMoment) {
        this.callMoment = callMoment;
    }
    // endregion

//    public Map<String, String> getErrorMessages()
//    {
//        Map<String, String> result = new HashMap<>();
//        if(phone == null || "".equals(phone))
//        {
//            result.put("phone", "Номер телефону не може бути порожнім");
//        }
//        else if(!Pattern.matches("^\\+380\\d{9}$",phone))
//        {
//            result.put("phone", "Номер телефону не відповідає шаблону: '\\+380\\d{9}'");
//        }
//        if(name == null || "".equals(name))
//        {
//            result.put("name", "Ім'я не може бути порожнім");
//        }
//        return result;
//    }

}
