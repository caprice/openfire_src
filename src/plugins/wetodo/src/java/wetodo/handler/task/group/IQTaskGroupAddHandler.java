package wetodo.handler.task.group;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.TaskGroupManager;
import wetodo.model.TaskGroup;
import wetodo.xml.task.group.TaskGroupAddXmlReader;
import wetodo.xml.task.group.TaskGroupAddXmlWriter;

import java.sql.Timestamp;

public class IQTaskGroupAddHandler extends IQHandler {

    private static final String NAME_SPACE = "lacool:todo:add:group";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskGroupManager taskGroupManager;

    public IQTaskGroupAddHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:todo:add:group===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        TaskGroup taskGroup = TaskGroupAddXmlReader.getTaskGroup(lacoolElement);
        taskGroup.setVersion(VERSION_ONE);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        taskGroup.setCreate_date(now);
        taskGroup.setModify_date(now);

        // persistent to db
        taskGroup = taskGroupManager.add(taskGroup);

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskGroupAddXmlWriter.write(taskGroup.getRoomid(), taskGroup, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        taskGroupManager = TaskGroupManager.getInstance();
    }

}
