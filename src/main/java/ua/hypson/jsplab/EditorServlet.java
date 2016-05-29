package ua.hypson.jsplab;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "EditorServ", description = "servlet for edit users", urlPatterns = { "/EditorServ" })
public class EditorServlet extends HttpServlet {
  private static final long serialVersionUID = 15648974312L;
  private UserDao userDao;
  private RoleDao roleDao;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EditorServlet() {
    super();
    userDao = new JdbcUserDao();
    roleDao = new JdbcRoleDao();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    response.getWriter().append("Served at: doGet:").append(request.getContextPath());
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    User editingUser = (User) session.getAttribute("editingUser");
    Role role = editingUser.getRole();
      if (!role.getName().equals(request.getParameter("role"))) {
        role = roleDao.findByName(request.getParameter("role"));
      }
      User updatingUser = User.createUser(editingUser.getId(), editingUser.getLogin(),
          request.getParameter("password"), request.getParameter("email"), request.getParameter("firstName"),
          request.getParameter("lastName"), parseDate(request.getParameter("birthday")), role);
      userDao.update(updatingUser);
    request.getRequestDispatcher("LoginServ").forward(request, response);
  }

  /**
   *
   * @param dateString
   *          String in format yyyy-MM-dd
   * @return java.sql.Date object parsed out of given string
   */
  private Date parseDate(String dateString) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date parsed;
    try {
      parsed = format.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return new java.sql.Date(parsed.getTime());
  }
}
