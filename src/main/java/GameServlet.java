import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet
/**
 * Сервлет обработки запросов на проверку предложенного пользователем варианта.
 */
public class GameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        try (PrintWriter writer = resp.getWriter()){
            writer.print(Game.getInstance().check(
                    req.getParameter("number"), req.getSession().getAttribute("user").toString()));
        }
    }
}
