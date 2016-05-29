package ua.hypson.jdbclab.factory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.hypson.jdbclab.dao.interfaces.AbstractJdbcDao;

public class ConnectionFactory extends AbstractJdbcDao {

  private static BasicDataSource dataSource;
  private static Properties properties;
  private static String propertiesPath;
  private static ConnectionFactory factory;

  private static void setPropertiesPath(String path) {
    propertiesPath = path;
  }

  public static ConnectionFactory getFactory(String path) {
    if (factory == null) {
      factory = new ConnectionFactory();
      setPropertiesPath(path);
    }
    return factory;
  }

  public static ConnectionFactory getFactory() {
    if (factory == null) {
      factory = new ConnectionFactory();
    }
    return factory;
  }

  private void loadProperties() {

    Properties prop = new Properties();
    try {
      prop.load(this.getClass().getClassLoader().getResourceAsStream(propertiesPath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    properties = prop;
  }

  public Properties getProperties() {
    return properties;
  }

  private void initializeDataSource() {
    loadProperties();
    dataSource = new BasicDataSource();
    dataSource.setDriverClassName(properties.getProperty("driver"));
    dataSource.setUsername(properties.getProperty("username"));
    dataSource.setPassword(properties.getProperty("password"));
    dataSource.setUrl(properties.getProperty("url"));
  }

  @Override
  public Connection createConnection() {
    if (dataSource == null) {
      initializeDataSource();
    }
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
