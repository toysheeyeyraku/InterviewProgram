package org.kovtun.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Configuration
public class DaoConfigure {
	@Bean
	public Dao getUserDao() {
		return new Dao();
	}

}
