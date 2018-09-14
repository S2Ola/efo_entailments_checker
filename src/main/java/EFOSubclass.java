import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EFOSubclass {
	public static <val> void main(String[] args) throws FileNotFoundException {

		String apiLink = "http://ec2-18-130-15-189.eu-west-2.compute.amazonaws.com/ontologies/efo";
		// variable to hold the URl link

		String tsvFile = "/Users/dipo/Documents/Damola/disease_data1.txt";
		// Reads in the file from the data format dot tsv

		String homeDir = System.getProperty("user.home");
		// This automatically captures the systems home directory

		String fileName = homeDir+"/Documents/output.csv";
		/*  File name is output.txt, this does not need to exist before,
            Java will automatically create it in your documents directory.*/

		MyFileWriter myFileWriter = new MyFileWriter ( fileName);
		// The class for writing the output into a file

		String line = "";
		//String cvsSplitBy = ",";

		FileReader fr = new FileReader ( tsvFile );
		// Reads the file from the local drive

		EFOSubclass efoSubclass = new EFOSubclass ( );
		//Assigned variable to the EFo Subclass

		List <String> subclasses = new ArrayList ( );
		// converts the subclasses to an array list

		EntailmentChecker entailmentChecker = new EntailmentChecker (apiLink);

		int count = 1;

		try (BufferedReader br = new BufferedReader ( fr )) {

			while ((line = br.readLine ( )) != null) {

				count++;
				if (count == 1) {
					continue;
				}

				String[] dataRow = line.split ( "\\t" );
				// split the rows into list

				String diseaseIdColumn = dataRow[0];

				// Capture Path To Root Column Data
				String pathToRootColumn = dataRow[2];

				String[] pathToRootSet = pathToRootColumn.split ( ";" );

				String report = "";

				for (String parent : pathToRootSet) {

					parent = parent.trim ();
					if (parent.equals ( "other" )) {
						continue;
					}
					boolean isEntailed = entailmentChecker.confirmSubclass ( diseaseIdColumn,  parent );
					if (!isEntailed) {
						report += diseaseIdColumn + " not a subclass of " + parent + "\n";
					}
					myFileWriter.writeToFile ( report );


				}

				}


		} catch (IOException e) {
			e.printStackTrace ( );
		}

		myFileWriter.close ();


	}
}







