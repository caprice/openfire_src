package wetodo.handler;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.error.IQError;

public abstract class IQBaseHandler extends IQHandler {
    protected IQHandlerInfo info;

    public IQBaseHandler() {
        super(null);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    protected IQ systemError(IQ packet, PacketError.Condition condition) {
        IQ reply = IQ.createResultIQ(packet);
        reply.setChildElement(packet.getChildElement().createCopy());
        reply.setError(condition);
        return reply;
    }

    protected IQ error(IQ packet, IQError.Condition condition) {
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.error);
        reply.setChildElement(IQError.getError(packet.getChildElement().createCopy(), condition));
        return reply;
    }

    protected IQ result(IQ packet, Element reasonElement) {
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        reply.setChildElement(reasonElement);
        return reply;
    }
}
