package expenses.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth/access-code")
public class AccessCodeController {

    @RequestMapping(params="error")
    public String error() {
        return "oauth/access-code/deny";
    }

    @RequestMapping(params="code")
    public String view(@RequestParam String code) {
        return "oauth/access-code/show";
    }
}
