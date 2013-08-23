package wetodo;

import org.jivesoftware.openfire.IQRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import wetodo.handler.account.IQAccountRegisterHandler;
import wetodo.handler.account.code.IQCodeSendHandler;
import wetodo.handler.account.code.IQCodeValidateHandler;
import wetodo.handler.room.IQRoomCreateHandler;
import wetodo.handler.room.IQRoomListHandler;
import wetodo.handler.task.*;
import wetodo.handler.task.group.IQTaskGroupAddHandler;
import wetodo.handler.task.group.IQTaskGroupDelHandler;
import wetodo.handler.task.group.IQTaskGroupListHandler;
import wetodo.handler.task.group.IQTaskGroupModifyHandler;

import java.io.File;

public class WetodoPlugin implements Plugin {

    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        IQRouter iqRouter = XMPPServer.getInstance().getIQRouter();

        // TaskGroup
        iqRouter.addHandler(new IQTaskGroupAddHandler());
        iqRouter.addHandler(new IQTaskGroupListHandler());
        iqRouter.addHandler(new IQTaskGroupModifyHandler());
        iqRouter.addHandler(new IQTaskGroupDelHandler());
        // Task
        iqRouter.addHandler(new IQTaskAddHandler());
        iqRouter.addHandler(new IQTaskDelHandler());
        iqRouter.addHandler(new IQTaskListHandler());
        iqRouter.addHandler(new IQTaskListAllHandler());
        iqRouter.addHandler(new IQTaskModifyHandler());
        // Code
        iqRouter.addHandler(new IQCodeSendHandler());
        iqRouter.addHandler(new IQCodeValidateHandler());
        // Account
        iqRouter.addHandler(new IQAccountRegisterHandler());
        // Room
        iqRouter.addHandler(new IQRoomListHandler());
        iqRouter.addHandler(new IQRoomCreateHandler());
    }

    public void destroyPlugin() {
    }
}