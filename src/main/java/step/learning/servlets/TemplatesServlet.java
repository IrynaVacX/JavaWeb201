package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.dao.AuthTokenDao;
import step.learning.dto.entities.AuthToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class TemplatesServlet extends HttpServlet
{
    private static byte[] buffer = new byte[16284];
    private final Logger logger;
    private final AuthTokenDao authTokenDao;
    @Inject
    public TemplatesServlet(Logger logger, AuthTokenDao authTokenDao) {
        this.logger = logger;
        this.authTokenDao = authTokenDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
//        hw

        // cw 27/10/23
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null)
        {
            sendResponse(resp, 400, "'Authorization' header required");
            return;
        }
        String authScheme = "Bearer ";
        if (!authHeader.startsWith(authScheme))
        {
            sendResponse(resp, 400, "'Bearer' scheme required");
            return;
        }
        String token = authHeader.substring(authScheme.length());
        AuthToken authToken = authTokenDao.getTokenByBearer(token);
//        String tokenErrorMessage = authTokenDao.checkToken(token);
        if (authToken == null)
        {
            sendResponse(resp, 403, "Token rejected");
            return;
        }

        /////////////////////////////////////////////
        String requestedTemplate = req.getPathInfo();
        URL url = this.getClass().getClassLoader()
                        .getResource("tpl" + requestedTemplate);

        File file;
        if (url == null || !(file = new File(url.getFile())).isFile())
        {
            resp.setStatus(404);
            return;
        }
        try(InputStream fileStream = Files.newInputStream(file.toPath()))
        {
            int bytesRead;
            resp.setContentType(
                    URLConnection
                            .getFileNameMap()
                            .getContentTypeFor(requestedTemplate)
            );
            OutputStream respStream = resp.getOutputStream();
            while((bytesRead = fileStream.read(buffer)) > 0)
            {
                respStream.write(buffer, 0, bytesRead);
            }
            respStream.close();
        }
        catch (IOException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage());
            resp.setStatus(500);
        }
    }

    private void sendResponse(HttpServletResponse resp, int status, String body) throws IOException
    {
        resp.setContentType("text/plain");
        resp.setStatus(status);
        resp.getWriter().print(body);
    }

}

