package wetodo.handler.account.code;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.session.ClientSession;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.error.IQError;
import wetodo.manager.CodeManager;
import wetodo.xml.account.code.CodeValidateXmlReader;
import wetodo.xml.account.code.CodeValidateXmlWriter;

public class IQCodeValidateHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:register:verify:auth_code";
    private IQHandlerInfo info;
    private CodeManager codeManager;

    public IQCodeValidateHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:register:verify:auth_code===");

        ClientSession session = sessionManager.getSession(packet.getFrom());

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String phone = CodeValidateXmlReader.getPhone(lacoolElement);
        String authCode = CodeValidateXmlReader.getAuthCode(lacoolElement);

        // persistent to db
        boolean ret = codeManager.validate(phone, authCode);
        if (!ret) {
            IQ result = IQ.createResultIQ(packet);
            result.setType(IQ.Type.error);
            result.setChildElement(IQError.getError(packet.getChildElement().createCopy(), IQError.Condition.auth_code_error));

            session.process(result);
            return result;
        }

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = CodeValidateXmlWriter.write(NAME_SPACE);
        reply.setChildElement(reasonElement);

        session.process(reply);
        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        codeManager = new CodeManager();
    }
}
