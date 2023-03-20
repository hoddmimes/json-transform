
function getUrlParam() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function loadUrl(newLocation)
{
  window.location = newLocation;
  return false;
}


function utf8Encode(s) {
  return unescape(encodeURIComponent(s));
}

function utf8Decode(s) {
  return decodeURIComponent(escape(s));
}

function utf8EncodeArray( pArray ) {
    for( var i = 0; i < pArray.length; i++) {
        pArray[i] = utf8Encode( pArray[i] );
    }
    return pArray;
}

function utf8DecodeArray( pArray ) {
    for( var i = 0; i < pArray.length; i++) {
        pArray[i] = utf8Decode( pArray[i] );
    }
    return pArray;
}

function isEmpty(x)
{
   return (
        (typeof x == 'undefined')
                    ||
        (x == null)
                    ||
        (x == false)  //same as: !x
                    ||
        (x.length == 0)
                    ||
        (x == "")
                    ||
        (x.replace(/\s/g,"") == "")
                    ||
        (!/[^\s]/.test(x))
                    ||
        (/^\s*$/.test(x))
  );
}



function compareLength( pStr, pLength ) {
      //console.log('str length' + pStr.length + ' compare-to: ' + pLength );
      return (pStr.length == pLength);
}

function isNumeric( pStr ) {
	var isNumericTxt = /^[-+]?(\d+|\d+\.\d*|\d*\.\d+)$/;
	return isNumericTxt.test( pStr );
}


function formatErrorMessage(jqXHR, exception) {

    if (jqXHR.status === 0) {
        return ('Not connected.\nPlease verify your network connection.');
    } else if (jqXHR.status == 404) {
        return ('The requested page not found. [404]');
    } else if (jqXHR.status == 500) {
        return ('Internal Server Error [500].');
    } else if (exception === 'parsererror') {
        return ('Requested JSON parse failed.');
    } else if (exception === 'timeout') {
        return ('Time out error.');
    } else if (exception === 'abort') {
        return ('Ajax request aborted.');
    } else {
        return ('Uncaught Error.\n' + jqXHR.responseText);
    }
}

function transformError(xhr, err) {
    var responseTitle= $(xhr.responseText).filter('title').get(0);
    alert($(responseTitle).text() + "\n" + formatErrorMessage(xhr, err) );
}





    function  trimstr( str, maxLength ) {
        tStr = str + ''
        if (tStr.length > maxLength) {
            return tStr.substring(0, maxLength);
        }
        return tStr;
    }



    function getParameterByName(name, url = window.location.href) {
        name = name.replace(/[\[\]]/g, '\\$&');
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }