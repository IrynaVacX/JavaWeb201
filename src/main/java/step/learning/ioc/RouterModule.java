package step.learning.ioc;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import step.learning.filters.*;
import step.learning.services.db.PlanetDbProvider;
import step.learning.servlets.*;

public class RouterModule extends ServletModule
{
    @Override
    protected void configureServlets()
    {
        // Третій спосіб конфігурування фільтрів та сервлетів - IoC
        filter("/*").through(CharsetFilter.class);

        serve("/"          ).with(HomeServlet.class);
        serve("/auth"      ).with(AuthServlet.class);
        serve("/jsp"       ).with(JspServlet.class);
        serve("/db"        ).with(DbServlet.class);
        serve("/filters"   ).with(FiltersServlet.class);
        serve("/ioc"       ).with(IocServlet.class);
        serve("/signup"    ).with(SignupServlet.class);
        serve("/spa"       ).with(SpaServlet.class);

    }
}
