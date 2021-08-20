/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
 * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */

var html = "text/html";
var encoding = "UTF-8";
var ClientSideResourceManager = Packages.com.google.refine.ClientSideResourceManager;


/*
 * Function invoked to initialize the extension.
 */

function registerCommands() {
    var RefineServlet = Packages.com.google.refine.RefineServlet;
    RefineServlet.registerCommand(module, "get-overpass-instances", new Packages.com.google.refine.osmextractor.commands.GetInstancesCommand());
    RefineServlet.registerCommand(module, "add-osm-data-to-project", new Packages.com.google.refine.osmextractor.commands.AddOSMDataToProjectCommand());
}

function registerControllers() {
    var IM = Packages.com.google.refine.importing.ImportingManager;

    IM.registerController(
        module,
        "osm-data-importing-controller",
        new Packages.com.google.refine.osmextractor.controllers.OSMDataImportingController()
    );
}

function init() {

  Packages.java.lang.System.out.println("Initializing OSM extension...");
  Packages.java.lang.System.out.println(module.getMountPoint());

  // Script files to inject into /project page
  ClientSideResourceManager.addPaths(
      "project/scripts",
      module,
      [
        "scripts/index.js",
        "scripts/dialog.js"
      ]
  );

  ClientSideResourceManager.addPaths(
      "index/scripts",
      module,
      [
          "scripts/index.js",
          "scripts/osm-import-ui.js"
      ]
  )

  // Style files to inject into /project page
  ClientSideResourceManager.addPaths(
      "project/styles",
      module,
      [
        "styles/dialog.less"
      ]
  );

    ClientSideResourceManager.addPaths(
        "index/styles",
        module,
        [
            "styles/osm-import-form.less"
        ]
    );

  registerCommands();
  registerControllers();
  //registerExporters();
}

/*
 * Function invoked to handle each request in a custom way.
 */
function process(path, request, response) {
  // Analyze path and handle this request yourself.

  if (path == "/" || path == "") {
    const numberA = 10;
    const numberB = 7;
    const context = {};
    // here's how to pass things into the .vt templates
    context.someList = ["Superior","Michigan","Huron","Erie","Ontario"];
    context.someString = "foofoo";
    context.someNumber1 = numberA;
    context.someNumber2 = numberB;


    send(request, response, "index.vt", context);
  }
}

function send(request, response, template, context) {
  butterfly.sendTextFromTemplate(request, response, context, template, encoding, html);
}
