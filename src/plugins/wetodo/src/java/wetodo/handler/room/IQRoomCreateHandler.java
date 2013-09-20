package wetodo.handler.room;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketError;
import wetodo.manager.RoomManager;
import wetodo.model.Room;
import wetodo.xml.room.RoomAddXmlReader;
import wetodo.xml.room.RoomAddXmlWriter;

public class IQRoomCreateHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:muc:create:room";
    private IQHandlerInfo info;
    private RoomManager roomManager;

    public IQRoomCreateHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:muc:create:room===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String subject = RoomAddXmlReader.getSubject(lacoolElement);
        JID userJid = packet.getFrom();

        // generate room jid on server side, not client sid.
        JID roomJid = RoomManager.getInstance().generateRoomJid(userJid);

        // persistent to db
        Room room = roomManager.create(roomJid, userJid, subject);

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = RoomAddXmlWriter.write(room, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        roomManager = new RoomManager();
    }
}
