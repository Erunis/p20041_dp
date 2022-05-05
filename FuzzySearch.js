(function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
(function (global){(function (){
var FuzzySearch = require('fuzzysearch-js')
var levenshteinFS = require('fuzzysearch-js/js/modules/LevenshteinFS');
var indexOfFS = require('fuzzysearch-js/js/modules/IndexOfFS');
var wordCountFS = require('fuzzysearch-js/js/modules/WordCountFS');
global.window.FuzzySearch  = FuzzySearch 
global.window.levenshteinFS  = levenshteinFS 
global.window.indexOfFS  = indexOfFS 
global.window.wordCountFS  = wordCountFS 
}).call(this)}).call(this,typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
},{"fuzzysearch-js":2,"fuzzysearch-js/js/modules/IndexOfFS":5,"fuzzysearch-js/js/modules/LevenshteinFS":6,"fuzzysearch-js/js/modules/WordCountFS":7}],2:[function(require,module,exports){
"Use Strict";

var prime = require('prime');
var trim = require('prime/string/trim');

var arr = {
	'forEach': require('prime/array/forEach')
};

var obj = {
	'mixin': require('prime/object/mixIn'),
	'fromPath': require('./fromPath'),
	'create': require('prime/object/create')
};


var FuzzySearch = prime({

    modules: null,

    options: {
        'caseSensitive': false,
        'termPath': '',
        'returnEmptyArray': false,
        'minimumScore': 0
    },

    constructor: function(searchSet, options) {
        this.options = obj.mixin(obj.create(this.options), options);
        this.searchSet = searchSet;
        this.modules = [];
    },

    addModule: function(mod) {
        this.modules.push(mod);
    },

    search: function(needle) {
        needle = !this.options.caseSensitive ? trim(needle).toLowerCase() : trim(needle);
        var result = [];

        arr.forEach(this.searchSet, function(value) {
            var origValue = value;
            var searchValue = this.options.termPath.length === 0 ? value : obj.fromPath(value, this.options.termPath);

            if (!this.options.caseSensitive) {
                searchValue = searchValue.toLowerCase();
            }

            var score = this.getCombinedModulePoints(needle, searchValue);

            if (score.combined >= this.options.minimumScore) result.push({'score': score.combined, 'details': score.details, 'value': origValue});
        }, this);

        if (!this.options.returnEmptyArray && result.length === 0) {
            return null;
        }

        return result.sort(function(a, b) {
            return b.score - a.score;
        });
    },

    getCombinedModulePoints: function(needle, haystack) {
        var result = {'combined': 0, 'details': []};
        arr.forEach(this.modules, function(mod) {
            var score = mod.search(needle, haystack).getPoints();
            var name = mod.getName();
            var factor = mod.getFactor();

            result.combined += factor * score;
            result.details.push({'name': name, 'score': score, 'factor': factor});
        });

        return result;
    },

    getMaximumScore: function() {
        var factorSum = 0;
        arr.forEach(this.modules, function(mod) {
            factorSum += mod.getFactor();
        });

        return 100 * factorSum;
    }

});

module.exports = FuzzySearch;
},{"./fromPath":3,"prime":10,"prime/array/forEach":9,"prime/object/create":12,"prime/object/mixIn":17,"prime/string/trim":18}],3:[function(require,module,exports){
var hasOwn = require('prime/object/hasOwn');

function fromPath(source, parts) {
	"use strict";

	if (typeof parts == 'string') parts = parts.split('.');
	for (var i = 0, l = parts.length; i < l; i++) {
		if (hasOwn(source, parts[i])) {
			source = source[parts[i]];
		} else {
			return null;
		}
	}
	return source;
}

module.exports = fromPath;
},{"prime/object/hasOwn":16}],4:[function(require,module,exports){
"Use Strict";

var prime = require('prime');
var obj = {
	'mixin': require('prime/object/mixIn'),
	'create': require('prime/object/create')
};
var FSModule = prime({

    lastTerm: '',
    lastHaystack: '',
    lastResults: null,

    options: {
        'factor': 1
    },

    constructor: function(options) {
        this.options = obj.mixin(obj.create(this.options), options);
        this.lastResults = [];
    },

    search: function(searchTerm) {
        throw new Error("search method not implemented");
    },

    getPoints: function() {
        throw new Error("getPoints method not implemented");
    },

    getMatches: function() {
        return this.lastResults;
    },

    getFactor: function() {
        return this.options.factor || 1;
    },

    getName: function() {
        if (!this.name) throw new Error("set module name!");

        return this.name;
    }

});

module.exports = FSModule;
},{"prime":10,"prime/object/create":12,"prime/object/mixIn":17}],5:[function(require,module,exports){
"Use Strict";

var prime = require('prime');
var FSModule = require('./FSModule');
var arr = {
	'forEach': require('prime/array/forEach')
};

var IndexOfFS = prime({

    inherits: FSModule,

    name: 'IndexOfFS',
    options: {
        'minTermLength': 3,
        'maxIterations': 500,
        'factor': 1
    },

    search: function(searchTerm, searchHaystack) {
        this.lastTerm = searchTerm;
        this.lastHaystack = searchHaystack;
        var minLength = searchTerm.length >= this.options.minTermLength ? this.options.minTermLength : searchTerm.length;

        var matches = [];
        var iterations = 0;
        do {
            var cm = this.getClosestMatch(searchTerm, searchHaystack);
            if (cm.length >= minLength) {
                matches.push(cm);
            }

            var substrc = (cm.length - 1 > 0) ? cm.length : 1;
            searchTerm = searchTerm.substr(substrc);
            iterations++;
        } while (searchTerm.length >= minLength && iterations <= this.options.maxIterations);


        this.lastResults = matches;
        return this;
    },

    getClosestMatch: function(searchTerm, haystack) {
        if (haystack.indexOf(searchTerm) != -1) {
            return searchTerm;
        }

        var length = searchTerm.length;

        for (var i = 0; i <= length; i++) {
            var term = searchTerm.substr(0, i);
            if (haystack.indexOf(term) != -1) {
                continue;
            }

            return term.substr(0, i - 1);
        }

        return "";
    },

    getPoints: function() {
        var sum = 0;
        arr.forEach(this.lastResults, function(result) {
            sum += result.length;
        });

        return 100 / this.lastTerm.length * sum;
    }

});

module.exports = function(options) {return new IndexOfFS(options);};
},{"./FSModule":4,"prime":10,"prime/array/forEach":9}],6:[function(require,module,exports){
"use strict";

var prime = require('prime');
var FSModule = require('./FSModule');
var arr = {
    'forEach': require('prime/array/forEach')
};
var lev = require('levenshtein');

var LevenshteinFS = prime({

    inherits: FSModule,

    name: 'LevenshteinFS',
    options: {
        'maxDistanceTolerance': 3
    },

    search: function (term, haystack) {
        var that = this;

        this.lastTerm = term;
        this.lastHaystack = haystack;

        var needleWords = term.split(' ');
        var haystackWords = haystack.split(' ');

        var matches = [];

        arr.forEach(haystackWords, function (haystackWord) {
            var best = that.options.maxDistanceTolerance + 1;
            arr.forEach(needleWords, function (needleWord) {
                var score = lev(needleWord, haystackWord);

                if (score < best) {
                    best = score;
                }
            });
            matches.push(best);
        });

        this.lastResults = matches;

        return this;
    },

    getPoints: function () {
        var haystackWords = this.lastHaystack.split(' ');

        var combinedScore = this.lastResults.reduce(function (p, c) {
            return p + c;
        });

        var points = 50 / haystackWords.length * this.lastResults.length;
        points += 50 / (haystackWords.length * this.options.maxDistanceTolerance) * (haystackWords.length * this.options.maxDistanceTolerance - combinedScore);

        return points;
    }

});

module.exports = function (options) {return new LevenshteinFS(options); };


},{"./FSModule":4,"levenshtein":8,"prime":10,"prime/array/forEach":9}],7:[function(require,module,exports){
"Use Strict";

var prime = require('prime');
var FSModule = require('./FSModule');
var number = {
	'limit': require('prime/number/limit')
};

var WordCountFS = prime({

    inherits: FSModule,

    name: 'WordCountFS',
    options: {
        'maxWordTolerance': 3
    },

    search: function(searchTerm, haystack) {
        this.lastTerm = searchTerm;
        this.lastHaystack = haystack;

        return this;
    },

    getPoints: function() {
        var needleWords = this.lastTerm.split(' ');
        var haystackWords = this.lastHaystack.split(' ');

        return 100 / this.options.maxWordTolerance * (this.options.maxWordTolerance - number.limit(Math.abs(haystackWords.length - needleWords.length), 0, this.options.maxWordTolerance));
    }

});

module.exports = function(options) {return new WordCountFS(options);};
},{"./FSModule":4,"prime":10,"prime/number/limit":11}],8:[function(require,module,exports){
(function(root, factory){
  if (typeof define == 'function' && typeof define.amd == 'object' && define.amd) {
    define(function(){
      return factory(root);
    });
  } else if (typeof module == 'object' && module && module.exports) {
    module.exports = factory(root);
  } else {
    root.Levenshtein = factory(root);
  }
}(this, function(root){

  function forEach( array, fn ) { var i, length
    i = -1
    length = array.length
    while ( ++i < length )
      fn( array[ i ], i, array )
  }

  function map( array, fn ) { var result
    result = Array( array.length )
    forEach( array, function ( val, i, array ) {
      result[i] = fn( val, i, array )
    })
    return result
  }

  function reduce( array, fn, accumulator ) {
    forEach( array, function( val, i, array ) {
      accumulator = fn( val, i, array )
    })
    return accumulator
  }

  // Levenshtein distance
  function Levenshtein( str_m, str_n ) { var previous, current, matrix
    // Constructor
    matrix = this._matrix = []

    // Sanity checks
    if ( str_m == str_n )
      return this.distance = 0
    else if ( str_m == '' )
      return this.distance = str_n.length
    else if ( str_n == '' )
      return this.distance = str_m.length
    else {
      // Danger Will Robinson
      previous = [ 0 ]
      forEach( str_m, function( v, i ) { i++, previous[ i ] = i } )

      matrix[0] = previous
      forEach( str_n, function( n_val, n_idx ) {
        current = [ ++n_idx ]
        forEach( str_m, function( m_val, m_idx ) {
          m_idx++
          if ( str_m.charAt( m_idx - 1 ) == str_n.charAt( n_idx - 1 ) )
            current[ m_idx ] = previous[ m_idx - 1 ]
          else
            current[ m_idx ] = Math.min
              ( previous[ m_idx ]     + 1   // Deletion
              , current[  m_idx - 1 ] + 1   // Insertion
              , previous[ m_idx - 1 ] + 1   // Subtraction
              )
        })
        previous = current
        matrix[ matrix.length ] = previous
      })

      return this.distance = current[ current.length - 1 ]
    }
  }

  Levenshtein.prototype.toString = Levenshtein.prototype.inspect = function inspect ( no_print ) { var matrix, max, buff, sep, rows
    matrix = this.getMatrix()
    max = reduce( matrix,function( m, o ) {
      return Math.max( m, reduce( o, Math.max, 0 ) )
    }, 0 )
    buff = Array( ( max + '' ).length ).join( ' ' )

    sep = []
    while ( sep.length < (matrix[0] && matrix[0].length || 0) )
      sep[ sep.length ] = Array( buff.length + 1 ).join( '-' )
    sep = sep.join( '-+' ) + '-'

    rows = map( matrix, function( row ) { var cells
      cells = map( row, function( cell ) {
        return ( buff + cell ).slice( - buff.length )
      })
      return cells.join( ' |' ) + ' '
    })

    return rows.join( "\n" + sep + "\n" )
  }

  Levenshtein.prototype.getMatrix = function () {
    return this._matrix.slice()
  }

  Levenshtein.prototype.valueOf = function() {
    return this.distance
  }

  return Levenshtein

}));

},{}],9:[function(require,module,exports){
/*
array:forEach
*/"use strict"

var forEach = function(self, method, context){
    for (var i = 0, l = self.length >>> 0; i < l; i++){
        if (method.call(context, self[i], i, self) === false) break
    }
    return self
}

module.exports = forEach

},{}],10:[function(require,module,exports){
/*
prime
 - prototypal inheritance
*/"use strict"

var hasOwn = require("./object/hasOwn"),
    forIn  = require("./object/forIn"),
    mixIn  = require("./object/mixIn"),
    filter = require("./object/filter"),
    create = require("./object/create"),
    type   = require("./type")

var defineProperty           = Object.defineProperty,
    getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor

try {
    defineProperty({}, "~", {})
    getOwnPropertyDescriptor({}, "~")
} catch (e){
    defineProperty = null
    getOwnPropertyDescriptor = null
}

var define = function(value, key, from){
    defineProperty(this, key, getOwnPropertyDescriptor(from, key) || {
        writable: true,
        enumerable: true,
        configurable: true,
        value: value
    })
}

var copy = function(value, key){
    this[key] = value
}

var implement = function(proto){
    forIn(proto, defineProperty ? define : copy, this.prototype)
    return this
}

var verbs = /^constructor|inherits|mixin$/

var prime = function(proto){

    if (type(proto) === "function") proto = {constructor: proto}

    var superprime = proto.inherits

    // if our nice proto object has no own constructor property
    // then we proceed using a ghosting constructor that all it does is
    // call the parent's constructor if it has a superprime, else an empty constructor
    // proto.constructor becomes the effective constructor
    var constructor = (hasOwn(proto, "constructor")) ? proto.constructor : (superprime) ? function(){
        return superprime.apply(this, arguments)
    } : function(){}

    if (superprime){

        mixIn(constructor, superprime)

        var superproto = superprime.prototype
        // inherit from superprime
        var cproto = constructor.prototype = create(superproto)

        // setting constructor.parent to superprime.prototype
        // because it's the shortest possible absolute reference
        constructor.parent = superproto
        cproto.constructor = constructor
    }

    if (!constructor.implement) constructor.implement = implement

    var mixins = proto.mixin
    if (mixins){
        if (type(mixins) !== "array") mixins = [mixins]
        for (var i = 0; i < mixins.length; i++) constructor.implement(create(mixins[i].prototype))
    }

    // implement proto and return constructor
    return constructor.implement(filter(proto, function(value, key){
        return !key.match(verbs)
    }))

}

module.exports = prime

},{"./object/create":12,"./object/filter":13,"./object/forIn":14,"./object/hasOwn":16,"./object/mixIn":17,"./type":19}],11:[function(require,module,exports){
/*
number:limit
*/"use strict"

var limit = function(self, min, max){
    return Math.min(max, Math.max(min, self))
}

module.exports = limit

},{}],12:[function(require,module,exports){
/*
object:create
*/"use strict"

var create = function(self){
    var constructor = function(){}
    constructor.prototype = self
    return new constructor
}

module.exports = create

},{}],13:[function(require,module,exports){
/*
object:filter
*/"use strict"

var forIn = require("./forIn")

var filter = function(self, method, context){
    var results = {}
    forIn(self, function(value, key){
        if (method.call(context, value, key, self)) results[key] = value
    })
    return results
}

module.exports = filter

},{"./forIn":14}],14:[function(require,module,exports){
/*
object:forIn
*/"use strict"

var has = require("./hasOwn")

var forIn = function(self, method, context){
    for (var key in self) if (method.call(context, self[key], key, self) === false) break
    return self
}

if (!({valueOf: 0}).propertyIsEnumerable("valueOf")){ // fix for stupid IE enumeration bug

    var buggy = "constructor,toString,valueOf,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString".split(",")
    var proto = Object.prototype

    forIn = function(self, method, context){
        for (var key in self) if (method.call(context, self[key], key, self) === false) return self
        for (var i = 0; key = buggy[i]; i++){
            var value = self[key]
            if ((value !== proto[key] || has(self, key)) && method.call(context, value, key, self) === false) break
        }
        return self
    }

}

module.exports = forIn

},{"./hasOwn":16}],15:[function(require,module,exports){
/*
object:forOwn
*/"use strict"

var forIn  = require("./forIn"),
    hasOwn = require("./hasOwn")

var forOwn = function(self, method, context){
    forIn(self, function(value, key){
        if (hasOwn(self, key)) return method.call(context, value, key, self)
    })
    return self
}

module.exports = forOwn

},{"./forIn":14,"./hasOwn":16}],16:[function(require,module,exports){
/*
object:hasOwn
*/"use strict"

var hasOwnProperty = Object.hasOwnProperty

var hasOwn = function(self, key){
    return hasOwnProperty.call(self, key)
}

module.exports = hasOwn

},{}],17:[function(require,module,exports){
/*
object:mixIn
*/"use strict"

var forOwn = require("./forOwn")

var copy = function(value, key){
    this[key] = value
}

var mixIn = function(self){
    for (var i = 1, l = arguments.length; i < l; i++) forOwn(arguments[i], copy, self)
    return self
}

module.exports = mixIn

},{"./forOwn":15}],18:[function(require,module,exports){
/*
string:trim
*/"use strict"

var trim = function(self){
    return (self + "").replace(/^\s+|\s+$/g, "")
}

module.exports = trim

},{}],19:[function(require,module,exports){
/*
type
*/"use strict"

var toString = Object.prototype.toString,
    types = /number|object|array|string|function|date|regexp|boolean/

var type = function(object){
    if (object == null) return "null"
    var string = toString.call(object).slice(8, -1).toLowerCase()
    if (string === "number" && isNaN(object)) return "null"
    if (types.test(string)) return string
    return "object"
}

module.exports = type

},{}]},{},[1]);
