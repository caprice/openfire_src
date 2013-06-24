package wetodo;

import java.io.File;

import org.jivesoftware.openfire.IQRouter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;

public class WetodoPlugin1 implements Plugin {

    public WetodoPlugin1() {
    }

    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        System.out.println("Hello World!!!!!");
        IQRouter iqRouter = XMPPServer.getInstance().getIQRouter();

        iqRouter.addHandler(new UserIQHandler());
        iqRouter.addHandler(new RegisterIQHandler());
    }

    public void destroyPlugin() {
    }
}