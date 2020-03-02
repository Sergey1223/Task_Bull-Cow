import database.Attempt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
/**
 * Сервлет обработки запроса на генерацию рейтинга.
 */
@WebServlet
public class RatingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("rating", Attempt.AttemptDao.getRating());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/rating.jsp").forward(req, resp);
    }
}
