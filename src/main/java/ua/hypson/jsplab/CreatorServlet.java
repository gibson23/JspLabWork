package ua.hypson.jsplab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet(name = "CreatorServ", description = "servlet for edit or create users", urlPatterns = { "/CreatorServ" })
public class CreatorServlet extends HttpServlet {
    private static final long serialVersionUID = 14328974312L;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final DateUtils dateUtils;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatorServlet() {
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
        Role role = roleDao.findByName(request.getParameter("role"));
        User creatingUser = User.createNewUser(request.getParameter("login"),
                request.getParameter("password"), request.getParameter("email"), request.getParameter("firstName"),
                request.getParameter("lastName"), dateUtils.parseDate(request.getParameter("birthday")), role);
        userDao.create(creatingUser);
        request.getRequestDispatcher("LoginServ").forward(request, response);
    }

}
