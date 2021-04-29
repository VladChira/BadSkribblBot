# BadSkribblBot
A terrible approach to the popular drawing game [skribbl.io](https://skribbl.io)

The bot draws on the canvas by moving the cursor to select the colors and paint.

# Usage
First of all, don't use this.

If you still want to use this, you must figure out the top-left coordinate of the canvas in pixels, and put that as the offsetX and offsetY.

```Java
    //Offsets for my 1080p screen
    private static int offsetX = 477;
    private static int offsetY = 222;
```

You must figure that out on your own since it depends on your resolution.

Once you are in-game, quickly search online the word (maybe add "clipart" after) and download it to the **images** folder. I was too lazy to implement actual Google Image Search.

Finally, run the program, minimize the window (***you have about 3 seconds before you lose control of your mouse***) and let the program to the work.

You have about 60% chance that it actually draws correctly. Good luck!

# Totally Legit License
Copyright 2021 Vlad Chira

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
