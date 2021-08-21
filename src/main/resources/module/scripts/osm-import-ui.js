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

Refine.OSMImportUI = function (controller) {
    this._controller = controller;
}

Refine.OSMImportUI.prototype.attachUI = function (body) {
    var self = this;
    self._body = body;

    self._body.html(DOM.loadHTML("osm-extractor", "scripts/osm-import-form.html"));
    self._elmts = DOM.bind(this._body);

    self._elmts.enterRawQuery.html($.i18n('osm-extractor/enter-raw-query'));

    self._elmts.nextButton.html($.i18n('osm-extractor/next->'));
    self._elmts.helpButton.html($.i18n('osm-extractor/help'));

    self._elmts.selectInstance.html($.i18n('osm-extractor/select-overpass-instance'));

    self._createAndPopulateSettingsTab();

    self._elmts.nextButton.click(function (_) {
        var overpassQuery = self._elmts.rawQueryInput[0] && self._elmts.rawQueryInput[0].value;
        var overpassAPI = $("#selectInstance").val();

        self._controller.startImportingData(overpassQuery, overpassAPI);
    });
}

Refine.OSMImportUI.prototype._createAndPopulateSettingsTab = function () {
    const overpassInstanceSelect = $('<select>').appendTo('body');
    overpassInstanceSelect.attr("id", "selectInstance");
    overpassInstanceSelect.css("font-weight", "normal");

    overpassInstanceSelect.appendTo($("#settings-select-overpass-instance")[0]);

    $.getJSON(
        "command/osm-extractor/get-overpass-instances",
        null,
        function (data) {
            if (data.instances && data.instances.length > 0) {
                var overpassInstances = data.instances;

                for (const instance of overpassInstances) {
                    overpassInstanceSelect.append($("<option>").val(instance).text(instance));
                }

                overpassInstanceSelect.val(overpassInstances[0]);
            } else {
                window.alert($.i18n("osm-extractor/overpass-instance-error"));
                console.error(data);
            }
        }
    );
};
Refine.OSMImportUI.prototype.focus = function () {
};

