import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Main{
    public static void main(String[] arg){
        String[] string = {"-mode", "dec", "-in", "C:\\road_to_treasure.txt","-out", "C:\\protected.txt", "-key", "55", "-alg" ,"shift"};
        ParseArg parse = new ParseArg(string);
        ProcessArg process = new ProcessArg(parse);
        process.process();

    }
}
//////////////////////////////////////////////////////////////////////
class ParseArg{
    String data;
    String mode = "enc";
    String pathIn;
    String pathOut;
    String alg = "shift";
    int key;

    ParseArg(String[] arg){
        for (int index = 0; index < arg.length;index++){
            if (arg[index].equals("-data")) {
                index++;
                this.data = arg[index];
            }
            if (arg[index].equals("-mode")) {
                index++;
                this.mode = arg[index];
            }
            if (arg[index].equals("-in")) {
                index++;
                this.pathIn = arg[index];
            }
            if (arg[index].equals("-out")) {
                index++;
                this.pathOut = arg[index];
            }
            if (arg[index].equals("-key")) {
                index++;
                this.key = Integer.parseInt(arg[index]);
            }
            if (arg[index].equals("-alg")) {
                index++;
                this.alg = arg[index];
            }

        }
    }
}//Creating storage from input
//////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////
class ProcessArg {
    private ParseArg parseArg;
    private Algorithm algorithm;

    ProcessArg(ParseArg parseArg) {
        this.parseArg = parseArg;
        this.algorithm = FabricAlgorithm.choseAlg(parseArg.alg);
    }

    public void process() {

        if (parseArg.pathIn != null) {
            try {
                parseArg.data = ProcessIOData.input(parseArg.pathIn);

            } catch (IOException e) {
                System.out.println("open file: " + parseArg.pathIn + "not found!");
            }
        }

        parseArg.data = algorithm.parse(parseArg.data, parseArg.mode, parseArg.key);

        if (parseArg.pathIn != null) {
            ProcessIOData.output(parseArg.data, parseArg.pathOut);
        } else {
            System.out.println(parseArg.data);
        }
    }

}//Main object for processing data
//////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////
abstract class Algorithm {

    abstract String parse(String data, String mode, int key);

}//Superclass for Algorithms

class Shift extends Algorithm {


    @Override
    String parse(String data, String mode, int key) {

        String awayLine = "";

        key = key % 26;

        if (mode.equals("dec")) {
            key = -key;
        }


        for (int index = 0; index < data.length(); index++) {

            if (data.charAt(index) > 64 && data.charAt(index) < 91){

                if(data.charAt(index)+key > 90){

                    awayLine += (char) (data.charAt(index) + key - 26);

                }else if(data.charAt(index)+key < 65){

                    awayLine += (char) (data.charAt(index) + key + 26);

                }else{
                    awayLine += (char) (data.charAt(index) + key);
                }
            }else if(data.charAt(index) > 96 && data.charAt(index) < 123){

                if(data.charAt(index)+key > 122){

                    awayLine += (char) (data.charAt(index) + key - 26);

                }else if(data.charAt(index)+key < 97){

                    awayLine += (char) (data.charAt(index) + key + 26);

                }else{

                    awayLine += (char) (data.charAt(index) + key);
                }
            }else{
                awayLine += data.charAt(index);
            }





        }

        return awayLine;
    }

}//Release Shift algorithm

class Unicode extends Algorithm {

    @Override
    String parse(String data, String mode, int key) {
        String awayLine = "";

        key = key % 127;

        if (mode.equals("dec")) {
            key = -key;
        }


        for(int index = 0; index < data.length();index++){

            if(key + data.charAt(index) > 127) {

                awayLine += (char) (data.charAt(index) + (key - 127));

            }else if (key + data.charAt(index) < 1){

                awayLine += (char) (data.charAt(index) + (key + 127));

            }else {

                awayLine += (char)(data.charAt(index) + key);
            }
        }

        return awayLine;

    }
}//Release Unicode algorithm

class FabricAlgorithm {

    static Algorithm choseAlg(String arg) {
        switch (arg) {
            case "unicode":
                return new Unicode();
            default:
                return new  Shift();
        }

    }

}//Create algorithm object
//////////////////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////////////////
class ProcessIOData{


    static String input(String pathIn) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathIn)));
    }//Read data from file



    static void output(String data, String pathOut){
        File file = new File(pathOut);
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        }catch (IOException e){
            System.out.println("Output file: " + pathOut +"not found!");
        }
    }//Save data to file

}//Read and Save data
//////////////////////////////////////////////////////////////////////