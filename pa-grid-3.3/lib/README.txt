!!! DO NOT PUT YOUR JARS HERE (<home>/lib) !!!

Instead, reference the <home>/pom.xml to include additional dependencies. Many of the jars are automatically 
retrieved via Maven2 Repositories using the Maven Ant Tasks (http://maven.apache.org/ant-tasks/index.html) 
plugin for dependency management

All of these dependencies could be retrieved via Maven2 Repositories provided they all existed and in lieu of 
expending all of that time & effort it was decided to reference the M2 repository for new or updated archives.  
Below is a file listing of lib folder at the time this README file was generated:

$ ll lib/
total 10344
drwxr-xr-x  26 smatyas  staff      884 Jun  1 17:53 .
drwxr-xr-x  37 smatyas  staff     1258 Jun  1 17:22 ..
drwxr-xr-x   8 smatyas  staff      272 Jun  1 17:53 .svn
-rwxr--r--   1 smatyas  staff     3056 Jun  1 14:04 README.txt
-rw-r--r--   1 smatyas  staff    15326 May  4 15:28 caGrid-Introduce-serviceTools-1.3.jar
-rw-r--r--   1 smatyas  staff    10106 May  4 15:28 caGrid-ServiceSecurityProvider-client-1.3.jar
-rw-r--r--   1 smatyas  staff     2004 May  4 15:28 caGrid-ServiceSecurityProvider-common-1.3.jar
-rw-r--r--   1 smatyas  staff     4861 May  4 15:28 caGrid-ServiceSecurityProvider-service-1.3.jar
-rw-r--r--   1 smatyas  staff    17982 May  4 15:28 caGrid-ServiceSecurityProvider-stubs-1.3.jar
-rw-r--r--   1 smatyas  staff    17203 May  4 15:28 caGrid-advertisement-1.3.jar
-rw-r--r--   1 smatyas  staff   151796 May  4 15:28 caGrid-core-1.3.jar
-rw-r--r--   1 smatyas  staff   107332 May  4 15:28 caGrid-metadata-common-1.3.jar
-rw-r--r--   1 smatyas  staff    36846 May  4 15:28 caGrid-metadata-data-1.3.jar
-rw-r--r--   1 smatyas  staff    28509 May  4 15:28 caGrid-metadata-security-1.3.jar
-rw-r--r--   1 smatyas  staff    63496 May  4 15:28 caGrid-metadatautils-1.3.jar
-rwxr-xr-x   1 smatyas  staff   261809 May  4 15:28 commons-lang-2.4.jar
-rw-r--r--   1 smatyas  staff    48758 May  4 15:28 globus_wsrf_mds_aggregator.jar
-rw-r--r--   1 smatyas  staff    52707 May  4 15:28 globus_wsrf_mds_aggregator_stubs.jar
-rw-r--r--   1 smatyas  staff    33005 May  4 15:28 globus_wsrf_servicegroup.jar
-rw-r--r--   1 smatyas  staff    50041 May  4 15:28 globus_wsrf_servicegroup_stubs.jar
-rw-r--r--   1 smatyas  staff    99026 May  4 15:28 jboss-aop-jdk50-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    44132 May  4 15:28 jboss-aspect-jdk50-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    73409 May  4 15:28 jboss-ejb3-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff    19858 May  4 15:28 jboss-ejb3x.jar
-rw-r--r--   1 smatyas  staff  3958623 May  4 15:28 jbossall-client-4.0.5.jar
-rw-r--r--   1 smatyas  staff   153253 May  4 15:28 jdom-1.0.jar


NOTE: 
1. The "jboss-*.*" files are needed for the po-services-client.jar to invoke Remote EJB3 services
2. All other jar files were included by Introduce 1.3