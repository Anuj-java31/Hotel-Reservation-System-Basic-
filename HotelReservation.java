import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HotelReservation {
    private static final String url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String username="root";
    private static final String password="1234";
    public static void main (String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection connection=DriverManager.getConnection(url,username,password);
            while (true){
                Scanner scanner=new Scanner(System.in);
                System.out.println("1. Make a reservation");
                System.out.println("2. View reservation");
                System.out.println("3. Get room number");
                System.out.println("4. Update reservation");
                System.out.println("5. Delete reservation");
                System.out.println("6. Exit");
                System.out.println("7. Choose an option");
                int choice=scanner.nextInt();
                switch (choice){
                    case 1:
                        makeReservation(scanner,connection);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(scanner,connection);
                        break;
                    case 4:
                        updateReservation(scanner,connection);
                        break;
                    case 5:
                        deleteReservation(scanner,connection);
                        break;
                    case 6:
                        exit();
                        scanner.close();
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice PLEASE TRY AGAIN...");
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    public static void makeReservation(Scanner scanner,Connection connection) throws SQLException{
        scanner.nextLine();
        System.out.print("Name: ");
        String name=scanner.nextLine();
        System.out.print("Room no. : ");
        int room=scanner.nextInt();
        System.out.print("Contact number: ");
        int contactNum=scanner.nextInt();
        System.out.println();
        String query="INSERT INTO reservations (name,room_no,contact_no) VALUES ('"+name+"', "+room+", "+contactNum+");";
        try(Statement statement=connection.createStatement()){
            int temp=statement.executeUpdate(query);
            if(temp==1){
                System.out.println("Reservation Successful");
            }else{
                System.out.println("Reservation Failed");
            }
        }catch (SQLException e){
            System.out.println("There something went wrong");
        }
    }
    public static void updateReservation(Scanner scanner,Connection connection){
        try{
            System.out.println("Enter Reservation ID to update");
            int reservationID=scanner.nextInt();
            scanner.nextLine();
            if(!reservationExists(connection,reservationID)){
                System.out.println("No reservation exits for this ID");
                return;
            }
            System.out.print("Enter new guest name: ");
            String name=scanner.nextLine();
            System.out.println();
            System.out.print("Enter new room number: ");
            int room=scanner.nextInt();
            System.out.print("Enter new contact number: ");
            int contact=scanner.nextInt();
            String query = "UPDATE reservations SET name='" + name + "', room_no=" + room + ", contact_no='" + contact + "' WHERE reservation_id=" + reservationID + ";";
            Statement statement=connection.createStatement();
            int temp=statement.executeUpdate(query);
            if(temp>0){
                System.out.println("Update Successful");
            }else{
                System.out.println("Update Failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean reservationExists(Connection connection,int reservationID){
        try{
            String query="SELECT reservation_id FROM reservations WHERE reservation_id="+reservationID;
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            return resultSet.next();
        }catch (Exception e){
            return false;
        }
    }
    public static void deleteReservation(Scanner scanner,Connection connection){
        try{
            System.out.println("Enter Reservation ID to delete");
            int reservationID=scanner.nextInt();
            scanner.nextLine();
            if(!reservationExists(connection, reservationID)){
                System.out.println("No reservation exits for this ID");
                return;
            }
            String query="DELETE FROM reservations WHERE reservation_id="+reservationID;
            Statement statement=connection.createStatement();
            int temp=statement.executeUpdate(query);
            if(temp>0){
                System.out.println("Deletion Successful");
            }else{
                System.out.println("Deletion Failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewReservation(Connection connection){
        String sql = "SELECT reservation_id, name, room_no, contact_no, reservation_date FROM reservations";

        try (Statement statement = connection.createStatement()){
             ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("name");
                int roomNumber = resultSet.getInt("room_no");
                String contactNumber = resultSet.getString("contact_no");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                // Format and display the reservation data in a table-like format
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void getRoomNumber(Scanner scanner,Connection connection){
        try {
            System.out.println("Enter Reservation ID to get Room number");
            int reservationID = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExists(connection, reservationID)) {
                System.out.println("No reservation exits for this ID");
                return;
            }
            String query="SELECT room_no FROM reservations WHERE reservation_id="+reservationID;
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if (resultSet.next()){
                System.out.println("Room number is "+resultSet.getInt("room_no"));
            }else{
                System.out.println("No reservation exits for this ID");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void exit() throws InterruptedException{
        System.out.print("Exiting System");
        for(int i=0;i<5;i++){
            System.out.print(".");
            Thread.sleep(500);
        }
        System.out.println();
        System.out.println("Thanks for using Hotel Reservation System");
    }

}
