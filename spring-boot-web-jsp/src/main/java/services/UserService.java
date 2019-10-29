package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Dao.Dao;
import models.Role;
import models.User;




@Service
public class UserService implements UserDetailsService {
	@Autowired 
	private Dao userDao;
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		/*User u =new User();
		ArrayList<Role> rr=new ArrayList<Role>();
		rr.add(Role.ADMIN);
		u.setAuthorities(rr);
		u.setPassword("as");
		u.setUsername("t");
		userDao.saveUser(u);*/
		Optional<User> w=userDao.findByUsername(arg0);
		if (w.isPresent()) {
			return w.get();
		}else {
			return new User();
		}
		
	}

}
