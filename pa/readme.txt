-----Original Message-----
From: Todd C. Parnell [mailto:tparnell@5amsolutions.com]
Sent: Wednesday, May 21, 2008 11:03 AM
To: Naveen Amiruddin
Subject: jbos 4.0.5 w/EJB3

Naveen,

Here is the information for getting JBoss 4.0.5 working with EJB3
support.  This is how the caArray developers set up their environment,
and this is supported by systems here at NCI.

Thanks,

Todd

Jboss 4.0.5GA
Installation

    1. Download the JBoss 4.0.5 JEMS Installer (1.2.0)
[http://sourceforge.net/project/downloading.php?group_id=22866&filename=jems-installer-1.2.0.GA.jar&70038359]
    2. Run jems-installer-1.2.0.GA.jar as an executable Java JAR.
    3. Specify C:/dev/app_servers/jboss-4.0.5.GA as the installation location.
    4. Choose "ejb3" as the installation profile type, click Next.
    5. Leave the "default packs" selected, click Next.
    6. Choose "Standard" installation, click Next.
    7. Add a system environment variable called JBOSS_HOME and set it to
/dev/app_servers/jboss-4.0.5.GA (Windows) or
/Applications/jboss-4.0.5.GA (Mac).




