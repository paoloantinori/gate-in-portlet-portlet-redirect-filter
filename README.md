This is the original GateIn Readme file. It will guid you to the configuration of the GateIn JBoss 7 distribution that requires you to uncomment fromt he default configuration files a couple of component used by the Sample Portal.

My work related to the Redirect filter is deployed automatically in a modified version of the Sample Portal and no particular action specific to my project need to be performed to build and deploy.

Just follow Gate In instruction and drop a comment on my Blog if you need help.

http://giallone.blogspot.co.uk/2013/05/gatein-interportlet-interpage.html


paolo

paolo.antinori@gmail.com  

-------------------


<!--~ Do not edit this derived file! See gatein-portal-quickstarts-parent/src/main/freemarker/gatein-sample-portal/README.md.ftl ~-->

Author: Thomas Heute, Peter Palaga  
Level: Beginner  
Technologies: GateIn, Portal Container  
Summary: GateIn Sample Portal  
Target Product: GateIn 3.5


What is it?
-----------

This project demonstrates a how to create a new Portal Container from scratch. It covers many aspects of new Portal Container
creation, such as:

* Custom Home Page Portlet
* Translation resource bundles
* Pages and Site Layouts
* Navigation Definitions
* Custom Sign In Page

For more details, see [Portal Containers and Extensions](https://docs.jboss.org/author/display/GTNPORTAL35/GDG-Portal+Containers+and+Extensions) page of 
GateIn Developer Guide.


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/system-requirements.md.ftl ~-->
System Requirements
-------------------

All you need to build this example project is Java 6.0 (Java SDK 1.6) or newer and Maven 3.0 or newer.

The project is designed to be deployed on GateIn Portal 3.5 running on either
JBoss AS or JBoss EAP. There is no support for JBoss Enterprise Portal Platform (EPP) yet, 
but this example projects will evolve to support the upcoming EPP version 6.


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/configure-maven.md.ftl ~-->
Configure Maven
---------------

You do not need to touch your settings.xml because of this quickstart. All necessary artifacts are available in public
repositories.


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/build-and-deploy-portal-container-or-extension.md.ftl ~-->
Build and Deploy
----------------

Portal Extensions and Portal Containers are not hot-deployable. Therefore, it is not possible to deploy them using 
`mvn jboss-as:deploy` or copying to the default JBoss AS deployment folder when 
GateIn is running. Instead of that you will need to:

1. Make sure that the Portal is not running, e.g. by running 

      $JBOSS_HOME/bin/jboss-cli.sh --connect controller=127.0.0.1:9999 command=:shutdown

2. Open a command line and navigate to the root directory of this quickstart.
3. Run the following command to build the archive:

      mvn clean package

   This will create `ear/target/gatein-sample-portal.ear`.
4. Copy this file into the GateIn extension deployment folder: $JBOSS_HOME/gatein/extensions

Setup JBoss Datasources and Security Policies
---------------------------------------------  

TODO: Be less ironic
The most modern JBoss school of thought says that Datasources and Security Policies need to be defined on the level of 
Application Server and that they cannot[*] be defined within EARs because centralized management is the thing you 
need more than flexibility. This is the reason why it is not enough just to deploy `gatein-sample-portal.ear`. You also need 
to define the Datasources and Security Policies e.g. in `$BOSS_HOME/standalone/configuration/standalone.xml`.

The `standalone.xml` available in the out-of-the-box GateIn 3.5 installation contains the needed pieces 
of XML commented out. Please search for `Uncommented this when deploying gatein-sample-portal` in the file and uncomment the 
necessary XML code blocks. Note that you should uncomment two `datasource`s and one `security-policy`.

> Except for editting standalone.xml, JBoss Datasources and Security Policies can be defined using CLI or JBoss Web Console as 
> described in [JBoss AS 7.1 Admin Guide](https://docs.jboss.org/author/display/AS71/Admin+Guide#AdminGuide-Datasources) 

[*] Datasources can be defined in EARs but such ones are not managed, see 
[JBoss AS 7.1 Admin Guide](https://docs.jboss.org/author/display/AS71/Admin+Guide#AdminGuide-Deploymentof%5Cds.xmlfiles).


Access the Sample Portal
------------------------

To ensure that the Sample Portal has been deployed successfully visit 
[http://127.0.0.1:8080/sample-portal](http://127.0.0.1:8080/sample-portal/)) with your web browser.


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/start-the-portal.md.ftl ~-->
Start the Portal
----------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Undeploy the Archive
--------------------

To delete a Portal Extension or Portal Container:
1. Delete JBOSS_HOME/gatein/extensions/gatein-sample-portal.ear
2. Restart GateIn

Please note that the content written to the database by the Extension or Portal Container (such as adding new pages) is not 
reversible.


Use JBoss Developer Studio or Eclipse to Run this Quickstart
------------------------------------------------------------

Portal Extensions and Portal Containers are not directly deployable from JBoss Developer Studio nor Eclipse at this time. 
See Build and Deploy section above for an alternative.  


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/debug.md.ftl ~-->
Debug the Application
---------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following 
commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc


<!--~ Included from gatein-portal-quickstarts-parent/src/main/freemarker/include/feedback.md.ftl ~-->
Feedback
--------

Please post feedback on this quickstart or GateIn on [User Forum](https://community.jboss.org/en/gatein?view=discussions).

