Setup instructions for pa-reg-web
---------------------------------

- open command prompt
- cd to build-pa directory
- "ant deploy:local:install" to setup database, jboss, and pa
- stop server (reboot or call appropriate command from new servers bin directory)
- expand server\default\deploy\pa.ear (folder name pa.ear)
- add the following to application.xml

    <module>
        <web>
            <web-uri>pa-reg-web.war</web-uri>
            <context-root>/pa-reg-web</context-root>
        </web>
    </module>

- change directory to pa-reg-web
- edit test.properties with appropriate path
- "ant package" to build 
- copy pa-reg-web.war to pa.ear folder
- start jboss
- point browser at url http://localhost:<port>/pa-reg-web
- valid account is curator/pass



