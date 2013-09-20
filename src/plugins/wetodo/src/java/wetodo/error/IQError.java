package wetodo.error;

import org.dom4j.Element;

public class IQError {

    public static Element getError(Element lacoolElement, Condition condition) {
        Element errorElement = lacoolElement.addElement("error");
        errorElement.addAttribute("code", condition.code + "");
        errorElement.addAttribute("msg", condition.msg);

        return lacoolElement;
    }

    public enum Condition {
        username_exist("username-exist", 2);
        private String msg;
        private int code;

        private Condition(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }
    }
}
