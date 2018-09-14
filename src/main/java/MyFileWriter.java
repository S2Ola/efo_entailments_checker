import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter {

	BufferedWriter writer;

	public MyFileWriter(String outputFileName) {
		try {
			writer = new BufferedWriter(new FileWriter (outputFileName, false));

		} catch (IOException e) {
			e.printStackTrace ( );
		}

	}

	public void writeToFile(String data){



		// Write to the file using BufferedReader and FileWriter
		try {
			writer.append(data);
			System.out.println("Data was written to File check directory: "+data);

		} catch (Exception e) {}

	}

	public void close(){
		try {
			writer.close ();
		} catch (IOException e) {
			e.printStackTrace ( );
		}
	}
}
