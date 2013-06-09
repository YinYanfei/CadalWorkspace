package com.org.xiao;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;


public class Server {
	public void StartServer(int method,String[] args){
		TTransport transport;
		try {
			// transport=new TSocket("localhost",8585);
			transport=new TSocket("10.15.62.107",8585);
			System.out.println("1");
			TProtocol protocol = new TBinaryProtocol(transport);
			System.out.println("2");
			Recommend.Client clients=new Recommend.Client(protocol);
			System.out.println("3");
			transport.open();
			System.out.println("4");
			switch(method){
			case 1: 
				if(args.length<2||args[1].equals("")){printError();transport.close();return;}
				String temp=clients.recommendActiveUserByBook(args[1]);
				System.out.println(temp.length());
				System.out.println(temp);			//
				/*
				for(int i=0;i<temp.size();i++){
					System.out.println(temp.get(i));
				}
				*/
				break;
			case 2:
				if(args.length<2||args[1].equals("")){printError();transport.close();return;}
				String temp1=clients.recommendActiveUserByInterest(args[1]);
				System.out.println(temp1);			//
				/*
				for(int i=0;i<temp1.size();i++){
					System.out.println(temp1.get(i));
				}
				*/
				break;
			case 3:
				if(args.length<2||args[1].equals("")){printError();transport.close();return;}
				String temp2=clients.recommendByInterest(args[1]);
				System.out.println(temp2);			//
				/*
				for(int i=0;i<temp2.size();i++){
					System.out.println(temp2.get(i).username+":"+temp2.get(i).school);
				}
				*/
				
				break;
			case 4:
				System.out.println("start to update data... It need a few minutes...");
				if(args.length<4){printError();transport.close();return;}
				String arg1=null,arg2=null,arg3=null;

				// 此处代码不对
				if(args[1].equals("null"))
					arg1=null;
				else
					arg1 = args[1];
				if(args[2].equals("null"))
					arg2=null;
				else
					arg2 = args[2];
				if(args[3].equals("null"))
					arg3=null;
				else
					arg3 = args[3];
				if(arg1==null&&arg2==null&&arg3==null){
					System.out.println("all input is null,exit directly...");
					transport.close();
					return;
				}
				System.out.println(clients.update(args[1], args[2], args[3]));
				break;
			}
			transport.close();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		int method=-1;
		if(args[0].equals("-book"))
			method=1;
		else if(args[0].equals("-topic"))
			method=2;
		else if(args[0].equals("-user"))
			method=3;
		else if(args[0].equals("-update"))
			method=4;
		else{
			printError();
			return;
		}
		Server server=new Server();
		server.StartServer(method,args);
	}
	public static void printError(){
		System.out.println("Error:\nUsage:recommendClient [-book|-user|-topic|-update] [args]");
		System.out.println("example:\n1:recommendClient -book bookid");
		System.out.println("\n2:recommendClient -user username");
		System.out.println("\n3:recommendClient -topic topic_name");
		System.out.println("\n4:recommendClient -update [address of accesslog.csv,if no accesslog file,please type null] [address of bookinfo.csv,if no bookinfo.csv file,please type null] [address of UserInfo.csv,if no UserInfo.csv file,please type null]");
		System.out.println("\nlike:recommendClient -update \\home\\aa\\accesslog.csv null null");
	}
}
