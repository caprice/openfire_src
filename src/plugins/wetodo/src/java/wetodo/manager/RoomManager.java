package wetodo.manager;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.muc.*;
import org.jivesoftware.openfire.muc.spi.LocalMUCRole;
import org.jivesoftware.openfire.muc.spi.LocalMUCRoom;
import org.xmpp.packet.JID;
import wetodo.dao.RoomDAO;
import wetodo.model.Room;

import java.util.List;

public class RoomManager {
    /**
     * Singleton: keep a static reference to teh only instance
     */
    private static RoomManager instance;

    public static RoomManager getInstance() {
        if (instance == null) {
            // Carefull, we are in a threaded environment !
            synchronized (RoomManager.class) {
                instance = new RoomManager();
            }
        }
        return instance;
    }

    public Room create(JID roomJid, JID userJid, String subject) {
        MultiUserChatService chatService =
                XMPPServer.getInstance().getMultiUserChatManager().getMultiUserChatService(roomJid);
        try {
            MUCRoom room = chatService.getChatRoom(roomJid.getNode(), userJid);
            room.setPersistent(true);
            room.setSubject(subject);
            room.setMembersOnly(true);
            room.saveToDB();
            System.out.println(roomJid.toBareJID());
            return RoomDAO.findByRoomJid(roomJid.toBareJID());


        } catch (NotAllowedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Room> list(String jid) {
        return RoomDAO.list(jid);
    }

    public void invite(JID roomJid, JID inviterJid, JID inviteeJid) {
        try {
            MultiUserChatService chatService =
                    XMPPServer.getInstance().getMultiUserChatManager().getMultiUserChatService(roomJid);
            LocalMUCRoom room = (LocalMUCRoom) chatService.getChatRoom(roomJid.getNode(), inviterJid);

            LocalMUCRole role = (LocalMUCRole) room.getOccupant(inviterJid.getNode());
            if (room.isMembersOnly()) {
                room.addMember(inviteeJid, null, role);
            }
            // Send the invitation to the invitee
            room.sendInvitation(inviteeJid,
                    "join in", role, null);
        } catch (NotAllowedException e) {
            e.printStackTrace();
        } catch (ForbiddenException e) {
            e.printStackTrace();
        } catch (ConflictException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
