# YAFM
Yet Another Food Mod for Minecraft.

Adds some more food to the game.  Yeah yeah, it's been done before, just not by me.

Tested with Minecraft Forge version 10.12.0.997.

## Compiling
I don't like Eclipse, so here's a step-by-step for how to build this by hand.

Dependencies: JDK, JRE, Gradle (tested with 1.10).

### Once
1. Set the GRADLE_HOME environment variable to wherever you installed Gradle (the folder that contains bin, init.d, lib, etc.).
2. Add JDK\bin, JRE\bin, and GRADLE_HOME\bin to your PATH.

### Every time
1. Navigate to the YAFM source tree.
2. Run "gradle".
3. The result will be in the "build\libs".
