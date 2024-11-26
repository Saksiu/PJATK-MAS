import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    private static boolean endProgram=false;
    private static BufferedReader reader;
    private static String[] lastUserInput;
    
    public static void main(String[] args) {
        reader=new BufferedReader(new InputStreamReader(System.in));
        handleUserInteraction();

        System.exit(0);
    }

    private static void handleUserInteraction(){
        onHelpOptionSelected();
        do{
            logAllInfo();
            log("Type command:");
            tryGetInputFromUser();
            //log("input: "+input);
            try
            {
                switch (lastUserInput[0]){
                    case "exit": case "abort": System.exit(0);break;
                    case "help": onHelpOptionSelected(); break;
                    case "add":
                        if(lastUserInput.length<2) throw new IllegalArgumentException("no type of object specified");
                        onAddOptionSelected(lastUserInput[1]);
                        break;
                    case "sign": onSignOptionSelected();break;
                    default: throw new IllegalArgumentException("no such command");
                }

            }catch (IllegalArgumentException e){log("error executing command "+lastUserInput[0]+":\n"+e.getMessage());}//e.printStackTrace();}
        }while (!endProgram);
    }

    private static void onHelpOptionSelected(){
        log("----HELP----");
        log("help - display list of commands");
        log("exit - exit the program.");
        log("abort - abort any current process and return to main menu");
        log("add <type> - begin creating the corresponding type object");
        log("sign - create an Endorsement Deal between a Company and a Player");
    }

    private static void onAddOptionSelected(String type) throws IllegalArgumentException{
        log("provide information according to the following format:");
        switch (type){
            case "player":
                log(Player.ConstructionFormat());
                log(Position.AllPositionsToString());
                tryGetInputFromUser(2,4);
                Player.Create(lastUserInput);
                break;
            case "agent":
                log(Agent.ConstructionFormat());
                tryGetInputFromUser(1,Integer.MAX_VALUE);
                Agent.Create(lastUserInput);
                break;
            case "team":
                log(Team.ConstructionFormat());
                log(Position.AllPositionsToString());
                tryGetInputFromUser(1,Team.MAX_TEAM_SIZE+1);
                Team.Create(lastUserInput);
                break;
            case "equipment": case "eq":
                log(Equipment.ConstructionFormat());
                log(Equipment.Condition.allConditionsToString());
                tryGetInputFromUser(4);
                Equipment.Create(lastUserInput);
                break;
            case "company":
                log(Company.ConstructionFormat());
                tryGetInputFromUser(1);
                Company.Create(lastUserInput);
                break;
            default: throw new IllegalArgumentException("no such type as \""+ type+"\"\ncreatable objects:\n" +
                    "agent, player, team, equipment/eq, company");
        }
    }

    private static void onSignOptionSelected(){
        log("provide information according to the following format:");
        log(EndorsementDeal.ConstructionFormat());
        tryGetInputFromUser(5);
        EndorsementDeal.Create(lastUserInput);
    }

    private static void tryGetInputFromUser(int argAmount)throws IllegalArgumentException{
        tryGetInputFromUser(argAmount,argAmount);
    }
    private static void tryGetInputFromUser() throws IllegalArgumentException{
        tryGetInputFromUser(1,Integer.MAX_VALUE);}

    private static void tryGetInputFromUser(int minArgAmount, int  maxArgAmount) throws IllegalArgumentException{
        try {
            lastUserInput = reader.readLine().toLowerCase().trim().split(" ");
            if(lastUserInput.length<minArgAmount||lastUserInput.length>maxArgAmount)
                throw new IllegalArgumentException("incorrect amount of input details, " +
                        "expected between "+minArgAmount+" and "+maxArgAmount+" args, " +
                        "instead received "+ lastUserInput.length);
        }
        catch (IOException e) {System.exit(1);}
    }

    private static void logAllInfo(){
        log(Player.ExtensionToString());
        log(Agent.ExtensionToString());
        log(Team.ExtensionToString());
        log(Company.ExtensionToString());
        log(EndorsementDeal.ExtensionToString());
    }

    public static void log (Object toLog){
        System.out.println(toLog);
    }
}
