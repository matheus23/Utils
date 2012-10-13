[MatrixMapping]: https://github.com/matheus23/Utils/tree/master/Utils/src/org/matheusdev/util/matrix "org.matheusdev.util.matrix"
[Matrix2Mapping]: https://github.com/matheus23/Utils/tree/master/Utils/src/org/matheusdev/util/matrix/matrix2 "org.matheusdev.util.matrix.matrix2"
[Matrix3Mapping]: https://github.com/matheus23/Utils/tree/master/Utils/src/org/matheusdev/util/matrix/matrix3 "org.matheusdev.util.matrix.matrix3"
[Templater.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/util/javatemplates/Templater.java "org.matheusdev.util.javatemplates.Templater.java"
[SimplexNoise.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/noises/SimplexNoise.java "org.matheusdev.noises.SimplexNoise.java"
[SimplexNoiseN.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/noises/SimplexNoise.java "org.matheusdev.noises.SimplexNoiseN.java"
[ValueNoise1D.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/noises/SimplexNoise.java "org.matheusdev.noises.ValueNoise1D.java"
[GifSequenceWriter.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/util/GifSequenceWriter.java "org.matheusdev.util.GifSequenceWriter.java"
[FrameUtils.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/util/FrameUtils.java "org.matheusdev.util.FrameUtils.java"
[GriddedImage.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/GriddedImage.java "org.matheusdev.GriddedImage.java"
[NumUtils.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/util/NumUtils.java "org.matheusdev.NumUtils.java"
[TextFileReader.java]: https://github.com/matheus23/Utils/blob/master/Utils/src/org/matheusdev/util/TextFileReader.java "org.matheusdev.util.TextFileReader.java"

[SimplexNoiseDemoImg]: http://dl.dropbox.com/u/45530199/Programs/SimplexNoiseN/image46.gif "Demo GIF"

# MatheusDev's Utils

## What is this?

This is a collection of useful classes in java, without dependencies to outer libs, except the standard java lib.

## What does it have?

Currently, the following stuff is implemented:
* ["Matrix" -> Array mapping](MatrixMapping) for N dimensions.
* Optimized [2D](Matrix2Mapping) and [3D](Matrix3Mapping) versions of the above.
* A "[javatemplater](Templater.java)", which was used to create the Matrix->Array Mapping classes.
* [1D Simplex Noise](SimplexNoise.java).
* [Simplex Noise for N Dimensions](SimplexNoiseN.java).
* [1D Value Noise](ValueNoise1D.java).
* Helper for writing [Gif-Animations](GifSequenceWriter.java) to files.
* A bunch of other Utilities, like [FrameUtils](FrameUtils.java), for positioning java.awt.Frame's in the mid of a desired screen, [GriddedImage](GriddedImage.java) to create png-Image grids for SpriteSheets, [NumUtils](NumUtils.java) and a [TextFileReader](TextFileReader.java).

## What can I do with this?

Look at this:
![Demo GIF][SimplexNoiseDemoImg]

All the Noise values generated for this image are basically gotten from this code:

```java
MatrixNf values = new SimplexNoiseN(
        5,                                   // Octaves
        new Random(),                        // Random number generator RNG
        new FloatInterpolationCubicSpline(), // Value interpolator
        64, 256, 256                         // Dimensions (3D)
        );
```