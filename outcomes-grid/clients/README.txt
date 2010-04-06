Release Notes
=============

  #Product:#	PODS External Service Client
  #Version:#	1.0
  #Date:#       April 2010

Contents
--------

   1. Introduction
   2. Distribution Contents
   3. Runtime Options
   4. Example Code
   5. NCICB Web Pages

Introduction
---------------------------

This distribution contains the resources necessary to connect to the
caGrid Service API of an existing PODS 2.0 server.

Distribution Contents
------------------------

The PODS client distribution includes the following contents:

/ <root directory>
 OutcomesServices-client.jar Jar containing client examples.
 build.xml            Runs the provided example clients via ant (required). This can also be used rebuild modified 
                      clients.
/docs                 Contains the Javadoc for all the PODS client related classes

/lib                  Contains all JARs required at runtime for any PODS grid
                      service clients written in Java. 
/src 	              Contains source of the example clients. Changes to them will be reflected when the client is
                      run from the command line.


Runtime Options
-----------------
The following options can be modified at via the ant command line. I.E ant -D<variable>=<value>

service.hostname
service.port

Example Code
------------------------

Example classes illustrating usage of various methods in the API can be seen in the src directory.

NCICB Web Pages
---------------

    * The NCI Center for Bioinformatics, http://ncicb.nci.nih.gov/
    * NCICB Application Support, http://ncicb.nci.nih.gov/NCICB/support
    * NCICB Download Center, http://ncicb.nci.nih.gov/download/
