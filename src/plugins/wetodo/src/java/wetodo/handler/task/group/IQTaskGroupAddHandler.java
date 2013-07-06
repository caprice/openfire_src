package wetodo.handler.task.group;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;

public class IQTaskGroupAddHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:iq:task:group";

    private IQHandlerInfo info;

    public IQTaskGroupAddHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) throws UnauthorizedException {
        System.out.println("===lacool:iq:task:group===");

        IQ reply = IQ.createResultIQ(packet);

        Element groups = packet.getChildElement();
        System.out.println("groups:" + groups);
        Element childElement = packet.getChildElement();
        System.out.println("clildElement:" + childElement);
        String namespace = childElement.getNamespaceURI(); //xmls value
        System.out.println("namespace:" + namespace);
        Element childElementCopy = packet.getChildElement().createCopy();
        System.out.println("childElementCopy:" + childElementCopy);

        reply.setType(IQ.Type.result);
        Element reason = DocumentHelper.createElement("lacool");
        reason.addNamespace("", NAME_SPACE);
        reply.setChildElement(reason);
        return reply;
    }
}
