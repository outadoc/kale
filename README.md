# Kale
Kotlin IRC message parsing, serialising and notifying. Provides useful abstractions with the intention of splitting message parsing and IRC state management.

[Warren](https://github.com/CarrotCodes/Warren) is the state tracking counterpart. Kale by itself does not track any IRC state.

[Thump](https://github.com/CarrotCodes/Thump) is the primary upstream project - a bridge that lets people chat between Minecraft and IRC whilst they play.

## Why should you use this framework?

Warren and Kale have a few advantages over other IRC frameworks:

* The responsibilities of parsing and state management are separated
* Both parsing and state management are verified by hundreds of unit tests
* Messages, and state handlers, are individually encapsulated

Note that this project is still pretty new. Planned features are tracked in [Issues](https://github.com/CarrotCodes/Warren/issues).

## TODO
* [RFC 1459](https://tools.ietf.org/html/rfc1459)
 * Pretty much done for majority of usage - consider being more complete
* [IRC v3](http://ircv3.net/irc/)
 * Conditionally register messages enabled by capabilities
 * BATCH

## Code License
The source code of this project is licensed under the terms of the ISC license, listed in the [LICENSE](LICENSE.md) file. A concise summary of the ISC license is available at [choosealicense.org](http://choosealicense.com/licenses/isc/).

## Building
This project uses Gradle and IntelliJ IDEA for pretty easy setup and building.

The general idea:
* **Setup**: `./gradlew clean idea`
* **Building**: `./gradlew build`
* **Producing an all-in-one Jar**: `./gradlew build shadowJar`

If you run in to odd Gradle issues, doing `./gradlew clean` usually fixes it.
