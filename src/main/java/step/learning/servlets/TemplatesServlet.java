package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class TemplatesServlet extends HttpServlet
{
    private static byte[] buffer = new byte[16284];
    private final Logger logger;
    @Inject
    public TemplatesServlet(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

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
}
