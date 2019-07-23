package com.xxxx.zk_demo.zk;


import org.apache.zookeeper.AsyncCallback.ChildrenCallback;

import java.util.List;

public class ChildrenCallBack implements ChildrenCallback {

	public void processResult(int rc, String path, Object ctx, List<String> children) {
		for (String s : children) {
			System.out.println(s);
		}
		System.out.println("ChildrenCallback:" + path);
		System.out.println((String)ctx);	
	}

}
