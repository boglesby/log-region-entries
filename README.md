# Log Region Entries
## Description

This project provides a function that logs RegionEntry instances for various types of Regions and key classes.

For each input Region name, the **LogRegionEntriesFunction**:

- gets the Region
- creates the appropriate RegionEntryLogger based on the type of Region
- invokes logEntries on the RegionEntryLogger

The **RegionEntryLogger**:

- gets and logs all entries for replicated Regions
- gets and logs all primary and secondary entries optionally grouped by bucket for partitioned Regions

## Initialization
Modify the **GEODE** environment variable in the *setenv.sh* script to point to a Geode installation directory.
## Build
Build the Spring Boot Client Application and Geode Server Function and logger classes using gradle like:

```
./gradlew clean jar bootJar
```
## Run Example
### Start and Configure Locator and Servers
Several scripts to start the locator and servers are provided:

- *startandconfigure.sh* - starts the servers with default configuration
- *startandconfigurereadserialized.sh* - starts the servers with PDX read-serialized set to true
- *startandconfigurestatsenabled.sh* - starts the servers with statistics configured on the Regions


Start and configure the locator and 2 servers using one of these scripts like:

```
./startandconfigure.sh
```
### Load Entries With Different Key Types
Run the client to load Trade instances with different key types into the Regions using the *runclient.sh* script like below.

The parameters are:

- operation (load-regions-different-key-types)

```
./runclient.sh load-regions-different-key-types
```
### Log RegionEntry Instances
Execute the **LogRegionEntriesFunction** to log the RegionEntry instances using the *runclient.sh* script like below.

The parameters are:

- operation (log-region-entries)
- region name(s) (ReplicatedTrade,PartitionedTrade)

```
./runclient.sh log-region-entries ReplicatedTrade,PartitionedTrade
```
### Shutdown Locator and Servers
Execute the *shutdownall.sh* script to shutdown the servers and locators like:

```
./shutdownall.sh
```
### Remove Locator and Server Files
Execute the *cleanupfiles.sh* script to remove the server and locator files like:

```
./cleanupfiles.sh
```
## Example Output
### Start and Configure Locator and Servers
Sample output from the *startandconfigure.sh* script is:

```
./startandconfigure.sh
1. Executing - start locator --name=locator

....................
Locator in <working-directory>/locator on xxx.xxx.x.x[10334] as locator is currently online.
Process ID: 26430
Uptime: 23 seconds
Geode Version: 1.13.1-build.0
Java Version: 1.8.0_151
Log File: <working-directory>/locator/locator.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

Successfully connected to: JMX Manager [host=xxx.xxx.x.x, port=1099]

Cluster configuration service is up and running.

2. Executing - set variable --name=APP_RESULT_VIEWER --value=any

Value for variable APP_RESULT_VIEWER is now: any.

3. Executing - configure pdx --read-serialized=false --disk-store=DEFAULT --auto-serializable-classes=example.domain.Trade

read-serialized = false
ignore-unread-fields = false
persistent = true
disk-store = DEFAULT
PDX Serializer = org.apache.geode.pdx.ReflectionBasedAutoSerializer
Non Portable Classes = [example.domain.Trade]
Cluster configuration for group 'cluster' is updated.

4. Executing - start server --name=server-1 --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false

........
Server in <working-directory>/server-1 on xxx.xxx.x.x[58075] as server-1 is currently online.
Process ID: 26432
Uptime: 6 seconds
Geode Version: 1.13.1-build.0
Java Version: 1.8.0_151
Log File: <working-directory>/server-1/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

5. Executing - start server --name=server-2 --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false

........
Server in <working-directory>/server-2 on xxx.xxx.x.x[58101] as server-2 is currently online.
Process ID: 26433
Uptime: 6 seconds
Geode Version: 1.13.1-build.0
Java Version: 1.8.0_151
Log File: <working-directory>/server-2/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

6. Executing - create region --name=PartitionedTrade --type=PARTITION_REDUNDANT

 Member  | Status | Message
-------- | ------ | ------------------------------------------------
server-1 | OK     | Region "/PartitionedTrade" created on "server-1"
server-2 | OK     | Region "/PartitionedTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

7. Executing - create region --name=PartitionedOverflowTrade --type=PARTITION_OVERFLOW

 Member  | Status | Message
-------- | ------ | --------------------------------------------------------
server-1 | OK     | Region "/PartitionedOverflowTrade" created on "server-1"
server-2 | OK     | Region "/PartitionedOverflowTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

8. Executing - create region --name=PartitionedPersistentTrade --type=PARTITION_PERSISTENT

 Member  | Status | Message
-------- | ------ | ----------------------------------------------------------
server-1 | OK     | Region "/PartitionedPersistentTrade" created on "server-1"
server-2 | OK     | Region "/PartitionedPersistentTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

9. Executing - create region --name=PartitionedLRUTrade --type=PARTITION_HEAP_LRU

 Member  | Status | Message
-------- | ------ | ---------------------------------------------------
server-1 | OK     | Region "/PartitionedLRUTrade" created on "server-1"
server-2 | OK     | Region "/PartitionedLRUTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

10. Executing - create region --name=PartitionedPersistentOverflowTrade --type=PARTITION_PERSISTENT_OVERFLOW

 Member  | Status | Message
-------- | ------ | ------------------------------------------------------------------
server-1 | OK     | Region "/PartitionedPersistentOverflowTrade" created on "server-1"
server-2 | OK     | Region "/PartitionedPersistentOverflowTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

11. Executing - create region --name=ReplicatedTrade --type=REPLICATE

 Member  | Status | Message
-------- | ------ | -----------------------------------------------
server-1 | OK     | Region "/ReplicatedTrade" created on "server-1"
server-2 | OK     | Region "/ReplicatedTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

12. Executing - create region --name=ReplicatedOverflowTrade --type=REPLICATE_OVERFLOW

 Member  | Status | Message
-------- | ------ | -------------------------------------------------------
server-1 | OK     | Region "/ReplicatedOverflowTrade" created on "server-1"
server-2 | OK     | Region "/ReplicatedOverflowTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

13. Executing - create region --name=ReplicatedPersistentTrade --type=REPLICATE_PERSISTENT

 Member  | Status | Message
-------- | ------ | ---------------------------------------------------------
server-1 | OK     | Region "/ReplicatedPersistentTrade" created on "server-1"
server-2 | OK     | Region "/ReplicatedPersistentTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

14. Executing - create region --name=ReplicatedLRUTrade --type=REPLICATE_HEAP_LRU

 Member  | Status | Message
-------- | ------ | --------------------------------------------------
server-1 | OK     | Region "/ReplicatedLRUTrade" created on "server-1"
server-2 | OK     | Region "/ReplicatedLRUTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

15. Executing - create region --name=ReplicatedPersistentOverflowTrade --type=REPLICATE_PERSISTENT_OVERFLOW

 Member  | Status | Message
-------- | ------ | -----------------------------------------------------------------
server-1 | OK     | Region "/ReplicatedPersistentOverflowTrade" created on "server-1"
server-2 | OK     | Region "/ReplicatedPersistentOverflowTrade" created on "server-2"

Cluster configuration for group 'cluster' is updated.

16. Executing - list regions

List of regions
----------------------------------
PartitionedLRUTrade
PartitionedOverflowTrade
PartitionedPersistentOverflowTrade
PartitionedPersistentTrade
PartitionedTrade
ReplicatedLRUTrade
ReplicatedOverflowTrade
ReplicatedPersistentOverflowTrade
ReplicatedPersistentTrade
ReplicatedTrade

17. Executing - deploy --jar=server/build/libs/server-0.0.1-SNAPSHOT.jar

 Member  |       Deployed JAR        | Deployed JAR Location
-------- | ------------------------- | ---------------------------------------------------------
server-1 | server-0.0.1-SNAPSHOT.jar | <working-directory>/server-1/server-0.0.1-SNAPSHOT.v1.jar
server-2 | server-0.0.1-SNAPSHOT.jar | <working-directory>/server-2/server-0.0.1-SNAPSHOT.v1.jar

18. Executing - list functions

 Member  | Function
-------- | ------------------------
server-1 | LogRegionEntriesFunction
server-2 | LogRegionEntriesFunction

************************* Execution Summary ***********************
Script file: startandconfigure.gfsh

Command-1 : start locator --name=locator
Status    : PASSED

Command-2 : set variable --name=APP_RESULT_VIEWER --value=any
Status    : PASSED

Command-3 : configure pdx --read-serialized=false --disk-store=DEFAULT --auto-serializable-classes=example.domain.Trade
Status    : PASSED

Command-4 : start server --name=server-1 --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false
Status    : PASSED

Command-5 : start server --name=server-2 --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false
Status    : PASSED

Command-6 : create region --name=PartitionedTrade --type=PARTITION_REDUNDANT
Status    : PASSED

Command-7 : create region --name=PartitionedOverflowTrade --type=PARTITION_OVERFLOW
Status    : PASSED

Command-8 : create region --name=PartitionedPersistentTrade --type=PARTITION_PERSISTENT
Status    : PASSED

Command-9 : create region --name=PartitionedLRUTrade --type=PARTITION_HEAP_LRU
Status    : PASSED

Command-10 : create region --name=PartitionedPersistentOverflowTrade --type=PARTITION_PERSISTENT_OVERFLOW
Status     : PASSED

Command-11 : create region --name=ReplicatedTrade --type=REPLICATE
Status     : PASSED

Command-12 : create region --name=ReplicatedOverflowTrade --type=REPLICATE_OVERFLOW
Status     : PASSED

Command-13 : create region --name=ReplicatedPersistentTrade --type=REPLICATE_PERSISTENT
Status     : PASSED

Command-14 : create region --name=ReplicatedLRUTrade --type=REPLICATE_HEAP_LRU
Status     : PASSED

Command-15 : create region --name=ReplicatedPersistentOverflowTrade --type=REPLICATE_PERSISTENT_OVERFLOW
Status     : PASSED

Command-16 : list regions
Status     : PASSED

Command-17 : deploy --jar=server/build/libs/server-0.0.1-SNAPSHOT.jar
Status     : PASSED

Command-18 : list functions
Status     : PASSED
```
### Load Entries With Different Key Types
Sample output from the *runclient.sh* script is:

```
./runclient.sh load-regions-different-key-types
...
2021-05-23 10:49:02.978  INFO 26564 --- [           main] example.client.Client                    : Starting Client on ...
...
2021-05-23 10:49:08.010  INFO 26564 --- [           main] example.client.Client                    : Started Client in 5.696 seconds (JVM running for 6.507)
2021-05-23 10:49:08.246  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=0, cusip=GE, shares=21, price=936.30)
2021-05-23 10:49:08.322  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=00000000, cusip=ORCL, shares=89, price=716.05)
2021-05-23 10:49:08.469  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=0000000000000000, cusip=PFE, shares=38, price=881.16)
2021-05-23 10:49:08.531  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=0, cusip=AAPL, shares=68, price=212.93)
2021-05-23 10:49:08.574  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=0, cusip=NVS, shares=1, price=352.77)
2021-05-23 10:49:08.647  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=28459999-5d3d-4e2c-817c-71878a135222, cusip=AMZN, shares=27, price=448.62)
2021-05-23 10:49:08.721  INFO 26564 --- [           main] example.client.service.TradeService      : Saved Trade(id=TradeKey(id=0), cusip=HD, shares=67, price=339.17)
```
### Log RegionEntry Instances
Sample output from the *runclient.sh* script is:

```
./runclient.sh log-region-entries ReplicatedTrade,PartitionedTrade
...
2021-05-23 10:52:44.507  INFO 26600 --- [           main] example.client.Client                    : Starting Client on ...
...
2021-05-23 10:52:49.145  INFO 26600 --- [           main] example.client.Client                    : Started Client in 5.154 seconds (JVM running for 5.8)
2021-05-23 10:52:49.212  INFO 26600 --- [           main] example.client.service.TradeService      : Logged entries for regionNames=ReplicatedTrade,PartitionedTrade; result={server-1=true, server-2=true}
```
Each server's log file will contain messages like this for each Region:

```
[info 2021/05/23 10:52:49.200 HST <ServerConnection on port 58075 Thread 2> tid=0x64] Executing function=LogRegionEntriesFunction; regionNames=[ReplicatedTrade, PartitionedTrade]; groupByBucket=false

[info 2021/05/23 10:52:49.206 HST <ServerConnection on port 58075 Thread 2> tid=0x64] 
Region /ReplicatedTrade contains the following 7 entries:

	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapStringKey1; regionEntryKeyClass=java.lang.String; regionEntryKey=0; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@59e19186; cachedDeserializableValueBytesLength=37]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapLongKey; regionEntryKeyClass=java.lang.Long; regionEntryKey=0; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@5dde0e62; cachedDeserializableValueBytesLength=42]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapIntKey; regionEntryKeyClass=java.lang.Integer; regionEntryKey=0; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@18dc9bbd; cachedDeserializableValueBytesLength=38]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapStringKey2; regionEntryKeyClass=java.lang.String; regionEntryKey=00000000; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@7cb67341; cachedDeserializableValueBytesLength=45]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapUUIDKey; regionEntryKeyClass=java.util.UUID; regionEntryKey=2546a798-e34b-44c0-98c9-7e50ec6b7aa3; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@290ea0f0; cachedDeserializableValueBytesLength=49]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapObjectKey; regionEntryKeyClass=example.domain.TradeKey; regionEntryKey=TradeKey(id=0); regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@48e16ad; cachedDeserializableValueBytesLength=64]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapObjectKey; regionEntryKeyClass=java.lang.String; regionEntryKey=0000000000000000; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@78f364f2; cachedDeserializableValueBytesLength=53]


[info 2021/05/23 10:52:49.210 HST <ServerConnection on port 58075 Thread 2> tid=0x64] 
Region /PartitionedTrade contains the following 4 primary entries:

	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapLongKey; regionEntryKeyClass=java.lang.Long; regionEntryKey=0; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@7bbd8db0; cachedDeserializableValueBytesLength=42]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapIntKey; regionEntryKeyClass=java.lang.Integer; regionEntryKey=0; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@67b9021a; cachedDeserializableValueBytesLength=39]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapUUIDKey; regionEntryKeyClass=java.util.UUID; regionEntryKey=28459999-5d3d-4e2c-817c-71878a135222; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@29c0c9e3; cachedDeserializableValueBytesLength=51]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapStringKey1; regionEntryKeyClass=java.lang.String; regionEntryKey=0; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@aab2517; cachedDeserializableValueBytesLength=36]

Region /PartitionedTrade contains the following 3 secondary entries:

	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapObjectKey; regionEntryKeyClass=java.lang.String; regionEntryKey=0000000000000000; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@4e1ac4d6; cachedDeserializableValueBytesLength=52]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapStringKey2; regionEntryKeyClass=java.lang.String; regionEntryKey=00000000; regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@34c37ffb; cachedDeserializableValueBytesLength=45]
	RegionEntry[regionEntryClass=org.apache.geode.internal.cache.entries.VersionedThinRegionEntryHeapObjectKey; regionEntryKeyClass=example.domain.TradeKey; regionEntryKey=TradeKey(id=0); regionEntryValueClass=org.apache.geode.internal.cache.VMCachedDeserializable; cachedDeserializableValueClass=[B; cachedDeserializableValueBytes=[B@52435e28; cachedDeserializableValueBytesLength=64]
```
### Shutdown Locator and Servers
Sample output from the *shutdownall.sh* script is:

```
./shutdownall.sh 

(1) Executing - connect

Connecting to Locator at [host=localhost, port=10334] ..
Connecting to Manager at [host=xxx.xxx.x.xx, port=1099] ..
Successfully connected to: [host=xxx.xxx.x.xx, port=1099]


(2) Executing - shutdown --include-locators=true

Shutdown is triggered
```
