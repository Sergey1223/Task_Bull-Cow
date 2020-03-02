import database.Attempt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet
/**
 * Сервлет обработки запроса на генерацию страницы попыток пользователя.
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute(
                    "userAttempts", Attempt.AttemptDao.getAll(req.getSession().getAttribute("user").toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}
