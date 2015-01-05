import java.io.*;

class Nuke2 {
    public static void main(String[] arg) throws Exception {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = keyboard.readLine();
        String outputLine = inputLine.substring(0, 1) + inputLine.substring(2);              
        System.out.println(outputLine);
    }	
}