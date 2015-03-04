package csci2020u.a3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;


class ParseFile implements RecordLoader {

	private String employerRegexEN = "([A-Za-z&,\\-/ÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖØÙÚÛÜáéíóúô'’\\. ]+)";

	private String employerRegexFR = "[A-Za-z&,\\-/ÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖØÙÚÛÜáéíóúô'’\\. ]+"; 

	private String lastNameRegex = "([A-Za-z&\\-ÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖØÙÚÛÜ'’\\.\\(\\) ]+);?";

	private String firstNameRegex = "([A-Za-z&\\-ÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖØÙÚÛÜ'’\\.\\(\\) ]+);?"; 

	private String positionRegex = "([A-Za-z&,\\-/áéíóúô'’\\. ]+)";

	private String salaryRegex = "(\\$\\d?,?\\d{3,},\\d{3,}.\\d{2})"; 

	private String taxRegex = "(\\$\\d+.\\d{2})"; 

	private String codeBlockRegex = "\\s*<tr>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">" 
		+ employerRegexEN 
		+ "<\\/span>\\s*<span lang=\"fr_ca\">&nbsp;\\/&nbsp;\\s*" 
		+ employerRegexFR
		+ "<\\/span><\\/td>\\s*<td align=\"left\" valign=\"top\">" 
		+ lastNameRegex
		+ "<\\/td>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\">" 
		+ firstNameRegex
		+ "<\\/td>\\s*<td align=\"left\" valign=\"top\"><span lang=\"en\">\\s*" 
		+ positionRegex
		+ "<\\/span>.+\\s*<td align=\"right\" valign=\"top\">" 
		+ salaryRegex 
		+ "<\\/td>\\s*<td colspan=\"2\" align=\"right\" valign=\"top\">" 
		+ taxRegex
		+ "<\\/td>\\s*<\\/tr>\\s*";


	private Pattern codeBlockPattern = Pattern.compile(codeBlockRegex);

	private String startBlockRegex = "\\s*<tr>\\s*";
	private String endBlockRegex = "\\s*<\\/tr>\\s*";
	
	public List<Record> load(String filename) throws Exception {

		//temp to check regex
		System.out.println(codeBlockRegex);

		List<Record> sunshineList = new ArrayList<Record>();

		try {

			BufferedReader inputFile = new BufferedReader(new FileReader(filename));			

			Pattern startBlockPattern = Pattern.compile(startBlockRegex);

			Pattern endBlockPattern = Pattern.compile(endBlockRegex);

			Matcher startBlockMatcher, endBlockMatcher;

			StringBuilder blockTest = new StringBuilder();

			String currentLine = "";

			while ((currentLine = inputFile.readLine()) != null) {

				startBlockMatcher = startBlockPattern.matcher(currentLine);						

				if (startBlockMatcher.find()) {

					blockTest.append(currentLine);

					while ((currentLine = inputFile.readLine()) != null) {

						endBlockMatcher = endBlockPattern.matcher(currentLine);

						if (endBlockMatcher.find()) {

							blockTest.append(currentLine);

							break;

						}

						blockTest.append(currentLine);

					}

					sunshineList.add(createRecord(blockTest.toString()));

					blockTest.setLength(0);

				}
				
			}

			inputFile.close();

		} catch (Exception e) {

			System.out.println("Something has gone terribly wrong");

			System.exit(1);

		}


		return sunshineList;

	}

	private Record createRecord(String currentBlock) {

		Record newUser = new Record();

		Matcher codeBlockMatcher = codeBlockPattern.matcher(currentBlock);

		if (codeBlockMatcher.find()) {

			newUser.employer = codeBlockMatcher.group(1);

			String lastName = codeBlockMatcher.group(2);

			String firstName = codeBlockMatcher.group(3);

			newUser.name = lastName + ", " + firstName;

			newUser.position = codeBlockMatcher.group(4);


			String salaryString = codeBlockMatcher.group(5);

			salaryString = salaryString.replace("$", "");

			salaryString = salaryString.replace(",", "");

			newUser.salary = Float.parseFloat(salaryString);

			newUser.sector = ""; //TODO

		} else {

			float tempSalary = 0;

			newUser.employer = "FAILED";
			newUser.name = "FAILED";
			newUser.position = "FAILED";
			newUser.salary = tempSalary; //TODO
			newUser.sector = "FAILEd"; //TODO


		}

		return newUser;


	}

}
