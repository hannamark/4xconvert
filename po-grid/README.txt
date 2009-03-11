Introduce Generated Service Skeleton:
======================================
This is an Introduce generated service.

All that is needed for this service at this point is to populate the
service side implemntation in the <service package>.service.<service name>Impl.java

Prerequisits:
=======================================
Java 1.5 and JAVA_HOME env defined
Ant 1.6.5 and ANT_HOME env defined
Globus 4.0.3 installed and GLOBUS_LOCATION env defined
(optional)Tomcat 5.0.28 installed and "CATALINA_HOME" env defined with globus deployed to it

To Build:
=======================================
"ant all" will build
"ant deployGlobus" will deploy to "GLOBUS_LOCATION"
"ant deployTomcat" will deploy to "CATALINA_HOME"

Setting up local dev env from scratch:
=======================================
1. Download and install postgress
2. Use psql to create poadmin w/ admin role.
3. Checkout everything under
  https://gforge.nci.nih.gov/svnroot/coppa/trunk
4. Go into build-po and run the deploy:install:local ant target. A
  jboss dir structure will be created under c:\apps with two server
  instances, one for the po ear and one for po grid. It will also start
  the servers. The server instances will be properly configured for po
  ear to talk to po grid.
5. Change env var JBOSS_HOME to point to the new jboss location.
6. Go into code/po and remove all the non-checked-in profiles.xml that
  have been created.
7. When making modifications to po ear please refer to the README
  inside code/po for instructions on building and deploying the po.ear.
8. When making modifications to po-grid, use the deployJBoss target to
  deploy the wsrf.war to the proper jboss instance.
9. You can create a local.properties to override any properties inside
  deploy.properties. If manually deploying new wsrf code into the build-po
  generated jboss instance install make "java.naming.provider.url.port=11499" a
  part of the local.properties as that is the port used for jndi naming upon
  initial install.

