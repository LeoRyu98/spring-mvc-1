package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns = /front-controller/v1 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // key: 매핑 URL, value: 호출된 컨트롤러
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        // requestURI 를 조회해서 실제 호출할 컨트롤러를 controllerMap 에서 찾는다.
        String requestURI = request.getRequestURI(); // 요청 URI
        ControllerV3 controller = controllerMap.get(requestURI);

        // 만약 없다면 404 상태 코드를 반환한다.
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 클라이언트 요청 정보들을 모두 꺼내서 Map 으로 변환
        Map<String, String> paramMap = createParamMap(request);

        // 컨트롤러에서 모델과 뷰 이름이 들어있는 ModelView 객체 반환
        ModelView mv = controller.process(paramMap);

        // ModelView 객체에서 뷰 이름 반환
        String viewName = mv.getViewName(); // 논리이름 new-form

        // 뷰 리졸버 메소드로 실제 물리 뷰 경로로 변경
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response); // 렌더링
    }

    // HttpServletRequest 에서 파라미터 정보를 모두 꺼내서 Map 으로 변환
    // 해당 Map 을 컨트롤러에 전달하면서 호출
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    // 뷰 리졸버
    // 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
