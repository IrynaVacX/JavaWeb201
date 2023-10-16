package step.learning.services.db;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class PlanetDbProvider implements DbProvider
{
    private Connection connection;

    @Override
    public Connection getConnection()
    {
        if(connection == null)
        {
            // load configuration
            // local file
            JsonObject config;
            try (Reader reader = new InputStreamReader(
                    Objects.requireNonNull(
                            this.getClass().getClassLoader()
                            .getResourceAsStream("db_config.json"))))
            {
                config = JsonParser.parseReader(reader).getAsJsonObject();
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }

            JsonObject planetScale = config
                    .get("DataProviders")
                    .getAsJsonObject()
                    .get("PlanetScale")
                    .getAsJsonObject();

            try
            {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                this.connection = DriverManager.getConnection(
                        planetScale.get("url").getAsString(),
                        planetScale.get("user").getAsString(),
                        planetScale.get("password").getAsString()
                );

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return this.connection;
    }
}
