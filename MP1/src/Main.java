import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {


    private static boolean endProgram=false;
    private static BufferedReader reader;
    private static String lastUserInput;
    public static void main(String[] args) {
        Book.loadBooks();
        //loadexampleData();
        reader=new BufferedReader(new InputStreamReader(System.in));
        handleUserInteraction();

        System.exit(0);
    }
    private static void handleUserInteraction(){
        onHelpOptionSelected();
        do{
            log("Type command:");
            tryGetInputFromUser();
            //log("input: "+input);
            switch (lastUserInput){
                case "exit": case "abort": System.exit(0);break;
                case "help": onHelpOptionSelected(); break;
                case "addbook": onAddBookOptionSelected();break;
                case "updatebook": onUpdateBookOptionSelected();break;
                case "listbooks": log(Book.AllBooksToString());break;
                case "findbooks": onFindBookOptionSelected(); break;
                default: log("Incorrect option selected, try again!");
            }
        }while (!endProgram);
    }

    private static void onHelpOptionSelected(){
        log("----HELP----");
        log("help - display list of commands");
        log("exit - exit the program.");
        log("abort - abort any current process and return to main menu");
        log("addbook - add book by giving it's title, author and publishing date");
        log("updatebook - update selected book (only genre changing supported)");
        log("listbooks - list all saved books");
        log("findbooks - find books matching given query");

    }
    private static void onAddBookOptionSelected(){
        log("Provide information on added book as follows:");
        log("[Title],[Author]&[Author]&...,[Publishing Date (yyyy-mm-dd)],[book genre (optional)]" +
                ", [no. of pages], [page size (diagonal cm)], [total amount of characters]");
        log("available genres: ");
        log(Book.allBookGenresToString());
        tryGetInputFromUser();
        if(lastUserInput.equalsIgnoreCase("abort")) return;

        String[] newBookData = Arrays
                .stream(lastUserInput.split(","))
                .map(String::trim).toArray(String[]::new);
        if(Book.tryAddBook(newBookData)==null)
            log("book data received in incorrect format, yikes!");

    }
    private static void onUpdateBookOptionSelected(){
        if(Book.getBooksAmount()==0){
            log("no Books saved!");
            return;
        }
        log(Book.AllBooksToString());
        log("select index of Book to update: ");
        tryGetInputFromUser();
        if(lastUserInput.compareToIgnoreCase("abort")==0) return;
        int index=-1;
        try
        {
            index= Integer.parseInt(lastUserInput);
            if (index<1||index>=Book.getBooksAmount()){
                log("index out of bounds!");
                return;
            }
        }catch (NumberFormatException ignored){}

        log("new book genre:");
        log(Book.allBookGenresToString());
        tryGetInputFromUser();
        if(lastUserInput.equalsIgnoreCase("abort")) return;

        Book.BookGenre updatedGenre= null;
        try {updatedGenre=Book.BookGenre.valueOf(lastUserInput);}
        catch (IllegalArgumentException ignored){}

        Book updated = Book.getBook(index-1).updateBookGenre(updatedGenre);
        log("updated Book:");
        log(updated);
    }

    private static void onFindBookOptionSelected(){
        log("Title, Author, Genre or publishing date of searched Book:");
        tryGetInputFromUser();
        if(lastUserInput.equalsIgnoreCase("abort")) return;

        ArrayList<Book> results=new ArrayList<>();
        results.addAll(Book.getBooksByTitle(lastUserInput));
        results.addAll(Book.getBooksByAuthor(lastUserInput));
        try {
            results.addAll(Book.getBooksByPublishingDate(LocalDate.parse(lastUserInput)));
            results.addAll(Book.getBooksByGenre(Book.BookGenre.valueOf(lastUserInput)));
        }catch (Exception ignore){}

        //results=Book.getBooksMatchingAny(lastUserInput);
        if(results.isEmpty()){
            log("no matching Books found"); return;
        }
        log("FOUND:");
        log(Book.AllBooksToString(results));
    }
    private static void tryGetInputFromUser(){
        try {lastUserInput = reader.readLine().toLowerCase();}
        catch (IOException e) {System.exit(1);}
    }

    public static void log (Object toLog){
        System.out.println(toLog);
    }

    //DEBUG

    private static void loadexampleData()
    {
        try
        {
            BufferedReader br=new BufferedReader(new FileReader("debugBooks.txt"));
            String fileline;
            while(true){
                fileline=br.readLine();
                Book.tryAddBook(fileline.split(","));
            }
        }catch (Exception e){return;}
    }
}
