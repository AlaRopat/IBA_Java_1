package by.iba.web.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import by.iba.web.entity.Role;
import by.iba.web.entity.User;

@WebFilter(urlPatterns = "/go_to_person.do")
public class LoginRequiredFilter implements Filter {

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws ServletException, IOException {

    HttpServletRequest request = (HttpServletRequest) req;
    User user = (User) request.getSession().getAttribute("user");
    if (Role.ADMIN.equals(user.getRole())) {
      chain.doFilter(req, resp);
    } else {
      request.getRequestDispatcher("login.do").forward(req, resp);
    }
  }
}
