package wetodo.handler.room;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.RoomManager;
import wetodo.model.User;
import wetodo.xml.room.RoomMemberXmlReader;
import wetodo.xml.room.RoomMemberXmlWriter;

import java.util.List;

public class IQRoomMemberHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:muc:fetch:friend";
    private IQHandlerInfo info;
    private RoomManager roomManager;

    public IQRoomMemberHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:muc:fetch:friend===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String roomId = RoomMemberXmlReader.getRoomId(lacoolElement);

        // persistent to db
        List<User> list = roomManager.fetchMemberList(roomId);
        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = RoomMemberXmlWriter.write(list, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        roomManager = new RoomManager();
    }
}
