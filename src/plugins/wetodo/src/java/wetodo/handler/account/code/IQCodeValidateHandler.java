package wetodo.handler.account.code;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.error.IQError;
import wetodo.handler.IQBaseHandler;
import wetodo.manager.CodeManager;
import wetodo.xml.account.code.CodeValidateXmlReader;
import wetodo.xml.account.code.CodeValidateXmlWriter;

public class IQCodeValidateHandler extends IQBaseHandler {
    protected String namespace = "lacool:register:verify:auth_code";

    public IQCodeValidateHandler() {
        super();
        this.info = new IQHandlerInfo("lacool", namespace);
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:register:verify:auth_code===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            return systemError(packet, PacketError.Condition.bad_request, true);
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String phone = CodeValidateXmlReader.getPhone(lacoolElement);
        String authCode = CodeValidateXmlReader.getAuthCode(lacoolElement);

        // persistent to db
        boolean ret = CodeManager.getInstance().validate(phone, authCode);
        if (!ret) {
            return error(packet, IQError.Condition.auth_code_error, true);
        }

        // output
        return result(packet, CodeValidateXmlWriter.write(namespace), true);
    }
}
