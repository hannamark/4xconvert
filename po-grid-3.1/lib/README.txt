!!! DO NOT PUT YOUR JARS HERE (<home>/lib) !!!

Instead, reference the <home>/pom.xml to include additional dependencies. Many of the jars are automatically 
retrieved via Maven2 Repositories using the Maven Ant Tasks (http://maven.apache.org/ant-tasks/index.html) 
plugin for dependency management

All of these dependencies could be retrieved via Maven2 Repositories provided they all existed and in lieu of 
expending all of that time & effort it was decided to reference the M2 repository for new or updated archives.  
Below is a file listing of lib folder at the time this README file was generated:

$ ll lib/
total 14104
drwxr-xr-x  28 smatyas  staff      952 Jun  1 13:53 .
drwxr-xr-x  39 smatyas  staff     1326 Jun  1 13:20 ..
drwxr-xr-x   8 smatyas  staff      272 Jun  1 13:53 .svn
-rwxr-xr-x   1 smatyas  staff      164 May  4 15:27 README.txt
-rw-r--r--   1 smatyas  staff    15326 May 15 13:39 caGrid-Introduce-serviceTools-1.3.jar
-rw-r--r--   1 smatyas  staff    10106 May 15 13:39 caGrid-ServiceSecurityProvider-client-1.3.jar
-rw-r--r--   1 smatyas  staff     2004 May 15 13:39 caGrid-ServiceSecurityProvider-common-1.3.jar
-rw-r--r--   1 smatyas  staff     4861 May 15 13:39 caGrid-ServiceSecurityProvider-service-1.3.jar
-rw-r--r--   1 smatyas  staff    17982 May 15 13:39 caGrid-ServiceSecurityProvider-stubs-1.3.jar
-rw-r--r--   1 smatyas  staff    17201 May 15 13:39 caGrid-advertisement-1.3.jar
-rw-r--r--   1 smatyas  staff   151793 May 15 13:39 caGrid-core-1.3.jar
-rw-r--r--   1 smatyas  staff   107332 May 15 13:39 caGrid-metadata-common-1.3.jar
-rwxr-xr-x   1 smatyas  staff    36955 May  4 15:27 caGrid-metadata-data-1.2.jar
-rw-r--r--   1 smatyas  staff    28509 May 15 13:39 caGrid-metadata-security-1.3.jar
-rw-r--r--   1 smatyas  staff     6781 May 15 13:39 caGrid-metadata-validator-1.3.jar
-rw-r--r--   1 smatyas  staff    63494 May 15 13:39 caGrid-metadatautils-1.3.jar
-rwxr-xr-x   1 smatyas  staff  1910121 May  4 15:27 castor-0.9.9.jar
-rw-r--r--   1 smatyas  staff   261809 May 19 17:51 commons-lang-2.4.jar
-rwxr-xr-x   1 smatyas  staff    48758 May 15 13:39 globus_wsrf_mds_aggregator.jar
-rwxr-xr-x   1 smatyas  staff    52707 May 15 13:39 globus_wsrf_mds_aggregator_stubs.jar
-rwxr-xr-x   1 smatyas  staff    33005 May 15 13:39 globus_wsrf_servicegroup.jar
-rwxr-xr-x   1 smatyas  staff    50041 May 15 13:39 globus_wsrf_servicegroup_stubs.jar
-rw-r--r--   1 smatyas  staff    99026 May  4 15:27 jboss-aop-jdk50-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    44132 May  4 15:27 jboss-aspect-jdk50-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    73409 May  4 15:27 jboss-ejb3-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    19858 May  4 15:27 jboss-ejb3x.jar
-rw-r--r--   1 smatyas  staff  3958623 May  4 15:27 jbossall-client-4.0.5.jar
-rwxr-xr-x   1 smatyas  staff   153253 May 15 13:39 jdom-1.0.jar

NOTE: 
1. The "jboss-*.*" files are needed for the po-services-client.jar to invoke Remote EJB3 services
2. All other jar files were included by Introduce 1.2 or during the upgrade using Introduce 1.3