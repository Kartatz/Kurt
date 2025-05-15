function getPath(element) {
	
	if (element.id === undefined) {
		return "";
	}
	
	if (element.id !== '') {
		return 'id("' + element.id + '")';
	}
	
	if (element === document.body) {
		return element.tagName.toLowerCase();
	}

	var ix = 0;
	var siblings = element.parentNode.childNodes;
	
	for (var index = 0; index < siblings.length; index++) {
		var sibling = siblings[index];
		
		if (sibling === element) {
			return getPath(element.parentNode) + '/' + element.tagName.toLowerCase() + '[' + (ix + 1) + ']';
		}
		
		if (sibling.nodeType === 1 && sibling.tagName === element.tagName) {
			ix++;
		}
	}
	
}

function getSelector(element) {
	
    var names = [];
    
    while (element.parentNode) {
        if (element.id) {
            names.unshift('#' + element.id);
            break;
        } else {
            if (element == element.ownerDocument.documentElement) {
				names.unshift(element.tagName.toLowerCase());
			} else {
                for (var c = 1, e = element; e.previousElementSibling; e = e.previousElementSibling, c++); {
                	names.unshift(element.tagName.toLowerCase() + ":nth-child(" + c + ")");
                }
            }
            element = element.parentNode;
        }
    }
    
    return names.join(" > ");
    
}


function injectSelectors() {

	document.addEventListener('click', function(event) {
		if (!window.canOpenSelectors) {
			return;
		}
		
		event.preventDefault();
		event.stopPropagation();
		
		var touchPoint = document.elementFromPoint(event.clientX, event.clientY);
		var parentContent = null;
		
		if (touchPoint.parentElement != null) {
			var parentContent = touchPoint.parentElement.outerHTML;
		}
		
		touchPoint.style.border = "1px solid red";
		
		var target = event.target;
		
		var path = getPath(target);
		var selector = getSelector(target);
		var content = touchPoint.outerHTML;
		
		internalBinding.setXPath(path);
		internalBinding.setSelector(selector);
		internalBinding.setContent(content);
		internalBinding.setContentParent(parentContent):
		
		window.setOuterHTML = function(source) {
			touchPoint.outerHTML = source;
		};
		
		window.setParentOuterHTML = function(source) {
			touchPoint.parentElement.outerHTML = source;
		};
	});
	
	window.isSelectorsEnabled = true;
	window.canOpenSelectors = false;

}
/*
function toggleSelectors() {
	
	window.canOpenSelectors = !window.canOpenSelectors;
	
	if (window.canOpenSelectors) {
		window.canOpenTouchInspector = false;
	}
	
	setTimeout(function() {
		webInpectorJavaScriptInterface.showSelectorsState(window.canOpenSelectors);
	}, 100);
	
	return true;
	
}

if (!window.isSelectorsEnabled) {
	injectSelectors();
}
*/

injectSelectors();