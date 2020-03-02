package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

/**
 * Класс попытки.
 * Попытка - количество вариантов, которое потребовалось пользоваетлю на успешное угадывание
 */
public class Attempt {
    /**
     * Логин пользователя
     */
    private String logIn;

    /**
     * Количество вариантов
     */
    private int value;

    /**
     * Дата проверки успешного варианта
     */
    private Date date;

    /**
     * Создание объекта для записи в БД
     * @param logIn логин пользователя
     * @param value количество вариантов
     */
    public Attempt (String logIn, int value) {
        this.logIn = logIn;
        this.value = value;
    }

    /**
     * Создание объекта для воссоздания из БД
     * @param logIn логин пользователя
     * @param value количество вариантов
     * @param date дата проверки успешного варианта
     */
    public Attempt (String logIn, int value, Date date) {
        this(logIn, value);
        this.date = date;
    }

    /**
     * Возвращает значение поля {@link Attempt#logIn}
     * @return логин
     */
    public String getLogIn() {
        return logIn;
    }

    /**
     * Возвращает значение поля {@link Attempt#value}
     * @return количество вариантов
     */
    public int getValue() {
        return value;
    }

    /**
     * Возвращает значение поля {@link Attempt#date}
     * @return дата проверки успешного варианта
     */
    public Date getDate() {
        return date;
    }

    /**
     * Утилитарный класс для записи/воссоздания {@link Attempt} в/из БД
     */
    public static class AttemptDao {

        /**
         * Записывает попытку в БД
         * @param attempt попытка
         * @throws SQLException
         */
        public static void save(Attempt attempt) throws SQLException {
            String sql = "INSERT INTO Attempts(Login, Value, Date) VALUES('" +
                    attempt.getLogIn() + "', " +
                    attempt.getValue() + ", " +
                    "(SELECT NOW()));";

            H2Connector.getInstance().executeUpdate(sql);
        }

        /**
         * Возвращает все попытки пользователя
         * @param logIn логин пользователя
         * @return попытки пользователя
         * @throws SQLException
         */
        public static ArrayList<Attempt> getAll(String logIn) throws SQLException {
            String sql = "SELECT Login, Value, Date FROM Attempts WHERE Login = '" + logIn + "';";

            ArrayList<Attempt> attempts = new ArrayList<>();

            ResultSet queryResult = H2Connector.getInstance().executeQuery(sql);

            while (queryResult.next()) {
                attempts.add(new Attempt(
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getDate(3, new GregorianCalendar())));
            }

            H2Connector.getInstance().getConn().close();

            return attempts;
        }

        /**
         * Возращает рейтинг всех пользователей на основе их среденего количества предложенных вариантов
         * @return отображение в виде дерева, где ключ - среденее количество вариантов во всех попытках, значение - логин пользователя
         * @throws SQLException
         */
        public static TreeMap getRating () throws SQLException {
            String sql = "SELECT Login, AVG(Value) FROM Attempts GROUP BY Login;";

            TreeMap rating = new TreeMap();

            ResultSet queryResult = H2Connector.getInstance().executeQuery(sql);

            while (queryResult.next()) {
                rating.put(queryResult.getDouble(2), queryResult.getString(1));
            }

            H2Connector.getInstance().getConn().close();

            return rating;
        }
    }
}
