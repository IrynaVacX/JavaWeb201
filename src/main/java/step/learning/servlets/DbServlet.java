package step.learning.servlets;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.services.db.DbProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
public class DbServlet extends HttpServlet
{
    private final DbProvider dbProvider;
    @Inject
    public DbServlet(DbProvider dbProvider)
    {
        this.dbProvider = dbProvider;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        String connectionStatus;
        try
        {
            dbProvider.getConnection();
            connectionStatus = "Connection OK";
        }
        catch(RuntimeException ex)
        {
            connectionStatus = "Connection error : " + ex.getMessage();
        }

        req.setAttribute("connectionStatus", connectionStatus);
        req.setAttribute("page-body", "db.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }

//    private final String dbPrefix = "PR01";
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
//    {
//        String status;
//        String message;
//        String sql = "CREATE TABLE " + dbPrefix + "call_me (" +
//                "id     BIGINT      PRIMARY KEY DEFAULT UUID_SHORT()," +
//                "name   VARCHAR(64) NULL," +
//                "phone  CHAR(13)    NOT NULL COMMENT '+39 098 765 43 21'" +
//                "moment DATETIME    DEFAULT CURRENT_TIMESTAMP" +
//                ")  ENGINE = InnoDB DEFAULT CHARSET = UTF8";
//        try(Statement statement = dbProvider.getConnection().createStatement())
//        {
//            statement.executeUpdate(sql);
//            status = "OK";
//            message = "Table created";
//        } catch (SQLException e) {
//            status = "error";
//            message = e.getMessage();
//        }
//
//        JsonObject result = new JsonObject();
//        result.addProperty("status", status);
//        result.addProperty("message", message);
//        resp.getWriter().print(result.toString());
//    }

}
