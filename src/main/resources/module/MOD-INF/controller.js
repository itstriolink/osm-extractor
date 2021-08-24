/*
 * MIT License
 *
 * Copyright (c) 2021 Labian Gashi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

var html = "text/html";
var encoding = "UTF-8";
var ClientSideResourceManager = Packages.com.google.refine.ClientSideResourceManager;
var logger = Packages.org.slf4j.LoggerFactory.getLogger("osm-extractor");

/*
 * Function invoked to initialize the extension.
 */

function registerCommands() {
    logger.trace("Initializing OSM Extractor commands...");
    var RefineServlet = Packages.com.google.refine.RefineServlet;
    RefineServlet.registerCommand(module, "get-overpass-instances", new Packages.com.labiangashi.refine.osmextractor.commands.GetInstancesCommand());
    RefineServlet.registerCommand(module, "add-osm-data-to-project", new Packages.com.labiangashi.refine.osmextractor.commands.AddOSMDataToProjectCommand());
    logger.trace("Finished initializing OSM Extractor commands.");
}

function registerControllers() {
    var IM = Packages.com.google.refine.importing.ImportingManager;

    IM.registerController(
        module,
        "osm-data-importing-controller",
        new Packages.com.labiangashi.refine.osmextractor.controllers.OSMDataImportingController()
    );
}

function init() {
    logger.trace("Initializing OSM Extractor extension...");
    logger.trace(module.getMountPoint());

    ClientSideResourceManager.addPaths(
        "index/scripts",
        module,
        [
            "scripts/index.js",
            "scripts/osm-import-ui.js"
        ]
    )

    ClientSideResourceManager.addPaths(
        "index/styles",
        module,
        [
            "styles/theme.less",
            "styles/dialog.less",
            "styles/osm-import-form.less"
        ]
    );

    registerCommands();
    registerControllers();
}

function process(path, request, response) {

    if (path == "/" || path == "") {
        const numberA = 10;
        const numberB = 7;
        const context = {};

        context.someList = ["Superior", "Michigan", "Huron", "Erie", "Ontario"];
        context.someString = "foofoo";
        context.someNumber1 = numberA;
        context.someNumber2 = numberB;


        send(request, response, "index.vt", context);
    }
}

function send(request, response, template, context) {
    butterfly.sendTextFromTemplate(request, response, context, template, encoding, html);
}
