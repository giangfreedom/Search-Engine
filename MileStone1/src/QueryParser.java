import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mark on 9/22/2016.
 */
public class QueryParser {

   PositionalInvertedIndex index;

   public QueryParser(){
      System.out.println("Parser made for Testing Only");
   }

   public QueryParser(PositionalInvertedIndex index){
      this.index = index;
   }

   public static List<Integer> parseQuery(String query, HashMap<String, List<Integer>> index){
      List<Integer> postings = new ArrayList<>(); //final merged postings list
      List<String> parsedquery = new ArrayList<>();
      String[] orsplit = query.split("\\+"); //splits the query by + if there's any
      System.out.println(Arrays.toString(orsplit));

      //loops through the orsplit list
      for(int i=0; i< orsplit.length; i++){
         String[] andmerge = splitQuotes(orsplit[i]);
         List<Integer> ormerge = index.get(andmerge[0]);
         for(int j=1; j<andmerge.length; j++){
            ormerge = andMerge(index.get(andmerge[j]), ormerge);
         }
         System.out.println("or merging: "+postings+" "+ormerge);
         postings = orMerge(ormerge, postings); //have to fix ormerge
         System.out.println(postings);
      }
      return postings;
   }

   //Splits query phrase into a String[] by surrounding quotes and spaces.
   private static String[] splitQuotes(String s){
      List<String> list = new ArrayList<String>();
      Matcher m = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'").matcher(s);
      while (m.find()) {
         list.add(m.group(0).replace("\"","")); //separates phrases and trims surrounding quotes
      }
      return list.toArray(new String[list.size()]);
   }

   public static List<Integer> andMerge(List<Integer> list1, List<Integer> list2){
      List<Integer> merged = new ArrayList<>();

      //if one list is null, return the other.
      if(list1==null){
         merged.addAll(list2);
      }
      else if(list2==null){
         merged.addAll(list1);
      }
      else{
         int pos1 = 0 , pos2 = 0; //current positions of each list
         while( pos1 < list1.size() && pos2 < list2.size() ){
            if (list1.get(pos1) == list2.get(pos2)) {
               merged.add(list1.get(pos1));
               pos1++;
               pos2++;
            }
            //have to put the first check if pos <= list.size to avoid bounds error.
            else if(list1.get(pos1) < list2.get(pos2)){
               pos1++;
            }
            else {
               pos2++;
            }
         }
      }
      return merged;
   }

   public static List<Integer> orMerge(List<Integer> list1, List<Integer> list2){
      List<Integer> merged = new ArrayList<>();

      //if one list is null, return the other.
      if(list1==null){
         merged.addAll(list2);
      }
      else if(list2==null){
         merged.addAll(list1);
      }
      else {
         int pos1 = 0, pos2 = 0; //current positions of each list
         while (pos1 < list1.size() && pos2 < list2.size()) {
            if (list1.get(pos1) == list2.get(pos2)) {
               merged.add(list1.get(pos1));
               pos1++;
               pos2++;
            } else if (list1.get(pos1) < list2.get(pos2)) {

               merged.add(list1.get(pos1));
               pos1++;
            } else {

               merged.add(list2.get(pos2));
               pos2++;
            }
         }
         // loop ends and lists have the same size. compare positions.
         // add the rest of the one that ended on a smaller position.
         if(list1.size() == list2.size()){
            if (pos1 < pos2) {
               System.out.println("added all list1 "+ list1);
               merged.addAll(list1.subList(pos1, list1.size()));
            } else if (pos1 > pos2) {
               System.out.println("added all list2 "+list2);
               merged.addAll(list2.subList(pos2, list2.size()));
            }
         }
         // loop ends and one list is bigger than the other.
         // add the remaining of the longer list.
         else if (list1.size() < list2.size()) {
            System.out.println("added all list2");
            merged.addAll(list2.subList(pos2, list2.size()));
         } else if (list1.size() > list2.size()) {
            System.out.println("added all list1");
            merged.addAll(list1.subList(pos1, list1.size()));
         }
      }
      return merged;
   }


}
