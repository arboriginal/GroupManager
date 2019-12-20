![# GroupManager](https://i.imgur.com/h1WW3ZE.png)

GroupManager is a permissions plugin for [Spigot](https://www.spigotmc.org) / [Paper](https://papermc.io) servers.

It was initially developed by the [Essentials team](https://github.com/orgs/essentials/people) as part of their project [Essentials](https://github.com/essentials/Essentials). As you probably know it, Essentials was abandoned, and forks came out, like [EssentialsX](https://github.com/EssentialsX/Essentials) for example, but GroupManager became a standalone plugin.

The repository I forked presents it like this:

« GroupManager was one of the first permission plugins ever available for Bukkit. Although there are plugins such as LuckPerms with more functionality than GroupManager, and the official support and development of Essentials plugins has ended, GroupManager is still preferred over other plugins by lots of server administrators. »

# Why this fork?

I was using a [fork of GroupManager](https://www.spigotmc.org/resources/groupmanager.38875/) without having time to check deeper, until a new release which was compiled with Java 11. As you can see reading for [Which versions of Java do you regularly use?](https://www.jetbrains.com/lp/devecosystem-2019/java/), [Which Version of Java Should You Use?](https://www.stackchief.com/blog/Which%20Version%20of%20Java%20Should%20You%20Use%3F) for example, the fact Spigot was still built with Java 8 and lots of Minecraft server hosting companies still use it, I created a fork to give the author my modified version of Maven's pom.xml file which will support Java 8 ([see here](https://www.spigotmc.org/threads/groupmanager.230254/page-5#post-3632641)). Then putting my eyes on the code, I've decided to use my own version instead of this one.

Moreover, even if some people are saying GroupManager is buggy, outdated, etc. I'm using it since 2012 without issues and except telling me "because X told it", those detractors never gave me a real reason to switch to another one permissions plugin... Creating my own version will give me the opportunity to study the code and make my own opinion.