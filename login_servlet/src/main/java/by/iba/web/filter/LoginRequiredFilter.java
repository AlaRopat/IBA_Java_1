package by.iba.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter(urlPatterns = "/go_to_person.do")
public class LoginRequiredFilter implements Filter {
  public void destroy() {}

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    if (request.getSession().getAttribute("user")=="admin") {
      chain.doFilter(req, resp);
    } else {
      request.getRequestDispatcher("login.do").forward(req, resp);
    }
  }

  public void init(FilterConfig config) throws ServletException {}
}
