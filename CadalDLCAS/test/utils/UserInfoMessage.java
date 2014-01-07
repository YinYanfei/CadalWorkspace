package cn.edu.zju.cadal.test.utils;

import kafka.message.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//UserInfoMessage就是一个可以被kafka发送和存储的对象
public class UserInfoMessage implements kafka.serializer.Encoder<UserInfo> {

	public static final Logger LOG=LoggerFactory.getLogger(UserInfo.class);
	
	@Override
	public Message toMessage(UserInfo words) {
//		LOG.info("start in encoding... ");
		return new Message(words.toString().getBytes());
	}
}
