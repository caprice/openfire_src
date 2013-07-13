package wetodo.handler.task.group;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.TaskGroupManager;
import wetodo.model.TaskGroup;
import wetodo.xml.task.group.TaskGroupListXmlReader;
import wetodo.xml.task.group.TaskGroupListXmlWriter;

import java.util.List;

public class IQTaskGroupListHandler extends IQHandler {

    private static final String NAME_SPACE = "lacool:iq:taskgroup:list";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskGroupManager taskGroupManager;

    public IQTaskGroupListHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:iq:taskgroup:list===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        int roomid = TaskGroupListXmlReader.getRoomid(lacoolElement);
        System.out.println("roomid:" + roomid);

        // persistent to db
        List<TaskGroup> list = taskGroupManager.list(roomid);
        System.out.println("list:" + list);

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskGroupListXmlWriter.write(list, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        taskGroupManager = TaskGroupManager.getInstance();
    }

}
