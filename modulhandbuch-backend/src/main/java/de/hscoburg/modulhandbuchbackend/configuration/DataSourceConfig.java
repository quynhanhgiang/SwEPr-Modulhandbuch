package de.hscoburg.modulhandbuchbackend.configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Value("${spring.datasource.jndi-name}")
	private String jndi;

	@Value("${spring.datasource.url-suffix}")
	private String suffix;
	
	@Bean
	public DataSource getDataSource() throws IOException, SQLException {
		InitialContext context = null;
		DataSource dataSource = null;
		
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup(this.jndi);
			DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
			System.out.println(dataSource.getConnection().getMetaData().getURL() + this.suffix);
			dataSourceBuilder.url(dataSource.getConnection().getMetaData().getURL() + this.suffix);
			return dataSourceBuilder.build();
		} catch (NamingException exception) {
			System.out.println("In catch");
			exception.printStackTrace();
		}

		return null;
	}
}
