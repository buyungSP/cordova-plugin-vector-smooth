## cordova-plugin-vector-smooth
-------------------------------------------------------
The cordova-plugin-vector-smooth plugin is used to convert vector drawable files into base64 encoded images for use in Cordova applications. It supports XML vector drawable files, as well as PNG and BMP file formats.

## Platform Support
-------------------------------------------------------
- Android

## Installation
-------------------------------------------------------
You can install this plugin through Cordova CLI by running the following command:

``
cordova plugin add cordova-plugin-vector-smooth
``
## Usage
### Function "open"
-------------------------------------------------------
```js
cordova.plugin.vector.smooth.open({
  name: 'ic_vector',
  color: '#FF0000'
}).then(function(result) {
  console.log(result.src);
});
```

- The "name" parameter (string) is the name of the vector drawable file in the res/drawable folder.
- The "color" parameter (string) is the color that will be used in the vector drawable.

### Output
-------------------------------------------------------
The output of this plugin is a base64 encoded image in PNG or BMP format, depending on the type of icon used. The output can be used in the <img> tag or as a background image in CSS.

```js
document.addEventListener('deviceready', onDeviceReady, false);


async function onDeviceReady() {
	const imgContainer = document.createDocumentFragment();

	const crop = await cordova.plugin.vector.smooth.open({name: 'crop', color: '#FF00FF'});

	const img = document.createElement('img');
	img.src = crop.src;

	imgContainer.appendChild(img);

  	document.body.appendChild(imgContainer);
}
```
In this example, the plugin opens the "crop" vector drawable file with a pink (#FF00FF) color. The resulting base64 encoded image is then used as the source of an <img> tag and added to the document body.