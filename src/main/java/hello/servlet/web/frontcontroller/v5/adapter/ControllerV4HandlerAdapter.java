package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        // handler 로 넘어온 객체가 ControllerV4 인지 확인 (맞으면 true)
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws ServletException, IOException {

        // handler(컨트롤러)를 맞게 캐스팅
        ControllerV4 controller = (ControllerV4) Handler;

        // paramMap, model 을 만들어서 해당 컨트롤러를 호출하고 뷰 이름을 반환받는다.
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        // 어댑터 변환
        // ControllerV4 는 뷰의 이름을 반환했지만, 어댑터는 이것을 ModelView 로 만들어서 형식을 맞추어 반환한다.
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    // HttpServletRequest 에서 파라미터 정보를 모두 꺼내서 Map 으로 변환
    // 해당 Map 을 컨트롤러에 전달하면서 호출
    private Map<String, String> createParamMap(HttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
