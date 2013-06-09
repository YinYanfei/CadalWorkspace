package collabstream.streaming;
//预定义的消息，表示不同的tuple类型
public enum MsgType {
	END_OF_DATA, //数据结束
	TRAINING_EXAMPLE, //训练样本
	PROCESS_BLOCK_REQ, //
	PROCESS_BLOCK_FIN,
	USER_BLOCK_REQ,
	ITEM_BLOCK_REQ,
	USER_BLOCK,
	ITEM_BLOCK,
	USER_BLOCK_SAVED,
	ITEM_BLOCK_SAVED
}