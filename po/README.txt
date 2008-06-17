1.	PO Source Code Structure and Build Process 

1.1	Source Tree Structure

  * services /
    Sub-project that packages all PO services for deployement
  * services / common
    Common services, including PO data model, persistence, generated DTO
  * services / person
    Person service API, based on common
  * services / organization
    Organization service API, based on common
  * services / po-correlation
    Person and organization correlation service API, to provide person-and-organization information
  * services / grid
    caGrid analytic service

  * applications /
    Sub-project for packaging relevant applications for deployment
  * applications / po-web
    Sub-project for web-based UI for P, O, and correlation services    

  * pom.xml
    Top level Maven project file
  * profiles.xml.example
    File to override default build (Maven) properties. To use rename to 'profiles.xml' and alter values within renamed file (Note: Do not commit your local 'profiles.xml' file to CM repository)
  * README.txt
    <this file>








