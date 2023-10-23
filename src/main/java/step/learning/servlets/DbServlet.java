package step.learning.servlets;

import com.google.gson.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.CallMeDao;
import step.learning.dto.entities.CallMe;
import step.learning.services.db.DbProvider;
import step.learning.services.formparse.FormParseResult;
import step.learning.services.formparse.FormParseService;

import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Singleton
public class DbServlet extends HttpServlet
{
    private final FormParseService formParseService;
    private final DbProvider dbProvider;
    private final String dbPrefix;
    private final CallMeDao callMeDao;

    @Inject
    public DbServlet(FormParseService formParseService, DbProvider dbProvider, @Named("db-prefix") String dbPrefix, CallMeDao callMeDao)
    {
        this.formParseService = formParseService;
        this.dbProvider = dbProvider;
        this.dbPrefix = dbPrefix;
        this.callMeDao = callMeDao;
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //метод который запускается перед тем, как будет произведено "разпределение" HTTP методами
        // тут можно добавить реакцию на дополнительные методы запроса
        switch (req.getMethod().toUpperCase())
        {
            case "PATCH":
            {
                doPatch(req, resp);
                break;
            }
            case "COPY":
            {
                doCopy(req, resp);
                break;
            }
            case "POST":
            {
                doPost(req, resp);
                break;
            }
            case "LINK":
            {
                doLink(req, resp);
                break;
            }
            default: super.service(req, resp);
        }
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
        try (Statement statement = dbProvider.getConnection().createStatement())
        {
            String sql = "SELECT * FROM " + dbPrefix + "call_me";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                calls.add(new CallMe(resultSet));
            }
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }

        req.setAttribute("calls", calls);

        req.setAttribute("page-body", "db.jsp");
        req.getRequestDispatcher("WEB-INF/_layout.jsp")
                .forward(req, resp);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String status;
        String message;
        String sql = "CREATE TABLE " + dbPrefix + "call_me(" +
                "`id`             BIGINT UNSIGNED PRIMARY KEY," +
                "`name`           VARCHAR(64) NULL," +
                "`phone`          CHAR(13)    NOT NULL COMMENT '+38 098 765 43 21'," +
                "`moment`         DATETIME    DEFAULT CURRENT_TIMESTAMP" +
                "`call_moment`    DATETIME    NULL" +
                "`delete_moment`  DATETIME    NULL" +
                ") ENGINE = InnoDB DEFAULT CHARSET = UTF8";
        try (Statement statement = dbProvider.getConnection().createStatement()){
            statement.executeUpdate(sql);
            status = "OK";
            message = "Table created";
        } catch (SQLException e) {
            status = "error";
            message = e.getMessage();
        }
        JsonObject result = new JsonObject();
        result.addProperty("status",status);
        result.addProperty("message",message);
        resp.getWriter().print(result.toString());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        String contentType = req.getContentType();
        if(contentType == null || !contentType.startsWith("application/json"))
        {
            resp.setStatus(415);
            resp.getWriter().print("\"Unsupported Media Type: 'application/json' only\"");
            return;
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        String json;
        JsonObject result = new JsonObject();
        try(InputStream body = req.getInputStream())
        {
            while((len = body.read(buffer)) > 0)
            {
                bytes.write(buffer,0,len);
            }
            json = bytes.toString(StandardCharsets.UTF_8.name());
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
            resp.setStatus(500);
            resp.getWriter().print("\"Server error. Details on server's logs \"");
            return;
        }
        JsonObject data;

        try
        {
            data = JsonParser.parseString(json).getAsJsonObject();
        }
        catch (JsonSyntaxException | IllegalStateException ex)
        {
            resp.setStatus(400);
            resp.getWriter().print("\"Invalid JSON. Object required \"");
            return;
        }

        String name,phone;
        try
        {
            name = data.get("name").getAsString();
            phone = data.get("phone").getAsString();
        }
        catch (Exception ignored){
            resp.setStatus(400);
            resp.getWriter().print("\"Invalid JSON data: required 'name' and 'phone' fields \"");
            return;
        }

        if(name.length() == 0)
        {
            resp.setStatus(400);
            resp.getWriter().print("\"Invalid 'name' field: the field must not be empty");
        }
        if (!Pattern.matches("^\\+380\\d{9}$","+" + phone))
        {
            resp.setStatus(400);
            resp.getWriter().print("\"Invalid 'phone' field: required '+\\d{12}' format\"");
        }
        if(resp.getStatus() == 400)
        {
            return;
        }
        phone = ("+"+phone).replaceAll("[\\s()-]+","");

        String sql = "INSERT INTO " + dbPrefix + "call_me ( id, name, phone ) " +
                "VALUES ( UUID_SHORT(), ?, ? )" ;
        try (PreparedStatement prep = dbProvider.getConnection().prepareStatement(sql))
        {
            prep.setString(1,name);     // ! у JDBC відлік від 1
            prep.setString(2,phone);    // 2 - другий плейсхолдер "?"
            prep.execute();
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage() + " " + sql);
            resp.setStatus(500);
            resp.getWriter().print("\"Server error. Details on server's logs \"");
            return;
        }
        resp.setStatus(201);
        result.addProperty("name", name);
        result.addProperty("phone", phone);
        result.addProperty("status", "created");
        resp.getWriter().print(result);
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

    //HWHWHW-=-20-10-2023-=-
    protected void doLink(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String contentType = req.getContentType();
        if(!contentType.startsWith("application/json"))
        {
            resp.setStatus(415);
            resp.getWriter().print("\"Unsupported Media Type: 'application/json' only\"");
            return;
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        String json;

        try(InputStream body = req.getInputStream())
        {
            while ((len = body.read(buffer)) > 0)
            {
                bytes.write(buffer, 0, len);
            }
            json = bytes.toString(StandardCharsets.UTF_8.name());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            resp.setStatus(500);
            resp.getWriter().print("\"Server error\"");
            return;
        }

        String sql = "update " + dbPrefix + "call_me set call_moment=? where id=?";
        Timestamp callTimestamp;
        try(PreparedStatement statement = dbProvider.getConnection().prepareStatement(sql))
        {
            JsonObject result = JsonParser.parseString(json).getAsJsonObject();
            long id = result.get("id").getAsLong();
            callTimestamp = new Timestamp(new Date().getTime());
            statement.setTimestamp(1, callTimestamp);
            statement.setLong(2, id);
            statement.execute();
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            resp.setStatus(500);
            resp.getWriter().print("\"Server error\"");
            return;
        }

        Gson gson = new GsonBuilder().create();
        JsonObject response = new JsonObject();
        response.addProperty("timestamp", callTimestamp.getTime());

        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(gson.toJson(response));
    }
}
