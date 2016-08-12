/*!
 * jQuery Validation Plugin v1.14.0
 *
 * http://jqueryvalidation.org/
 *
 * Copyright (c) 2015 Jörn Zaefferer
 * Released under the MIT license
 */
(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "./jquery.validate"], factory );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

$.validator.addMethod("MACAddr", function(value, element) {
    var tel = /^[A-Fa-f0-9]{2}(:[A-Fa-f0-9]{2}){5}$/;
    return this.optional(element) || (tel.test(value));
}, "MAC地址格式不合法 - xx:xx:xx:xx:xx:xx");


}));