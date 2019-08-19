# opsu!variant

A fork of [opsu!](https://github.com/itdelatrisu/opsu), which is an [osu!](https://osu.ppy.sh) clone, towards a different direction.

## Rationale
This fork aims to create a system where most binaries can be built from a single codebase, only compiling platform-specific code where needed, while including future updates.

Also, this fork has distinct branches to make code flow cleaner across releases:

* [`master`](https://github.com/Lenidevelaire/opsu-variant/tree/master), is the main branch and contains the release-ready code which is mostly tested and ready for deployment.
* Version branches (`v/#.#.#`) are version-specific branches which may or may not work.
* Feature branches (`f/<feature-name>`) contains experimental features which may be broken or not work as intended.
* Fix branches (`b/issue-#`) contain non-finalized fixes relating to a specific issue.
* [`gh-pages`](https://github.com/Lenidevelaire/opsu-variant/tree/gh-pages), contains the webpage design for this repository.

## Downloads
Prebuilt JARs are available on the [releases page.](https://github.com/Lenidevelaire/opsu-variant/releases)

## Building
The project is built and distributed as a [Gradle](https://gradle.org/) project.

Gradle builds are built to the `build` directory.
* To run the project, execute the Gradle task `run`.
* To create a single executable jar, execute the Gradle task `jar`.
  This will compile a jar to `build/libs/opsu-variant-${version}.jar` with the libraries,
  resources and natives packed inside the jar.
  * Setting the "XDG" property (`-PXDG=true`) will make the application use XDG folders under Unix-like operating systems.
  * Setting the "excludeFFmpeg" property (`-PexcludeFFmpeg`) will exclude FFmpeg shared libraries from the jar.

## Credits
opsu! was made by Jeffrey Han ([@itdelatrisu](https://github.com/itdelatrisu/)).  All game concepts and designs are based on work by [osu!](https://osu.ppy.sh/) developer Dean Herbert
([@ppy](https://github.com/ppy)).  Other opsu! credits can be found [here](CREDITS.md).

opsu!variant (everything within the package `lc.lenidevelaire.aria`) were made by the [Lenidevelaire team](https://github.com/Lenidevelaire), which may contain modified opsu! sources.

## License
**This software is licensed under GNU GPL version 3.**
You can find the full text of the license [here](LICENSE).
