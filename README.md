#Jscratch
Assignment Two Readme

Welcome to the introduction of software: Jscratch. This software is designed and implemented by Mingkun Ni (ID 20655166). Here are some instruction about how to use this software.

1. The main program of this game is called "Main.java". To run this software, you need to install make on your local machine and type in "make run" with provided make file
2. There exist three component in this software. They perform different function:
  1) Menu bar
     This component performs basic file load,save, and create function. You can save your image drawn in canvas in local address. Later on, you can load it back to keep working on it.
  2) Button panel
     This component contains three sub-components: ToolPanel, ColorPanel, ThinknessPanel.
     1. ToolPanel is used to select the tool you can use while drawing. It offers following buttons:
        Select - you can select a shape drawn and change its color/thickness
        Eraser - you can delete a shape by click in it
        DrawLine - you can draw a line by clicking one point, dragging mouse, and clicking the second point
        DrawCircle - you can draw a circle by clicking one point, dragging mouse, and clicking the second point
        DrawRectangle - you can draw a rectangle by clicking one point, dragging mouse, and clicking the second point
     2. ColorPanel is used to select a color you can use while drawing. It offers six basic colors: Blue, Red, Green, Yellow, Black, and White. The default color is Black. Other than these 6 colors, you can also using color chooser to pick one color you like by click Chooser button. Current selected color is shown on chooser button.
     3. ThicknessPanel is used to select a thickness you can use while drawing. It offers three levels of thickness: 1-pixel,3-pixel, and 5-pixel. Default is 1-pixel.

3. UI is designed by me. There are some features I need to mention to you here:
  1) Whole windows uses BoarderLayout. For Button panel, I uses BoxLayout, and all three sub-components uses flowLayout
  2) Tool you selected will have a black,thicker border so that you know you selected it
  3) When you choose a shape you draw, ColorPanel and ThicknessPanel will change to button that is corresponding to the properties of selected shape
  4) After you load or save a file, you can save current file and cover the original file by click "save..."

As the developer of this software, I truly hope you will enjoy this software and have an excellent experience using it.

Name: Mingkun Ni
ID: 20655166
Email: m8ni@uwaterloo.ca
