# ❄ BetterSnowierSnow ![](https://img.shields.io/badge/Release-1.4-yellow) [![](https://img.shields.io/badge/Minecraft-1.20.4-success)](https://minecraft.net/) [![](http://cf.way2muchnoise.eu/full_446813_downloads.svg)](https://www.curseforge.com/minecraft/bukkit-plugins/better-snowier-snow)
[![](https://img.shields.io/modrinth/dt/E1q92r8V)](https://modrinth.com/plugin/better-snowier-snow)



<p align="center"><img align="center" style="width:10%; height:10%;" src="logo.png" alt="Better Snowier Snow Logo"/></p>

Have you ever felt like snow in Minecraft isn't quite right? Well, fear no more my friend because with this plugin you can make it act more realistically!

Introducing: **Better Snowier Snow**!

This plugin is inspired by the [Snowier Snow plugin by hobblyhobo](https://www.spigotmc.org/resources/snowiersnow.72213/). If you have a moment go check
his plugin as well!

# 🔧 How it works
Better Snowier Snow provides a simple configuration tool that allows you to change the behaviour of Powder Snow, Snow Blocks and Snow Layers, to make them act more realistically.
For instance, you can give them gravity, so they will fall when the block below is broken, or you can make them stack up when it's snowing!

# ⚙ Configuring
The configuration file allows you to configure different aspects of the plugin

**snowPoseMaxLayers**: The maximum number of snow layers that will be posed when snowing

**snowPoseBlocks**: On how many blocks snow layers can be posed every tick while snowing

**snowPoseWorlds**: List of worlds where the plugin has effect

**snowPoseIgnoredChunks**: List of chunk rectangular regions where the plugin won't have effect.
The string syntax is the following: **&lt;start chunk x&gt;,&lt;start chunk y&gt;,&lt;end chunk x&gt;,&lt;end chunk y&gt;,&lt;prevent also vanilla layer from posing&gt;**

To add just one chunk to the list just set both start chunk and end chunk coordinates to the same

**slownessOnSnow**: If true, players will get the slowness effect while walking on snow blocks or layers

**slownessMinLayers**: The minimum amount of layers to apply the slowness effect to players when they walk on it

**slownessStrength**: The slowness level the player will get

**slownessSneakingPrevent**: If true, players won't get the slowness effect (if active) when they are sneaking

**metrics**: Enable [bStats metrics](https://bstats.org/plugin/bukkit/Better%20Snowier%20Snow/9912). This **does not collect any personal/sensitive information**. If enabled you need [bStats](https://bstats.org/) installed

# 📜 Requirements
Better Snowier Snow does not require any additional plugin or mod to run, other than [Spigot itself](https://www.spigotmc.org/).

# ✔ Compatibility
Better Snowier Snow has been built on top of **Spigot API-version 1.13** and has been tested on **Minecraft 1.16.4**.

Unless some major changes happens on either Spigot API or Minecraft itself, the plugin should work on newest versions as well.

If you find any bug related to a new version of one of these, please report them to the [Issue Tracker](https://github.com/JimiIT92/BetterSnowierSnow/issues).

A Sponge port is also in development.

# 👨🏼‍💻 How to contribute
You can contribute by forking this project and making [Pull Requests](https://github.com/JimiIT92/BetterSnowierSnow/pulls). Just make sure
that the additions are in line with the plugin philosophy, which is to bring an almost vanilla experience to players.


# 🖥 Downloads

You can download Better Snowier Snow from the [CurseForge page](https://www.curseforge.com/minecraft/bukkit-plugins/better-snowier-snow) or from [Modrinth](https://modrinth.com/plugin/better-snowier-snow/versions).

You can also download Better Snowier Snow from the [Spigot page](https://www.spigotmc.org/resources/better-snowier-snow.89028/)

These are the only places where I directly upload new releases. Every other website shouldn't be trusted.

# 🍺 Support the project
Developing and maintaining this plugin requires some time and effort, but after all I really enjoy doing this ❤

This plugin is **FREE TO USE** and will ALWAYS be. If someone asked you some money, you got scammed! 😥 

But if you want to show some support to the project (or just buy me some beer to produce more code), Ko-Fi is the only way:

<p align="center"><a href="https://ko-fi.com/jimi_" target="_blank"><img src="https://uploads-ssl.webflow.com/5c14e387dab576fe667689cf/61e11d503cc13747866d338b_Button-2.png" width=660 height=100 alt="Buy Me A Beer"></a></p>

Any other websites asking you money for this project is a scam, and you should immediately report it! 😡

# 📖 Credits
All credits go to **hobblyhobo** for creating the [Snowier Snow plugin](https://www.spigotmc.org/resources/snowiersnow.72213/).
This plugin is amazing, however I felt like some features were missing. Also, some might not like how the snow poses in his plugin,
so that's why this uses a different approach (that leads to smoother snow formations).

Also thanks to [TRUEC0DER](https://github.com/TRUEC0DER) for addressing [an issue with the SnowMelt Event](https://github.com/JimiIT92/BetterSnowierSnow/pull/3) that makes any block prevents from disappearing

# 😁 In conclusion

I hope you like Better Snowier Snow and make it grow! Peace! 😁
