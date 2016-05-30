package ua.hypson.jsplab;

import java.io.IOException;

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
import ua.hypson.jsplab.service.DateUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "EditorServ", description = "servlet for edit users", urlPatterns = { "/EditorServ" })
public class EditorServlet extends HttpServlet {
  private static final long serialVersionUID = 15648974312L;
  private final UserDao userDao;
  private final RoleDao roleDao;
  private final DateUtils dateUtils;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EditorServlet() {
    super();
    userDao = new JdbcUserDao();
    roleDao = new JdbcRoleDao();
    dateUtils = new DateUtils();
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
      User updatingUser = User.createNewUser(editingUser.getLogin(),
          request.getParameter("password"), request.getParameter("email"), request.getParameter("firstName"),
          request.getParameter("lastName"), dateUtils.parseDate(request.getParameter("birthday")), role);
      userDao.update(updatingUser);
    request.getRequestDispatcher("LoginServ").forward(request, response);
  }

}
