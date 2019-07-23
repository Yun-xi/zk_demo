package com.xxxx.zk_demo.zk;


import org.apache.zookeeper.AsyncCallback.VoidCallback;

public class DeleteCallBack implements VoidCallback {

	public void processResult(int i, String s, Object o) {
		System.out.println("删除节点" + s);
		System.out.println((String)o);
	}
}
