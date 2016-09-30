import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mark on 9/23/2016.
 */
public class QueryMain {

   public static void main(String[] args){
      HashMap<String, List<Integer>> index = new HashMap<>();
      List<Integer> mark = Arrays.asList(1,3,5);
      List<Integer> spencer= Arrays.asList(1,3,17,20);
      List<Integer> hey=Arrays.asList(3,5,6);
      List<Integer> marksp = Arrays.asList(3,5);
      index.put("Mark", mark);
      index.put("Spencer", spencer);
      index.put("\"Hey There\"", hey);
      index.put("MarkSp", marksp);

      String query = "Mark Spencer + \"Hey There\" MarkSp" ; //Should query (Mark&&Spencer) || ("Hey There") [1,3]


   }
}
