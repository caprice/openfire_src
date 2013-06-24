package wetodo;

import org.jivesoftware.openfire.PacketRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.event.SessionEventDispatcher;
import org.jivesoftware.openfire.event.SessionEventListener;
import org.jivesoftware.openfire.muc.*;
import org.jivesoftware.openfire.muc.spi.*;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketExtension;
import org.xmpp.packet.Presence;

import java.io.File;
import java.util.List;

public class WetodoPlugin implements Plugin {

    private JID serverAddress;
    private JoinGroupsSessionEventListener listener = new JoinGroupsSessionEventListener();
    private XMPPServer server;
    private MultiUserChatServiceImpl mucService;

    private class JoinGroupsSessionEventListener implements SessionEventListener {
        public void sessionCreated(Session session) {
            System.out.println("a client connect!");
            JID userJid = session.getAddress();
            JoinGroups(userJid);
        }

        public void sessionDestroyed(Session session) {
            //ignore
            JID userJid = session.getAddress();
            LeaveGroups(userJid);
        }

        public void resourceBound(Session session) {
            // Do nothing.
        }

        public void anonymousSessionCreated(Session session) {
            //ignore
        }

        public void anonymousSessionDestroyed(Session session) {
            //ignore
        }
    }

    public WetodoPlugin() {
    }

    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        // TODO Auto-generated method stub
        server = XMPPServer.getInstance();
        //mucService=server.getMultiUserChatManager().getMultiUserChatService(ser)
        serverAddress = new JID(XMPPServer.getInstance().getServerInfo().getXMPPDomain());
        SessionEventDispatcher.addListener(listener);
        System.out.println("join groups plugin is running!");
    }

    public void LeaveGroups(JID userJid) {
        System.out.println(userJid.toBareJID() + " is leaving the room!");
        List<String> roomIds = MUCPersistenceManager.getRoomIDsByUserJid(userJid.toBareJID());
        for (String roomId : roomIds) {
            System.out.println("room id:" + roomId);
            org.jivesoftware.openfire.muc.spi.RoomInfo rminf = MUCPersistenceManager.getRoomInfoByRoomId(roomId);
            String serviceID = rminf.getServiceID();
            mucService = (MultiUserChatServiceImpl) server.getMultiUserChatManager().getMultiUserChatService(Long.parseLong(rminf.getServiceID()));
            System.out.println("service id:" + serviceID);
            String roomName = rminf.getName();
            System.out.println("room name:" + roomName);
            LocalMUCRoom room = (LocalMUCRoom) mucService.getChatRoom(roomName);
            //从数据库中查询他的姓名作为昵称（得自己实现）
            String nickname = MUCPersistenceManager.getNickNameByJId(userJid.toBareJID());
            if (nickname == null) {
                if (userJid.getNode() != null) {
                    nickname = userJid.getNode();

                } else {
                    nickname = userJid.getResource();
                }
            }


            // The user leaves the room 用户离开群

            try {
                if (!room.hasOccupant(nickname))
                    return;
                LocalMUCRole role = (LocalMUCRole) room.getOccupant(nickname);
                room.leaveRoom(role);
                System.out.println("leaved!");
            } catch (UserNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void JoinGroups(JID userJid) {
        System.out.println(userJid.toBareJID() + " is joining the room!");
        try {

            List<String> roomIds = MUCPersistenceManager.getRoomIDsByUserJid(userJid.toBareJID());
            for (String roomId : roomIds) {
                System.out.println("room id:" + roomId);
                org.jivesoftware.openfire.muc.spi.RoomInfo rminf = MUCPersistenceManager.getRoomInfoByRoomId(roomId);
                String serviceID = rminf.getServiceID();
                mucService = (MultiUserChatServiceImpl) server.getMultiUserChatManager().getMultiUserChatService(Long.parseLong(rminf.getServiceID()));
                System.out.println("service id:" + serviceID);
                String roomName = rminf.getName();
                System.out.println("room name:" + roomName);
                LocalMUCRoom room = (LocalMUCRoom) mucService.getChatRoom(roomName);
                //从数据库中查询他的姓名作为昵称（得自己实现）
                String nickname = MUCPersistenceManager.getNickNameByJId(userJid.toBareJID());
                if (nickname == null) {
                    if (userJid.getNode() != null) {
                        nickname = userJid.getNode();

                    } else {
                        nickname = userJid.getResource();
                    }
                }


                HistoryRequest historyRequest = null;
                String password = null;

                //构建成员进入群的Presence
                Presence presence = new Presence();
                presence.setTo(room.getJID().toBareJID() + "/" + nickname);
                presence.setFrom(userJid);
                PacketExtension extension = new PacketExtension("x", "http://jabber.org/protocol/muc");
                presence.addExtension(extension);

                PacketRouter pr = server.getPacketRouter();
                LocalMUCUser user = new LocalMUCUser(mucService, pr, userJid);

                // The user joins the room 用户进入群
                try {
                    LocalMUCRole role = room.joinRoom(nickname,
                            password,
                            historyRequest,
                            user,
                            presence);
                    System.out.println("joined!");
                    user.addRole(roomName, role);
                } catch (UnauthorizedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UserAlreadyExistsException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (RoomLockedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ForbiddenException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (RegistrationRequiredException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ConflictException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ServiceUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NotAcceptableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroyPlugin() {
        SessionEventDispatcher.removeListener(listener);
        listener = null;
        serverAddress = null;
        server = null;
        mucService = null;
    }
}