package wetodo.handler.room;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketError;
import wetodo.manager.RoomManager;
import wetodo.xml.room.RoomInviteXmlReader;
import wetodo.xml.room.RoomInviteXmlWriter;

public class IQRoomInviteHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:muc:invite:friend";
    private IQHandlerInfo info;
    private RoomManager roomManager;

    public IQRoomInviteHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:muc:invite:friend===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String roomID = RoomInviteXmlReader.getRoomId(lacoolElement);
        JID inviteeJid = RoomInviteXmlReader.getInviteeJid(lacoolElement);
        JID roomJid = new JID(roomID);
        JID inviterJid = packet.getFrom();

        // persistent to db
        System.out.println("===persistent===");
        roomManager.invite(roomJid, inviterJid, inviteeJid);
        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = RoomInviteXmlWriter.write(NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        roomManager = new RoomManager();
    }
}
