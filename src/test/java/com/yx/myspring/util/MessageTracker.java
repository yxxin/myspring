package com.yx.myspring.util;

import java.util.ArrayList;
import java.util.List;

public abstract class MessageTracker {
	private static List<String> MESSAGE=new ArrayList<String>();
	public static List<String> getMessage(){
		return MESSAGE;
	}
	public static void addMessage(String message) {
		MESSAGE.add(message);
	}
	public static void clearMessage() {
		MESSAGE.clear();
	}
}
