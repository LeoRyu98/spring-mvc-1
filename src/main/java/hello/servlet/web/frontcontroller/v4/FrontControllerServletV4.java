package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        String requestURI = request.getRequestURI(); // 요청 URI

        ControllerV4 controller = controllerMap.get(requestURI);
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);

        // 모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다.
        // 컨트롤러에서 모델 객체에 값을 담으면 여기에 그대로 담겨있게 된다.
        // V3 에서는 요청된 컨트롤러 내부에서 모델을 생성 후 값을 넣어서 모델을 반환했다.
        // V4 에서는 프론트 컨트롤러에서 미리 빈 모델 객체를 만들어서 요청된 컨트롤러에 보낸다.
        // 요청된 컨트롤러 안에서 파라미터로 들어온 모델에 값을 넣고 반환 값은 뷰 경로만 반환한다.
        Map<String, Object> model = new HashMap<>(); // 추가

        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);
        view.render(model, request, response);
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
