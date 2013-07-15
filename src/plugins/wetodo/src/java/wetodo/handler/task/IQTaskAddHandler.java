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
import wetodo.xml.task.TaskAddXmlReader;
import wetodo.xml.task.TaskAddXmlWriter;

import java.sql.Date;
import java.util.Map;

public class IQTaskAddHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:iq:task:add";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskGroupManager taskGroupManager;
    private TaskManager taskManager;

    public IQTaskAddHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:iq:task:add===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        Task task = TaskAddXmlReader.getTask(lacoolElement);
        Date now = new Date(System.currentTimeMillis());
        task.setCreate_date(now);
        task.setModify_date(now);

        // persistent to db
        Map resultMap = taskManager.add(task);
        task = (Task) resultMap.get("task");
        TaskGroup taskGroup = (TaskGroup) resultMap.get("taskgroup");

        // output
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskAddXmlWriter.write(task, taskGroup, NAME_SPACE);
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
