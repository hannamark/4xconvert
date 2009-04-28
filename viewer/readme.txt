The reporting sub-groups for now are:

Abstractor - CTRO Staff
Submitter - Public


To build and deploy pa applications using project ant tasks:

1.  run reg-web/ant package
2.  run viewer/ant package
3.  in pa/build.properties set include.viewer=true
4.  run pa/ant deploy-notest 
        or 
    pa/ant deploy
    
    
To deploy only the viewer application:

1.  complete above process
2.  run viewer/ant deploy-notest
        or
    viewer/ant deploy