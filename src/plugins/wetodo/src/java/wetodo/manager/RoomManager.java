package wetodo.manager;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.muc.MUCRoom;
import org.jivesoftware.openfire.muc.MultiUserChatService;
import org.jivesoftware.openfire.muc.NotAllowedException;
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

}
