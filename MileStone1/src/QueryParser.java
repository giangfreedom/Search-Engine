import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

   public List<Integer> ParseQuery(String query){
      List<Integer> postings = new ArrayList<>(); //final merged postings list
      List<String> parsedquery = new ArrayList<>();

      return postings;
   }

   public static List<Integer> AndMerge(List<Integer> list1, List<Integer> list2){
      List<Integer> merged = new ArrayList<>();
      //sort if not done so already
      Collections.sort(list1);
      Collections.sort(list2);

      int pos1 = 0 , pos2 = 0; //current positions of each list
      while( pos1 < list1.size() && pos2 < list2.size() ){
         System.out.println("pos1:"+pos1+" "+list1.get(pos1) +" pos2:"+pos2+" "+list2.get(pos2));
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
      return merged;
   }

   public static List<Integer> OrMerge(List<Integer> list1, List<Integer> list2){
      List<Integer> merged = new ArrayList<>();
      //sort if not done so already
      Collections.sort(list1);
      Collections.sort(list2);

      int pos1 = 0 , pos2 = 0; //current positions of each list
      while( pos1 < list1.size() && pos2 < list2.size() ){
         System.out.println("pos1:"+pos1+" "+list1.get(pos1) +" pos2:"+pos2+" "+list2.get(pos2));
         if (list1.get(pos1) == list2.get(pos2)) {
            merged.add(list1.get(pos1));
            pos1++;
            pos2++;
         }
         else if(list1.get(pos1) < list2.get(pos2)){

            merged.add(list1.get(pos1));
            pos1++;
         }
         else {

            merged.add(list2.get(pos2));
            pos2++;
         }
      }
      if( list1.size() < list2.size()){
         merged.addAll(list2.subList(pos2,list2.size()));
      }
      else if(list1.size() > list2.size()) {
         merged.addAll(list1.subList(pos1,list1.size()));
      }
      return merged;
   }


}
