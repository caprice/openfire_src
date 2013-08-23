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
import wetodo.xml.room.RoomListXmlWriter;

import java.util.List;

public class IQRoomListHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:muc:fetch:room_list";
    private IQHandlerInfo info;
    private RoomManager roomManager;

    public IQRoomListHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:muc:fetch:room_list===");

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

        // persistent to db
        List<Room> list = roomManager.list(jid);
        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = RoomListXmlWriter.write(list, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        roomManager = new RoomManager();
    }
}
