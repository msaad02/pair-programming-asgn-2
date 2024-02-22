# Pair Programming Assignment

Using IntelliJ, setup for project is something like this:

In IntelliJ navigate to
File > Project Structure > Libraries

Click the "+" in the second column and select "From Maven"

Add these:
```
org.openjfx:javafx-base:17.0.1
org.openjfx:javafx-controls:17.0.1
```
---

**NOTE:** There is no "dbConfig.ini" file in this project. Place one in the root directory with the following contents:

```
username=<USERNAME>
password=<PASSWORD>
dbName=spr24_csc429_<USERNAME>
server=csdb.brockport.edu
```

Also, the mariadb driver is not in here.
# pair-programming-asgn-2
