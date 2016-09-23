import java.util.Arrays;
import java.util.List;

/**
 * Created by Mark on 9/23/2016.
 */
public class QueryMain {

   public static void main(String[] args){
      List<Integer> list1 = Arrays.asList(1,4,7,8,10,13,20,24,25,26,29);
      List<Integer> list2 = Arrays.asList(1,5,9,11,12,13,18,24,25,28,29,40,52);

      System.out.println(list1+"\n"+list2+"\n"+QueryParser.AndMerge(list1, list2));
   }
}
