package ie.atu.sw;

import java.util.LinkedList;
import java.util.List;
/*
 * This class is used to store the definition and page numbers of a word for the index in one object.
 */
public class WordDetail {
	private String definition;
	public List<Integer> pageNumbers;
	
	public WordDetail(String definition) {
		this.definition = definition;
		this.pageNumbers = new LinkedList<Integer>();
	}

	public String getDefinition() {
		return definition;
	}


	@Override
	public String toString() {
		String tempDefinition = definition; 
		String pageNumbersString = "{ ";
		try{
			for(int i = 0; i < pageNumbers.size(); i++) {
				pageNumbersString += pageNumbers.get(i) + " ";
			}
			pageNumbersString += "}";
		}catch(Exception e){
			return tempDefinition;
		}
		return tempDefinition + ": " + pageNumbersString;
	}
}
