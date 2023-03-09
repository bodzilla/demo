package com.bodzilla;

import com.bodzilla.web.Servlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class ApplicationLauncher {

    public static void main(String[] args) throws LifecycleException {
        var tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        var context = tomcat.addContext("", null);
        var servlet = Tomcat.addServlet(context, "servlet", new Servlet());
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }
}
