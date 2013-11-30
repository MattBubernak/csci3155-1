/*
 * This is a wrapper that helps execute a jsy program in Node.js (Google's V8 Javascript interpreter).
 * 
 * Requires nodejs installed and the following command:
 *
 *   node jsy.js program.jsy
 *
 * where program.jsy is your jsy test program file.
 *
 */

const jsy = {
    print: console.log
};

const jsyprogram = (function () {
    const usage = function () {
	console.error("Usage: node jsy.js program.jsy");
	process.exit(1);
    };

    if (process.argv.length !== 3) {
	usage();
    }

    const fs = require('fs');
    const filename = process.argv[2];
    return fs.readFileSync(filename, 'utf8');
})();

const jsytojs = function (jsyprogram) {
    return jsyprogram;
};

const jsyvalue = eval(jsytojs(jsyprogram));
console.log(jsyvalue)
