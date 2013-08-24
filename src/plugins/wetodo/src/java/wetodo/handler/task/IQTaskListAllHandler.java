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
import wetodo.xml.task.TaskListAllXmlWriter;
import wetodo.xml.task.TaskListXmlReader;

import java.util.List;

public class IQTaskListAllHandler extends IQHandler {
    private static final String NAME_SPACE = "lacool:todo:fetch:all";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskManager taskManager;
    private TaskGroupManager taskGroupManager;

    public IQTaskListAllHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:todo:fetch:all===");

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

        // persistent to db
        List<Task> taskList = taskManager.list_all(roomid);

        // output
        System.out.println("=== output ===");
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskListAllXmlWriter.write(roomid, taskList, NAME_SPACE);
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
