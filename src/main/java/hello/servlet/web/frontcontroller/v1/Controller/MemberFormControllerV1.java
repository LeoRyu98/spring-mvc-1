package hello.servlet.web.frontcontroller.v1.Controller;

import hello.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 해당 경로의 뷰(View)를 찾는다.
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        // 다른 서블릿이나 JSP 로 이동 (View 로 제어권을 넘겨준다)
        // 서버 내부에서 다시 호출이 발생한다.
        dispatcher.forward(request, response);
    }
}
