package com.test;

import java.util.HashMap;
import java.util.Map;


public class ThriftCaseImpl implements ThriftCase.Iface{

	public UserResponse integralService(UserRequest request)  {
		try{
			UserResponse urp=new UserResponse();
			if(request.identitycard.equals("32010619881231103X")){
				urp.setCode("0");
				Map params=new HashMap();
				params.put("integral", "10");
				urp.setParams(params);

			}
			System.out.print("接收参数是：identitycard="+request.identitycard);
			return urp;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
