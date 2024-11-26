import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Book implements Serializable
{

    private static final String BOOKS_FILE_PATH="books.txt";
    private static final double MINIMAL_BOOK_SIZE=15.0; //in cm diagonal

    private static ArrayList<Book> Books=new ArrayList<>();
    private String _title;
    private List<String> _authors;
    private LocalDate _publishingDate;
    private BookGenre _bookGenre =null;
    private BookDetails _bookDetails;


    public Book(String title, List<String> author, LocalDate publishingDate, BookGenre bookGenre,
                int pages, double pageSize, int characterCount){
        _title=title;
        _authors =author;
        _publishingDate=publishingDate;
        _bookGenre=bookGenre;
        _bookDetails=new BookDetails(pages,pageSize,characterCount);
    }

    public Book(String[] newBookData)
    {
        this(
            newBookData[0],                                     //title
            Arrays.asList(newBookData[1].split("&")),     //authors
            LocalDate.parse(newBookData[2]),                    //publishing date
            Book.BookGenre.valueOf(newBookData[3]),             //(optional) genre
            Integer.parseInt(newBookData[4]),                   //page count
            Double.parseDouble(newBookData[5]),                 //page size
            Integer.parseInt(newBookData[6])                    //character count
        );
    }


    public static ArrayList<Book> getBooksByAuthor(String author){
        return Books.stream()
                .filter(book -> book._authors.stream().anyMatch(a->a.equalsIgnoreCase(author)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<Book> getBooksByTitle(String title){
        return Books.stream()
                .filter(book -> book._title.equalsIgnoreCase(title))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<Book> getBooksByPublishingDate(LocalDate publishingDate){
        return Books.stream()
                .filter(book -> (book._publishingDate.compareTo(publishingDate) == 0))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<Book> getBooksByGenre(BookGenre bookGenre){
        return Books.stream()
                .filter(book -> Objects.equals(book._bookGenre,bookGenre))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Book getBook(int index){return Books.get(index);}
    public static int getBooksAmount(){return Books.size();}

    public Book updateBookGenre(BookGenre newGenre){
        _bookGenre =newGenre;
        return this;
    }

    public static String AllBooksToString(){return AllBooksToString(Books);}

    public static String AllBooksToString(ArrayList<Book> books){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<books.size();i++)
            stringBuilder.append(i+1).append(". ").append(books.get(i)).append("\n");
        return stringBuilder.toString();
    }

    public static Book tryAddBook(String[] rawBookData){
        try
        {
            Book newBook = new Book(rawBookData);
            Books.add(newBook);
            saveBooks();
            return newBook;
        }catch (IllegalArgumentException e){return null;}
    }

    public static void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE_PATH))) {
            for (Book book : Books) oos.writeObject(book);}
        catch (IOException e) {e.printStackTrace();}
    }
    public static void loadBooks()
    {
        Books.clear();
        File file = new File(BOOKS_FILE_PATH);
        if (file.exists() && file.length() != 0)
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
            {
                while (true)
                {
                    Book book = (Book) ois.readObject();
                    Books.add(book);
                }
            } catch (Exception ignored){}
    }
    public static String allBookGenresToString(){
        //return Stream.of(Arrays.stream(BookGenre.values()).map(BookGenre::name)).toString();
        String genres="";
        for(BookGenre BookGenreValue:BookGenre.values())
            genres+=BookGenreValue+"\n";
        return genres;
    }

    @Override
    public String toString() {
        return "Title: " + _title + " | " +
                "Authors: " + _authors + " | " +
                "Publishing Date: " + _publishingDate + " | " +
                "Genre: " + ((_bookGenre ==null)?"unknown": _bookGenre) + " | " +
                "Details: "+_bookDetails;
    }


    public enum BookGenre{
        romance,
        fantasy,
        thriller,
        science_Fiction,
        science
    }
    public class BookDetails implements Serializable{
        public int pages;
        public double pageSize; //in diagonal cm
        public int totalLength;
        public int getAvgPageDensity() {return totalLength/pages;}

        public BookDetails(int pages, double pageSize, int totalLength) {
            if(pageSize<MINIMAL_BOOK_SIZE) throw new IllegalArgumentException();
            this.pages = pages;
            this.pageSize = pageSize;
            this.totalLength = totalLength;
        }

        @Override
        public String toString()
        {
            return " pages: " + pages +
                    ", pageSize: " + pageSize +
                    ", totalLength: " + totalLength+
                    ", avg. page density: " + getAvgPageDensity();
        }
    }
}

