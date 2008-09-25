Setup instructions for pa-reg-web
---------------------------------

- open command prompt
- cd to build-pa directory
- "ant deploy:local:install" to setup database, jboss, and pa
- stop server (reboot or call appropriate command from new servers bin directory)
- change directory to pa-reg-web
- create build.properties using build.properties.example as a template.
- "ant deploy" to build and deploy
- start jboss
- point browser at url http://localhost:<port>/registry
- valid account is curator/pass



