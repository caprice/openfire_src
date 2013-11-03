package wetodo.handler.account.code;

import com.twilio.sdk.TwilioRestException;
import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.error.IQError;
import wetodo.exception.AuthCodeOverloadException;
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

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            return systemError(packet, PacketError.Condition.bad_request, true);
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String phone = CodeSendXmlReader.getPhone(lacoolElement);
        String countryCode = CodeSendXmlReader.getCountryCode(lacoolElement);

        // persistent to db
        try {
            CodeManager.getInstance().send(phone, countryCode);
        } catch (TwilioRestException e) {
            return error(packet, IQError.Condition.sms_server_error, true);
        } catch (UserAlreadyExistsException e) {
            return error(packet, IQError.Condition.username_exist, true);
        } catch (AuthCodeOverloadException e) {
            return error(packet, IQError.Condition.auth_code_overload, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // output
        return result(packet, CodeSendXmlWriter.write(namespace), true);
    }
}
