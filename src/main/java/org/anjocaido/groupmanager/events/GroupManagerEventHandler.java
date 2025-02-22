package org.anjocaido.groupmanager.events;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.bukkit.Server;

/**
 * @author ElgarL
 */
public class GroupManagerEventHandler {
    private final GroupManager plugin;
    private final Server       server;

    public GroupManagerEventHandler(GroupManager plugin) {
        this.plugin = plugin;
        server      = plugin.getServer();
    }

    protected void callEvent(GMGroupEvent event) {
        event.schedule(event);
    }

    protected void callEvent(GMUserEvent event) {
        event.schedule(event);
    }

    protected void callEvent(GMSystemEvent event) {
        event.schedule(event);
    }

    public void callEvent(Group group, GMGroupEvent.Action action) {
        callEvent(new GMGroupEvent(group, action));
    }

    public void callEvent(String groupName, GMGroupEvent.Action action) {
        callEvent(new GMGroupEvent(groupName, action));
    }

    public void callEvent(User user, GMUserEvent.Action action) {
        callEvent(new GMUserEvent(user, action));
    }

    public void callEvent(String userName, GMUserEvent.Action action) {
        callEvent(new GMUserEvent(userName, action));
    }

    public void callEvent(GMSystemEvent.Action action) {
        callEvent(new GMSystemEvent(action));
    }

    public GroupManager getPlugin() {
        return plugin;
    }

    public Server getServer() {
        return server;
    }
}
