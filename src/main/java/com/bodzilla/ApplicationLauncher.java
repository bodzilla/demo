package com.bodzilla;

import com.bodzilla.context.ApplicationConfiguration;
import javax.servlet.ServletContext;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {

  public static void main(String[] args) throws LifecycleException {
    var tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.getConnector();

    var tomcatCtx = tomcat.addContext("", null);

    var applicationContext = createApplicationContext(tomcatCtx.getServletContext());
    var dispatcherServlet = new DispatcherServlet(applicationContext);
    var servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/*");

    tomcat.start();
  }

  public static WebApplicationContext createApplicationContext(ServletContext servletContext) {
    var ctx = new AnnotationConfigWebApplicationContext();
    ctx.register(ApplicationConfiguration.class);
    ctx.setServletContext(servletContext);
    ctx.refresh();
    ctx.registerShutdownHook();
    return ctx;
  }
}
