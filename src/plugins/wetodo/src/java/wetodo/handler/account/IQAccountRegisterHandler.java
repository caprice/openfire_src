package wetodo.handler.account;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.dao.UserDAO;
import wetodo.error.IQError;
import wetodo.exception.AuthCodeErrorException;
import wetodo.handler.IQBaseHandler;
import wetodo.manager.AccountManager;
import wetodo.xml.account.AccountRegisterXmlReader;
import wetodo.xml.account.AccountRegisterXmlWriter;

public class IQAccountRegisterHandler extends IQBaseHandler {

    protected String namespace = "lacool:register";

    public IQAccountRegisterHandler() {
        super();
        this.info = new IQHandlerInfo("lacool", namespace);
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:register===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            return systemError(packet, PacketError.Condition.bad_request, true);
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String username = AccountRegisterXmlReader.getUsername(lacoolElement);
        String password = AccountRegisterXmlReader.getPassword(lacoolElement);
        String nickname = AccountRegisterXmlReader.getNickname(lacoolElement);
        String phone = AccountRegisterXmlReader.getPhone(lacoolElement);
        String authCode = AccountRegisterXmlReader.getAuthCode(lacoolElement);

        // persistent to db
        try {
            AccountManager.getInstance().register(username, password, nickname, phone, authCode);
            int registGiveVipDays = 15;
            UserDAO.increaseVip(username, registGiveVipDays);
        } catch (UserAlreadyExistsException e) {
            return error(packet, IQError.Condition.username_exist, true);
        } catch (AuthCodeErrorException e) {
            return error(packet, IQError.Condition.auth_code_error, true);
        }

        // output
        return result(packet, AccountRegisterXmlWriter.write(namespace), true);
    }
}
