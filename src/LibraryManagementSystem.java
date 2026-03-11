import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LibraryManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static Connection con = DBConnection.getConnection();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n1 Add Book");
            System.out.println("2 View Books");
            System.out.println("3 Register Student");
            System.out.println("4 Issue Book");
            System.out.println("5 Return Book");
            System.out.println("6 Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    viewBooks();
                    break;

                case 3:
                    addStudent();
                    break;

                case 4:
                    issueBook();
                    break;

                case 5:
                    returnBook();
                    break;

                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void addBook() {
        try {
            System.out.print("Book Name: ");
            String name = sc.next();

            System.out.print("Author: ");
            String author = sc.next();

            System.out.print("Quantity: ");
            int qty = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "insert into books(name,author,quantity) values(?,?,?)");

            ps.setString(1, name);
            ps.setString(2, author);
            ps.setInt(3, qty);

            ps.executeUpdate();

            System.out.println("Book Added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewBooks() {
        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from books");

            while (rs.next()) {

                System.out.println(
                        rs.getInt(1) + " "
                                + rs.getString(2) + " "
                                + rs.getString(3) + " "
                                + rs.getInt(4));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addStudent() {
        try {

            System.out.print("Student Name: ");
            String name = sc.next();

            PreparedStatement ps = con.prepareStatement(
                    "insert into students(name) values(?)");

            ps.setString(1, name);

            ps.executeUpdate();

            System.out.println("Student Registered");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void issueBook() {
        try {

            System.out.print("Student ID: ");
            int sid = sc.nextInt();

            System.out.print("Book ID: ");
            int bid = sc.nextInt();

            PreparedStatement check = con.prepareStatement(
                    "select quantity from books where id=?");

            check.setInt(1, bid);

            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

                PreparedStatement issue = con.prepareStatement(
                        "insert into issue_book(student_id,book_id,issue_date,fine) values(?,?,CURDATE(),0)");

                issue.setInt(1, sid);
                issue.setInt(2, bid);

                issue.executeUpdate();

                PreparedStatement update = con.prepareStatement(
                        "update books set quantity=quantity-1 where id=?");

                update.setInt(1, bid);
                update.executeUpdate();

                System.out.println("Book Issued");

            } else {
                System.out.println("Book Not Available");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void returnBook() {
        try {

            System.out.print("Issue ID: ");
            int iid = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                    "select issue_date,book_id from issue_book where issue_id=?");

            ps.setInt(1, iid);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                java.util.Date issueDate = rs.getDate(1);
                int bookId = rs.getInt(2);

                long diff = (new java.util.Date().getTime() - issueDate.getTime()) / (1000 * 60 * 60 * 24);

                int fine = 0;

                if (diff > 7) {
                    fine = (int) (diff - 7) * 10;
                }

                PreparedStatement update = con.prepareStatement(
                        "update issue_book set return_date=CURDATE(), fine=? where issue_id=?");

                update.setInt(1, fine);
                update.setInt(2, iid);

                update.executeUpdate();

                PreparedStatement bookUpdate = con.prepareStatement(
                        "update books set quantity=quantity+1 where id=?");

                bookUpdate.setInt(1, bookId);
                bookUpdate.executeUpdate();

                System.out.println("Returned. Fine = " + fine);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
