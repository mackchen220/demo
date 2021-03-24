package org.jeecg.modules.commons.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具
 * @author: 宇少
 */
public class ThreadPoolUtil {

	public static ExecutorService newFixedThreadPool(int nThreads, String groupName) {
		if (nThreads < 0) {
			return null;
		}
		return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), new MyThreadFactory(groupName));
	}

	public static ScheduledExecutorService newScheduledThreadPool(int nThreads, String groupName) {
		if (nThreads < 0) {
			return null;
		}
		return new ScheduledThreadPoolExecutor(nThreads, new MyThreadFactory(groupName));
	}

	private static class MyThreadFactory implements ThreadFactory {
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String threadNamePrefix;

		MyThreadFactory(String poolName) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			threadNamePrefix = poolName + "-th-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, threadNamePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}
}
