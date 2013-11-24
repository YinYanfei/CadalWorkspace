package cn.edu.zju.cadal.kafka.message;

import kafka.message.Message;

//BookViewMessage就是一个可以被kafka发送和存储的对象
public class BookViewMessage implements kafka.serializer.Encoder<BookView> {

	@Override
	public Message toMessage(BookView bookView) {
		return new Message(bookView.toString().getBytes());
	}

}
