package wetodo.manager;

import com.twilio.sdk.TwilioRestException;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import wetodo.dao.UserDAO;
import wetodo.exception.AuthCodeOverloadException;
import wetodo.model.User;
import wetodo.sms.Code;
import wetodo.sms.GuardHelper;
import wetodo.sms.SMS;

public class CodeManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static CodeManager instance;

    public static CodeManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (CodeManager.class) {
                instance = new CodeManager();
            }
        }
        return instance;
    }

    public static boolean send(String phone, String countryCode) throws TwilioRestException, UserAlreadyExistsException, AuthCodeOverloadException {
        User user = UserDAO.findByUsername(phone);
        if (user != null) {
            throw new UserAlreadyExistsException();
        }
        if (GuardHelper.isOverload(phone)) {
            throw new AuthCodeOverloadException();
        }
        long code = Code.get(phone);
        SMS.send(phone, countryCode, code);
        GuardHelper.fill(phone);
        return true;
    }

    public static boolean validate(String phone, String authCode) {
        try {
            return Code.validate(phone, Long.parseLong(authCode));
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
