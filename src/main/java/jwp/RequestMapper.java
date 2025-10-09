package jwp;

import jwp.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private final Map<String, Controller> mappings = new HashMap<>();

    public RequestMapper() {
        mappings.put("/:GET", new HomeController());
        mappings.put("/user/signup:POST", new CreateUserController());
        mappings.put("/user/list:GET", new ListUserController());
        mappings.put("/user/login:GET", new LoginUserController());
        mappings.put("/user/login:POST", new LoginUserController());
        mappings.put("/user/logout:GET", new LogoutUserController());
        mappings.put("/user/updateForm:GET", new UpdateFormController());
        mappings.put("/user/update:POST", new UpdateUserController());
    }

    public Controller getMapping(String requestURI, String method) {
        return mappings.get(requestURI + ":" + method);
    }
}
