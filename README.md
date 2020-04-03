<img src="https://github.com/mail-ru-im/bot-python/blob/master/logo.png" width="100" height="100">

# Java interface for bot API

[![Build Status](https://travis-ci.com/mail-ru-im/bot-java.svg?branch=master)](https://travis-ci.com/mail-ru-im/bot-java)
[![codecov](https://codecov.io/gh/mail-ru-im/bot-java/branch/master/graph/badge.svg)](https://codecov.io/gh/mail-ru-im/bot-java)

## Install
 Maven
```xml
    <repositories>
        ...
        <repository>
            <id>jcenter</id>
            <url>https://jcenter.bintray.com</url>
        </repository>
        ...
    </repositories>
    
    <dependencies>
        ...
        <dependency>
            <groupId>io.github.mail-ru-im</groupId>
            <artifactId>bot-api</artifactId>
            <version>1.1</version>
        </dependency>
        ...
    </dependencies>

```
 Gradle
```groovy
repositories {
    jcenter()
}

dependencies {
    implementation 'io.github.mail-ru-im:bot-api:1.1'
}
```

## Usage

Create your own bot by sending the /newbot command to Metabot and follow the instructions.

Note a bot can only reply after the user has added it to his contacts list, or if the user was the first to start a dialogue.

### Create your bot

```java

BotApiClient client = new BotApiClient(token); // create bot with token received from Metabot

BotApiClientController controller = BotApiClientController.startBot(client);
client.addOnEventFetchListener(events -> { 
    //subscribe to new events
});

controller.sendActions(chatId, ChatAction.TYPING);
long messageId = controller.sendTextMessage(chatId, "Bot message").getMsgId(); // send message
controller.editText(chatId, messageId, "Edited bot message");  // edit message
controller.replyText(chatId, "Reply msg", messageId);

File file = new File("myfile.txt");
controller.sendFile(chatId, file);
controller.replyFile(chatId, file, messageId, "Awesome file");

client.stop(); // stop when work done
```

