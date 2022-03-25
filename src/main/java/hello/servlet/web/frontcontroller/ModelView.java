package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

/**
 * String MVC 에서 ModelAndView 와 비슷한 역할을 수행하는 클래스
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
