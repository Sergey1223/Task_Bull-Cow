package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс соединения с БД и выполнения запросов
 */
public class H2Connector {
    /**
     * Ссылка на единственный экземпляр класса
     */
    private static H2Connector instance;

    /**
     * Соединение
     */
    private static Connection conn;

    /**
     * Конструктор инициализации БД
     */
    private H2Connector() {
        initDB();
    }

    /**
     * Возвращает соединение. Необходим для закрытия соединения извне после получения результата из ResultSet
     * @return соединение
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Инициализирует БД. Создает таблици, если их не существует.
     */
    private void initDB() {
        try {
            Class.forName("org.h2.Driver").getDeclaredConstructor().newInstance();

            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "Login VARCHAR(15) PRIMARY KEY," +
                    "Password VARCHAR(20));" +
                    "CREATE TABLE IF NOT EXISTS Attempts (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT," +
                    "Login VARCHAR(15) REFERENCES Users(Login)," +
                    "Value INT," +
                    "Date DATETIME);";
            executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Возвращает экземпляр класса соединения
     * @return класс соединения
     */
    public static H2Connector getInstance() {
        if (instance == null) {
            instance = new H2Connector();
        }
        return instance;
    }

    /**
     * Выполнчет запрос на добавление данных в БД
     * @param sql строка запроса
     * @throws SQLException
     */
    public void executeUpdate(String sql) throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

        conn.createStatement().executeUpdate(sql);
        conn.close();
    }

    /**
     * Выполняет запрос на получение данных из БД.
     * @param sql строка запроса
     * @return результат запроса
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

        return conn.createStatement().executeQuery(sql);
    }
}
