import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Book class
class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void issueBook() {
        isAvailable = false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

// Library class
class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public boolean issueBook(String title) {
        Book book = searchBook(title);
        if (book != null && book.isAvailable()) {
            book.issueBook();
            return true;
        } else {
            return false;
        }
    }

    public boolean returnBook(String title) {
        Book book = searchBook(title);
        if (book != null && !book.isAvailable()) {
            book.returnBook();
            return true;
        } else {
            return false;
        }
    }

    public List<Book> getBooks() {
        return books;
    }
}

// Main GUI class
public class GuiLibrary {
    private Library library;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField titleField;
    private JTextField authorField;

    public GuiLibrary() {
        library = new Library();
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());

        JButton searchButton = new JButton("Search Book");
        searchButton.addActionListener(e -> searchBook());

        JButton issueButton = new JButton("Issue Book");
        issueButton.addActionListener(e -> issueBook());

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(e -> returnBook());

        JButton displayButton = new JButton("Display All Books");
        displayButton.addActionListener(e -> displayBooks());

        inputPanel.add(addButton);
        inputPanel.add(searchButton);
        inputPanel.add(issueButton);
        inputPanel.add(returnButton);
        inputPanel.add(displayButton);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        if (!title.isEmpty() && !author.isEmpty()) {
            library.addBook(new Book(title, author));
            textArea.append("Book added: " + title + "\n");
            titleField.setText("");
            authorField.setText("");
        } else {
            textArea.append("Please enter both title and author.\n");
        }
    }

    private void searchBook() {
        String title = titleField.getText();
        Book book = library.searchBook(title);
        if (book != null) {
            textArea.append("Found: " + book + "\n");
        } else {
            textArea.append("Book not found.\n");
        }
    }

    private void issueBook() {
        String title = titleField.getText();
        if (library.issueBook(title)) {
            textArea.append("Book issued: " + title + "\n");
        } else {
            textArea.append("Book not available for issue.\n");
        }
    }

    private void returnBook() {
        String title = titleField.getText();
        if (library.returnBook(title)) {
            textArea.append("Book returned: " + title + "\n");
        } else {
            textArea.append("This book is already available or not found.\n");
        }
    }

    private void displayBooks() {
        textArea.append("Books in Library:\n");
        for (Book book : library.getBooks()) {
            textArea.append(book + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiLibrary::new);
    }
}
