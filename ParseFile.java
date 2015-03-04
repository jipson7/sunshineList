package csci2020u.a3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;


class ParseFile implements RecordLoader {

	private String employerRegexEN = "([A-Za-z ]+)";

	private String employerRegexFR = "[A-Za-z' ]+"; 

	private String lastNameRegex = "([A-Z ]+)";

	private String firstNameRegex = "([A-Z ]+)"; 

	private String positionRegex = "([A-Za-z, ]+)";

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

	private String startBlockRegex = "\\s*<tr>\\s*";
	private String endBlockRegex = "\\s*<\\/tr>\\s*";
	
	public List<Record> load(String filename) throws Exception {

		List<Record> sunshineList = new ArrayList<Record>();

		try {

			BufferedReader inputFile = new BufferedReader(new FileReader(filename));			

			Pattern codeBlockPattern = Pattern.compile(codeBlockRegex);

			Pattern startBlockPattern = Pattern.compile(startBlockRegex);

			Pattern endBlockPattern = Pattern.compile(endBlockRegex);

			Matcher codeBlockMatcher, startBlockMatcher, endBlockMatcher;

			StringBuilder blockTest = new StringBuilder();

			while ((currentLine = inputFile.readLine()) != null) {

					
				
			}

			inputFile.close();

		} catch (Exception e) {

			System.out.println("Something has gone terribly wrong");

			System.exit(1);

		}


		return sunshineList;

	}

	private Record createRecord(String currentBlock) {

		

	}

}
