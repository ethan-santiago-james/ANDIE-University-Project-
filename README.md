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

#### Newly added features

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


### How was the system tested?

The features added were tested against the requirements specified in the lab book. Gaussian filter
was tested with all available inputs with the use of print statements, and performed as expected.
Also, when new features were added, it was tested if the new features affected the functionality of
earlier added features.

### Known Bugs

* The redo button does not work for the "Transform" operations

### Refactoring

No refactoring was done in this current version of Andie.