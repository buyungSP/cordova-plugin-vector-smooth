
module.exports = {
    open: function(options) {
         return new Promise(function(successCallback, errorCallback) {
            finalOption = {
                name: 'crop',
                color: '#FF0000'
            };
            finalOption.name = options.name||finalOption.name;
            finalOption.color = options.color||finalOption.color;
            cordova.exec(successCallback, errorCallback, "Vector", "open", [finalOption]);
        });
    }
};