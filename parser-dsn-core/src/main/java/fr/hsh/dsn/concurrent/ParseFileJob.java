package fr.hsh.dsn.concurrent;

import java.nio.file.Path;
import java.util.concurrent.Callable;

import fr.hsh.dsn.bat.ExitValue;

public class ParseFileJob implements Callable<ExitValue> {
	private final Path filePath;
	public ParseFileJob(Path filePath) {
		super();
		this.filePath = filePath;
	}
	@Override
	public ExitValue call() throws Exception {
		//		this.filePath.toFile().;
		return null;
	}

}
