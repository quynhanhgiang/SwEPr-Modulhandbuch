package de.hscoburg.modulhandbuchbackend;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@SpringBootApplication
@ConfigurationPropertiesScan("de.hscoburg.modulhandbuchbackend.properties")
public class ModulhandbuchBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ModulhandbuchBackendApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ModulhandbuchBackendApplication.class);
    }

    /*@Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {

            protected TomcatWebServer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("java:comp/env/jdbc/modulhandbuch_dev_db");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.mariadb.jdbc.driver");
                resource.setProperty("url", "jdbc:mariadb//localhost:3306/swepr_test");
                resource.setProperty("username", "admin");
                resource.setProperty("password", "password");
                context.getNamingResources().addResource(resource);
            }
        };
    }*/
}
