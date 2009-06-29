The reporting protection groups for now are:

Abstractor - CTRO Staff (sample account is abstractor/pass)
Reporter - Read only group for PA also works with viewer
Submitter - Public (can be created through Registry)


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

 
To set up Eclipse.

1.  download Eclipse 3.5
2.  install Sublipse (http://subclipse.tigris.org/update_1.6.x)
3.  install PMD plugin (http://pmd.sourceforge.net/eclipse)
4.  install Checkstyle plugin (http://eclipse-cs.sf.net/update)
5.  create user dictionary Preferences->General->Text Editors->Spelling
6.  activate save actions Preferences->General->Editors->Text Editors->Save Actions
        remove trailing whitespace
        use blocks always
        use parenthesis only if necessary
        use this only if necessary
        remove all unused and unnecessary  
7.  create custom formatter based on eclipse built-in Preferences->Java->Code Style->Formatter
        Tab policy: spaces only
        Maximum line width: 120
        Maximum comment width: 120
8.  create custom formatter based on eclipse built-in Preferences->Java->Code Style->Formatter
        Tab policy: spaces only
        Maximum line width: 120
        Maximum comment width: 120
9.  Preferences->Java->Compiler->Errors/Warnings
        Warn on missing @Override
        ignore unhandled token in @SuppressWarnings



To run in Eclipse.

1.  run viewer/ant ivy-get
2.  start eclipse and import project
3.  create classpath variables CODE_PA (<coppa dir>/code/pa) and VIEWER_LIB (<coppa dir>/
4.  create java working set viewer_src comprised of the two src/java directories.
    