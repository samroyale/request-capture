# Request Capture
### A web app for capturing request callbacks implemented in Java, jQuery + Google App Engine

[![Build Status](https://api.travis-ci.org/samroyale/request-capture.svg?branch=master)](https://travis-ci.org/samroyale/request-capture)

Any requests you send to */capture/[tag]/...* will be stored and can then be viewed.  This is useful for API's that accept a callback URL and send back their response asynchronously.

See the *Getting Started* page for more details: http://requestcapture.appspot.com/start

Requires Java and Maven to build + run locally (tested most recently with Java 1.7 and Maven 3.2.2)

To build it:
```
mvn clean install
```

To run it:
```
mvn appengine:devserver
```

To deploy the latest:
```
mvn appengine:update
```

See https://cloud.google.com/appengine/docs/java/tools/maven for more details.
