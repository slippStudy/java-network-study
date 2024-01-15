package org.example.len.ch16_advenced.util;

import org.example.len.ch16_advenced.queue.Queue;

import java.lang.reflect.Constructor;

public class DynamicClassLoader {

	public static Object createInstance(String type, Queue queue) throws Exception {
		Object obj = null;
		if (type != null) {
			Class[] paramType = new Class[] { Queue.class };
			Constructor con = Class.forName(type).getConstructor(paramType);
			Object[] params = new Object[] { queue };
			obj = con.newInstance(params);
		}
		return obj;
	}

}