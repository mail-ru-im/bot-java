<img src="https://github.com/mail-ru-im/bot-python/blob/master/logo.png" width="100" height="100">

# Java interface for bot API

[![Build Status](https://travis-ci.org/mail-ru-im/bot-java.svg?branch=master)](https://travis-ci.org/mail-ru-im/bot-java)
[![codecov](https://codecov.io/gh/mail-ru-im/bot-java/branch/master/graph/badge.svg)](https://codecov.io/gh/mail-ru-im/bot-java)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.mail.im/bot-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.mail.im/bot-api)

## Install

Group ids `io.github.mail-ru-im` and `ru.mail` will no longer being maintained. We moved this artifact to the `ru.mail.im` group id.

### Maven
```xml
    <repositories>
        ...
        <repository>
            <id>mavencentral</id>
            <name>Maven Central Repository</name>
            <url>http://repo1.maven.org/maven2</url>
        </repository>
        ...
    </repositories>
    
    <dependencies>
        ...
        <dependency>
            <groupId>ru.mail.im</groupId>
            <artifactId>bot-api</artifactId>
            <version>1.2.3</version>
        </dependency>
        ...
    </dependencies>

```
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'ru.mail.im:bot-api:1.2.3'
}
```

## Usage

Create your own bot by sending the /newbot command to Metabot and follow the instructions.

Note a bot can only reply after the user has added it to his contacts list, or if the user was the first to start a dialogue.

### Create your bot

```java

// For ICQ New/Agent: create bot with token received from Metabot
BotApiClient client = new BotApiClient(token);
// For Myteam: create bot with token from Metabot and host url (for example `https://myteam.mail.ru/`)
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
MessageResponse sendFileResponse = controller.sendFile(
    new SendFileRequest()
        .setChatId(chatId)
        .setFile(file)
        .setCaption("Awesome file")
        .setReplyMsgId(Collections.singletonList(messageId))
);
if (!sendFileResponse.isOk()) { // sent successfully or not
    // and get the error description if sending failed
    String errorDescription = sendFileResponse.getDescription();
}

client.stop(); // stop when work done
```

## Changelog

`1.2.3`
- Catch all exceptions in IOBackoff.execute

`1.2.2` 
- Support text formats: MarkdownV2, HTML
- Support button's style for inline keyboard
- Moved from JCenter with `ru.mail` group id to Maven Central with `ru.mail.im` group id

`1.2.1` 
- Api response status check possibility

`1.2.0` 
- Support inline keyboards        
- Moved from `io.github.mail-ru-im` to `ru.mail` group id

