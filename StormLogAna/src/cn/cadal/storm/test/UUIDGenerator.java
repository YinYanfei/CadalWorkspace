package cn.cadal.storm.test;

import java.util.UUID;

public class UUIDGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UUID uuid_1 = UUID.randomUUID();
		System.out.println("UUID one: " + uuid_1);

		UUID uuid_2 = UUID.randomUUID();
		System.out.println("UUID two: " + String.valueOf(uuid_2));
	}

}
