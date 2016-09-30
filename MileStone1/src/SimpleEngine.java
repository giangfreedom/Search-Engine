
import javafx.geometry.Pos;

import javax.sound.sampled.Port;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

/**
A very simple search engine. Uses an inverted index over a folder of TXT files.
*/
public class SimpleEngine {

   public static void main(String[] args) throws IOException {
      final Path currentWorkingPath = Paths.get("C:\\Users\\Mark\\Documents\\CSULB\\CECS_429 - Search Engine\\Homework\\Homework1\\MobyDick10Chapters").toAbsolutePath();
      
      // the inverted index
      final PositionalInvertedIndex index = new PositionalInvertedIndex();
      
      // the list of file names that were processed
      final List<String> fileNames = new ArrayList<String>();

      
      // This is our standard "walk through all .txt files" code.
      Files.walkFileTree(currentWorkingPath, new SimpleFileVisitor<Path>() {
         int mDocumentID  = 0;
         
         public FileVisitResult preVisitDirectory(Path dir,
          BasicFileAttributes attrs) {
            // make sure we only process the current working directory
            if (currentWorkingPath.equals(dir)) {
               return FileVisitResult.CONTINUE;
            }
            return FileVisitResult.SKIP_SUBTREE;
         }

         public FileVisitResult visitFile(Path file,
          BasicFileAttributes attrs) {
            // only process .txt files
            if (file.toString().endsWith(".txt")) {
               // we have found a .txt file; add its name to the fileName list,
               // then index the file and increase the document ID counter.
               System.out.println("Indexing file " + file.getFileName());
               
               
               fileNames.add(file.getFileName().toString());
               // how to get position of the term in the docid
               indexFile(file.toFile(), index, mDocumentID);
               mDocumentID++;
            }
            return FileVisitResult.CONTINUE;
         }

         // don't throw exceptions if files are locked/other errors occur
         public FileVisitResult visitFileFailed(Path file,
          IOException e) {

            return FileVisitResult.CONTINUE;
         }

      });
      
      printResults(index, fileNames);

      // Main Querying Begins.
      // Ask the user to search for a term. Stops when user enters 'quit'
      Scanner scan = new Scanner(System.in);
      String userinput = "";
      String[] mDictionary = index.getDictionary();
      DocumentProcessing processor = new DocumentProcessing();

      do{
         System.out.println("Enter a term to search for:  ");
         userinput = scan.nextLine();
         PorterStemmer porter = new PorterStemmer();


         if(userinput.startsWith(":stem")){
            String stemmed = porter.processToken(userinput.substring(6,userinput.length()));
            System.out.print(" -> "+stemmed+"\n");
         }
         else if (userinput.startsWith(":quit")){
            //end loop and break
         }
         else{

            List<Integer> results = QueryParser.parseQuery(userinput, index);
            if(results.size()>0){
               System.out.printf("%-15s  ", userinput+":");
               for (Integer i : results ){
                  System.out.printf("%-15s", fileNames.get(i));
                  System.out.printf("%-18s", "\n");
               }
               System.out.print("\n");
            }
            else
               System.out.println("Term not found in the index");
         }
      } while ( !userinput.equals(":quit") );
      
   }

   /**
   Indexes a file by reading a series of tokens from the file, treating each 
   token as a term, and then adding the given document's ID to the inverted
   index for the term.
   @param file a File object for the document to index.
   @param index the current state of the index for the files that have already
   been processed.
   @param docID the integer ID of the current document, needed when indexing
   each term from the document.
   */
   private static void indexFile(File file, PositionalInvertedIndex index,
    int docID) {
      // Read each token from the stream and add it to the index.
      int Position  = 0;
      try {
         SimpleTokenStream mTStream = new SimpleTokenStream(file);
         DocumentProcessing dp = new DocumentProcessing();
         String t = "";
         while (mTStream.hasNextToken()){
            t = mTStream.nextToken();
            //special case if token is hyphenated
            if(t.contains("-")){
               for( String s : dp.SplitHyphenWord(t)){
                  index.addTerm( s, docID, Position);
               }
            }
            else
               index.addTerm( dp.normalizeToken(t), docID, Position);
            Position++;
         }
      }catch(FileNotFoundException ex){
         System.out.println("File Not Found");
      }
   }

   private static void printResults(PositionalInvertedIndex index,
    List<String> fileNames) {
     
      // TO-DO: print the inverted index.
      // Retrieve the dictionary from the index. (It will already be sorted.)
      // For each term in the dictionary, retrieve the postings list for the
      // term. Use the postings list to print the list of document names that
      // contain the term. (The document ID in a postings list corresponds to 
      // an index in the fileNames list.)
      
      // Print the postings list so they are all left-aligned starting at the
      // same column, one space after the longest of the term lengths. Example:
      // 
      // as:      document0 document3 document4 document5
      // engines: document1
      // search:  document2 document4
      String[] mDictionary = index.getDictionary();
      for (String s : mDictionary){
         System.out.printf("%-15s  ", s+":");
         for (PositionArray i : index.getPostings(s) ){
            System.out.printf("%-15s", fileNames.get(i.getDocID()));
            System.out.printf("%-15s", i.getListofPos()  );
            System.out.printf("%-18s", "\n");
         }
         System.out.print("\n");
      }
   }
}
