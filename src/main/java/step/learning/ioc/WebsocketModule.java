package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import step.learning.ws.WebsocketConfigurator;

public class WebsocketModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        requestStaticInjection(WebsocketConfigurator.class);
    }
}
