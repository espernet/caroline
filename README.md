# Caroline
Caroline is an IRC bot created to give quick responses to users regarding frequently asked questions in #help on EsperNet. It uses a SQLite database that stores the command triggers and responses to frequently asked questions. These commands can be manipulated by sending private messages to the bot.

## Configuration
The bot can be easily configured by creating the file `caroline.yml` in the same working directory Caroline is ran from.

Here is an example configuration:

```yaml
nick: Caroline
login: caroline
identify: nickpassword
finger: Caroline doesn't like to be fingered.
version: Caroline v1.0
server: irc.example.com
port: 6667
help_channel: "#help"
test_channel: "#carolinetest"
db_url: jdbc:sqlite:caroline.db
```

`nick` sets the name display to others in IRC.
`login` sets the user shown in the hostmask.
`identify` sets the password sent to NickServ after connecting. This option may be omitted.
`finger` sets the CTCP finger response. This option may be omitted.
`version` sets the CTCP version response. This option may be omitted.
`server` sets the IRC server to connect to.
`port` sets the port for the IRC server to connect to.
`help_channel` sets the help channel the bot joins.
`test_channel` sets the test channel the bot joins, which allows for testing the bot's functionality in a separate channel. This option may be omitted.
`db_url` sets the location of the database. If the database does not exist it will be created on the next run along with being populated with the required tables.

## Usage
Certain commands can be triggered over a private message to the bot. This allows for modifying its command database. All commands are case insensitive. These commands and the ones defined in the database can be accessed by anyone that is opped or voiced in the configured help channel.

```
HELP          This command.
SHOWCOMMANDS  This command.
LIST          Lists all commands stored in the database.
ADD           Adds a command to the database.
DEL           Removes a command from the database.
EDIT          Changes a response for a certain command in the database.
SAY           Bot says something to a channel or user.
ACT           Bot does an action in a channel or towards an user.
```

To get the syntax for the commands of `ADD`, `DEL`, `EDIT`, `SAY`, and `ACT`, simply send the command without any arguments and the syntax will be sent back to you. Commands that are defined in the database are triggered by a message sent to the help channel instead.

### Adding a Command
Commands can be added by using the following syntax:

```
ADD <trigger> <msg>
```

For example, this adds !caroline with the response "Caroline!":

```
ADD !caroline Caroline!
```

### Deleting a Command
Commands can be deleted by using the following syntax:

```
DEL <id>
```

For example, this would delete the command that has the ID of 1:

```
DEL 1
```

To find the ID of a command, use `LIST`

### Editing a Command
Commands can be modified by using the following syntax:

```
EDIT <id> <msg>
```

For example, this would edit the command that the ID of 1 to respond with "I've been modified!":

```
EDIT 1 I've been modified!
```

To find the ID of a command, use `LIST`

## License
Copyright (c) 2012 Kramer Campbell and the EsperNet IRC Network, released under the GPL v3.