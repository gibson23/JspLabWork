package ua.hypson.jdbclab.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.junit.Test;

import ua.hypson.jdbclab.dao.interfaces.RoleDao;
import ua.hypson.jdbclab.dao.interfaces.UserDao;
import ua.hypson.jdbclab.entity.Role;
import ua.hypson.jdbclab.entity.User;
import ua.hypson.jdbclab.factory.ConnectionFactory;

public class JdbcUserDaoTest extends DBTestCase {

  private final UserDao userDao;
  private final RoleDao roleDao;
  private final Properties connectionProperties;
  private static final ConnectionFactory factory;

  @Override
  protected IDataSet getDataSet() throws Exception {

    return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("preset.xml"));
  }

  static {
    factory = ConnectionFactory.getFactory("test.properties");
    Connection conn = factory.createConnection();
    Statement stmt;
    try {
      stmt = conn.createStatement();
      stmt.execute("DROP TABLE IF EXISTS ROLE");
      stmt.execute(
          "CREATE TABLE IF NOT EXISTS ROLE " + "(PK_ROLE_ID BIGINT PRIMARY KEY, NAME VARCHAR(255) NOT NULL UNIQUE)");
      stmt.execute("INSERT INTO ROLE (PK_ROLE_ID, NAME) VALUES (0, 'default')");
      stmt.execute("DROP TABLE IF EXISTS USER");
      stmt.execute("CREATE TABLE IF NOT EXISTS USER "
          + "(PK_USER_ID BIGINT PRIMARY KEY AUTO_INCREMENT, LOGIN VARCHAR(255) NOT NULL UNIQUE, PASSWORD VARCHAR(255) NOT NULL, EMAIL VARCHAR(255) NOT NULL UNIQUE,"
          + " FIRSTNAME VARCHAR(255), LASTNAME VARCHAR(255), BIRTHDAY DATE, FK_ROLE_ID BIGINT)");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public JdbcUserDaoTest(String name) {
    super(name);
    userDao = new JdbcUserDao();
    roleDao = new JdbcRoleDao();
    connectionProperties = factory.getProperties();
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
        connectionProperties.getProperty("driver"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
        connectionProperties.getProperty("url"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, connectionProperties.getProperty("username"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, connectionProperties.getProperty("password"));
  }

  @Override
  protected void setUpDatabaseConfig(DatabaseConfig config) {
    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
  }

  @Test(timeout = 100)
  public void testUserDao() throws Exception {
    User extractedUser = userDao.findByEmail("igor@igor");
    assertEquals("Igorek", extractedUser.getFirstName());
    assertEquals("igor", extractedUser.getLogin());
    assertEquals("12345", extractedUser.getPassword());
    assertEquals("Nikolaev", extractedUser.getLastName());
    assertEquals("1961-02-20", extractedUser.getBirthday().toString());

    extractedUser = userDao.findByLogin("igor");
    assertEquals("Igorek", extractedUser.getFirstName());

  }

  @Test(timeout = 100)
  public void testUserCreate() throws Exception {
    @SuppressWarnings("deprecation")
    User addingUser = User.createNewUser("gri", "poiu", "grisha2000@mail.ru", "Grigoriy", "Skovoroda",
        new Date(20, 5, 5), Role.getDefaultRole());

    userDao.create(addingUser);

    User extractedCreatedUser = userDao.findByLogin("gri");
    assertEquals("Skovoroda", extractedCreatedUser.getLastName());

    addingUser.setFirstName("Svetlana");
    userDao.update(addingUser);
    extractedCreatedUser = userDao.findByLogin("gri");
    assertEquals("Svetlana", extractedCreatedUser.getFirstName());

    userDao.remove(addingUser);
    extractedCreatedUser = userDao.findByLogin("gri");
    assertNull(extractedCreatedUser);
  }

  @Test(timeout = 100)
  public void testRoleOfUser() throws Exception {
    User user = userDao.findByLogin("vasya");
    Role role = roleDao.findByName("admin");
    user.setRole(role);
    userDao.update(user);

    IDataSet databaseDataSet = getConnection().createDataSet();
    ITable actualTable = databaseDataSet.getTable("USER");


    IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream
            ("afterset.xml"));
    ITable expectedTable = expectedDataSet.getTable("USER");

    ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData()
            .getColumns());
    Assertion.assertEquals(expectedTable, filteredTable);
  }

}
