package wetodo.handler.task.group;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.handler.IQHandler;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
import wetodo.manager.TaskGroupManager;
import wetodo.model.TaskGroup;
import wetodo.xml.task.group.TaskGroupDelXmlReader;
import wetodo.xml.task.group.TaskGroupDelXmlWriter;

public class IQTaskGroupDelHandler extends IQHandler {

    private static final String NAME_SPACE = "lacool:todo:delete:group";
    private static final int VERSION_ONE = 1;
    private IQHandlerInfo info;
    private TaskGroupManager taskGroupManager;

    public IQTaskGroupDelHandler() {
        super(null);
        this.info = new IQHandlerInfo("lacool", NAME_SPACE);
    }

    @Override
    public IQHandlerInfo getInfo() {
        return info;
    }

    @Override
    public IQ handleIQ(IQ packet) {
        System.out.println("===lacool:todo:delete:group===");

        // valid
        if (!packet.getType().equals(IQ.Type.set)) {
            IQ result = IQ.createResultIQ(packet);
            result.setChildElement(packet.getChildElement().createCopy());
            result.setError(PacketError.Condition.bad_request);
            return result;
        }

        // xml reader
        Element lacoolElement = packet.getChildElement();
        TaskGroup taskGroup = TaskGroupDelXmlReader.getTaskGroup(lacoolElement);

        // persistent to db
        System.out.println("=== del before ===");
        taskGroupManager.del(taskGroup.getTgid());
        System.out.println("=== del after ===");

        // output
        System.out.println("=== output before ===");
        IQ reply = IQ.createResultIQ(packet);
        reply.setType(IQ.Type.result);
        Element reasonElement = TaskGroupDelXmlWriter.write(taskGroup.getRoomid(), taskGroup, NAME_SPACE);
        System.out.println("=== del after ===");
        reply.setChildElement(reasonElement);

        return reply;
    }

    @Override
    public void initialize(XMPPServer server) {
        super.initialize(server);
        taskGroupManager = TaskGroupManager.getInstance();
    }

}
