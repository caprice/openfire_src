package wetodo.handler.account.code;

import com.twilio.sdk.TwilioRestException;
import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.error.IQError;
import wetodo.handler.IQBaseHandler;
import wetodo.manager.CodeManager;
import wetodo.xml.account.code.CodeSendXmlReader;
import wetodo.xml.account.code.CodeSendXmlWriter;

public class IQCodeSendHandler extends IQBaseHandler {
    protected String namespace = "lacool:register:query:auth_code";

    public IQCodeSendHandler() {
        super();
        this.info = new IQHandlerInfo("lacool", namespace);
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:register:query:auth_code===");

        ClientSession session = sessionManager.getSession(packet.getFrom());

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);

            // difference, need invoke session.process() method
            session.process(result);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String phone = CodeSendXmlReader.getPhone(lacoolElement);
        String countryCode = CodeSendXmlReader.getCountryCode(lacoolElement);

        // persistent to db
        try {
            CodeManager.getInstance().send(phone, countryCode);
        } catch (TwilioRestException e) {
            e.printStackTrace();
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
        Element reasonElement = CodeSendXmlWriter.write(namespace);
        reply.setChildElement(reasonElement);

        // difference, need invoke session.process() method
        session.process(reply);
        return reply;
    }
}
