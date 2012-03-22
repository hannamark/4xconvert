When updating to the next release version make the following changes:

1. Update the following properties in build-po/project.properties. Do not use SNAPSHOT's as we always want to make sure we are pulling from the local Maven repository.
    po-services.version
    po-services-client.version
    po-ear.version
2. Update the version in the following POM.
    po/pom.xml
3. Update the parent version in the following POM's.
    po/client/pom.xml
    po/ear/pom.xml
    po/services/pom.xml
    po/web/pom.xml
