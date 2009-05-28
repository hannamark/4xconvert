The reporting protection groups for now are:

Abstractor - CTRO Staff (sample account is abstractor/pass)
Reporter - Read only group for PA also works with viewer
Submitter - Public (can be created throu Registry)


To build and deploy pa applications using project ant tasks:

1.  run reg-web/ant package
2.  run viewer/ant package
3.  in pa/build.properties make sure that "exclude.viewer=true"
        is commented out.
4.  run pa/ant deploy-notest 
        or 
    pa/ant deploy
    
    
To deploy only the viewer application:

1.  complete above process
2.  run viewer/ant deploy-notest
        or
    viewer/ant deploy
    
    
To run in eclipe.

1.  run viewer/ant ivy-get
2.  start eclipse and import project
3.  create classpath variables CODE_PA (<coppa dir>/code/pa) and VIEWER_LIB (<coppa dir>/
    