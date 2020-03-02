import database.UserAccount;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet
/**
 * Сервлет обработки запросов авторизации.
 * В случае успешного входа/регистрации перенаправляет пользователя на страницу игры.
 */
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserAccount userAccount = UserAccount.UserAccountDao.get(req.getParameter("logIn"));

            // Запись не существует в БД
            if (userAccount == null) {
                // Регистрация
                if (req.getSession().getAttribute("auth").equals("signUp")) {
                    userAccount = new UserAccount(req.getParameter("logIn"), req.getParameter("password"));

                    UserAccount.UserAccountDao.save(userAccount);


                    req.getSession().setAttribute("user", userAccount.getLogIn());
                    resp.sendRedirect("/bull_cow/game.jsp");
                }
                // Вход
                else {
                    req.setAttribute("msg", "Неверный логин/пароль");
                    req.getRequestDispatcher("/logIn.jsp").forward(req, resp);
                }
            }
            else {
                // Регистрация
                if (req.getSession().getAttribute("auth").equals("signUp")) {
                    req.setAttribute("msg", "Данный логин занят");
                    req.getRequestDispatcher("/signUp.jsp").forward(req, resp);
                }
                // Вход
                else {
                    req.getSession().setAttribute("user", userAccount.getLogIn());
                    resp.sendRedirect("/bull_cow/game.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
