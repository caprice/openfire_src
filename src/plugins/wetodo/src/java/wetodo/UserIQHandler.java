package wetodo;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.muc.ForbiddenException;
import org.jivesoftware.openfire.muc.MUCRole;
import org.jivesoftware.openfire.muc.MUCRoom;
import org.jivesoftware.openfire.muc.MultiUserChatService;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

public class UserIQHandler extends IQHandler {

    private static final String MODULE_NAME = "user handler";

    private static final String NAME_SPACE = "wetodo:user";

    private IQHandlerInfo info;

    public UserIQHandler() {
        super(MODULE_NAME);
        info = new IQHandlerInfo("query", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) throws UnauthorizedException {
        System.out.println("===handleIQ===");
        Element groups = packet.getChildElement();

        Element childElement = packet.getChildElement();
        String namespace = childElement.getNamespaceURI();
        System.out.println("===namespace:" + namespace + "===");

        Element childElementCopy = packet.getChildElement().createCopy();
        System.out.println("===child:" + childElementCopy + "===");

        final String toUser = groups.elementText("host").split("@")[0];
        System.out.println("===toUser:" + toUser + "===");

        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.error);
        Element reason = DocumentHelper.createElement("reason");
        reason.addNamespace("", NAME_SPACE);
        reason.setText("no");
        reply.setChildElement(reason);
        String roomjid = "song@conference.192.168.1.126";
        JID rJid = new JID(roomjid);
        MultiUserChatService chatService = XMPPServer.getInstance()
                .getMultiUserChatManager().getMultiUserChatService(rJid);
        MUCRoom room = chatService.getChatRoom(rJid.getNode());
        // room.send(reply);
        org.xmpp.packet.Message message = new org.xmpp.packet.Message();
        message.setTo(roomjid);
        message.setBody("send flower");
        message.setType(Message.Type.groupchat);
        MUCRole senderRole;
        try {
            senderRole = room.getOccupant("tester001");
            room.sendPublicMessage(message, senderRole);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (ForbiddenException e) {
            e.printStackTrace();
        }
        return reply;
    }
}
