# IRCord
A discord bot that act like a bridge that communicates between your IRC server to your discord guild!
----
![XKCD 1782](https://imgs.xkcd.com/comics/team_chat.png)

## Example setup:
```json
{
  "discord": {
    "token": "<Discord_token>",
    "bridge": [
      "<Your_webhook>"
    ],
    "listen": [
      "<Discord_guild>:<Discord_channel>",
      "*:*",
      "<Discord_guild>:*"
    ]
  },
  "irc": {
    "server": "<IRC_Server_address>",
    "real_name": "<irc_real_name>",
    "ident": "<irc_ident>",
    "nick": "<nick>",
    "channels": [
      "<IRC channel, example, #test>",
      "#help"
    ]
  }
}
```

The bot use [PircBotX](https://github.com/pircbotx/pircbotx) and [JDA](https://github.com/DV8FromTheWorld/JDA) to connect
