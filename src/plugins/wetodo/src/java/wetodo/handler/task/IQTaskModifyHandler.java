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
import wetodo.xml.task.TaskModifyXmlReader;
import wetodo.xml.task.TaskModifyXmlWriter;

import java.sql.Timestamp;
import java.util.Map;

public class IQTaskModifyHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:todo:modify:task";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskGroupManager taskGroupManager;
    private TaskManager taskManager;

    public IQTaskModifyHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:todo:modify:task===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        Task task = TaskModifyXmlReader.getTask(lacoolElement);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        task.setModify_date(now);

        // persistent to db
        Map resultMap = taskManager.modify(task);
        task = (Task) resultMap.get("task");
        TaskGroup taskGroup = (TaskGroup) resultMap.get("taskgroup");

        // output
        System.out.println("=== output ===");
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskModifyXmlWriter.write(task.getRoomid(), task, taskGroup, NAME_SPACE);
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        taskGroupManager = TaskGroupManager.getInstance();
        taskManager = TaskManager.getInstance();
    }
}
