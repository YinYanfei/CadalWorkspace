
package com.javabloger.layer.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import  com.javabloger.gen.code.Blog;
import  com.javabloger.gen.code.ThriftCase;

public class BusinessImpl implements ThriftCase.Iface {

	public int testCase1(int num1, int num2, String num3) throws TException {
		 		int i=num1+num2;
		 		System.out.print ( "testCase1  num1+num2 is :"+ i );System.out.println( "   num3 is :"+ num3 );
		 		return i;
	}

	public List<String> testCase2(Map<String, String> num1) throws TException {
		System.out.println("testCase2 num1 :"+ num1 );
		List<String> list= new ArrayList<String> ();
		list.add("num1");
		return list;
	}

	public void testCase3() throws TException {
		System.out.println ("testCase3 ..........."+new java.util.Date() );
	}

	public void testCase4(List<Blog> blogs) throws TException {
		System.out.print("testCase4 ..........."   );
		
		for (int i=0;i<blogs.size();i++){
			Blog blog=blogs.get(i) ;
			System.out.print  ("id:"+blog.getId());System.out.print  (",IpAddress:"+blog.getIpAddress() );
			System.out.print  (",Content:"+new String (blog.getContent() ));
			System.out.print  (",topic:"+blog.getTopic()  );System.out.print  (",time:"+blog.getCreatedTime() );
		}
		System.out.println ("\n");
	}

	 

	 
 
}
