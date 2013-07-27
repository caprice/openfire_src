package wetodo.handler.account.code;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.CodeManager;
import wetodo.xml.account.code.CodeSendXmlReader;
import wetodo.xml.account.code.CodeSendXmlWriter;

public class IQCodeSendHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:register:query:auth_code";
    private IQHandlerInfo info;
    private CodeManager codeManager;

    public IQCodeSendHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:register:query:auth_code===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String phone = CodeSendXmlReader.getPhone(lacoolElement);

        // persistent to db
        codeManager.send(phone);

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = CodeSendXmlWriter.write(NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        codeManager = new CodeManager();
    }
}
