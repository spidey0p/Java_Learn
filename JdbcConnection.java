import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcConnection {

    // Database credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase"; // Replace 'mydatabase' with your database name
    static final String USER = "root"; // Replace with your database username
    static final String PASS = "password"; // Replace with your database password

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // 1. Register JDBC driver (optional for most modern drivers)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Open a connection
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 3. Execute a query to create a table
            System.out.println("Creating table...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Employees (" +
                    "id INT AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "position VARCHAR(255), " +
                    "salary DOUBLE, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully...");

            // 4. Insert data into the table
            System.out.println("Inserting data into table...");
            sql = "INSERT INTO Employees (name, position, salary) VALUES ('John Doe', 'Manager', 5000), " +
                    "('Jane Smith', 'Developer', 4000), " +
                    "('Mike Johnson', 'Developer', 4000)";
            stmt.executeUpdate(sql);
            System.out.println("Data inserted successfully...");

            // 5. Update data in the table
            System.out.println("Updating data in table...");
            sql = "UPDATE Employees SET salary = 4500 WHERE position = 'Developer'";
            stmt.executeUpdate(sql);
            System.out.println("Data updated successfully...");

            // 6. Delete data from the table
            System.out.println("Deleting data from table...");
            sql = "DELETE FROM Employees WHERE name = 'John Doe'";
            stmt.executeUpdate(sql);
            System.out.println("Data deleted successfully...");

            // 7. Perform a JOIN query
            System.out.println("Creating Departments table...");
            sql = "CREATE TABLE IF NOT EXISTS Departments (" +
                    "id INT AUTO_INCREMENT, " +
                    "dept_name VARCHAR(255), " +
                    "emp_id INT, " +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (emp_id) REFERENCES Employees(id))";
            stmt.executeUpdate(sql);
            System.out.println("Departments table created successfully...");

            System.out.println("Performing JOIN query...");
            sql = "SELECT Employees.name, Employees.position, Departments.dept_name " +
                    "FROM Employees " +
                    "INNER JOIN Departments ON Employees.id = Departments.emp_id";
            ResultSet rs = stmt.executeQuery(sql);

            // 8. Extract data from result set
            while (rs.next()) {
                String name = rs.getString("name");
                String position = rs.getString("position");
                String deptName = rs.getString("dept_name");

                // Display values
                System.out.print("Name: " + name);
                System.out.print(", Position: " + position);
                System.out.println(", Department: " + deptName);
            }
            rs.close();

        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
