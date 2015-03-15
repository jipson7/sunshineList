import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;


class ParseFile implements RecordLoader {

	long currentID = 0;

	private String employerRegexEN = "([A-Za-z0-9&,\\-/;'’\\.\\(\\) ]+)";

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

	private String secondCodeBlockRegex = "\\s*<tr>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">" 
		+ employerRegexEN
		+ "<\\/span>\\s*<\\/td>\\s*<td align=\"left\" valign=\"top\">" 
		+ lastNameRegex
		+ "<\\/td>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\">" 
		+ firstNameRegex 
		+ "<\\/td>\\s*<td align=\"left\" valign=\"top\"><span lang=\"en\">\\s*?" 
		+ positionRegex 
		+ "<\\/span><\\/td>\\s*<td align=\"right\" valign=\"top\">" 
		+ salaryRegex
		+ "<\\/td>\\s*<td colspan=\"2\" align=\"right\" valign=\"top\">" 
		+ taxRegex
		+ "<\\/td>\\s*<\\/tr>\\s*";

	private String addendaURL = "<a href='\\/en\\/publications\\/salarydisclosure\\/2014\\/addenda_14.html#[A-Za-z\\- ]+'>";

	private String thirdCodeBlockRegex = "\\s*<tr>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">" 
		+ addendaURL 
		+ employerRegexEN 
		+ "<\\/span>\\s*<\\/td>\\s*<td align=\"left\" valign=\"top\">" 
		+ addendaURL
		+ lastNameRegex 
		+ "<\\/a><\\/td>\\s*<td colspan=\"2\" align=\"left\" valign=\"top\">" 
		+ addendaURL 
		+ firstNameRegex 
		+ "<\\/a><\\/td>\\s*<td align=\"left\" valign=\"top\"><span lang=\"en\">\\s*" 
		+ addendaURL 
		+ positionRegex 
		+ "<\\/span><\\/td>\\s*<td align=\"right\" valign=\"top\">" 
		+ addendaURL 
		+ salaryRegex 
		+ "<\\/a><\\/td>\\s*<td colspan=\"2\" align=\"right\" valign=\"top\">" 
		+ addendaURL 
		+ taxRegex 
		+ "<\\/a><\\/td>\\s*<\\/tr>";


	private Pattern codeBlockPattern = Pattern.compile(codeBlockRegex);

	private Pattern secondCodeBlockPattern = Pattern.compile(secondCodeBlockRegex);

	private Pattern thirdCodeBlockPattern = Pattern.compile(thirdCodeBlockRegex);

	private String sectorRegex = "<h1> Public Sector Salary Disclosure for 2013: ([A-Za-z: ]+)</h1>";

	private String startBlockRegex = "\\s*<tr>\\s*";
	private String endBlockRegex = "\\s*<\\/tr>\\s*";

	private String currentSector = "VOID";

	public List<Record> load(String filename) throws Exception {

		List<Record> sunshineList = new ArrayList<Record>();

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

					currentSector = sectorMatcher.group(1).trim();

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

		return sunshineList;

	}

	private Record createRecord(String currentBlock) {

		Record newUser = new Record();

		Matcher codeBlockMatcher = codeBlockPattern.matcher(currentBlock);

		if (codeBlockMatcher.find()) {

			newUser.employer = codeBlockMatcher.group(1).trim();

			newUser.name = buildNameString(codeBlockMatcher.group(5), codeBlockMatcher.group(6));

			newUser.position = codeBlockMatcher.group(7).trim();

			newUser.salary = convertSalaryToFloat(codeBlockMatcher.group(8));

			newUser.sector = currentSector;

			newUser.id = (++currentID);


		} else {

			Matcher secondCodeBlockMatcher = secondCodeBlockPattern.matcher(currentBlock);

			Matcher thirdCodeBlockMatcher = thirdCodeBlockPattern.matcher(currentBlock);

			if (secondCodeBlockMatcher.find()) {

				newUser.employer = secondCodeBlockMatcher.group(1).trim();

				newUser.name = buildNameString(secondCodeBlockMatcher.group(2), secondCodeBlockMatcher.group(3));

				newUser.position = secondCodeBlockMatcher.group(4).trim();

				newUser.salary = convertSalaryToFloat(secondCodeBlockMatcher.group(5));

				newUser.sector = currentSector;

				newUser.id = (++currentID);
			
				
			} else if (thirdCodeBlockMatcher.find()) {

				newUser.employer = thirdCodeBlockMatcher.group(1).trim();

				newUser.name = buildNameString(thirdCodeBlockMatcher.group(2), thirdCodeBlockMatcher.group(3));

				newUser.position = thirdCodeBlockMatcher.group(4).trim();

				newUser.salary = convertSalaryToFloat(thirdCodeBlockMatcher.group(5));

				newUser.sector = currentSector;

				newUser.id = (++currentID);

			} else {

				return null;
			}

		}

		return newUser;


	}

	private float convertSalaryToFloat(String salaryString) {

			salaryString = salaryString.replace("$", "");
			salaryString = salaryString.replace(",", "");
			return Float.parseFloat(salaryString.trim());

	}

	private String buildNameString(String lastName, String firstName) {

		String nameToAdd = lastName + ", " + firstName;

		nameToAdd = nameToAdd.replace("  ", " ").trim();

		return nameToAdd;

	}

}
