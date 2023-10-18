package step.learning.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.CallMeDao;
import step.learning.dto.entities.CallMe;
import step.learning.services.db.DbProvider;

import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class DbServlet extends HttpServlet
{
    private final DbProvider dbProvider;
    private final String dbPrefix;
    private final CallMeDao callMeDao;

    @Inject
    public DbServlet(DbProvider dbProvider, @Named("db-prefix") String dbPrefix, CallMeDao callMeDao)
    {
        this.dbProvider = dbProvider;
        this.dbPrefix = dbPrefix;
        this.callMeDao = callMeDao;
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

        List<CallMe> calls = new ArrayList<>();
        try (Statement statement = dbProvider.getConnection().createStatement()) {
            String sql = "SELECT * FROM " + dbPrefix + "call_me";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
//                String id = resultSet.getString("id");
//                String name = resultSet.getString("name");
//                String phone = resultSet.getString("phone");
//                Date moment = resultSet.getDate("moment");
                calls.add(new CallMe(resultSet));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        req.setAttribute("calls", calls);

        req.setAttribute("page-body", "db.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        String status;
        String message;
        String sql = "CREATE TABLE " + dbPrefix + "call_me (" +
                "id      BIGINT       PRIMARY KEY ," +
                "name    VARCHAR(64)  NULL," +
                "phone   CHAR(13)     NOT NULL COMMENT '+38 098 765 43 21'," +
                "moment  DATETIME     DEFAULT CURRENT_TIMESTAMP" +
                ") ENGINE = InnoDB DEFAULT CHARSET = UTF8";
        try(Statement statement = dbProvider.getConnection().createStatement())
        {
            statement.executeUpdate(sql);
            status = "OK";
            message = "Table created";
        }
        catch (SQLException e)
        {
            status = "error";
            message = e.getMessage();
        }

        JsonObject result = new JsonObject();
        result.addProperty("status", status);
        result.addProperty("message", message);
        resp.getWriter().print(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String contentType = req.getContentType();
        if(contentType == null || !contentType.startsWith("application/json"))
        {
            resp.setStatus(415);
            resp.getWriter().println("\"Unsupported media type : 'application/json' only \"");
            return;
        }
        // insert - додати дані до БД. Дані передаються як JSON у тілі запиту
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len ;
        String json ;
        JsonObject result = new JsonObject() ;
        try( InputStream body = req.getInputStream() )
        {
            while( ( len = body.read( buffer ) ) > 0 )
            {
                bytes.write( buffer, 0, len );
            }
            json = bytes.toString( StandardCharsets.UTF_8.name() );
            JsonObject data = JsonParser.parseString( json ).getAsJsonObject();
            result.addProperty("name", data.get("name").getAsString() );
            result.addProperty("phone", data.get("phone").getAsString() );
        }
        catch(IOException | IllegalStateException ex)
        {
            result.addProperty("message", ex.getMessage() ) ;
        }
        resp.getWriter().print( result.toString() ) ;
    }


    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //метод который запускается перед тем, как будет произведено "разпределение" HTTP методами
        // тут можно добавить реакцию на дополнительные методы запроса
        switch (req.getMethod().toUpperCase()){
            case "PATCH": doPatch(req, resp); break;
            //case "PURGE": break;
            //case "LINK": break;
            //case "UNLINK": break;
            case "COPY": doCopy(req, resp); break;
            //case "MOVE": break;
            default: super.service(req, resp);
        }
    }

    protected void doCopy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<CallMe> calls = callMeDao.getAll();

                //= new ArrayList<>();
//        calls.add(new CallMe("100500", "Qwerty", "+380876432612", new Date()));
//        calls.add(new CallMe("100501", "Aswd", "+380264562854", new Date()));

        Gson gson = new GsonBuilder().create();
        resp.getWriter().print(gson.toJson(calls));
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.getWriter().print("Patch works");
    }

}
