# Endless Sky Shipyard

Coordinate finder and ship builder for Endless Sky. To be the complete ship maker for ES covering all from stats, outfitting, to positioning hardpoints.

## Compilation

```bash
javac *.java
jar -cfe es_shipyard.jar Shipyard *.class
#todo compile to .exe executable?
```
(Literally just too lazy to get a proper IDE setup.)

Or you could probably open it in your favorite Java IDE and click on whatever build and run the program.

## Running

```bash
java -jar es_shipyard.jar
```

## Usage

* Left-Click anywhere within the image bound to select a position.
* Right-Click within the central section to select the hardpoint type to add.
* Arrow Keys to fine tune when focus is on the central section (click it).
	* Use Ctrl and/or Shift modifier for rougher, faster moves.
* Ctrl-Z to undo last added hardpoint.
* Ctrl-Shift-Z to redo. (Currently history doesn't clear after doing other stuffs.)
* Alt-M or select "Mirror" on the left menu to automatically duplicate points across x-axis.
* Alt-C select "Snap to center" to snap X 0.5 to 0 or X within +-0.005x of the image width.
* Alt-X or Alt-Y or select Lock X or Y axis when moving the pointer.
* Hold A to draw a angle guide and display the current angle relative to the front.
* Scroll wheel to zoom in and out.
* Middle mouse button to drag
* Home key to reset zoom and drag.
* Configure hardpoint values on the left panel.


I was planing on something like this for a while. Finding out my favourite ES ship builder was taken down for some reason finally pushed me to do it, wasn't a fan of web stuffs anyway (and that was one of the big reason why). With this application, as long as you have it and a functioning computer you can always use it.

Why Java? I know a bit of it and want to write more, no good reason really. Might rewrite in Rust or whatever fancy new language later if I feel like it.
