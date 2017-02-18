# LRU Cache
The aim of this project is to provide an implementation of a **Last Recently Used (LRU) Cache** in which both, the `get`
and `put` operations are O(1) in terms of time complexity.



This LRU cache implementation uses a `Map` to be able to retrieve elements by their key in a O(1) time and also a `LinkedList`
for managing the last recent usage of the elements. The list maintains the order in which the elements are accessed;
 the last recently used element is always the first element in the list.


## Disclaimer
This project is just for learning purposes. A better approach to create a LRU Cache would be just
extending the `LinkedHashMap` class overriding the `removeEldestEntry(Map.Entry<K,V> eldest)` method according to the
current size and the wanted capacity limit.

## Requirements

* **Java 8**
* maven (to compile, run tests and package)

## Run tests

You can run **unit tests** with the following command:

```
$> mvn clean test
```

For the **integration tests** you can execute:

```
$> mvn clean verify
```

## Install the library

```
$> mvn clean install
```

The command above will install the library to your local maven repository so that it can be used for your
other projects by just including it as a dependency.

```maven
<dependency>
    <groupId>com.perapoch.cache</groupId>
    <artifactId>cache</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```