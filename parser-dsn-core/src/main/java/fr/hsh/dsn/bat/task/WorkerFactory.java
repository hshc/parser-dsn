package fr.hsh.dsn.bat.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerFactory implements ThreadFactory {
	static final AtomicInteger	poolNumber		= new AtomicInteger(1);
	final ThreadGroup			group;
	final AtomicInteger			threadNumber	= new AtomicInteger(1);
	final String				namePrefix;

	public WorkerFactory() {
		SecurityManager s = System.getSecurityManager();
		this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.namePrefix = "Factory" + poolNumber.getAndIncrement() + "_Worker";
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
