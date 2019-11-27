package org.kovtun.Dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DaoConfigure {
	@Bean 
	   public Dao getUserDao() {
		   return new Dao();
	   }
	
}
