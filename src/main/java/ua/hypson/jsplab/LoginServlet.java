package ua.hypson.jsplab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.hypson.jdbclab.dao.impl.JdbcRoleDao;
import ua.hypson.jdbclab.dao.impl.JdbcUserDao;
import ua.hypson.jdbclab.dao.interfaces.RoleDao;
import ua.hypson.jdbclab.dao.interfaces.UserDao;
import ua.hypson.jdbclab.entity.Role;
import ua.hypson.jdbclab.entity.User;
import ua.hypson.jdbclab.factory.ConnectionFactory;
import ua.hypson.jsplab.service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServ", description = "login servlet", urlPatterns = { "/LoginServ" })
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 789456123L;
  private LoginService loginService;

  static {
    ConnectionFactory factory = ConnectionFactory.getFactory("h2.properties");
    Connection conn = factory.createConnection();
    Statement stmt;
    RoleDao roleDao = new JdbcRoleDao();
    UserDao userDao = new JdbcUserDao();
    try {
      stmt = conn.createStatement();
        stmt.execute("DROP TABLE IF EXISTS ROLE");
        stmt.execute("DROP TABLE IF EXISTS USER");
      stmt.execute(
          "CREATE TABLE IF NOT EXISTS ROLE " + "(PK_ROLE_ID BIGINT PRIMARY KEY, NAME VARCHAR(255) NOT NULL UNIQUE)");

      stmt.execute("CREATE TABLE IF NOT EXISTS USER "
          + "(PK_USER_ID BIGINT PRIMARY KEY, LOGIN VARCHAR(255) NOT NULL UNIQUE, PASSWORD VARCHAR(255) NOT NULL, EMAIL VARCHAR(255) NOT NULL UNIQUE,"
          + " FIRSTNAME VARCHAR(255), LASTNAME VARCHAR(255), BIRTHDAY DATE, FK_ROLE_ID BIGINT)");
      Role adminRole = new Role();
      adminRole.setId(1L);
      adminRole.setName("admin");

      Role userRole = new Role();
      userRole.setId(2L);
      userRole.setName("regular");
      roleDao.create(adminRole);
      roleDao.create(userRole);
      @SuppressWarnings("deprecation")
      User admin = User.createUser(User.counter, "admin", "admin", "admin@my.app", "Vasiliy", "Zhar",
          new Date(87, 7, 23), adminRole);
      User vika = User.createUser(User.counter, "vika", "vika", "vika@my.app", "Vika", "Zhar", new Date(97, 7, 23),
          userRole);
      User masha = User.createUser(User.counter, "masha", "masha", "masha@my.app", "Masha", "Zhar", new Date(85, 7, 23),
          userRole);
      User inna = User.createUser(User.counter, "inna", "inna", "inna@my.app", "Inna", "Zhar", new Date(77, 7, 23),
          userRole);
      userDao.create(admin);
      userDao.create(inna);
      userDao.create(vika);
      userDao.create(masha);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * @see HttpServlet#HttpServlet()
   */
  public LoginServlet() {
    super();
    loginService = new LoginService();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    if (session.getAttribute("user") == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "no logged user for this session");

    }
    User user = (User) session.getAttribute("user");

    if (user.getRole().getName().equals("admin")) {
      List<User> users = loginService.getAllUsers();
      request.setAttribute("users", users);
      request.getRequestDispatcher("adminhome.jsp").forward(request, response);
    } else {
      request.getRequestDispatcher("success.jsp").forward(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    User user;
    if (session.getAttribute("user") == null) {
      user = loginService.login(request.getParameter("login"), request.getParameter("password"));
      session.setAttribute("user", user);
    } else {
      user = (User) session.getAttribute("user");
    }
    if (user.getRole().getName() == "admin") {
      List<User> users = loginService.getAllUsers();
      request.setAttribute("users", users);
      request.getRequestDispatcher("adminhome.jsp").forward(request, response);
    } else {
      request.getRequestDispatcher("success.jsp").forward(request, response);
    }
  }
}
