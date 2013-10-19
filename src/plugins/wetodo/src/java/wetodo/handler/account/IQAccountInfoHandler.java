package wetodo.handler.account;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketError;
import wetodo.dao.UserDAO;
import wetodo.model.User;
import wetodo.xml.account.AccountInfoXmlWriter;

public class IQAccountInfoHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:member:query:deadline";
    private IQHandlerInfo info;

    public IQAccountInfoHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:member:query:deadline===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        JID userJid = packet.getFrom();
        String username = userJid.getNode();

        // persistent to db
        User user = UserDAO.findByUsername(username);

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = AccountInfoXmlWriter.write(NAME_SPACE, user);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
    }
}
