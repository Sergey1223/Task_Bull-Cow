package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс аккаунта пользователя
 */
public class UserAccount {
    /**
     * Логин пользователя. Логин должен содержать не менее 4-х и не более 15-ти символов.
     * Не допускаются символы кириллицы и пробелы
     */
    private String logIn;

    /**
     * Пароль пользователя. Пароль должен содеражать не менее 4-х и не более 20-ти символов
     */
    private String password;

    /**
     * Создание объекта для записи в БД
     * @param logIn логин
     * @param password пароль
     */
    public UserAccount(String logIn, String password) {
        this.logIn = logIn;
        this.password = password;
    }

    /**
     * Возвращает значение поля {@link UserAccount#logIn}
     * @return логин
     */
    public String getLogIn() {
        return logIn;
    }

    /**
     * Возвращает значение поля {@link UserAccount#password}
     * @return пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Утилитарный класс для записи/воссоздания {@link UserAccount} в/из БД
     */
    public static class UserAccountDao {

        /**
         * Сохраянет данные аккаунта в БД
         * @param userAccount аккаунт
         * @throws SQLException
         */
        public static void save(UserAccount userAccount) throws SQLException {
            String sql = "INSERT INTO Users VALUES('" +
                    userAccount.getLogIn() + "', '" +
                    userAccount.getPassword() + "');";

            H2Connector.getInstance().executeUpdate(sql);
        }

        /**
         * Возвращает акаааунт из БД, если он существует, иначе возвращает null
         * @param logIn логин
         * @return  акаунт
         * @throws SQLException
         */
        public static UserAccount get(String logIn) throws SQLException {
            String sql = "SELECT * FROM Users WHERE Login = '" + logIn + "';";

            UserAccount userAccount = null;
            ResultSet queryResult = H2Connector.getInstance().executeQuery(sql);

            if (queryResult.next()) {
                userAccount = new UserAccount(queryResult.getString(1), queryResult.getString(2));
            }

            H2Connector.getInstance().getConn().close();

            return userAccount;
        }
    }
}