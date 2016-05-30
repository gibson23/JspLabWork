package ua.hypson.jsplab.service;

import ua.hypson.jdbclab.dao.impl.JdbcUserDao;
import ua.hypson.jdbclab.dao.interfaces.UserDao;
import ua.hypson.jdbclab.entity.User;
import ua.hypson.jsplab.exception.InvalidUserInputException;

import java.util.List;

public class LoginService {

  private final UserDao userDao;

  public LoginService() {
    userDao = new JdbcUserDao();
  }

  public User login(String login, String password) {
    User fetched = userDao.findByLogin(login);
    if (null == fetched) {
      throw new InvalidUserInputException("Bad credentials");
    } else if (!password.equals(fetched.getPassword())) {
      throw new InvalidUserInputException("Bad credentials");
    }

    return fetched;
  }

  public List<User> getAllUsers() {
    return userDao.findAll();
  }
}
