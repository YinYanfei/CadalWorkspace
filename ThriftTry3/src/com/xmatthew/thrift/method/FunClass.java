package com.xmatthew.thrift.method;

import java.nio.ByteBuffer;

import org.apache.thrift.TException;

public class FunClass {

	/**
	 * Functions
	 */
	public void add(String key, ByteBuffer value) throws TException {
		System.out.println("invoke 'add'(" + key + ", " + new String(value.array()) + ")");
	}

	public ByteBuffer get(String key) throws TException {
		System.out.println("invoke 'set'(" + key + ")");
		ByteBuffer bb = ByteBuffer.wrap("get success".getBytes());
		return bb;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
