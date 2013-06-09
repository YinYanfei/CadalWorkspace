package thrift.demo.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import thrift.demo.gen.User;
import thrift.demo.gen.UserNotFound;
import thrift.demo.gen.UserService.Iface;

public class UserServerHandler implements Iface{

	public User getUserByName(String username) throws UserNotFound, TException {
		if("user1".equals(username)) {
			User user = new User();
			
			user.setId(1);
			user.setUsername("user1");
			user.setPassword("pwd1");
			
			return user;
		}else{
			throw new UserNotFound();
		}
	}

	public List<User> getUsers() throws TException {
		List<User> list = new ArrayList<User>();
		
		User u1 = new User();
		u1.setId(1);
		u1.setUsername("user1");
		u1.setPassword("pwd1");
		User u2 = new User();
		u2.setId(2);
		u2.setUsername("user2");
		u2.setPassword("pwd2");
		
		list.add(u1);
		list.add(u2);
		
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
