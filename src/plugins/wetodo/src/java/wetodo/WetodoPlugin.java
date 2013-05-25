package wetodo;

import java.io.File;

import org.jivesoftware.openfire.IQRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.handler.IQHandler;

public class WetodoPlugin implements Plugin {

    public WetodoPlugin() {
    }

    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        System.out.println("Hello World!!!!");
        IQHandler myHandler = new UserIQHandler();
        IQRouter iqRouter = XMPPServer.getInstance().getIQRouter();
        iqRouter.addHandler(myHandler);
    }

    public void destroyPlugin() {
    }
}