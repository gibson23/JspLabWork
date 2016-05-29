package ua.hypson.jsplab;

import ua.hypson.jdbclab.entity.User;
import ua.hypson.jsplab.service.BirthdayToAgeConverter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.List;

/**
 * Tag that turns list of users into html table with edit and delete buttons
 * Created by admin on 27.05.2016.
 */
public class MyCustomTag implements Tag {

    private PageContext pageContext;
    private Tag parent;
    private List<User> list;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public void setPageContext(PageContext pc) {
        this.pageContext = pc;
    }

    @Override
    public void setParent(Tag t) {
        this.parent = t;
    }

    @Override
    public Tag getParent() {
        return parent;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter jspWriter = pageContext.getOut();
        BirthdayToAgeConverter ageConverter = new BirthdayToAgeConverter();

        try {
            jspWriter.println("<table border=\"1\" style=\"width:100%\">\n" +
                    "  <tr>\n" +
                    "    <td>Login</td>\n" +
                    "    <td>First Name</td> \n" +
                    "    <td>Last Name</td>\n" +
                    "    <td>Age</td>\n" +
                    "    <td>Role</td>\n" +
                    "    <td>Actions</td>\n" +
                    "  </tr>\n");
            for (User user : list) {
                jspWriter.println("  <tr>\n");
                jspWriter.println("    <td>" + user.getLogin() + "</td>\n");
                jspWriter.println("    <td>" + user.getFirstName() + "</td>\n");
                jspWriter.println("    <td>" + user.getLastName() + "</td>\n");
                jspWriter.println("    <td>" + ageConverter.convert(user.getBirthday()) + "</td>\n");
                jspWriter.println("    <td>" + user.getRole().getName() + "</td>\n");
                jspWriter.println("    <td>" + "<form action=\"EditDeleteServ\" method=\"post\">\n" +
                        "    <button name=\"edit\" value=\"" + user.getLogin() + "\">Edit</button>\n" +
                        "</form>");
                jspWriter.println("<form action=\"EditDeleteServ\" method=\"post\">\n" +
                        "    <button onclick=\"return confirm('Are you sure?')\" name=\"delete\" value=\""
                        + user.getLogin() + "\">Delete</button>\n" +
                        "</form>" + "</td>\n");
                jspWriter.println("  </tr>\n");
            }
            jspWriter.println("<table>");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {

    }
}
