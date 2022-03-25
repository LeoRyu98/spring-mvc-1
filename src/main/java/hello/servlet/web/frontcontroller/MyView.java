package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 해당 경로(viewPath) 서블릿이나 JSP 로 이동 (View 로 제어권을 넘겨준다)
 * 서버 내부에서 다시 호출이 발생(forward)한다.
 * JSP 로 forward 해서 JSP 를 렌더링 한다.
 */
public class MyView {

    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    /**
     * model 에 담긴 data 를 모두 꺼내서 request.setAttribute 에 넣는다.
     * JSP 는 request.setAttribute 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서 담아둔다.
     * (다른 뷰 템플릿은 다른 방식을 사용한다)
     */
    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, value) -> request.setAttribute(key, value));
    }
}
