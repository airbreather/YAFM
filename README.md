YAFM
====

Yet Another Food Mod for Minecraft.

Adds some more food to the game.  Yeah yeah, it's been done before, just not by me.

Tested with Minecraft Forge version 9.11.1.916.

Compiling
---------

I don't like Eclipse, so here's a step-by-step for how to build this by hand.

Dependencies: JDK, JRE.

1. Add JDK\bin and JRE\bin to your PATH.
2. Download the "src" version of Minecraft Forge from http://files.minecraftforge.net (tested with 9.11.1.916).
3. Extract "forge" somewhere, and navigate there.
4. Run "install.cmd" or "install.sh".
6. From this repository, copy the contents of "source" into "mcp/src/minecraft".  Sanity check: there should be a "mcp/src/minecraft/airbreather" folder now.
7. From this repository, copy the "assets" folder into "mcp/reobf/minecraft".  Sanity check: there should be a "mcp/reobf/minecraft/assets/yafm" folder now.
8. Back in the "forge" directory, run "recompile.bat" or "recompile.sh".
9. When that completes, run "reobfuscate.bat" or "reobfuscate.sh".
10. Navigate to "reobf/minecraft".
11. Zip up everything in here and name the resulting file "yafm-1.0.0.jar".
12. (mostly optional) Also add the "mcmod.info" and "forge.version" files from this repository to the root of that zip file.
