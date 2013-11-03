package wetodo.handler;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.session.ClientSession;
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

    protected IQ systemError(IQ packet, PacketError.Condition condition, boolean isAnonymity) {
        if (isAnonymity) {
            ClientSession session = sessionManager.getSession(packet.getFrom());
            IQ reply = IQ.createResultIQ(packet);
            reply.setChildElement(packet.getChildElement().createCopy());
            reply.setError(condition);
            session.process(reply);
            return reply;
        }
        return systemError(packet, condition);
    }

    protected IQ error(IQ packet, IQError.Condition condition) {
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.error);
        reply.setChildElement(IQError.getError(packet.getChildElement().createCopy(), condition));
        return reply;
    }

    protected IQ error(IQ packet, IQError.Condition condition, boolean isAnonymity) {
        if (isAnonymity) {
            ClientSession session = sessionManager.getSession(packet.getFrom());
            IQ reply = IQ.createResultIQ(packet);
            reply.setType(IQ.Type.error);
            reply.setChildElement(IQError.getError(packet.getChildElement().createCopy(), condition));
            session.process(reply);
            return reply;
        }
        return error(packet, condition);
    }

    protected IQ result(IQ packet, Element reasonElement) {
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        reply.setChildElement(reasonElement);
        return reply;
    }

    protected IQ result(IQ packet, Element reasonElement, boolean isAnonymity) {
        if (isAnonymity) {
            ClientSession session = sessionManager.getSession(packet.getFrom());
            IQ reply = IQ.createResultIQ(packet);
            reply.setType(IQ.Type.result);
            reply.setChildElement(reasonElement);
            session.process(reply);
            return reply;
        }
        return result(packet, reasonElement);
    }
}
