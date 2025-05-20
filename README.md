# ANDIE README File


## Team Name: Team P

### Team Members

* Sam Hostad
* Ethan James
* Adam Lindbom
* Bradyn Salmon

### How to operate the application

- Load the image you want to edit from your filesystem with the use of the open button
- Use the rest of the UI features to apply filters, flip the image, rotate the image, invert the image, or apply colour filters
- Save an operations file by clicking Save or Save As
- Exit the application by clicking Exit
- Save a copy of your edited image by clicking Export

#### Newly added features for Checkpoint 1

- Internationalisation (Spanish, English, and French)
- Gaussian Blur Filter
- Sharpen Filter
- Median Filter
- Image Rotation/Inversion
- Colour Channel Cycling 
- Image Resizing
- Image Flips
- Image Export (PNG, and GIF)
- Some patches were made to prevent some system crashes

#### Newly added features for Checkpoint 2

- Extended Filters (mean, sharpen, and gaussian filters can now handle edges of images)
- Filters with negative results (filters now account for positive, and negative numbers for dimensions of the convolution)
- Emboss Filter
- Edge Detection Filter
- Brightness and Contrast Adjustment
- Block Averaging
- Random Scattering
- Toolbar with custom icons
- Keyboard Shortcuts (Open, Exit, Save, Save As, Export, Rotate Left, Rotate Right, Zoom In, Zoom Out)
- Ability to crop an image with mouse selection (you first select the region then press a button to crop)
- Drawing Operation (oval, line, and rectangle options where you also select a region before pressing a button)
- Macros (added ability to save sequences of operations to an ops file for later use)
- Random Macro Feature (extra feature that generates a macro with random image operations each 
with random parameters with a size determined by the user through a prompt response)


### How was the system tested in Checkpoint 1?

The features added were tested against the requirements specified in the lab book. Gaussian filter
was tested with all available inputs with the use of print statements, and performed as expected.
Also, when new features were added, it was tested if the new features affected the functionality of
earlier added features.

### How was the system tested in Checkpoint 2?

Unit tests were added using the JUnit framework to a test file that has unit tests written for 
functionality all across the ANDIE application. A unit-test job was added to the CI pipeline script
to always runs these tests during each commit to the GitLab repository as a form of regression testing
to ensure that newly added features don't cause earlier important tests to fail. It was also thoroughly 
tested that the JAR file generated via GitLab pages executed normally after each commit to the 
GitLab server.

### Known Bugs

* The redo button does not work for the "Transform" operations

### Refactoring

No refactoring was done in this current version of Andie.