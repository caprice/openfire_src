package wetodo.handler.task;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.TaskGroupManager;
import wetodo.manager.TaskManager;
import wetodo.model.Task;
import wetodo.model.TaskGroup;
import wetodo.xml.task.TaskListXmlReader;
import wetodo.xml.task.TaskListXmlWriter;

import java.util.List;

public class IQTaskListHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:todo:fetch:task_list";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskManager taskManager;
    private TaskGroupManager taskGroupManager;

    public IQTaskListHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:todo:fetch:task_list===");

        // valid
        if (!packet.getType().equals(IQ.Type.get)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        String roomid = TaskListXmlReader.getRoomid(lacoolElement);
        String tgid = TaskListXmlReader.getTgid(lacoolElement);

        // persistent to db
        List<Task> taskList = taskManager.list(roomid, tgid);
        TaskGroup taskGroup = taskGroupManager.find(tgid);

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskListXmlWriter.write(roomid, taskList, taskGroup, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        taskManager = TaskManager.getInstance();
        taskGroupManager = TaskGroupManager.getInstance();
    }
}
