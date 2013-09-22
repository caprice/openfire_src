package wetodo.handler.room;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.session.ClientSession;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.RoomManager;
import wetodo.model.Room;
import wetodo.xml.room.RoomQueryXmlReader;
import wetodo.xml.room.RoomQueryXmlWriter;

public class IQRoomQueryHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:muc:query:room";
    private IQHandlerInfo info;
    private RoomManager roomManager;

    public IQRoomQueryHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:muc:query:room===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        ClientSession session = sessionManager.getSession(packet.getFrom());
        String jid = session.getAddress().toBareJID();
        Element lacoolElement = packet.getChildElement();
        String roomId = RoomQueryXmlReader.getRoomId(lacoolElement);

        // persistent to db
        Room room = roomManager.query(roomId);
        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = RoomQueryXmlWriter.write(room, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        roomManager = new RoomManager();
    }
}
