<img src="https://github.com/mail-ru-im/bot-python/blob/master/logo.png" width="100" height="100">

# Java interface for bot API

[![Build Status](https://travis-ci.org/mail-ru-im/bot-java.svg?branch=master)](https://travis-ci.org/mail-ru-im/bot-java)
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
            <version>1.2.0</version>
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
    implementation 'io.github.mail-ru-im:bot-api:1.2.0'
}
```

## Usage

Create your own bot by sending the /newbot command to Metabot and follow the instructions.

Note a bot can only reply after the user has added it to his contacts list, or if the user was the first to start a dialogue.

### Create your bot

```java

// create bot with token received from Metabot for ICQ New/Agent
BotApiClient client = new BotApiClient(token);
// create bot with token and api url (for example `https://myteam.mail.ru/`) from Metabot
BotApiClient client = new BotApiClient(apiBaseUrl, token);

BotApiClientController controller = BotApiClientController.startBot(client);
client.addOnEventFetchListener(events -> { 
    //subscribe to new events
});

// send actions
controller.sendActions(chatId, ChatAction.TYPING);

// send message
long messageId = controller.sendTextMessage(
    new SendTextRequest()
        .setChatId(chatId)
        .setText("Bot message")
).getMsgId();

// edit message
controller.editText(
    new EditTextRequest()
        .setChatId(chatId)
        .setMsgId(messageId)
        .setNewText("Edited bot message")
);  

// reply message
controller.sendTextMessage(
    new SendTextRequest()
        .setChatId(chatId)
        .setText("Reply msg")
        .setReplyMsgId(Collections.singletonList(messageId))
);

File file = new File("myfile.txt");
// send file
controller.sendFile(
    new SendFileRequest()
        .setChatId(chatId)
        .setFile(file)
);

// reply file
controller.sendFile(
    new SendFileRequest()
        .setChatId(chatId)
        .setFile(file)
        .setCaption("Awesome file")
        .setReplyMsgId(Collections.singletonList(messageId))
);

client.stop(); // stop when work done
```

