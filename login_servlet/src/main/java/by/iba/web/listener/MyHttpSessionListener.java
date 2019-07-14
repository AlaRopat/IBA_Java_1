package by.iba.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

  public void sessionCreated(HttpSessionEvent event) {
    event.getSession().setMaxInactiveInterval(15 * 6); // in seconds
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {

  }
}
