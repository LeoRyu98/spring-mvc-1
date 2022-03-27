package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * String MVC 에서 ModelAndView 와 비슷한 역할을 수행하는 클래스
 * 뷰의 이름과 뷰를 렌더링할 때 필요한 모델(Map) 객체를 가지고 있다.
 * 모델은 단순히 요청된 컨트롤러에서 뷰에 필요한 데이터를 key, value 로 넣어주면 된다.
 */
public class ModelView {

    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
