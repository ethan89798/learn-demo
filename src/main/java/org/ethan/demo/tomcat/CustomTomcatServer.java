package org.ethan.demo.tomcat;


import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTomcatServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomTomcatServer.class);

    public static void main(String[] args) throws LifecycleException {
        String classPath = System.getProperty("user.dir");
        LOGGER.info(classPath);

        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(9090);

        Connector connector = tomcat.getConnector();
        connector.setPort(6789);

        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        Context context = tomcat.addContext(host, "/", classPath);
        if (context instanceof StandardContext) {
//            StandardContext standardContext = (StandardContext) context;
//            standardContext.setDefaultWebXml("");

        }
        Wrapper wrapper = tomcat.addServlet("/", "demoServlet", new DemoServlet());
        wrapper.addMapping("/demo");

        tomcat.start();
        // 防止main线程死掉整个程序死掉
        tomcat.getServer().await();
    }
}
