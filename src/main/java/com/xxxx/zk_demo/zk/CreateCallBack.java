package com.xxxx.zk_demo.zk;


import org.apache.zookeeper.AsyncCallback.StringCallback;

public class CreateCallBack implements StringCallback {

	public void processResult(int i, String s, Object o, String s1) {
		System.out.println("创建节点: " + s);
		System.out.println((String)o);
	}
}
