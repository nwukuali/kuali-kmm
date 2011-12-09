Be sure to read the javadoc at the top of the code for each utility first.

The quickest way to run all of these is going to be through eclipse:
	right-click->Run As->Java Application on the .java file.
	
You will also have to create a Run Configuration (right-click->Run As->Run Configurations...)
for each utility.  Be sure that in the Arguments tab to put boGen.conf.xml and to follow this format
for each BO you want to generate files for:

	<bo name="BoName" class="org.kuali.ext.mm.businessobject.BoName" />

 
	