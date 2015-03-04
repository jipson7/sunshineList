package csci2020u.a3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;


class ParseFile implements RecordLoader {

	private String employerRegex = "<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">"
		+ "([A-Za-z&,\\-/áéíóúô'’\\. ]+)";

	public List<Record> load(String filename) throws Exception {

		List<Record> sunshineList = new ArrayList<Record>();

		try {

			BufferedReader inputFile = new BufferedReader(new FileReader(filename));			

			Pattern pattern = Pattern.compile(employerRegex);

			Matcher matcher;
		
			String currentLine;

			while ((currentLine = inputFile.readLine()) != null) {

				matcher = pattern.matcher(currentLine);

				if (matcher.find()) {

					Record currentRecord = new Record();	

					currentRecord.employer = matcher.group(1);

					sunshineList.add(currentRecord);

				}

			}

			inputFile.close();

		} catch (Exception e) {

			System.out.println("Could not open output.txt");

			System.exit(1);

		}


		return sunshineList;

	}

}
