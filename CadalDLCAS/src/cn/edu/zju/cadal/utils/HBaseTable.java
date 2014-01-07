package cn.edu.zju.cadal.utils;

public class HBaseTable {
	/**
	 * Read region
	 */
	public static String READ_INFO = "read_info";
	public static String READ_INFO_IP = "read_info_ip";
	public static String READ_INFO_USER = "read_info_user";
	public static String READ_INFO_BOOK = "read_info_book";
	public static String READ_COMMENT = "read_comment";
	public static String READ_TAG = "read_tag";
	
	/**
	 * Search region
	 */
	public static String SEARCH_TERM = "search_term";
	public static String SEARCH_CLICK = "search_click";
	
	/**
	 * Personal region
	 */
	public static String PERSONAL_FOLLOW = "personal_follow";
	public static String PERSONAL_REPLY = "personal_reply";
	public static String PERSONAL_SHARE = "personal_share";
	public static String PERSONAL_VISIT = "personal_visit";
	public static String PERSONAL_BUTTON = "personal_button";
	
	/**
	 * Recommand region
	 */
	public static String REC_BOOK = "rec_book";
	public static String REC_USER = "rec_user";
	public static String REC_TAG = "rec_tag";
	public static String REC_TAG_BOOK = "rec_tag_book";
}
