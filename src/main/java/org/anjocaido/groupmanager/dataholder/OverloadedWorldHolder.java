package org.anjocaido.groupmanager.dataholder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.anjocaido.groupmanager.data.User;

/**
 * @author gabrielcouto
 */
public class OverloadedWorldHolder extends WorldDataHolder {
    protected final Map<String, User> overloadedUsers = Collections.synchronizedMap(new HashMap<String, User>());

    public OverloadedWorldHolder(WorldDataHolder ph) {
        super(ph.getName());
        setGroupsFile(ph.getGroupsFile());
        setUsersFile(ph.getUsersFile());
        groups = ph.groups;
        users  = ph.users;
    }

    @Override
    public User getUser(String userId) {
        String lowered = userId.toLowerCase();
        return overloadedUsers.containsKey(lowered) ? overloadedUsers.get(lowered) : super.getUser(userId);
    }

    @Override
    public void addUser(User theUser) {
        if (theUser.getDataSource() != this) theUser = theUser.clone(this);
        if (theUser == null) return;

        if ((theUser.getGroup() == null) || (!getGroups().containsKey(theUser.getGroupName().toLowerCase())))
            theUser.setGroup(getDefaultGroup());

        if (overloadedUsers.containsKey(theUser.getUUID().toLowerCase())) {
            overloadedUsers.remove(theUser.getUUID().toLowerCase());
            overloadedUsers.put(theUser.getUUID().toLowerCase(), theUser);
            return;
        }

        removeUser(theUser.getUUID());
        getUsers().put(theUser.getUUID().toLowerCase(), theUser);
        setUsersChanged(true);
    }

    @Override
    public boolean removeUser(String userId) {
        if (overloadedUsers.containsKey(userId.toLowerCase())) {
            overloadedUsers.remove(userId.toLowerCase());
            return true;
        }

        if (getUsers().containsKey(userId.toLowerCase())) {
            getUsers().remove(userId.toLowerCase());
            setUsersChanged(true);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeGroup(String groupName) {
        if (groupName.equals(getDefaultGroup().getUUID())) return false;

        synchronized (getGroups()) {
            for (String key : getGroups().keySet()) if (groupName.equalsIgnoreCase(key)) {
                getGroups().remove(key);

                synchronized (getUsers()) {
                    for (String userKey : getUsers().keySet()) {
                        User user = getUsers().get(userKey);
                        if (user.getGroupName().equalsIgnoreCase(key)) user.setGroup(getDefaultGroup());

                    }
                }

                synchronized (overloadedUsers) {
                    for (String userKey : overloadedUsers.keySet()) {
                        User user = overloadedUsers.get(userKey);
                        if (user.getGroupName().equalsIgnoreCase(key)) user.setGroup(getDefaultGroup());

                    }
                }

                setGroupsChanged(true);
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<User> getUserList() {
        Collection<User> overloadedList = new ArrayList<User>();
        synchronized (getUsers()) {
            Collection<User> normalList = getUsers().values();
            for (User u : normalList) overloadedList.add(overloadedUsers.containsKey(u.getUUID().toLowerCase())
                    ? overloadedUsers.get(u.getUUID().toLowerCase())
                    : u);
        }

        return overloadedList;
    }

    public boolean isOverloaded(String userId) {
        return overloadedUsers.containsKey(userId.toLowerCase());
    }

    public void overloadUser(String userId) {
        if (!isOverloaded(userId)) {
            User theUser = getUser(userId).clone();
            if (overloadedUsers.containsKey(theUser.getUUID().toLowerCase()))
                overloadedUsers.remove(theUser.getUUID().toLowerCase());
            overloadedUsers.put(theUser.getUUID().toLowerCase(), theUser);
        }
    }

    public void removeOverload(String userId) {
        overloadedUsers.remove(getUser(userId).getUUID().toLowerCase());
    }

    /**
     * Gets the user in normal state. Surpassing the overload state.
     * It doesn't affect permissions. But it enables plugins change the actual user permissions even in overload mode.
     */
    public User surpassOverload(String userId) {
        if (!isOverloaded(userId)) return getUser(userId);
        if (getUsers().containsKey(userId.toLowerCase())) return getUsers().get(userId.toLowerCase());
        return createUser(userId);
    }
}
