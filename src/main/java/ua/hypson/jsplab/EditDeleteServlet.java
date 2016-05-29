package ua.hypson.jsplab;

import ua.hypson.jdbclab.dao.impl.JdbcUserDao;
import ua.hypson.jdbclab.dao.interfaces.UserDao;
import ua.hypson.jdbclab.entity.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "EditDeleteServ", description = "login servlet", urlPatterns = { "/EditDeleteServ" })
public class EditDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 15648974312L;
    private UserDao userDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditDeleteServlet() {
        super();
        userDao = new JdbcUserDao();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession();
        String editingUserLogin = request.getParameter("edit");
        String deletingUserLogin = request.getParameter("delete");
        if(editingUserLogin != null) {
            User editingUser = userDao.findByLogin(editingUserLogin);
            session.setAttribute("editingUser", editingUser);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        } else if(deletingUserLogin != null) {
            User deletingUser = userDao.findByLogin(deletingUserLogin);
            userDao.remove(deletingUser);
            request.getRequestDispatcher("LoginServ").forward(request, response);
        }
    }
}
