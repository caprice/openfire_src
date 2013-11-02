package wetodo.handler.account;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.dao.UserDAO;
import wetodo.error.IQError;
import wetodo.manager.AccountManager;
import wetodo.xml.account.AccountRegisterXmlReader;
import wetodo.xml.account.AccountRegisterXmlWriter;

public class IQAccountRegisterHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:register";
    private IQHandlerInfo info;
    private AccountManager accountManager;

    public IQAccountRegisterHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:register===");

        ClientSession session = sessionManager.getSession(packet.getFrom());

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);

            session.process(result);
            return result;
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
            accountManager.register(username, password, nickname, phone, authCode);
            int registGiveVipDays = 15;
            UserDAO.increaseVip(username, registGiveVipDays);
        } catch (UserAlreadyExistsException e) {
            IQ result = IQ.createResultIQ(packet);
            result.setType(IQ.Type.error);
            result.setChildElement(IQError.getError(packet.getChildElement().createCopy(), IQError.Condition.username_exist));

            session.process(result);
            return result;
        }

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = AccountRegisterXmlWriter.write(NAME_SPACE);
        reply.setChildElement(reasonElement);

        session.process(reply);
        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        accountManager = new AccountManager();
    }
}
