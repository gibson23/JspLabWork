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

import ua.hypson.jdbclab.dao.impl.JdbcRoleDao;
import ua.hypson.jdbclab.dao.impl.JdbcUserDao;
import ua.hypson.jdbclab.dao.interfaces.RoleDao;
import ua.hypson.jdbclab.dao.interfaces.UserDao;
import ua.hypson.jdbclab.entity.Role;
import ua.hypson.jdbclab.entity.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "CreatorServ", description = "servlet for edit or create users", urlPatterns = { "/CreatorServ" })
public class CreatorServlet extends HttpServlet {
    private static final long serialVersionUID = 14328974312L;
    private UserDao userDao;
    private RoleDao roleDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatorServlet() {
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
        Role role = roleDao.findByName(request.getParameter("role"));
        User creatingUser = User.createUser(User.counter, request.getParameter("login"),
                request.getParameter("password"), request.getParameter("email"), request.getParameter("firstName"),
                request.getParameter("lastName"), parseDate(request.getParameter("birthday")), role);
        userDao.create(creatingUser);
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
