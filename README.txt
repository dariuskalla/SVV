Java version: 17.0.1 (installed); 1.8
Used Java Spring.
Dependencies in pom:
 - spring boot starter parent; 2.5.3
 - starter actuator;
 - starter web;
 - cloud starter config;
 - cloud starter test;
 - junit 4.12;
 - spring boot maven plugin;

I wanted to finish the implementation of this web server in order to have more free time for the following weeks and start working on my
licenta. And to try to make the delayed delivery worthy of a bit more delay to finish the implementation. I will provide the other stuff 
you presented at the lab until the presentation of the project. 

How to use the app:
1. Open config-server and webServerCore in Intellij;
2. Run ConfigServerApplication in config-server, then CoreApplication in webServerCore;
3. Open a browser and go to 127.0.0.1:8282 -> this should open the log in page. Type Admin Admin.
4. The following page should be 127.0.0.1:8282\info with the little GUI and server management.
5. After starting the server, go to 127.0.0.1:8181 and should display the default webpage, which is Home.html. Surf through those little pages.

OR

How to use the app:
1. Open config-server and webServerCore in Intellij;
2. Go to config-server -> src -> main -> resources and open application.properties;
3. Here manager port and default server port are already set, but can have any value starting from 1024, along with user and password (which 
	are only formal, both Admin). Change default.base-dir.maintenance and default.base-dir.general to their corresponding paths in the 
	folder webServerCore;
4. First, run ConfigServerApplication in config-server, then CoreApplication in webServerCore;
5. Open a browser and go to 127.0.0.1:8282 -> this should open the log in page. Type Admin Admin.
6. The following page should be 127.0.0.1:8282\info with the little GUI and server management.
7. After starting the server, go to 127.0.0.1:8181 and should display the default webpage, which is Home.html. Surf through those little pages.

