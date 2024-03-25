import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * CSVReader reads and manipulates data from a .csv by headers, lines(rows), and columns.
 */
public class CSVReader {

    int size, numLines;
    String[] headers;
    ArrayList<String[]> lines = new ArrayList<String[]>();
    Scanner input;

    //default constructor populates arraylist with file headers
    public CSVReader(File file) throws FileNotFoundException{
        //use scanner to get header tokens from first line. assign size value accordingly.

        input = new Scanner(file);
        headers = popHeaders();
        size = headers.length;
        popLines();
        //debug printing
        for(String s : headers){System.out.println(s);}
        System.out.println(size);

    }

    /**
     * Gets all tokens which fall under a given header from the file.
     * @param header the header tag to use. Needs to be exactly accurate to the string text in the file.
     * @return String[] of tokens which fall under the header.
     */
    public String[] getTokens(String header){
        
        //input validation, checks to make sure the argument is contained in headers array
        boolean contains = false;
        int headerIndex = 0;
        for(String s : headers){
            headerIndex++;
            if(s.equals(header)){
                contains = true;
                break;
            }
        }
        if(!contains){throw new NoSuchElementException("Token not contained in headers.");}
        //

        String[] out = new String[numLines];

        int count = 0;
        for(String[] s : lines){

            out[count] = s[headerIndex];
            count++;

        }

        return out;
    }

    /**
     * Overloaded getTokens will use a given set of lines instead of the whole arraylist.
     * @param lines String[][] which was created by getLines()
     * @param header header tag to use
     */
    public String[] getTokens(String[][] lines, String header){

        //input validation, checks to make sure the argument is contained in headers array
        boolean contains = false;
        int headerIndex = 0;
        for(String s : headers){
            headerIndex++;
            if(s.equals(header)){
                contains = true;
                break;
            }
        }
        if(!contains){throw new NoSuchElementException("Token not contained in headers.");}
        //

        String[] out = new String[lines.length];

        int count = 0;
        for(String[] s : lines){

            out[count] = s[headerIndex];
            count++;

        }

        return out;
    }

    /**
     * Overloaded getTokens will get a given range of lines and use them instead of the entire arraylist.
     * @param start starting index
     * @param end index
     * @param header header tag to use
     */
    public String[] getTokens(int start, int end, String header){

        String[][] lines = getLines(start, end);

        return getTokens(lines, header);

    }

    /**
     * //TODO make getLines method
     * @param start begin index
     * @param end end index
     * @return String[] of String[]'s from the chosen range of lines
     */
    public String[][] getLines(int start, int end){

        //input validation. end should be equal or greater than start, and both should be within the size bounds of the arraylist.
        if((start < 0 || end > size) || end <= start){
            throw new IllegalArgumentException("Invalid parameters, check your range. End < start, or range is outside of Arraylist size bounds. ArrayList size: " + size);
        }

        int arrSize = end - start;
        String[][] out = new String[arrSize][size];

        for(int i = 0; i < arrSize; i++){

            out[i] = lines.get(start+i);

        }

        return out;

    }


    public int getSize(){return size;}

    public int getNumLines(){return this.numLines;}

    public ArrayList<String[]> getList(){return this.lines;}





    //-----------------------------------------------------------------------------------------//

    /**
     * Inserts all headers from the file into a String[]
     * @return string[] of headers from file
     */
    private String[] popHeaders() throws FileNotFoundException {

        //Scanner input = new Scanner(file);
        input.useDelimiter("\n");
        Scanner line = new Scanner(input.next());
        line.useDelimiter(";");
        int numHeaders = 0;

        ArrayList<String> headers = new ArrayList<String>();
        while(line.hasNext()){

            headers.add(line.next());
            numHeaders++;

        }

        String[] out = new String[numHeaders];
        headers.toArray(out);


        return out;
    }

    /**
     * Called by constructor to populate the aarraylist which contains line object (String[]'s)
     */
    private void popLines(){

        int count = 0;
        while(input.hasNext()){

            Scanner line = new Scanner(input.next());
            line.useDelimiter(";");
            String[] arr = new String[size];

            //TODO find a non O(n^2) solution for populating tokens?
            for(int i = 0; i < size; i++){
                arr[i] = line.next();
            }

            lines.add(arr);
            count++;

        }

        numLines = count;
        //lines ArrayList should be populated with String arrays,
        //each one containing all tokens from a single line of the file(of data , not headers)
        // the tokens should be separated, so each array should be of length this.size



    }

}
