package wetodo.error;

public class IAPError {

    public enum Condition {
        success("success", 0),

        json_error("json_error", 21000),
        receipt_error("receipt_error", 21002),
        receipt_valid_error("receipt_valid_error", 21003),
        shared_secret_error("shared_secret_error", 21004),
        server_error("server_error", 21005),
        subscribe_expire("subscribe_expire", 21006),
        sandbox_receipt("sandbox_receipt", 21007),
        production_receipt("production_receipt", 21008);
        private String msg;
        private int code;

        private Condition(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }
}
