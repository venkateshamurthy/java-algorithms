package yajna;

public class WordCount {

	public static void main(String[] args){
		
		String sentence="quick fox   publicly static main fox";
		String word =   "fox";
		int occurance=0;
		
		for(int i=0;i<sentence.length()-word.length();)
		{
			String slice=sentence.substring(i, i+word.length());
			//log.info("slice=->"+slice+"<-");
			if(slice.equalsIgnoreCase(word)){
				occurance++;
				i=i+word.length();
			}
			else i++;
		}
		//log.info("occurance="+occurance);
		findLargestWordSize(sentence);
	}
	
	public static void findLargestWordSize(String sentence){
		int largest=0;
		String largestWord=null;
		
		for(int i=0,j=0; i<=j && j<sentence.length();){
			if(sentence.charAt(j)==' '){
				if(j-i>largest){
					largest=j-i;
					largestWord=sentence.substring(i, j);
				}
				j++;
				i=j;
			}
			else
				j++;
		}
		System.out.println(largestWord+" "+largest);
	}
	
}
