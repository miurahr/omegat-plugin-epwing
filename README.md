EPWING plugin for OmegaT
========================

The **EPWING Dictionary Plugin for OmegaT** project provides EPWING dictionray plugin
 for the open-source translation tool [OmegaT](http://www.omegat.org/).

EPWING was a popular dictionary format and standard in Japanese market in 1980-2010 days.
Many dictionaries are published as CD-ROM book with EPWING format.
There are some major dictionaries still sold in Amazon book store.
There are also same dictionaries are distributed in the format.

Note: In recent days, major publisher provide smartphone application with their
dictionaries, which is not able to integrate with OmegaT.

## Features

The plugin help OmegaT users to use dictionary data from CD-ROM/DVD-ROM EPWING format.
You should copy or symbolic link dictionary data into dictionary folder.

It also supports following extensions;

- Appendix extension: Appendix is an extension introduced by EB library.
  User can define GAIJI-Unicode mapping in Appendix dictionary format.
  Format details are on [EB library home page](http://www.mistys-internet.website/eb/) and
  [EBAppendix document](http://www.mistys-internet.website/eb/doc/ebappendix.html), Or
  see source at [EB library(mirror)](https://github.com/jokester/eb)
  
  You can find an up-to-date definitions on https://github.com/eb4j/furoku-data
  and download from github release
  

Target                    |  Source       | Stop-code | Status  | download
------------------------- | ------------- | --------- | ------ | -------------------
大修館ジーニアス英和大辞典    | genius.yml    | yes       | Beta | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/genius.zip)
大修館ジーニアス英和辞典第5版 | genius.yml    | yes       | Beta | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/genius.zip)
ビジネス技術実用英語大辞典V5  | unno5.yml     | auto      | Beta | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/unno5.zip)
ビジネス技術実用英語大辞典V6.02 | unno602.yml | auto      | Beta | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/unno602.zip)
大修館ジーニアス英和辞典第4版 | genius43.yml  | yes       | Alpha | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/genius43.zip)
岩波広辞苑第4版 第5版       | kojien.yml     | yes      | Alpha | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/kojien.zip)
研究社英和中辞典            | chujiten.yml  | yes       | Pre-Alpha | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/chujiten.zip)
クラウン仏和辞典            | crown.yml     | yes       | Pre-Alpha | [link](https://github.com/eb4j/furoku-data/releases/download/continuous-build/crown.zip)

  
  
- Map extension: Map is an extension introduced by EBWin, EBPocket, EBMac, and EBStudio.
  User can define GAIJI-Unicode mapping in simple text file and put it beside of dictionary data.
  [GAIJI/*.map Specification](http://ebstudio.info/manual/EBPocket/0_0_4_4.html)
  You can find several pre-defined maps on internet search, and you can find files in EBWin/EBMac
  distribution for several known dictionaries.
  
  Map extension don't have a definition of article stop-code. You are recommended to use Appendix for stop-code even when you 
  want to use the map.

### Search features

- The plugin does not support FULL-TEXT search. It supports EPWING standard search methods,
  that are pre indexed in dictionary files.

- Exact match search

- Prefix search

## Dependency

* Java 8 or later.
* OmegaT 4.3.0 or later.

## Downloads:

* The latest stable version of the EPWING dictionary plugin 
for OmegaT is at https://github.com/miurahr/omegat-plugin-epwing/releases/

## Install:

Please unzip the distributed archive, and you will find a jar file and
several documents. You should copy jar file into OmegaT plugin folder where
it depends on Operating systems. 

#### Linux

Place plugin jar file at ~/.omegat/plugins/ for single user,  or OmegaT installation directory for all user
ex. /opt/omegat/OmegaT-5.5.0/plugins/ 

#### Windows

On Windows you can install the plugin to the plugins directory 
where OmegaT is installed (e.g. C:\Program Files\OmegaT) or to your Application Data directory:
C:\Users\<username>\AppData\Roaming\OmegaT

#### MacOS X

On OS X you are recommended to install the plugin to /Users/<username>/Library/Preferences/OmegaT/plugins. 
