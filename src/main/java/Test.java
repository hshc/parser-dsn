import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class Test {
	public static void main(String[] args) throws IOException {
		//		String fileName = "/home/nta/Documents/DSN/03 - Conception/ExemplesDSN/Signalement Fin de Contrat.txt";
		String fileName = "/home/nta/Documents/DSN/03 - Conception/ExemplesDSN/DSN_F10JsUvULtGF64RkroqJfv3_1051_P0012.txt";
		int numIeration = 50;
		String title[] = {"readUsingFiles", "readUsingScanner", "readUsingBufferedReader", "readUsingBufferedReaderJava7", "readUsingBufferedReader", "readUsingFileReader", "readUsingBufferChannel"};
		String recap[][] = new String[numIeration+1][title.length];
		for (int lI = 0; lI < recap[0].length; lI++) {
			recap[0][lI] = title[lI];
		}
		for (int lI = 0; lI < numIeration; lI++) {
			long alapsedTime = 0;
			long time = System.currentTimeMillis();
			//using Java 7 Files class to process small files, get complete file data
			readUsingFiles(fileName);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingFiles: "+alapsedTime);
			recap[lI+1][0] = Long.toString(alapsedTime);

			//using Scanner class for large files, to read line by line
			time = System.currentTimeMillis();
			readUsingScanner(fileName);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingScanner: "+alapsedTime);
			recap[lI+1][1] = Long.toString(alapsedTime);

			//read using BufferedReader, to read line by line
			time = System.currentTimeMillis();
			readUsingBufferedReader(fileName);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingBufferedReader: "+alapsedTime);
			recap[lI+1][2] = Long.toString(alapsedTime);

			time = System.currentTimeMillis();
			readUsingBufferedReaderJava7(fileName, StandardCharsets.ISO_8859_1);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingBufferedReaderJava7: "+alapsedTime);
			recap[lI+1][3] = Long.toString(alapsedTime);

			time = System.currentTimeMillis();
			readUsingBufferedReaderCharset(fileName, StandardCharsets.ISO_8859_1);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingBufferedReader: "+alapsedTime);
			recap[lI+1][4] = Long.toString(alapsedTime);

			//read using FileReader, no encoding support, not efficient
			time = System.currentTimeMillis();
			readUsingFileReader(fileName);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingFileReader: "+alapsedTime);
			recap[lI+1][5] = Long.toString(alapsedTime);

			time = System.currentTimeMillis();
			readUsingBufferChannel(fileName);
			alapsedTime = System.currentTimeMillis() - time;
			//			System.out.println("readUsingBufferChannel: "+alapsedTime);
			recap[lI+1][6] = Long.toString(alapsedTime);
		}
		System.out.println(recap);
		for (String[] lElement : recap) {
			for (int j = 0; j < lElement.length; j++) {
				System.out.print(lElement[j]);
				if ((j+1) == lElement.length) {
					System.out.println();
				} else {
					System.out.print(", ");
				}
			}
		}
	}

	private static void readUsingFiles(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		//read file to byte array
		//		byte[] bytes = Files.readAllBytes(path);
		//read file to String list
		List<String> allLines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
	}

	private static void readUsingScanner(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		Scanner scanner = new Scanner(path);
		//read line by line
		while(scanner.hasNextLine()){
			//process each line
			String line = scanner.nextLine();
		}
	}

	private static void readUsingBufferedReader(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
			//process the line
			//			System.out.println(line);
		}
		//close resources
		br.close();
		fr.close();
	}

	private static void readUsingBufferedReaderJava7(String fileName, Charset cs) throws IOException {
		Path path = Paths.get(fileName);
		BufferedReader br = Files.newBufferedReader(path, cs);
		String line;
		while((line = br.readLine()) != null){
			//process the line
			//			System.out.println(line);
		}
		br.close();
	}

	private static void readUsingBufferedReaderCharset(String fileName, Charset cs) throws IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, cs);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while((line = br.readLine()) != null){
			//process the line
			//			System.out.println(line);
		}
		br.close();

	}

	private static void readUsingFileReader(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
			//process the line
			//			System.out.println(line);
		}
		br.close();
		fr.close();

	}

	private static void readUsingBufferChannel(String fileName) throws IOException {
		RandomAccessFile aFile = null;
		aFile = new RandomAccessFile(fileName, "r");
		FileChannel inChannel = aFile.getChannel();

		//create buffer with capacity of 500 bytes
		ByteBuffer buf = ByteBuffer.allocateDirect(5000);

		int bytesRead = inChannel.read(buf); //read into buffer.
		while (bytesRead != -1) {
			buf.flip();  //make buffer ready for read
			while(buf.hasRemaining()){
				char t = (char)buf.get();
				//				System.out.print(t); // read 1 byte at a time
			}
			buf.clear(); //make buffer ready for writing
			bytesRead = inChannel.read(buf);
		}
		aFile.close();

		//		sun.misc.Cleaner cleaner = ((DirectBuffer) buffer).cleaner();
		//		   cleaner.clean();
	}
}
