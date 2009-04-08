Because this project uses libraries in the Globus directory, we configure an eclipse variable to point to that location.
The .classpath file for this project references that variable.  As a one time setup:

Highlight the PAServices project in Eclipse.
Alt-Enter (or right click -> Properties)
Click Java Build Path
Click Add Variable...
Click Configure Variables...
Click New...
Enter:  Name = "PA_GLOBUS" (no quotes), Path = "<path to globus>" (Like C:\dev\ws-4.0.3)
Click OK

You will need to rebuild or refresh the project after you quit all the dialogues.

