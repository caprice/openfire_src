package wetodo;

import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.IQRouter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketExtension;
import org.xmpp.packet.Presence;
import wetodo.handler.task.group.IQTaskGroupAddHandler;
import wetodo.handler.task.group.IQTaskGroupListHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WetodoPlugin implements Plugin {

    private static final Logger Log = LoggerFactory.getLogger(WetodoPlugin.class);
    private JID serverAddress;
    private JoinGroupsSessionEventListener listener = new JoinGroupsSessionEventListener();
    private WetodoMUCEventListener wetodoMUCEventListener = new WetodoMUCEventListener();
    private XMPPServer server;
    private MultiUserChatServiceImpl mucService;

    public WetodoPlugin() {
    }

    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        // TODO Auto-generated method stub
        server = XMPPServer.getInstance();
        //mucService=server.getMultiUserChatManager().getMultiUserChatService(ser)
        serverAddress = new JID(XMPPServer.getInstance().getServerInfo().getXMPPDomain());
        SessionEventDispatcher.addListener(listener);
        MUCEventDispatcher.addListener(wetodoMUCEventListener);
        System.out.println("join groups plugin is running!");

        // router
        IQRouter iqRouter = server.getIQRouter();
        iqRouter.addHandler(new IQTaskGroupAddHandler());
        iqRouter.addHandler(new IQTaskGroupListHandler());
    }

    public void LeaveGroups(JID userJid) {
        System.out.println(userJid.toBareJID() + " is leaving the room!");
        List<String> roomIds = MUCPersistenceManager.getRoomJidsByUserJid(userJid.toBareJID());
        for (String roomId : roomIds) {
            System.out.println("room id:" + roomId);
            org.jivesoftware.openfire.muc.spi.RoomInfo rminf = MUCPersistenceManager.getRoomInfoByRoomJid(roomId);
            String serviceID = rminf.getServiceID();
            mucService = (MultiUserChatServiceImpl) server.getMultiUserChatManager().getMultiUserChatService(Long.parseLong(rminf.getServiceID()));
            System.out.println("service id:" + serviceID);
            String roomName = rminf.getName();
            System.out.println("room name:" + roomName);
            LocalMUCRoom room = (LocalMUCRoom) mucService.getChatRoom(roomName);

            String nickname = MUCPersistenceManager.getNickNameByJId(userJid.toBareJID());
            if (nickname == null) {
                if (userJid.getNode() != null) {
                    nickname = userJid.getNode();

                } else {
                    nickname = userJid.getResource();
                }
            }


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
        System.out.println("=====================================================================================");
        System.out.println(userJid.toBareJID() + " is joining the room!");
        try {

            List<String> roomJids = MUCPersistenceManager.getRoomJidsByUserJid(userJid.toBareJID());
            for (String roomJid : roomJids) {
                System.out.println("roomJid:" + roomJid);
                org.jivesoftware.openfire.muc.spi.RoomInfo rminf = MUCPersistenceManager.getRoomInfoByRoomJid(roomJid);
                String serviceID = rminf.getServiceID();
                mucService = (MultiUserChatServiceImpl) server.getMultiUserChatManager().getMultiUserChatService(Long.parseLong(rminf.getServiceID()));
                System.out.println("service id:" + serviceID);
                String roomName = rminf.getName();
                System.out.println("room name:" + roomName);
                LocalMUCRoom room = (LocalMUCRoom) mucService.getChatRoom(roomName);

                String nickname = MUCPersistenceManager.getNickNameByJId(userJid.toBareJID());
                if (nickname == null) {
                    if (userJid.getNode() != null) {
                        nickname = userJid.getNode();

                    } else {
                        nickname = userJid.getResource();
                    }
                }
                System.out.println("nickname:" + nickname);


                HistoryRequest historyRequest = null;
                String password = null;

                Presence presence = new Presence();
                presence.setTo(room.getJID().toBareJID() + "/" + nickname);
                presence.setFrom(userJid);
                PacketExtension extension = new PacketExtension("x", "http://jabber.org/protocol/muc");
                presence.addExtension(extension);

                PacketRouter pr = server.getPacketRouter();
                LocalMUCUser user = new LocalMUCUser(mucService, pr, userJid);

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

    private class WetodoMUCEventListener implements MUCEventListener {

        @Override
        public void roomCreated(JID roomJID) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void roomDestroyed(JID roomJID) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void occupantJoined(JID roomJID, JID user, String nickname) {
            //To change body of implemented methods use File | Settings | File Templates.
            System.out.println("============================================");
            System.out.println("occupantJoined");
            System.out.println(roomJID);
            System.out.println(user);
            System.out.println(nickname);
            Connection con = null;
            PreparedStatement pstmt = null;
            String insert_muc_member = "INSERT INTO roomMember VALUES (null,?,?,?)";
            try {
                con = DbConnectionManager.getConnection();
                pstmt = con.prepareStatement(insert_muc_member);
                pstmt.setString(1, user.toBareJID());
                pstmt.setString(2, roomJID.toBareJID());
                pstmt.setString(3, nickname);
                pstmt.executeUpdate();
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                Log.error(sqle.getMessage(), sqle);
            } finally {
                DbConnectionManager.closeConnection(pstmt, con);
            }
        }

        @Override
        public void occupantLeft(JID roomJID, JID user) {
            //To change body of implemented methods use File | Settings | File Templates.
            //To change body of implemented methods use File | Settings | File Templates.
            System.out.println("============================================");
            System.out.println("occupantLeft");
            System.out.println(roomJID);
            System.out.println(user);
            //Connection con = null;
            //PreparedStatement pstmt = null;
            //String delete_muc_member = "DELETE FROM roomMember where jid=? and roomID=?";
            //try {
            //    con = DbConnectionManager.getConnection();
            //    pstmt = con.prepareStatement(delete_muc_member);
            //    pstmt.setString(1, user.toBareJID());
            //   pstmt.setString(2, roomJID.toBareJID());
            //   pstmt.executeUpdate();
            //} catch (SQLException sqle) {
            //    Log.error(sqle.getMessage(), sqle);
            //} finally {
            //   DbConnectionManager.closeConnection(pstmt, con);
            //}
        }

        @Override
        public void nicknameChanged(JID roomJID, JID user, String oldNickname, String newNickname) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void messageReceived(JID roomJID, JID user, String nickname, Message message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void privateMessageRecieved(JID toJID, JID fromJID, Message message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void roomSubjectChanged(JID roomJID, JID user, String newSubject) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

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
}