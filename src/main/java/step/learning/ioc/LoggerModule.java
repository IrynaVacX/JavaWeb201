package step.learning.ioc;

import com.google.inject.AbstractModule;

import java.io.IOException;
import java.io.InputStream;

public class LoggerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        try(InputStream properties = this.getClass().getClassLoader()
                .getResourceAsStream("logging.properties"))
        {

        }
        catch (IOException ex)
        {
            System.err.println("Logger config error : " + ex.getMessage());
        }
    }
}
