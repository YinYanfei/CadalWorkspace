package cn.edu.zju.cadal.kafka.message;

import kafka.message.Message;

//BookViewMessage����һ�����Ա�kafka���ͺʹ洢�Ķ���
public class BookViewMessage implements kafka.serializer.Encoder<BookView> {

	@Override
	public Message toMessage(BookView bookView) {
		return new Message(bookView.toString().getBytes());
	}

}
