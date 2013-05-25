package wetodo;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;

public class RegisterIQHandler extends IQHandler {

    private static final String NAME_SPACE = "lacool:iq:register";

    private IQHandlerInfo info;

    public RegisterIQHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) throws UnauthorizedException {
        System.out.println("===lacool:iq:register===");

        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reason = DocumentHelper.createElement("lacool");
        reason.addNamespace("", NAME_SPACE);
        reply.setChildElement(reason);
        return reply;
    }
}
