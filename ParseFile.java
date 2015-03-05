package csci2020u.a3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;


class ParseFile implements RecordLoader {

	//temp
	private int NumberOfFailedRecords = 0;

	private String employerRegexEN = "([A-Za-z&,\\-/;'’\\. ]+)";

	private String employerRegexFR = "[A-Za-z&,\\-/;'’\\. ]+";

	private String lastNameRegex = "([A-Za-z&\\-;'’\\.\\(\\)_ ]+);?";

	private String firstNameRegex = "([A-Za-z&\\-;'’\\.\\(\\) ]+);?"; 

	private String positionRegex = "([A-Za-z0-9:;&#\",\\-/'’\\.\\(\\)\\s\\\\`\\+ ]+)";

	private String salaryRegex = "(\\$\\d?,?\\d{3,},\\d{3,}.\\d{2})"; 

	private String taxRegex = "(\\$\\d?\\d?\\d?,?\\d+.\\d{2})"; 

	private String codeBlockRegex = "\\s*<tr>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">" 
		+ employerRegexEN 
		+ "<\\/span>\\s*((<span lang=\"fr_ca\">&nbsp;/&nbsp;\\s*"
		+ employerRegexFR
		+ "</span></td>)|(</td>))\\s*<td align=\"left\" valign=\"top\">" 
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

	private String secondCodeBlockRegex = "<tr>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\">" 
		+ lastNameRegex
		+ "<\\/td>\\s*<td align=\"left\" valign=\"top\">" 
		+ firstNameRegex
		+ "<\\/td>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\">" 
		+ employerRegexEN
		+ "<\\/td>\\s*<td align=\"left\" valign=\"top\">" 
		+ employerRegexEN
		+ "\\/<span lang=\"fr_ca\">" 
		+ employerRegexFR
		+ "<\\/span>\\s*<\\/td>\\s*<td align=\"left\" valign=\"top\">" 
		+ positionRegex
		+ "\\/<span lang=\"fr_ca\">" 
		+ employerRegexFR
		+ "<\\/span>\\s*<\\/td>\\s*<td align=\"right\" valign=\"top\">" 
		+ salaryRegex
		+ "<\\/td>\\s*<td colspan=\"2\" align=\"right\" valign=\"top\">" 
		+ taxRegex
		+ "<\\/td>\\s*<\\/tr>\\s*";


	private Pattern codeBlockPattern = Pattern.compile(codeBlockRegex);

	private Pattern secondCodeBlockPattern = Pattern.compile(secondCodeBlockRegex);

	private String sectorRegex = "<h1> Public Sector Salary Disclosure for 2013: ([A-Za-z: ]+)</h1>";

	private String startBlockRegex = "\\s*<tr>\\s*";
	private String endBlockRegex = "\\s*<\\/tr>\\s*";

	private String currentSector = "VOID";
	
	public List<Record> load(String filename) throws Exception {

		List<Record> sunshineList = new ArrayList<Record>();

		System.out.println(secondCodeBlockRegex);

		try {

			BufferedReader inputFile = new BufferedReader(new FileReader(filename));			

			Pattern startBlockPattern = Pattern.compile(startBlockRegex);

			Pattern endBlockPattern = Pattern.compile(endBlockRegex);

			Pattern sectorPattern = Pattern.compile(sectorRegex);

			Matcher startBlockMatcher, endBlockMatcher, sectorMatcher;

			StringBuilder blockTest = new StringBuilder();

			String currentLine = "";

			while ((currentLine = inputFile.readLine()) != null) {

				sectorMatcher = sectorPattern.matcher(currentLine);

				if (sectorMatcher.find()) {

					currentSector = sectorMatcher.group(1);

					continue;

				}

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

					Record recordToAdd = createRecord(blockTest.toString());

					if (recordToAdd != null) {

						sunshineList.add(recordToAdd);

					}

					blockTest.setLength(0);

				}
				
			}

			inputFile.close();

		} catch (Exception e) {

			System.out.println("Something has gone terribly wrong");

			System.exit(1);

		}

		System.out.println("The number of failed records is : " + NumberOfFailedRecords);

		return sunshineList;

	}

	private Record createRecord(String currentBlock) {

		Record newUser = new Record();

		Matcher codeBlockMatcher = codeBlockPattern.matcher(currentBlock);

		if (codeBlockMatcher.find()) {

			newUser.employer = codeBlockMatcher.group(1);

			String lastName = codeBlockMatcher.group(5);

			String firstName = codeBlockMatcher.group(6);

			String nameToAdd = lastName + ", " + firstName;

			nameToAdd = nameToAdd.replace("  ", " ");

			newUser.name = nameToAdd;

			newUser.position = codeBlockMatcher.group(7);

			newUser.salary = convertSalaryToFloat(codeBlockMatcher.group(8));

			newUser.sector = currentSector;


		} else {

			Matcher secondCodeBlockMatcher = secondCodeBlockPattern.matcher(currentBlock);

			if (secondCodeBlockMatcher.find()) {

				String lastName = secondCodeBlockMatcher.group(1);

				String firstName = secondCodeBlockMatcher.group(2);

				String nameToAdd = lastName + ", " + firstName;

				nameToAdd = nameToAdd.replace("  ", " ");

				newUser.name = nameToAdd;

				newUser.employer = secondCodeBlockMatcher.group(3);

				newUser.sector = secondCodeBlockMatcher.group(4);

				newUser.position = secondCodeBlockMatcher.group(5);
			
				newUser.salary = convertSalaryToFloat(secondCodeBlockMatcher.group(6));
	
				
			} else {

				System.out.println("Error : " + currentBlock);

				NumberOfFailedRecords++;	

				return null;

			}

		}

		return newUser;


	}

	private float convertSalaryToFloat(String salaryString) {

			salaryString = salaryString.replace("$", "");
			salaryString = salaryString.replace(",", "");
			return Float.parseFloat(salaryString);

	}

}
