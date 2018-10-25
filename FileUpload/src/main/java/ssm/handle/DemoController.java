package ssm.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class.getName());


    // URL: http://localhost:8080/page/demo/rest
    @RequestMapping(Urls.PAGE_DEMO_REST)
    @ResponseBody
    public String toHelloPage(ModelMap map) {
        map.put("action", "access demo page");
        return "index.jsp111";
    }

}
