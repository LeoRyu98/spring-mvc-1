package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") // 별도의 web.xml 작성없이 annotation 사용
public class HelloServlet extends HttpServlet {

    /**
     * HTTP 요청을 통해 URL 이 호출되면 서블릿 컨테이너는 다음 메서드를 실행한다.
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // 데이터를 일일히 파싱하지 않아도 쉽게 서블릿을 통해 데이터를 읽을 수 있다.
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // HTTP Header
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // HTTP Message body
        response.getWriter().write("hello " + username);
    }
}
