!!! DO NOT PUT YOUR JARS HERE (<home>/lib) !!!

Instead, reference the <home>/pom.xml to include additional dependencies. Many of the jars are automatically 
retrieved via Maven2 Repositories using the Maven Ant Tasks (http://maven.apache.org/ant-tasks/index.html) 
plugin for dependency management

All of these dependencies could be retrieved via Maven2 Repositories provided they all existed and in lieu of 
expending all of that time & effort it was decided to reference the M2 repository for new or updated archives.  
Below is a file listing of lib folder at the time this README file was generated:

$ ll lib/
total 15352
drwxr-xr-x  30 smatyas  staff     1020 Dec  9 17:32 .
drwxr-xr-x  32 smatyas  staff     1088 Dec  9 17:15 ..
drwxr-xr-x   8 smatyas  staff      272 Dec  9 17:32 .svn
-rw-r--r--   1 smatyas  staff     3056 Dec  9 17:31 README.txt
-rw-r--r--   1 smatyas  staff    15328 Dec  9 17:32 caGrid-Introduce-serviceTools-1.3.jar
-rw-r--r--   1 smatyas  staff    10108 Dec  9 17:32 caGrid-ServiceSecurityProvider-client-1.3.jar
-rw-r--r--   1 smatyas  staff     2006 Dec  9 17:32 caGrid-ServiceSecurityProvider-common-1.3.jar
-rw-r--r--   1 smatyas  staff     4863 Dec  9 17:32 caGrid-ServiceSecurityProvider-service-1.3.jar
-rw-r--r--   1 smatyas  staff    17971 Dec  9 17:32 caGrid-ServiceSecurityProvider-stubs-1.3.jar
-rw-r--r--   1 smatyas  staff    17204 Dec  9 17:32 caGrid-advertisement-1.3.jar
-rw-r--r--   1 smatyas  staff   151798 Dec  9 17:32 caGrid-core-1.3.jar
-rw-r--r--   1 smatyas  staff     3073 Dec  9 17:31 caGrid-enforce-auth-extension-Service-1.3.jar
-rw-r--r--   1 smatyas  staff   107334 Dec  9 17:32 caGrid-metadata-common-1.3.jar
-rw-r--r--   1 smatyas  staff    36848 Dec  9 17:32 caGrid-metadata-data-1.3.jar
-rw-r--r--   1 smatyas  staff    28511 Dec  9 17:32 caGrid-metadata-security-1.3.jar
-rw-r--r--   1 smatyas  staff     6781 Dec  9 17:31 caGrid-metadata-validator-1.3.jar
-rw-r--r--   1 smatyas  staff    63497 Dec  9 17:32 caGrid-metadatautils-1.3.jar
-rw-r--r--   1 smatyas  staff  2034081 Dec  9 17:31 castor-1.0.2.jar
-rw-r--r--   1 smatyas  staff    48758 Dec  9 17:31 globus_wsrf_mds_aggregator.jar
-rw-r--r--   1 smatyas  staff    52707 Dec  9 17:31 globus_wsrf_mds_aggregator_stubs.jar
-rw-r--r--   1 smatyas  staff    33005 Dec  9 17:31 globus_wsrf_servicegroup.jar
-rw-r--r--   1 smatyas  staff    50041 Dec  9 17:31 globus_wsrf_servicegroup_stubs.jar
-rw-r--r--   1 smatyas  staff   398728 Dec  9 17:31 jaxmejs-0.5.2.jar
-rw-r--r--   1 smatyas  staff    99026 Dec  9 17:31 jboss-aop-jdk50-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    44132 Dec  9 17:31 jboss-aspect-jdk50-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    73409 Dec  9 17:31 jboss-ejb3-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    19858 Dec  9 17:31 jboss-ejb3x.jar
-rw-r--r--   1 smatyas  staff  3958623 Dec  9 17:31 jbossall-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff   153253 Dec  9 13:59 jdom-1.0.jar
-rw-r--r--   1 smatyas  staff   374646 Dec  9 17:31 xsom14.jar


NOTE: 
1. The "jboss-*.*" files are needed for the pa-services-client.jar to invoke Remote EJB3 services
2. All other jar files were included by Introduce 1.2 or during the upgrade using Introduce 1.3
